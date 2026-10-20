"""Adds the Protestant/Anglican saints and the KJV scripture passages to assets/seed/*.json.

Rerunnable: rows whose natural key already exists are left alone. Scripture text comes from the
cache written by scripts/fetch-scripture.py (public-domain King James Version).

    python scripts/add-content.py <scripture_kjv.json>
"""
import json
import sys
from pathlib import Path

SEED = Path(__file__).resolve().parent.parent / "app" / "src" / "main" / "assets" / "seed"

SAINTS = [
    {
        "tradition": "protestant,anglican",
        "en": None,
        "tr": ("Martin Luther", "February 18",
               "Alman rahip ve ilahiyatçı (1483–1546). 1517’deki Doksan Beş Tez’i Protestan "
               "Reformu’nu başlattı. İnsanların iman yoluyla lütufla kurtulduğunu "
               "öğretti, sıradan insanlar okuyabilsin diye Kutsal Kitap’ı Almanca’ya "
               "çevirdi ve “Sağlam Kale Tanrımızdır” gibi ilahiler yazdı. "
               "Luteran ve Anglikan kiliseleri onu ölüm gününde anıyor.",
               "Luteran kiliseler, Kutsal Kitap çevirmenleri, ilahi yazarları"),
    },
    {
        "tradition": "protestant,anglican",
        "en": None,
        "tr": ("John Wesley", "May 24",
               "Anglikan rahip (1703–1791) ve Metodist hareketin kurucusu. 24 Mayıs 1738’de Londra’da "
               "Aldersgate Sokağı’ndaki bir toplantıda Mesih’e güvenle yüreğinin "
               "“garipçe ısındığını” hissetti. Ardından İngiltere "
               "boyunca, çoğu zaman açık havada, kilisenin ulaşmadığı "
               "insanlara vaaz verdi. Metodist ve Anglikan kiliseleri tarafından anılır.",
               "Metodist kiliseler, gezgin vaizler, yoksullar"),
    },
    {
        "tradition": "protestant,anglican",
        "en": None,
        "tr": ("Dietrich Bonhoeffer", "April 9",
               "Alman Luteran papaz ve ilahiyatçı (1906–1945). Nazi rejimine karşı çıktı "
               "ve İtiraf Eden Kilise’nin önderlerinden biri oldu. 1943’te tutuklandı ve 9 "
               "Nisan 1945’te Flossenbürg toplama kampında idam edildi. “Pahalı "
               "Müritlik” ve “Ortak Yaşam” adlı kitapları bugün de "
               "yaygın olarak okunuyor.",
               "Luteran ve Anglikan kiliseler, zulme uğrayan Hristiyanlar, mahkumlar"),
    },
    {
        "tradition": "protestant,anglican",
        "en": None,
        "tr": ("Charles Wesley", "March 29",
               "İngiliz Anglikan rahip ve ilahi yazarı (1707–1788), John Wesley’nin kardeşi. "
               "Binlerce ilahi yazdı; aralarında “Hark! The Herald Angels Sing” ve “Love "
               "Divine, All Loves Excelling” da var.",
               "İlahi yazarları, kilise müzisyenleri"),
    },
    {
        "tradition": "protestant,anglican",
        "en": ("William Tyndale", "October 6",
               "English scholar and priest (c. 1494–1536) who translated the New Testament into English from the "
               "original Greek so that ordinary people could read it. He was arrested, condemned and executed near "
               "Brussels in 1536. Much of his wording lives on in the King James Bible. Anglican churches remember "
               "him on October 6.",
               "Bible translators, English-speaking Christians"),
        "tr": ("William Tyndale", "October 6",
               "İngiliz bilgin ve rahip (yaklaşık 1494–1536). Yeni Antlaşma’yı "
               "sıradan insanlar okuyabilsin diye orijinal Yunancadan İngilizce’ye çevirdi. "
               "Tutuklandı, mahkum edildi ve 1536’da Brüksel yakınlarında idam edildi. "
               "\u0130fadelerinin büyük kısmı King James İncili’nde yaşamaya devam "
               "eder. Anglikan kiliseler onu 6 Ekim’de anıyor.",
               "Kutsal Kitap çevirmenleri"),
    },
    {
        "tradition": "anglican,protestant",
        "en": ("Thomas Cranmer", "March 21",
               "Archbishop of Canterbury (1489–1556) and chief author of the first Book of Common Prayer (1549), "
               "whose language shaped English-speaking worship for centuries. Imprisoned under Queen Mary I, he was "
               "burned at the stake in Oxford on March 21, 1556.",
               "The Anglican Communion, liturgists"),
        "tr": ("Thomas Cranmer", "March 21",
               "Canterbury Başpiskoposu (1489–1556) ve ilk Ortak Dua Kitabı’nın (1549) "
               "başlıca yazarı; kitabın dili yüzyıllar boyunca İngilizce konuşan "
               "toplumların ibadetini biçimlendirdi. Kraliçe I. Mary döneminde hapsedildi ve 21 "
               "Mart 1556’da Oxford’da kazığa bağlanarak yakıldı.",
               "Anglikan Cemaati, liturji yazarları"),
    },
    {
        "tradition": "protestant,anglican",
        "en": ("Johann Sebastian Bach", "July 28",
               "German Lutheran composer and organist (1685–1750) who dedicated his work Soli Deo Gloria, "
               "“to God alone be the glory.” He served as cantor in Leipzig and wrote hundreds of church "
               "cantatas, the Passions and the Mass in B minor. Remembered by Lutheran and Anglican churches on the "
               "day of his death.",
               "Church musicians, organists, composers"),
        "tr": ("Johann Sebastian Bach", "July 28",
               "Alman Luteran besteci ve orgcu (1685–1750). Eserlerini “Soli Deo Gloria”, yani "
               "“yalnızca Tanrı’ya şükran olsun” diyerek ithaf etti. "
               "Leipzig’de kantor olarak görev yaptı; yüzlerce kilise kantatı, Pasyonlar ve "
               "Si minor Ayin’i besteledi. Luteran ve Anglikan kiliseler onu ölüm gününde "
               "anıyor.",
               "Kilise müzisyenleri, orgcular, besteciler"),
    },
    {
        "tradition": "protestant",
        "en": ("John Calvin", "May 27",
               "French reformer and theologian (1509–1564) who led the Reformation in Geneva. His Institutes of "
               "the Christian Religion became a foundation of Reformed and Presbyterian theology. Remembered by "
               "Reformed churches on the day of his death.",
               "Reformed and Presbyterian churches"),
        "tr": ("John Calvin", "May 27",
               "Fransız reformcu ve ilahiyatçı (1509–1564); Cenevre’deki Reform’a "
               "önderlik etti. “Hristiyan Dininin Esasları” adlı eseri Reformed ve "
               "Presbiteryen teolojisinin temellerinden biri oldu. Reformed kiliseler onu ölüm "
               "gününde anıyor.",
               "Reformed ve Presbiteryen kiliseler"),
    },
    {
        "tradition": "protestant,anglican",
        "en": ("John Newton", "December 21",
               "English sea captain turned Anglican priest (1725–1807). After a violent storm at sea in 1748 he "
               "turned to faith, left the slave trade a few years later, and eventually became an outspoken "
               "opponent of it. He wrote the hymn “Amazing Grace.”",
               "Hymn writers, those seeking forgiveness"),
        "tr": ("John Newton", "December 21",
               "İngiliz denizci kaptan iken Anglikan rahip olan (1725–1807) bir kişi. 1748’de "
               "denizde yaşadığı şiddetli fırtınanın ardından "
               "inanca yöneldi, birkaç yıl sonra köle ticaretini bıraktı ve sonunda "
               "ona açıkça karşı çıkan biri oldu. “Amazing "
               "Grace” ilahisini yazdı.",
               "İlahi yazarları, bağışlanma arayanlar"),
    },
    {
        "tradition": "anglican",
        "en": ("George Herbert", "February 27",
               "Welsh-born Anglican priest and poet (1593–1633) who served the small country parish of Bemerton, "
               "near Salisbury. His collection of devotional poems, The Temple, includes “Love (III)” and "
               "“Teach me, my God and King.”",
               "Poets, country parish priests"),
        "tr": ("George Herbert", "February 27",
               "Galler doğumlu Anglikan rahip ve şair (1593–1633); Salisbury yakınlarındaki "
               "küçük Bemerton köy cemaatine hizmet etti. Dua şiirlerinden oluşan "
               "“The Temple” adlı derlemesinde “Love (III)” ve “Teach me, my God and "
               "King” yer alır.",
               "Şairler, köy cemaati rahipleri"),
    },
    {
        "tradition": "anglican,protestant",
        "en": ("William Wilberforce", "July 30",
               "English politician and Anglican layman (1759–1833) who, after an evangelical conversion, led the "
               "long parliamentary campaign against the slave trade. The Slave Trade Act passed in 1807. He died in "
               "July 1833, as Parliament was finalising the abolition of slavery throughout the British Empire.",
               "Campaigners against slavery, reformers"),
        "tr": ("William Wilberforce", "July 30",
               "İngiliz siyasetçi ve Anglikan seçkin (1759–1833). Evanjelik bir dönüşüm"
               "ün ardından köle ticaretine karşı uzun parlamento mücadelesine "
               "önderlik etti. Köle Ticareti Yasası 1807’de kabul edildi. 1833 Temmuz’unda, "
               "Parlamento İngiliz İmparatorluğu’nda köleliğin kaldırılmasını "
               "sonuçlandırırken öldü.",
               "Kölelik karşıtları, reformcular"),
    },
]

