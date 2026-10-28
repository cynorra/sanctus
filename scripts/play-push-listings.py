# -*- coding: utf-8 -*-
"""Push the 87-locale store listing CSV to Google Play via the Android Publisher API.

usage: python scripts/play-push-listings.py [--key <service-account.json>] [--csv sanctus_store_listing_full.csv] [--apply]
Without --apply it only stages the edit, validates it and deletes it (nothing goes live).
The service account must be invited in Play Console (Users and permissions) with permission to edit the store listing.
"""
import argparse, csv, io, sys
from google.oauth2 import service_account
from googleapiclient.discovery import build
from googleapiclient.errors import HttpError

PKG = 'com.sanctus.christian.prayer'
ap = argparse.ArgumentParser()
ap.add_argument('--key', help='service-account JSON; if omitted, uses the PLAY_ACCESS_TOKEN env var (e.g. from gcloud impersonation)')
ap.add_argument('--csv', default='sanctus_store_listing_full.csv')
ap.add_argument('--apply', action='store_true')
a = ap.parse_args()

rows = list(csv.reader(io.StringIO(open(a.csv, 'rb').read().decode('utf-8-sig'), newline='')))[1:]
if a.key:
    creds = service_account.Credentials.from_service_account_file(a.key, scopes=['https://www.googleapis.com/auth/androidpublisher'])
else:
    import os
    from google.oauth2.credentials import Credentials
    creds = Credentials(os.environ['PLAY_ACCESS_TOKEN'])
svc = build('androidpublisher', 'v3', credentials=creds, cache_discovery=False)
edits = svc.edits()
edit_id = edits.insert(packageName=PKG, body={}).execute()['id']
ok, failed = [], []
for lang, title, short, full in rows:
    try:
        edits.listings().update(packageName=PKG, editId=edit_id, language=lang,
                                body={'language': lang, 'title': title, 'shortDescription': short, 'fullDescription': full}).execute()
        ok.append(lang)
    except HttpError as e:
        failed.append((lang, e.reason if hasattr(e, 'reason') else str(e)))
print('staged', len(ok), 'failed', len(failed))
for l, r in failed: print('  FAIL', l, r)
if a.apply and ok:
    edits.commit(packageName=PKG, editId=edit_id).execute()
    print('COMMITTED edit', edit_id)
else:
    edits.delete(packageName=PKG, editId=edit_id).execute()
    print('dry run: edit discarded, nothing published')
