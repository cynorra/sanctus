"""Tags every prayer and saint in assets/seed/*.json with the traditions it belongs to.

Rerunnable: it only (re)writes the "tradition" field. "all" means every tradition; otherwise a
comma-separated list of catholic / orthodox / protestant / anglican.

English and Turkish rows are tagged by name from the tables below. Other languages only have a
handful of translated core prayers, so they are tagged by category and by Marian/devotional
keywords; anything that does not match stays "all".
"""
import json
import re
import sys
from pathlib import Path

SEED = Path(__file__).resolve().parent.parent / "app" / "src" / "main" / "assets" / "seed"

C, O, P, A = "catholic", "orthodox", "protestant", "anglican"


def t(*ids):
    return ",".join(ids)


ALL = "all"

PRAYERS_EN = {
    "Our Father": ALL, "Hail Mary": t(C, O, A), "Glory Be": t(C, O, A),
    "Apostles' Creed": ALL, "Act of Contrition": C, "Morning Offering": C,
    "Evening Prayer": ALL, "Hail, Holy Queen": C, "Rosary Opening Prayer": C,
    "Fatima Prayer": C, "Prayer to Saint Michael": C, "Memorare": C, "Anima Christi": t(C, A),
    "Lauds — Morning Prayer": t(C, A), "Vespers — Evening Prayer": t(C, A),
    "Compline — Night Prayer": t(C, O, A), "The Way of the Cross": t(C, A),
    "Divine Mercy Chaplet": C, "The Angelus": C, "Regina Coeli": C,
    "Prayer of Saint Francis (Peace Prayer)": ALL, "Angel of God (Guardian Angel Prayer)": C,
    "Grace Before Meals": ALL, "Prayer to the Holy Spirit": ALL, "Prayer Before a Crucifix": C,
    "Prayer to Saint Joseph": C, "Eternal Rest (Prayer for the Dead)": t(C, A),
    "Suscipe (Prayer of Saint Ignatius)": C, "Act of Faith": C, "Act of Hope": C,
    "Act of Love": C, "The Divine Praises": C,
    "Let Nothing Disturb You (Prayer of Saint Teresa of Ávila)": ALL,
    "Prayer Before Study (Prayer of Saint Thomas Aquinas)": t(C, A), "Grace After Meals": ALL,
    "De Profundis (Psalm 130)": ALL, "O Sacrament Most Holy": C, "Jesus Prayer": t(O, C, A),
    "Nicene Creed": ALL, "Litany of the Blessed Virgin Mary (Litany of Loreto)": C,
    "Sub Tuum Praesidium": t(C, O), "Te Deum": t(C, A, P), "Gloria in Excelsis Deo": t(C, A, P),
    "Sanctus": t(C, A, P), "Agnus Dei": t(C, A, P), "Miraculous Medal Prayer": C,
    "O Salutaris Hostia": C, "Tantum Ergo": C, "Litany of Humility": C,
    "Act of Spiritual Communion": C, "Ave Maris Stella": C, "Confiteor (I Confess)": C,
    "Prayer of Saint Patrick (Christ Be With Me)": ALL, "Novena to the Sacred Heart of Jesus": C,
    "Lectio Divina — Praying with Scripture": ALL, "Examination of Conscience": ALL,
    "Nicene Creed (Eastern Orthodox Text)": O, "Trisagion Prayers (The Usual Beginning)": O,
    "Prayer of the Optina Elders": O, "Eternal Memory (Orthodox Memorial Prayer)": O,
    "Serenity Prayer": ALL, "Morning Prayer (Martin Luther)": P,
    "Evening Prayer (Martin Luther)": P, "Covenant Prayer (John Wesley)": t(P, A),
    "General Thanksgiving (Book of Common Prayer)": t(A, P),
    "ACTS Prayer — A Guide to Personal Prayer": ALL,
    "Prayer of Saint Ephrem the Syrian (Lenten Prayer)": t(O, A, C),
    "A Collect for Purity (Book of Common Prayer)": A, "Litany of Saint Joseph": C,
    "Novena Prayer to Saint Jude Thaddeus": C, "Litany of the Saints (Short Form)": t(C, A),
    "Litany of the Most Sacred Heart of Jesus": C,
    "Prayer Before Holy Communion (Prayer of Saint John Chrysostom)": t(O, C),
    "Prayer of Humble Access (Book of Common Prayer)": A,
    "Examination of Conscience for Married Couples and Parents": t(C, A),
    "Collect for Grace (Book of Common Prayer)": A, "Collect for Peace (Book of Common Prayer)": A,
    "Phos Hilaron (O Gladsome Light) — Ancient Vesper Hymn": t(O, A),
    "Axion Estin (It Is Truly Right) — Hymn to the Theotokos": O,
    "The Daily Examen of Saint Ignatius of Loyola": ALL, "Novena to The Divine Mercy": C,
    "Novena to the Holy Spirit (Prayer of Cardinal Mercier)": C,
    "Litany of the Holy Name of Jesus": C, "General Confession (Book of Common Prayer)": A,
    "Lectio Divina — The Road to Emmaus (Luke 24:13–35)": ALL,
    "Examination of Conscience According to the Ten Commandments": ALL,
    "The Sinner's Prayer — An Evangelical Guide to Receiving Christ": P,
}

