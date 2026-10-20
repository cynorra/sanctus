"""Adds Turkish prayers (scripts/prayers_tr_*.py) to assets/seed/prayers.json.

Each Turkish row copies category and tradition tags from its English row. Rerunnable by Turkish name.
    python scripts/add-prayers-tr.py
"""
import importlib
import json
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
PRAYERS = HERE.parent / "app" / "src" / "main" / "assets" / "seed" / "prayers.json"

rows = json.loads(PRAYERS.read_text(encoding="utf-8"))
en = {r["name"]: r for r in rows if r["language"] == "en"}
have = {r["name"] for r in rows if r["language"] == "tr"}
added, unmatched, translated = 0, [], set()
for module in ("prayers_tr_1", "prayers_tr_2", "prayers_tr_3"):
    for en_name, tr_name, content in importlib.import_module(module).DATA:
        src = en.get(en_name)
        if src is None:
            unmatched.append(en_name)
            continue
        translated.add(en_name)
        if tr_name in have:
            continue
        rows.append({"name": tr_name, "category": src["category"], "language": "tr",
                     "content": content, "tradition": src["tradition"]})
        have.add(tr_name)
        added += 1
body = ",\r\n".join("  " + json.dumps(r, ensure_ascii=False, separators=(",", ":")) for r in rows)
PRAYERS.write_bytes(("[\r\n" + body + "\r\n]\r\n").encode("utf-8"))
print("added", added, "| unmatched:", unmatched, "| tr total:", sum(1 for r in rows if r["language"] == "tr"))