PRAYERS_TR_NOTE = "Scripture is added in English only; no Turkish public-domain text has been sourced yet."


def dump_rows(rows, seps):
    body = ",\r\n".join("  " + json.dumps(o, ensure_ascii=False, separators=seps) for o in rows)
    return ("[\r\n" + body + "\r\n]\r\n").encode("utf-8")


# Tags for rows that exist in the live Firestore content but were never in the repo seed.
P_ALL = "all"
ORPHAN_PRAYERS = {
    "Benedictus (Canticle of Zechariah)": "all",
    "Cherubic Hymn (Cherubikon) — Byzantine Divine Liturgy": "orthodox",
    "Collect for Aid against Perils (Book of Common Prayer)": "anglican",
    "Collect for the Second Sunday in Advent (Book of Common Prayer)": "anglican",
    "Collect for the Sunday Next Before Advent (Book of Common Prayer)": "anglican",
    "Ecce Agnus Dei (Behold the Lamb of God)": "catholic",
    "Examination of Conscience According to the Seven Deadly Sins": "catholic,anglican",
    "Examination of Conscience According to the Works of Mercy": "catholic",
    "Examination of Conscience Based on the Lord's Prayer": "all",
    "Examination of Conscience for the Engaged (Preparing for Marriage)": "catholic,anglican",
    "Examination of Conscience for Those in Work and Professional Life": "all",
    "Examination of Conscience for Young People and Students": "all",
    "Grace Before Meals (Orthodox)": "orthodox",
    "Kontakion of Pascha — Though Thou Didst Descend Into the Grave": "orthodox",
    "Kontakion of the Akathist Hymn to the Theotokos": "orthodox",
    "Litany of the Precious Blood of Jesus": "catholic",
    "Magnificat (Canticle of Mary)": "all",
    "Mystery of Faith (Memorial Acclamation)": "catholic",
    "Novena Prayer to Saint Joseph": "catholic",
    "Nunc Dimittis (Canticle of Simeon)": "all",
    "O Only-Begotten Son (Monogenes) — Byzantine Liturgy Hymn": "orthodox",
    "Opening Troparion of the Small Paraklesis to the Theotokos": "orthodox",
    "Paschal Troparion — Christ is Risen (Orthodox Easter Hymn)": "orthodox",
    "Prayer Before the Crucifix of San Damiano (Saint Francis of Assisi)": "catholic,anglican",
    "Prayer of Saint Chrysostom (Book of Common Prayer)": "anglican",
    "Rejoice, O Virgin Theotokos (Orthodox Troparion)": "orthodox",
    "The Doxology (Praise God, From Whom All Blessings Flow)": "protestant,anglican",
    "The Grace (2 Corinthians 13:14)": "all",
    "The Great Litany (Litany of Peace) — Byzantine Divine Liturgy": "orthodox",
    "The Heidelberg Catechism — What Is Your Only Comfort": "protestant",
    "The Particular Examen of Saint Ignatius of Loyola": "catholic",
    "The Westminster Shorter Catechism — Q1: Man's Chief End": "protestant",
    "Veni Creator Spiritus (Come, Holy Ghost, Our Souls Inspire)": "catholic,anglican",
    "Aziz Yusuf'a Dokuz Günlük Dua (Novena)": "catholic",
}
ORPHAN_SAINTS = {
    "Saint Mary of Egypt": "orthodox,catholic", "Saint Anselm of Canterbury": "catholic,anglican",
    "Saint Mark the Evangelist": "all", "Saint Louis-Marie de Montfort": "catholic",
    "Saint Lawrence of Rome": "catholic,orthodox,anglican", "Saint Bernard of Clairvaux": "catholic,anglican",
    "Saint Herman of Alaska": "orthodox", "Our Lady of Guadalupe": "catholic", "Saint Barbara": "catholic,orthodox",
    "Saint Juan Diego": "catholic", "Saint Scholastica": "catholic", "Our Lady of Lourdes": "catholic",
    "Saints Cyril and Methodius": "catholic,orthodox", "Saint Gregory of Narek": "orthodox,catholic",
    "Saint Xenia of Saint Petersburg": "orthodox", "Saint Nino of Georgia": "orthodox",
    "Saint Gregory Nazianzen": "all", "Saint John of Kronstadt": "orthodox", "Saint Olga of Kiev": "orthodox",
    "Saint Paisios of Mount Athos": "orthodox", "Saint Vladimir the Great": "orthodox",
    "Saint Charbel Makhlouf": "catholic", "Saint Cyril of Alexandria": "all", "Saint Boniface": "all",
    "Saint Columba of Iona": "all", "Saint Ephrem the Syrian": "orthodox,catholic,anglican",
    "Saint Cyril of Jerusalem": "all", "Saint John Climacus": "orthodox,catholic",
    "Saints Perpetua and Felicity": "all", "Saint Charles Wesley": "protestant,anglican",
    "Saint Gregory Palamas": "orthodox", "Saint Albert the Great": "catholic",
    "Saint Catherine of Alexandria": "catholic,orthodox", "Saint Margaret Mary Alacoque": "catholic",
    "Saint Cyprian of Carthage": "all", "Saint Hildegard of Bingen": "catholic",
    "Martin Luther": "protestant,anglican", "John Wesley": "protestant,anglican",
    "Dietrich Bonhoeffer": "protestant,anglican",
}


