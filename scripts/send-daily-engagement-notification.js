#!/usr/bin/env node
// Sends one daily push notification to the "engagement" FCM topic. What it sends, in priority order:
//   1. a liturgical-calendar message on big days (Ash Wednesday, Holy Week, Easter, Pentecost, Advent
//      Sundays, Christmas, major feasts) - Western (Gregorian) dates; Easter is computed;
//   2. the saint of the day on Wednesdays and Saturdays (when Firestore has a saint for that feast date);
//   3. otherwise today's rotating featured prayer (same day-of-year modulo pick as the app's dashboard
//      "Prayer of the Day" card, so the notification and the in-app card agree), with one of 28 rotating titles.
// Flags for local testing: --dry-run (print, don't send) and --date YYYY-MM-DD (pretend it is that day).
//
// Auth: Google Application Default Credentials (gcloud user login locally, or
// google-github-actions/auth with a service account in CI) - needs the
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
async function sendPush(token, title, body, link) {
  const url = `https://fcm.googleapis.com/v1/projects/${PROJECT_ID}/messages:send`;
  const res = await fetch(url, {
    method: 'POST',
    headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' },
    body: JSON.stringify({
      message: {
        topic: TOPIC,
        // link = {prayer_name, language} or {saint_name, saint_feast_date, language} or {}. App versions
        // from before saint links ignore the saint keys and just open the default screen.
        data: { title, body, ...link },
        android: { priority: 'high' },
      },
    }),
  });
  if (!res.ok) throw new Error(`FCM send failed: ${res.status} ${await res.text()}`);
  return res.json();
}

const MONTHS = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

// Saints are stored per tradition and this topic reaches every tradition, so pick the most broadly
// recognised one (see rank below); skip non-saint entries.
async function fetchSaintForDate(token, feastDate) {
  const url = `https://firestore.googleapis.com/v1/projects/${PROJECT_ID}/databases/(default)/documents:runQuery`;
  const eq = (field, v) => ({ fieldFilter: { field: { fieldPath: field }, op: 'EQUAL', value: { stringValue: v } } });
  const res = await fetch(url, {
    method: 'POST',
    headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' },
    body: JSON.stringify({ structuredQuery: {
      from: [{ collectionId: 'saints' }],
      where: { compositeFilter: { op: 'AND', filters: [eq('language', 'en'), eq('feastDate', feastDate)] } },
    } }),
  });
  if (!res.ok) throw new Error(`Firestore saints query failed: ${res.status} ${await res.text()}`);
  // The audience leans Catholic: shared-by-all first, then Catholic (also-shared before Catholic-only), then Orthodox, then the rest.
  const rank = t => (t === 'all' ? 0 : t.includes('catholic') ? (t.includes(',') ? 1 : 2) : t.includes('orthodox') ? 3 : 4);
  const cands = (await res.json())
    .filter(r => r.document)
    .map(r => ({
      name: r.document.fields.name?.stringValue || '',
      description: r.document.fields.description?.stringValue || '',
      tradition: r.document.fields.tradition?.stringValue || 'all',
    }))
    .filter(c => c.name && c.description && !/book of common prayer/i.test(c.name))
    .sort((a, b) => rank(a.tradition) - rank(b.tradition) || a.name.localeCompare(b.name));
  return cands[0] || null;
}

