"""Adds scripts/prayers_en_extra.py to assets/seed/prayers.json (rerunnable by language+name).

    python scripts/add-prayers-extra.py
"""
import json
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
import prayers_en_extra  # noqa: E402

PRAYERS = HERE.parent / "app" / "src" / "main" / "assets" / "seed" / "prayers.json"
rows = json.loads(PRAYERS.read_text(encoding="utf-8"))
have = {(r["language"], r["name"]) for r in rows}
added = 0
for name, category, tradition, content in prayers_en_extra.DATA:
    if ("en", name) in have:
        continue
    rows.append({"name": name, "category": category, "language": "en", "content": content, "tradition": tradition})
    added += 1
body = ",\r\n".join("  " + json.dumps(r, ensure_ascii=False, separators=(",", ":")) for r in rows)
PRAYERS.write_bytes(("[\r\n" + body + "\r\n]\r\n").encode("utf-8"))
print("added", added, "total", len(rows))