def import_orphans(dump, prayers, saints):
    """Copies live Firestore rows missing from the seed into it, with their tradition tag, so the seed
    stays the single source of truth and the next content sync does not overwrite them with less."""
    have_p = {(p["language"], p["name"]) for p in prayers}
    have_s = {(s["language"], s["feastDate"], s["name"]) for s in saints}
    added_p = added_s = 0
    unmapped = []
    for r in dump["prayers"]:
        if (r["language"], r["name"]) in have_p:
            continue
        tag = ORPHAN_PRAYERS.get(r["name"])
        if tag is None and (r["name"].startswith("Lectio Divina") or "Vicdan Muhasebesi" in r["name"]):
            tag = "all"  # scripture meditations and examinations of conscience suit every tradition
        if tag is None:
            unmapped.append(("prayer", r["language"], r["name"]))
            continue
        prayers.append({"name": r["name"], "category": r["category"], "language": r["language"],
                        "content": r["content"], "tradition": tag})
        added_p += 1
    for r in dump["saints"]:
        if (r["language"], r["feastDate"], r["name"]) in have_s:
            continue
        tag = ORPHAN_SAINTS.get(r["name"])
        if tag is None:
            unmapped.append(("saint", r["language"], r["name"]))
            continue
        saints.append({"name": r["name"], "feastDate": r["feastDate"], "description": r["description"],
                       "patronOf": r.get("patronOf", ""), "language": r["language"], "tradition": tag})
        added_s += 1
    if unmapped:
        for u in unmapped:
            print("UNMAPPED", u, file=sys.stderr)
        sys.exit(1)
    return added_p, added_s


