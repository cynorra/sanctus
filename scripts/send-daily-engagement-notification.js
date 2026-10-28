#!/usr/bin/env node
// Sends one daily push notification to the "engagement" FCM topic: a short inviting title
// (28 titles rotating by day of year, not the same text every day) + today's rotating featured prayer
// as the body — the same "Prayer of the Day" pick logic the app itself uses on the dashboard
// (day-of-year modulo prayer count), so the notification and the in-app card always agree.
//
// Auth: Google Application Default Credentials (gcloud user login locally, or
// google-github-actions/auth with a service account in CI) — needs the
// https://www.googleapis.com/auth/firebase.messaging scope, which a normal
// `gcloud auth print-access-token` / cloud-platform-scoped service account already covers.

const { execSync } = require('child_process');

const PROJECT_ID = process.env.FIREBASE_PROJECT_ID || 'sanctus-b5259';
const TOPIC = 'engagement';

const TITLES = [
  'Take a moment to pray today 🙏',
  'Your prayer of the day is waiting',
  'Start your day in peace with Sanctus',
  'Give God one minute today',
  "Have you read today's prayer?",
  'A new day, a new moment for prayer',
  'Open your heart to Him today',
  'A quiet minute with God 🕊️',
  'Pause. Breathe. Pray.',
  "Today's prayer is ready for you",
  'Bring this day to God',
  'Let prayer lead your morning',
  'One prayer, one peaceful moment',
  'God is listening - talk to Him today',
  'A small prayer for a big day',
  'Start with gratitude today 🙏',
  'Make room for prayer today',
  'Your daily bread for the soul',
  'Slow down and pray with us',
  'A prayer to carry through your day',
  'Rest your heart in prayer',
  'Peace begins with a prayer',
  'Walk with God today',
  'Lift your worries to Him',
  'Before the day gets busy…',
  'A moment of stillness awaits ✝️',
  'Pray with the Church today',
  'Close the day with a prayer 🌙',
];

function getAccessToken() {
  return execSync('gcloud auth print-access-token').toString().trim();
}

function dayOfYearUTC(date) {
  const start = Date.UTC(date.getUTCFullYear(), 0, 1);
  const diff = Date.UTC(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate()) - start;
  return Math.floor(diff / 86400000) + 1;
}

async function fetchEnglishPrayers(token) {
  const url = `https://firestore.googleapis.com/v1/projects/${PROJECT_ID}/databases/(default)/documents:runQuery`;
  const body = {
    structuredQuery: {
      from: [{ collectionId: 'prayers' }],
      where: {
        fieldFilter: {
          field: { fieldPath: 'language' },
          op: 'EQUAL',
          value: { stringValue: 'en' },
        },
      },
    },
  };
  const res = await fetch(url, {
    method: 'POST',
    headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  });
  if (!res.ok) throw new Error(`Firestore query failed: ${res.status} ${await res.text()}`);
  const rows = await res.json();
  return rows
    .filter(r => r.document)
    .map(r => ({
      name: r.document.fields.name?.stringValue || '',
      content: r.document.fields.content?.stringValue || '',
    }))
    .filter(p => p.name && p.content)
    .sort((a, b) => a.name.localeCompare(b.name)); // stable order, matches the app's ORDER BY name
}

function excerpt(text, maxChars) {
  const collapsed = text.replace(/\n/g, ' ').trim();
  return collapsed.length <= maxChars ? collapsed : collapsed.slice(0, maxChars).trim() + '…';
}

// Data-only payload (no top-level "notification" field) on purpose: FCM only hands a
// notification-payload message to the app's own code when the app is in the foreground — in the
// background/killed state (the common case for a daily reminder), the OS displays it generically
// and drops any custom tap deep-link. A data-only payload always reaches
// SanctusFirebaseMessagingService.onMessageReceived(), which builds the notification itself and
// can attach the prayer_name/language so tapping it opens that exact prayer.
async function sendPush(token, title, body, prayerName, language) {
  const url = `https://fcm.googleapis.com/v1/projects/${PROJECT_ID}/messages:send`;
  const res = await fetch(url, {
    method: 'POST',
    headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' },
    body: JSON.stringify({
      message: {
        topic: TOPIC,
        data: { title, body, prayer_name: prayerName, language },
        android: { priority: 'high' },
      },
    }),
  });
  if (!res.ok) throw new Error(`FCM send failed: ${res.status} ${await res.text()}`);
  return res.json();
}

async function main() {
  const token = getAccessToken();
  const now = new Date();

  const prayers = await fetchEnglishPrayers(token);
  if (prayers.length === 0) {
    console.log('No English prayers found in Firestore — skipping send.');
    return;
  }
  const doy = dayOfYearUTC(now);
  const prayer = prayers[doy % prayers.length];

  const title = TITLES[doy % TITLES.length];
  // Body format rotates too (deep link data below is unaffected by how the text is phrased).
  const bodyFormats = [
    () => `${prayer.name} — ${excerpt(prayer.content, 100)}`,
    () => `“${excerpt(prayer.content, 110)}” — ${prayer.name}`,
    () => `Today's prayer: ${prayer.name}`,
    () => `${prayer.name}: ${excerpt(prayer.content, 80)}`,
  ];
  const body = bodyFormats[Math.floor(doy / TITLES.length + doy) % bodyFormats.length]();

  console.log(`Sending to topic "${TOPIC}": "${title}" / "${body}" (deep link: ${prayer.name}, en)`);
  const result = await sendPush(token, title, body, prayer.name, 'en');
  console.log('Sent:', result.name || result);
}

main().catch(e => { console.error(e); process.exit(1); });