PRAYERS_TR = {
    "Babamız Duası": ALL, "Selam Ey Meryem": t(C, O, A), "Sabah Duası": ALL, "Akşam Duası": ALL,
    "Ölülere Dua": t(C, A), "Şan Olsun": t(C, O, A), "Havariler İnancı": ALL,
    "Pişmanlık Duası": C, "Selam Ey Hükümdar Ana": C, "Tespihin Başlangıç Duası": C,
    "Fatima Duası": C, "Aziz Mikail Duası": C, "Memorare": C, "Mesih'in Ruhu": t(C, A),
    "Övgüler — Sabah İbadeti": t(C, A), "Akşam İbadeti — Vesperler": t(C, A),
    "Gece İbadeti — Komplin": t(C, O, A), "Çarmıh Yolu": t(C, A), "İlahi Merhamet Koroncası": C,
    "Angelus (Rab'bin Melek Duası)": C, "Regina Coeli (Göklerin Kraliçesi)": C,
    "İsa'nın Kutsal Kalbine Dokuz Günlük Dua (Novena)": C, "Meryem Ana Duaları (Loreto Litanyası)": C,
    "Lectio Divina — Kutsal Kitapla Dua Etmek": ALL, "Vicdan Muhasebesi": ALL,
    "Optina Ruhbanlarının Duası": O, "Sonsuz Anma (Ortodoks Anma Duası)": O,
    "Sükûnet Duası (Serenity Prayer)": ALL, "Sabah Duası (Martin Luther)": P,
    "Akşam Duası (Martin Luther)": P, "Ahit Duası (John Wesley)": t(P, A),
    "Genel Şükran Duası (Book of Common Prayer)": t(A, P),
    "ACTS Dua Rehberi — Kişisel Dua için Bir Yöntem": ALL,
}

# Catholic-only devotions recognised by keyword in any language (lower-cased substrings).
CATHOLIC_KEYWORDS = [
    "novena", "nowenna", "novene", "novène", "novenn", "nonena", "tesbih", "tespih", "rosary",
    "rosario", "rosenkranz", "rosaire", "rosar", "litan", "litá", "loreto", "lauret",
    "angelus", "ángelus", "memorare", "regina", "fatima", "fátima", "divine mercy", "misericord",
    "barmherzigkeit", "miséricorde", "mercy", "herz jesu", "sagrado coraz", "sacré-cœur",
    "sacre", "tasbih", "تساعية", "لِطانية", "المسبحة", "الوردية",
]