def main():
    scripture = json.loads(Path(sys.argv[1]).read_text(encoding="utf-8"))
    dump = json.loads(Path(sys.argv[2]).read_text(encoding="utf-8"))
    prayers = json.loads((SEED / "prayers.json").read_text(encoding="utf-8"))
    saints = json.loads((SEED / "saints.json").read_text(encoding="utf-8"))

    have_prayers = {(p["language"], p["name"]) for p in prayers}
    added_p = 0
    for s in scripture:
        key = ("en", s["name"])
        if key in have_prayers:
            continue
        # The API hard-wraps lines and puts the KJV's italic (supplied) words on lines of their own,
        # so collapse each verse to one clean line first.
        verses = [" ".join(v.split()) for v in s["verses"]]
        if s["name"].startswith("The Lord's Prayer"):
            verses[0] = verses[0].replace("After this manner therefore pray ye: ", "", 1)
        content = "\n".join(verses) + "\n\n— " + s["reference"] + " (KJV)"
        prayers.append({"name": s["name"], "category": "scripture", "language": "en",
                        "content": content, "tradition": s["tradition"]})
        added_p += 1

    have_saints = {(s["language"], s["feastDate"], s["name"]) for s in saints}
    added_s = 0
    for entry in SAINTS:
        for lang in ("en", "tr"):
            if entry[lang] is None:
                continue
            name, feast, desc, patron = entry[lang]
            if (lang, feast, name) in have_saints:
                continue
            saints.append({"name": name, "feastDate": feast, "description": desc, "patronOf": patron,
                           "language": lang, "tradition": entry["tradition"]})
            added_s += 1

    op, os_ = import_orphans(dump, prayers, saints)
    print("imported from Firestore - prayers:", op, "saints:", os_)
    (SEED / "prayers.json").write_bytes(dump_rows(prayers, (",", ":")))
    (SEED / "saints.json").write_bytes(dump_rows(saints, (", ", ": ")))
    print("added prayers:", added_p, "saints:", added_s)


if __name__ == "__main__":
    main()