function firstSentence(text, maxChars) {
  const t = text.replace(/\s+/g, ' ').trim();
  // End at the first . ! ? followed by a capital letter, but not at abbreviations like "c." / "d." / "St.".
  const re = /[.!?](?=\s+[A-Z"(])/g;
  let m;
  while ((m = re.exec(t))) {
    if (/(^|[\s(])(c|ca|d|b|r|fl|St|Sts|Bl|Fr)$/.test(t.slice(0, m.index))) continue;
    return excerpt(t.slice(0, m.index + 1), maxChars);
  }
  return excerpt(t, maxChars);
}

const SAINT_TITLES = [
  'Saint of the day',
  'Meet today\'s saint',
  'Remembering a saint today',
  'Walk with the saints today',
  'A friend in heaven: today\'s saint',
];

// Western Easter (anonymous Gregorian algorithm), as a UTC date.
function easterUTC(y) {
  const a = y % 19, b = Math.floor(y / 100), c = y % 100, d = Math.floor(b / 4), e = b % 4;
  const f = Math.floor((b + 8) / 25), g = Math.floor((b - f + 1) / 3);
  const h = (19 * a + b - d - g + 15) % 30, i = Math.floor(c / 4), k = c % 4;
  const l = (32 + 2 * e + 2 * i - h - k) % 7, m = Math.floor((a + 11 * h + 22 * l) / 451);
  const month = Math.floor((h + l - 7 * m + 114) / 31), day = ((h + l - 7 * m + 114) % 31) + 1;
  return new Date(Date.UTC(y, month - 1, day));
}

const addDays = (d, n) => new Date(d.getTime() + n * 86400000);
const key = d => d.toISOString().slice(0, 10);

function liturgicalEvents(y) {
  const E = easterUTC(y), ev = {};
  const put = (d, title, body) => { ev[key(d)] = { title, body }; };
  const fixed = (m, d, title, body) => put(new Date(Date.UTC(y, m - 1, d)), title, body);

  put(addDays(E, -46), 'Ash Wednesday: Lent begins 🙏', 'Forty days of prayer, fasting and almsgiving. Begin with a prayer of repentance today.');
  ['First', 'Second', 'Third', 'Fourth'].forEach((w, i) =>
    put(addDays(E, -42 + 7 * i), `${w} Sunday of Lent`, 'Keep your Lenten journey steady — take a quiet minute of prayer today.'));
  put(addDays(E, -7), 'Palm Sunday', 'Holy Week begins. Walk with Jesus into Jerusalem in prayer today.');
  put(addDays(E, -3), 'Holy Thursday', 'The Lord\'s Supper. Spend a few quiet minutes with Him tonight.');
  put(addDays(E, -2), 'Good Friday', 'Be still at the foot of the Cross today.');
  put(addDays(E, -1), 'Holy Saturday', 'A day of waiting and silence before the joy of Easter.');
  put(E, 'He is risen! Happy Easter ✝️', 'Christ is risen, alleluia! Celebrate the day with a prayer of thanksgiving.');
  put(addDays(E, 7), 'Divine Mercy Sunday', 'Rest in God\'s mercy today. Jesus, I trust in You.');
  put(addDays(E, 39), 'Ascension of the Lord', 'Christ ascends in glory. Lift your heart to heaven in prayer today.');
  put(addDays(E, 49), 'Pentecost Sunday 🕊️', 'Come, Holy Spirit! Ask for His gifts in prayer today.');

  // First Sunday of Advent: the Sunday between Nov 27 and Dec 3.
  const nov27 = new Date(Date.UTC(y, 10, 27));
  const adv1 = addDays(nov27, (7 - nov27.getUTCDay()) % 7);
  ['First', 'Second', 'Third', 'Fourth'].forEach((w, i) =>
    put(addDays(adv1, 7 * i), `${w} Sunday of Advent`,
      i === 0 ? 'A new Church year begins. Prepare your heart for Christmas in prayer.' : 'Keep watching and waiting. Take a quiet minute of prayer today.'));

  fixed(1, 1, 'Mary, Mother of God', 'Begin the new year with Mary. Entrust the year ahead to God in prayer.');
  fixed(1, 6, 'Epiphany of the Lord', 'The Magi found the newborn King. Seek Him in prayer today.');
  fixed(3, 25, 'The Annunciation', 'Mary said yes. Pray the Angelus today.');
  fixed(8, 15, 'The Assumption of Mary', 'Mary is taken up into heaven. Pray a Hail Mary today.');
  fixed(11, 1, 'All Saints\' Day', 'Celebrate the saints in glory — and the call to holiness for us all.');
  fixed(11, 2, 'All Souls\' Day', 'Remember the faithful departed in prayer today.');
  fixed(12, 8, 'The Immaculate Conception', 'Honor Mary, full of grace, with a prayer today.');
  fixed(12, 24, 'Christmas Eve', 'Tonight we wait with joy. Spend a quiet moment in prayer.');
  fixed(12, 25, 'Merry Christmas ✝️', 'Glory to God in the highest! A savior is born. Celebrate with a prayer of thanksgiving.');
  return ev;
}

async function main() {
  const argv = process.argv.slice(2);
  const dry = argv.includes('--dry-run');
  const di = argv.indexOf('--date');
  const now = di !== -1 ? new Date(argv[di + 1] + 'T08:00:00Z') : new Date();
  const doy = dayOfYearUTC(now);
  const dow = now.getUTCDay();

  let title, body, link;

  const event = liturgicalEvents(now.getUTCFullYear())[key(now)];
  if (event) {
    ({ title, body } = event);
    link = {};
  }

  const token = getAccessToken();

  if (!event && (dow === 3 || dow === 6)) { // Wednesday / Saturday: saint of the day
    const feast = `${MONTHS[now.getUTCMonth()]} ${now.getUTCDate()}`;
    const saint = await fetchSaintForDate(token, feast);
    if (saint) {
      title = SAINT_TITLES[doy % SAINT_TITLES.length];
      body = `${saint.name} — ${firstSentence(saint.description, 110)}`;
      link = { saint_name: saint.name, saint_feast_date: feast, language: 'en' };
    }
  }

  if (!title) {
    const prayers = await fetchEnglishPrayers(token);
    if (prayers.length === 0) {
      console.log('No English prayers found in Firestore — skipping send.');
      return;
    }
    const prayer = prayers[doy % prayers.length];
    title = TITLES[doy % TITLES.length];
    // Body format rotates too (deep link data is unaffected by how the text is phrased).
    const bodyFormats = [
      () => `${prayer.name} — ${excerpt(prayer.content, 100)}`,
      () => `“${excerpt(prayer.content, 110)}” — ${prayer.name}`,
      () => `Today's prayer: ${prayer.name}`,
      () => `${prayer.name}: ${excerpt(prayer.content, 80)}`,
    ];
    body = bodyFormats[Math.floor(doy / TITLES.length + doy) % bodyFormats.length]();
    link = { prayer_name: prayer.name, language: 'en' };
  }

  console.log(`${dry ? '[dry-run] Would send' : 'Sending'} to topic "${TOPIC}" for ${key(now)}: "${title}" / "${body}" (link: ${JSON.stringify(link)})`);
  if (dry) return;
  const result = await sendPush(token, title, body, link);
  console.log('Sent:', result.name || result);
}

main().catch(e => { console.error(e); process.exit(1); });