SAINTS_ALL = {
    "Saint Joseph", "Saint Patrick", "Saint Peter the Apostle", "Saint Paul the Apostle",
    "Saint Mary Magdalene", "Saint Augustine of Hippo", "Saint Nicholas",
    "Saint John the Baptist", "Saint Luke the Evangelist", "Saint John the Evangelist",
    "Saint Stephen", "Saint Christopher", "Saint George", "Archangel Michael",
    "Saint Andrew the Apostle", "Saint Thomas the Apostle", "Saint Bartholomew the Apostle",
    "Saint Matthew the Apostle", "Saint James the Greater", "Saint Barnabas", "Saint Jerome",
    "Saint Ambrose", "Saint Athanasius of Alexandria", "Saint Basil the Great",
    "Saint John Chrysostom", "Saint Polycarp", "Saint Ignatius of Antioch",
    "Saint Irenaeus of Lyons", "Saint Anthony the Great", "Saint Gregory the Great",
    "Saint Cecilia", "Saint Lucy", "Saint Valentine", "Saint Sebastian", "Saint Agnes of Rome",
}
SAINTS_MAP = {
    "Saint Francis of Assisi": t(C, A), "Saint Clare of Assisi": t(C, A),
    "Blessed Virgin Mary (Assumption)": t(C, O, A), "Saint Anne": t(C, O),
    "Saint Teresa of Ávila": t(C, A), "Saint John of Damascus": t(O, C),
    "Saint Seraphim of Sarov": O, "Saint Nektarios of Aegina": O,
    "Saint Silouan the Athonite": O, "Martin Luther": t(P, A), "John Wesley": t(P, A),
    "Dietrich Bonhoeffer": t(P, A), "Saint Thomas More": t(C, A),
}
SAINTS_TR = {
    "Aziz Yusuf", "Aziz Patrick", "Havari Aziz Petrus", "Havari Aziz Pavlus",
    "Azize Meryem Magdalena", "Hippolu Aziz Augustinus", "Aziz Nikolaos", "Vaftizci Yahya",
    "İncilci Aziz Luka", "İncilci Aziz Yuhanna", "Aziz İstefanos", "Aziz Christophoros",
    "Aziz Georgios", "Başmelek Mikail", "Aziz Valentinus",
}


def classify_other_prayer(p):
    name = p["name"].lower()
    if p["category"] == "rosary":
        return C
    if any(k in name for k in CATHOLIC_KEYWORDS):
        return C
    return ALL


def main():
    prayers = json.loads((SEED / "prayers.json").read_text(encoding="utf-8"))
    saints = json.loads((SEED / "saints.json").read_text(encoding="utf-8"))
    missing = []
    # Rows added later by scripts/add-content.py already carry their own tag; only --force retags them.
    force = "--force" in sys.argv
    for p in prayers:
        lang, name = p["language"], p["name"]
        if "tradition" in p and not force:
            continue
        if lang == "en":
            if name not in PRAYERS_EN:
                missing.append(("prayer", lang, name))
                continue
            p["tradition"] = PRAYERS_EN[name]
        elif lang == "tr":
            if name not in PRAYERS_TR:
                missing.append(("prayer", lang, name))
                continue
            p["tradition"] = PRAYERS_TR[name]
        else:
            p["tradition"] = classify_other_prayer(p)
    for s in saints:
        lang, name = s["language"], s["name"]
        if "tradition" in s and not force:
            continue
        if lang == "en":
            s["tradition"] = ALL if name in SAINTS_ALL else SAINTS_MAP.get(name, C)
        elif lang == "tr":
            s["tradition"] = ALL if name in SAINTS_TR else C
        else:
            s["tradition"] = C
    if missing:
        for m in missing:
            print("UNMAPPED", m, file=sys.stderr)
        sys.exit(1)
    # Keep each file's existing layout (one object per line, CRLF) so the diff only shows the tag.
    for fname, data, seps in (("prayers.json", prayers, (",", ":")), ("saints.json", saints, (", ", ": "))):
        body = ",\r\n".join("  " + json.dumps(o, ensure_ascii=False, separators=seps) for o in data)
        (SEED / fname).write_bytes(("[\r\n" + body + "\r\n]\r\n").encode("utf-8"))
    counts = {}
    for row in prayers + saints:
        counts[row["tradition"]] = counts.get(row["tradition"], 0) + 1
    print(sorted(counts.items(), key=lambda kv: -kv[1]))


if __name__ == "__main__":
    main()
