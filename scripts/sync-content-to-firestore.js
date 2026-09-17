#!/usr/bin/env node
// Syncs app/src/main/assets/seed/*.json into Firestore so the app can fetch prayer/saint
// content without an app-store release. Idempotent: re-running with unchanged content just
// re-writes the same documents.
//
// Auth: expects Google Application Default Credentials to already be set up in the
// environment — either `gcloud auth application-default login` locally, or (in CI)
// google-github-actions/auth with a service account key. This script only ever needs
// Firestore write access (roles/datastore.user), nothing broader.
//
// Usage: node scripts/sync-content-to-firestore.js [--version <string>]
// Version defaults to the short git SHA of HEAD so the app can detect "content changed"
// without us tracking a counter anywhere.

const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

const PROJECT_ID = process.env.FIREBASE_PROJECT_ID || 'sanctus-b5259';
const ASSETS_DIR = path.join(__dirname, '..', 'app', 'src', 'main', 'assets', 'seed');
const BASE = `https://firestore.googleapis.com/v1/projects/${PROJECT_ID}/databases/(default)/documents`;

function getAccessToken() {
  // Works for both a locally-run `gcloud auth application-default login` session and a
  // service-account key activated via `gcloud auth activate-service-account` in CI.
  return execSync('gcloud auth print-access-token').toString().trim();
}

function slugify(s) {
  return String(s)
    .toLowerCase()
    .normalize('NFKD').replace(/[̀-ͯ]/g, '')
    .replace(/[^a-z0-9]+/g, '-')
    .replace(/^-+|-+$/g, '')
    .slice(0, 60) || 'x';
}

function toFirestoreValue(v) {
  if (v === null || v === undefined) return { nullValue: null };
  if (typeof v === 'boolean') return { booleanValue: v };
  if (typeof v === 'number') return { integerValue: String(Math.trunc(v)) };
  return { stringValue: String(v) };
}

function toFields(obj) {
  const fields = {};
  for (const [k, v] of Object.entries(obj)) fields[k] = toFirestoreValue(v);
  return fields;
}

async function commitBatch(token, writes) {
  const res = await fetch(`${BASE}:commit`, {
    method: 'POST',
    headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' },
    body: JSON.stringify({ writes }),
  });
  if (!res.ok) throw new Error(`Commit failed: ${res.status} ${await res.text()}`);
  return res.json();
}

async function uploadCollection(token, collection, docs, idFn) {
  const seenIds = new Set();
  const writes = docs.map(doc => {
    let id = idFn(doc);
    while (seenIds.has(id)) id += '-x';
    seenIds.add(id);
    return {
      update: {
        name: `projects/${PROJECT_ID}/databases/(default)/documents/${collection}/${id}`,
        fields: toFields(doc),
      },
    };
  });

  const CHUNK = 400;
  for (let i = 0; i < writes.length; i += CHUNK) {
    await commitBatch(token, writes.slice(i, i + CHUNK));
    console.log(`  ${collection}: ${Math.min(i + CHUNK, writes.length)}/${writes.length}`);
  }
}

async function setMetaVersion(token, key, version) {
  await commitBatch(token, [{
    update: {
      name: `projects/${PROJECT_ID}/databases/(default)/documents/content_meta/${key}`,
      fields: toFields({ version, updatedAt: new Date().toISOString() }),
    },
  }]);
}

function resolveVersion() {
  const arg = process.argv.indexOf('--version');
  if (arg !== -1 && process.argv[arg + 1]) return process.argv[arg + 1];
  try {
    return execSync('git rev-parse --short HEAD', { cwd: __dirname }).toString().trim();
  } catch {
    return String(Date.now());
  }
}

async function main() {
  const token = getAccessToken();
  const version = resolveVersion();

  const prayers = JSON.parse(fs.readFileSync(path.join(ASSETS_DIR, 'prayers.json'), 'utf8'));
  const saints = JSON.parse(fs.readFileSync(path.join(ASSETS_DIR, 'saints.json'), 'utf8'));

  console.log(`Syncing content to Firestore project ${PROJECT_ID}, version=${version}`);

  console.log(`Uploading ${prayers.length} prayers...`);
  await uploadCollection(token, 'prayers', prayers, p => `${p.language}_${slugify(p.name)}_${slugify(p.category)}`);

  console.log(`Uploading ${saints.length} saints...`);
  await uploadCollection(token, 'saints', saints, s => `${s.language}_${slugify(s.feastDate)}_${slugify(s.name)}`);

  console.log('Setting content_meta versions...');
  await setMetaVersion(token, 'prayers', version);
  await setMetaVersion(token, 'saints', version);

  console.log('Done.');
}

main().catch(e => { console.error(e); process.exit(1); });
