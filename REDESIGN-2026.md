# Sanctus 2026 redesign: branch notes

Everything here lives on `redesign-2026`. **Do not merge to `main` without reading "Before merging".**

## Getting a build to test

Every push to a non-`main` branch runs `.github/workflows/test-build.yml` (about 6 minutes) and uploads a
signed **release APK and AAB** as workflow artifacts (`sanctus-apk-N`, `sanctus-aab-N`).

```
gh run list --branch redesign-2026 --limit 3
gh run download <run-id> -n sanctus-apk-<run_number> -D out
adb install -r out/app-release.apk
```

The APK is signed with the upload key from the repo secrets. A copy installed from Google Play is signed
differently, so uninstall it once before the first install.

## What changed

- **Traditions.** `Tradition.java`, Room version 4 (`tradition` column on prayers and saints), the seed JSON
  `tradition` field, a picker in onboarding and in Settings, DAO filters. Rows are tagged `all` or a comma list
  of `catholic`, `orthodox`, `protestant`, `anglican`. The user's choice `general` shows everything.
- **Onboarding.** It used to open the dashboard with no navigation bar (the activity hosted the main nav graph).
  Now it is a real three-step flow and sets `onboarding_done`. People updating from an older version go through it
  once as well, so everyone makes a deliberate choice.
- **Design system.** Ink black, candlelight gold, one violet; glass cards; Inter and EB Garamond; outline icons;
  aurora backdrop; floating nav pill. Tokens live in `values/colors.xml`, `values/themes.xml`,
  `values/styles_components.xml`.
- **Motion.** `MotionKit` (staggered screen entrance, press scaling, shimmer, breathing glow), tab transitions in
  `MainActivity`, list entrance animations, the animated bead ring in `RosaryRingView`.
- **Content.** 30 King James Version passages (fetched from bible-api.com by `scripts/fetch-scripture.py`, never
  typed by hand), reformers and hymn writers, and the roughly 85 prayers and saints that only existed in the live
  Firestore (imported by `scripts/add-content.py`).
- **Fixes.** Rosary text language, dead strip above the nav bar, FAB overlap, localized category names and feast
  dates, missing Turkish strings.

## Added in the second pass (v1.1.0, versionCode 14)

- **Tradition palettes.** Accent, glow and surface tints are theme attributes (`values/attrs_tradition.xml`,
  overlays in `values/themes_tradition.xml`): Catholic/general gold + violet, Orthodox gold + crimson,
  Protestant sky blue, Anglican emerald. `SanctusApplication` applies the overlay to every activity;
  changing the tradition in Settings recreates the activity. Widget layouts keep static colours.
- **Orthodox calendar.** `OrthodoxCalendar` (Julian Pascha, fixed feasts shifted by the Julian gap, Nativity fast
  and Great Lent seasons). The calendar screen, dashboard season chip and upcoming feasts use it for Orthodox.
- **Counter tab per tradition.** Rosary for Catholic/Anglican/general, the Jesus Prayer rope
  (`JesusPrayerFragment`, 100 knots, haptics) for Orthodox, none for Protestant.
- **Protestant content.** 9 hymns, Luther's morning/evening prayers and table graces, Heidelberg Q1,
  Westminster Q1, Wesley's Covenant Prayer. Hymn and Luther/Heidelberg texts were fetched from Wikisource
  (public domain, Army and Navy Hymnal 1920, Olney Hymns 1840, Concordia Triglotta 1921). The Westminster
  and Wesley texts were entered by hand.
- **Translations.** All 38 non-English/Turkish locales now have every string (verified by script), including
  Orthodox feast names and the Jesus Prayer strings. Machine-assisted drafts: have a native speaker skim the
  rare languages (am, ig, mg, yo, zu) before shouting about them.
- **Launcher icon.** New vector adaptive icon (flame in a halo), themed monochrome layer, regenerated legacy PNGs,
  512 px Play icon in `play_console_assets/icon_new/icon_512.png`.
- Predictive back enabled in the manifest.

## Before merging to `main`

1. A push to `main` that touches `app/src/main/assets/seed/**` runs `sync-content.yml`, which writes the seed to
   the **live Firestore**. That adds the `tradition` field to documents and the new passages. Older app versions
   ignore the new field, but they will list the new Scripture rows under the "Devotional" filter. Check this is fine.
2. A tag `v*` runs `release-build.yml` and produces the Play Store AAB. Bump `versionCode` and `versionName` first.
3. Play Store screenshots in `play_console_assets/` show an older four-tab layout and must be regenerated.
4. `metadata/whatsnew-v14.txt` still has to be written (limit 500 characters per locale).

## Known gaps

- Scripture is English (KJV) only; it is shown in every language.
- Hymn and prayer content is English only.
- Legacy PNG launcher icons (API 24-25) were rendered from the same artwork with PIL, not from the vector.

## Scripts

| Script | Purpose |
| --- | --- |
| `scripts/tag-traditions.py` | Tags untagged prayers and saints (`--force` retags everything) |
| `scripts/fetch-scripture.py <out.json>` | Downloads the KJV passages into a cache file |
| `scripts/add-content.py <scripture.json> <firestore_dump.json>` | Adds scripture, reformers and Firestore-only rows to the seed |
