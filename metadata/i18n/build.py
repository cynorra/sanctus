# -*- coding: utf-8 -*-
"""Builds the full 87-locale Sanctus Play listing CSV.
Base = rows already translated in 'sanctus_store_listing (1).csv'; overlay = content_*.py modules.
Each content module defines L = {code: (title, short, intro, [(heading, body)] * 7, closing)}.
Output format matches the file the console accepted before: BOM, 'Language Code' header, CRLF, en-US first.
"""
import csv, glob, importlib.util, io, os, sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.abspath(os.path.join(HERE, '..', '..'))
BASE = os.path.join(ROOT, 'sanctus_store_listing (1).csv')
OUT = os.path.join(ROOT, 'sanctus_store_listing_full.csv')
ORDER = os.path.join(ROOT, 'metadata', 'play-store-listings-all-languages.csv')
EMOJI = ['✝️', '📖', '📿', '⏰', '🕊️', '📅', '🌍']
ALIAS = {'en-AU': 'en-US', 'en-CA': 'en-US', 'en-GB': 'en-US', 'en-IN': 'en-US', 'en-SG': 'en-US', 'en-ZA': 'en-US', 'fa-AE': 'fa', 'fa-AF': 'fa', 'fa-IR': 'fa', 'ms-MY': 'ms', 'es-419': 'es-ES', 'es-US': 'es-ES',
         'fr-CA': 'fr-FR', 'pt-PT': 'pt-BR', 'zh-HK': 'zh-TW'}

def read(p):
    t = open(p, 'rb').read().decode('utf-8-sig')
    return list(csv.reader(io.StringIO(t, newline='')))[1:]

def compose(intro, secs, closing):
    parts = [intro]
    for e, (h, b) in zip(EMOJI, secs):
        parts.append((e + ' ' if e else '') + h + '\n' + b)
    parts.append(closing)
    return '\n\n'.join(parts)

rows = {r[0]: r[1:] for r in read(BASE)}
new = {}
for f in sorted(glob.glob(os.path.join(HERE, 'content_*.py'))):
    spec = importlib.util.spec_from_file_location('c', f)
    m = importlib.util.module_from_spec(spec); spec.loader.exec_module(m)
    for code, (title, short, intro, secs, closing) in m.L.items():
        assert len(secs) == 7, code
        new[code] = [title, short, compose(intro, secs, closing)]
rows.update(new)
# competitor-driven tweaks (tweaks.py): T = {code: {'title':..., 'short':..., 'sub': [(old, new), ...]}}
tw = type('T', (), {'T': {}})
for code, t in tw.T.items():
    ti, sh, fu = rows[code]
    ti = t.get('title', ti); sh = t.get('short', sh)
    for old, nw in t.get('sub', []):
        assert old in fu, (code, old)
        fu = fu.replace(old, nw, 1)
    rows[code] = [ti, sh, fu]
for a, src in ALIAS.items():
    if src in new: rows[a] = rows[src]
for c in list(rows):
    if c.startswith('en-') and c != 'en-US': rows[c] = rows['en-US']

order = [r[0] for r in read(ORDER)]
assert sorted(order) == sorted(rows) and len(order) == 87
bad = 0
for c in order:
    t, s, f = rows[c]
    if len(t) > 30 or len(s) > 80 or len(f) > 4000 or not (t and s and f):
        print('LIMIT', c, len(t), len(s), len(f)); bad += 1
en = rows['en-US'][2]
todo = [c for c in order if c not in new and c not in ALIAS]
buf = io.StringIO(newline='')
w = csv.writer(buf, lineterminator='\r\n')
w.writerow(['Language Code', 'Title', 'Short Description', 'Full Description'])
for c in order: w.writerow([c] + rows[c])
open(OUT, 'wb').write(b'\xef\xbb\xbf' + buf.getvalue().encode('utf-8'))
print('written', OUT, 'rows', len(order), 'limit errors', bad)
print('not yet rewritten:', len(todo), todo)
