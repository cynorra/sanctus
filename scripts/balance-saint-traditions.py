"""Gives every tradition a saint on every day of the year, without inventing entries.

For each day a tradition has nothing, the best existing English entry for that day is tagged for it:
Episcopal-calendar entries first, then biblical persons, early martyrs and fathers, then the rest.
Marian devotions and post-Reformation Catholic saints are never lent to another tradition.
Days with no eligible entry are printed so they can be written by hand.

    python scripts/balance-saint-traditions.py
"""
import datetime
import json
from pathlib import Path

SAINTS = Path(__file__).resolve().parent.parent / "app" / "src" / "main" / "assets" / "seed" / "saints.json"
rows = json.loads(SAINTS.read_text(encoding="utf-8"))

# Never lent across traditions: Marian titles/devotions and Catholic saints of the modern era.
BLOCK = ("Our Lady", "Holy Name of Mary", "Queenship", "Blessed Virgin", "Immaculate", "Theotokos",
         "Sorrows", "Basilica", "Basilicas", "Chair of Saint", "Faustina", "Acutis", "John Paul II",
         "Pio of", "Frassati", "Gianna", "Louis-Marie", "Lisieux", "Bernadette", "Drexel", "Escriva",
         "Juan Diego", "Cabrini", "Vianney", "Liguori", "Teresa of Calcutta", "Margaret Mary", "Rita of",
         "Philip Neri", "Isidore the Farmer", "Anthony of Padua", "Seven Holy Founders", "Pius",
         "Rose of Lima", "Borromeo", "Martin de Porres", "John Bosco", "Francis Borgia", "Cajetan",
         "Vincent Ferrer", "Josemaria", "Paul of the Cross", "Claret", "Alphonsus Rodriguez",
         "Marianne Cope", "Albert the Great", "Francis of Paola", "John Baptist de La Salle",
         "Julie Billiart", "Conrad of Piacenza", "Hyacintha", "Berard", "Marguerite", "Jane Frances",
         "Louise de Marillac", "Gertrude", "Lidwina", "Dedication", "Presentation of the Blessed",
         "Heribert", "Ludger", "Rupert", "Turibius", "Casimir", "Stanislaus", "Henry II", "Henry of Treviso",
         "Cuthbert of Lindisfarne\", \"catholic", "Mary, Mother of God", "Saint Anne")
BIBLICAL = ("The Holy Apostle", "The Holy Prophet", "Apostle", "Apostles", "Prophet", "Righteous",
            "Holy Innocents", "Synaxis", "Saints Zechariah", "Archangel", "Martha", "Mary Magdalene")


def eligible(row):
    return not any(b in row["name"] for b in BLOCK)


def score(row, tradition):
    tags = row["tradition"].split(",")
    s = 0
    if "anglican" in tags: s += 5
    if row["name"].startswith(BIBLICAL): s += 4
    if row["name"].startswith(("Martyr", "Hieromartyr", "Great Martyr", "Pope Saint", "Saint ")): s += 2
    if "orthodox" in tags: s += 2
    if tradition == "catholic" and "catholic" in tags: s += 3
    return s


days = [(datetime.date(2024, 1, 1) + datetime.timedelta(i)) for i in range(366)]
names = [f"{d.strftime('%B')} {d.day}" for d in days]
unfilled = {}
for tradition in ("catholic", "protestant", "anglican", "orthodox"):
    for day in names:
        todays = [r for r in rows if r["language"] == "en" and r["feastDate"] == day]
        if any(r["tradition"] == "all" or tradition in r["tradition"].split(",") for r in todays):
            continue
        options = sorted((r for r in todays if eligible(r)), key=lambda r: -score(r, tradition))
        if not options:
            unfilled.setdefault(day, []).append(tradition)
            continue
        options[0]["tradition"] += "," + tradition

body = ",\r\n".join("  " + json.dumps(r, ensure_ascii=False) for r in rows)
SAINTS.write_bytes(("[\r\n" + body + "\r\n]\r\n").encode("utf-8"))
print("still empty:", unfilled)
