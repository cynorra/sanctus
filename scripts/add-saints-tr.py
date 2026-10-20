"""Adds Turkish saint entries (scripts/saints_tr_*.py) to assets/seed/saints.json.

Each Turkish row copies the tradition tags of its English row, so run this after
add-saints-daily.py and balance-saint-traditions.py. Rerunnable. Prints English entries
that still have no Turkish translation (`--missing` prints them in full).

    python scripts/add-saints-tr.py [--missing]
"""
import importlib
import json
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
SAINTS = HERE.parent / "app" / "src" / "main" / "assets" / "seed" / "saints.json"
MODULES = ["saints_tr_1", "saints_tr_2", "saints_tr_3", "saints_tr_orth_1", "saints_tr_orth_2",
           "saints_tr_prot_1", "saints_tr_prot_2", "saints_tr_old_1", "saints_tr_old_2", "saints_tr_old_3"]

rows = json.loads(SAINTS.read_text(encoding="utf-8"))
en = {(r["feastDate"], r["name"]): r for r in rows if r["language"] == "en"}
tr = {(r["feastDate"], r["name"]): r for r in rows if r["language"] == "tr"}
translated_en = set()
unmatched = []
for name in MODULES:
    try:
        mod = importlib.import_module(name)
    except ModuleNotFoundError:
        continue
    for date, en_name, tr_name, desc, patron in mod.DATA:
        src = en.get((date, en_name))
        if src is None:
            unmatched.append((date, en_name))
            continue
        translated_en.add((date, en_name))
        row = tr.get((date, tr_name))
        if row is None:
            row = {"name": tr_name, "feastDate": date, "description": desc, "patronOf": patron,
                   "language": "tr", "tradition": src["tradition"]}
            rows.append(row)
            tr[(date, tr_name)] = row
        else:
            row["tradition"] = src["tradition"]

body = ",\r\n".join("  " + json.dumps(r, ensure_ascii=False) for r in rows)
SAINTS.write_bytes(("[\r\n" + body + "\r\n]\r\n").encode("utf-8"))

# An English entry counts as translated if we translated it or an existing Turkish row sits on its date
# with identical tradition tags (the original hand-written Turkish rows).
missing = [k for k in en if k not in translated_en]
print("tr rows:", sum(1 for r in rows if r["language"] == "tr"), "| unmatched keys:", unmatched[:5], len(unmatched),
      "| en without a translation module entry:", len(missing))
if "--missing" in sys.argv:
    for date, name in missing:
        r = en[(date, name)]
        print(json.dumps([date, name, r["description"], r["patronOf"]], ensure_ascii=False))
