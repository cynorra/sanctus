"""One-off: corrections found when checking the Catholic/shared entries against catholic.org
(and Wikipedia for canonisation status). Edits the source data files; the seed rows are rebuilt afterwards."""
from pathlib import Path

ROOT = Path(__file__).resolve().parent


def read(name):
    return (ROOT / name).read_text(encoding="utf-8")


def write(name, text):
    (ROOT / name).write_text(text, encoding="utf-8")


def replace(name, old, new):
    text = read(name)
    assert old in text, (name, old)
    write(name, text.replace(old, new, 1))


def drop(name, date, title):
    key = '    ("%s", "%s",' % (date, title)
    lines = read(name).split("\n")
    kept = [l for l in lines if not l.startswith(key)]
    assert len(kept) == len(lines) - 1, (name, date, title)
    write(name, "\n".join(kept))


# Ivo of Kermartin: feast is May 19, not May 23.
replace("saints_daily_en_2.py", '("May 23", "Saint Ivo of Kermartin"', '("May 19", "Saint Ivo of Kermartin"')
replace("saints_tr_2.py", '("May 23", "Saint Ivo of Kermartin"', '("May 19", "Saint Ivo of Kermartin"')
# Raphael Kalinowski: feast is November 15.
replace("saints_daily_en_3.py", '("December 2", "Saint Raphael Kalinowski"', '("November 15", "Saint Raphael Kalinowski"')
replace("saints_tr_3.py", '("December 2", "Saint Raphael Kalinowski"', '("November 15", "Saint Raphael Kalinowski"')
# Cabrini is already listed on her feast, November 13; December 22 was only her death date.
drop("saints_daily_en_3.py", "December 22", "Saint Frances Xavier Cabrini")
drop("saints_tr_3.py", "December 22", "Saint Frances Xavier Cabrini")
# Charles de Foucauld was canonised in 2022; Henry of Treviso is only Blessed.
replace("saints_daily_en_3.py", '"Blessed Charles de Foucauld"', '"Saint Charles de Foucauld"')
replace("saints_tr_3.py", '"Blessed Charles de Foucauld", "Mübarek Charles de Foucauld"', '"Saint Charles de Foucauld", "Aziz Charles de Foucauld"')
replace("saints_daily_en_2.py", '"Saint Henry of Treviso"', '"Blessed Henry of Treviso"')
replace("saints_tr_2.py", '"Saint Henry of Treviso", "Treviso\'lu Aziz Henry"', '"Blessed Henry of Treviso", "Treviso\'lu Mübarek Henry"')
print("ok")
