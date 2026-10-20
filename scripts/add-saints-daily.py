"""Fills the English saint calendar so every day of the year has a saint.

Rerunnable: rows whose (feastDate, name, language) already exist are left alone.
    python scripts/add-saints-daily.py
"""
import datetime
import json
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
import importlib  # noqa: E402
PARTS = [importlib.import_module(m) for m in (
    "saints_daily_en_1", "saints_daily_en_2", "saints_daily_en_3",
    "saints_daily_en_orth_1", "saints_daily_en_orth_2",
    "saints_daily_en_prot_1", "saints_daily_en_prot_2", "saints_daily_en_fill")]

SAINTS = HERE.parent / "app" / "src" / "main" / "assets" / "seed" / "saints.json"

rows = json.loads(SAINTS.read_text(encoding="utf-8"))
index = {(r["feastDate"], r["name"], r["language"]): r for r in rows}
added = 0
for mod in PARTS:
    for date, name, tradition, desc, patron in mod.DATA:
        key = (date, name, "en")
        if key in index:
            # Same saint already listed: only widen the tradition tags.
            row = index[key]
            tags = row["tradition"].split(",")
            if row["tradition"] != "all":
                for tag in tradition.split(","):
                    if tag not in tags:
                        tags.append(tag)
                row["tradition"] = "all" if tradition == "all" else ",".join(tags)
            continue
        row = {"name": name, "feastDate": date, "description": desc,
               "patronOf": patron, "language": "en", "tradition": tradition}
        rows.append(row)
        index[key] = row
        added += 1

# Same layout as the hand-kept file: one compact object per line, CRLF, so diffs stay readable.
body = ",\r\n".join("  " + json.dumps(r, ensure_ascii=False) for r in rows)
SAINTS.write_bytes(("[\r\n" + body + "\r\n]\r\n").encode("utf-8"))
days = {r["feastDate"] for r in rows if r["language"] == "en"}
every = [f"{(datetime.date(2024, 1, 1) + datetime.timedelta(i)).strftime('%B')} {(datetime.date(2024, 1, 1) + datetime.timedelta(i)).day}" for i in range(366)]
print(f"added {added}; en days covered {len([d for d in every if d in days])}/366; missing: {[d for d in every if d not in days]}")
