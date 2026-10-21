"""One-off: applies the corrections found when checking Orthodox dates against oca.org
(and one Protestant date fix) to the source data files. Not needed again after it has run."""
from pathlib import Path

ROOT = Path(__file__).resolve().parent


def read(name):
    return (ROOT / name).read_text(encoding="utf-8")


def write(name, text):
    (ROOT / name).write_text(text, encoding="utf-8")


def drop(name, date, title):
    key = '    ("%s", "%s",' % (date, title)
    lines = read(name).split("\n")
    kept = [l for l in lines if not l.startswith(key)]
    assert len(kept) == len(lines) - 1, (name, date, title)
    write(name, "\n".join(kept))


def redate(name, old, title, new):
    a = '    ("%s", "%s",' % (old, title)
    b = '    ("%s", "%s",' % (new, title)
    text = read(name)
    assert a in text, (name, old, title)
    write(name, text.replace(a, b, 1))


E1, E2 = "saints_daily_en_orth_1.py", "saints_daily_en_orth_2.py"
T1, T2 = "saints_tr_orth_1.py", "saints_tr_orth_2.py"
for en_file, tr_file, date, title in [
    (E1, T1, "February 13", "The Apostles Aquila and Priscilla"),
    (E1, T1, "March 24", "Venerable Zachariah of Mount Athos"),
    (E1, T1, "March 29", "Venerable Mark of Athens"),
    (E1, T1, "May 27", "Martyr Therapon of Cyprus"),
    (E1, T1, "June 6", "Venerable Hilarion of Pelekete"),
    (E2, T2, "July 23", "Hieromartyr Phocas of Sinope"),
    (E2, T2, "October 13", "Martyr Carpus, Bishop of Thyatira"),
]:
    drop(en_file, date, title)
    drop(tr_file, date, title)
redate(E1, "April 14", "The Apostles Aristarchus, Pudens and Trophimus", "April 15")
redate(T1, "April 14", "The Apostles Aristarchus, Pudens and Trophimus", "April 15")
redate(E2, "November 24", "Hieromartyr Clement of Rome", "November 25")
redate(T2, "November 24", "Hieromartyr Clement of Rome", "November 25")
redate("saints_daily_en_prot_1.py", "May 10", "David Livingstone", "May 1")
redate("saints_tr_prot_1.py", "May 10", "David Livingstone", "May 1")

for name, old, new in [("add-saints-daily.py", '"saints_daily_en_orth_2",', '"saints_daily_en_orth_2", "saints_daily_en_orth_fix",'),
                       ("add-saints-tr.py", '"saints_tr_orth_2",', '"saints_tr_orth_2", "saints_tr_orth_fix",')]:
    text = read(name)
    if "orth_fix" not in text:
        write(name, text.replace(old, new, 1))
print("ok")
