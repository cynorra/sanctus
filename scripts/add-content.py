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
        "en": ("Martin Luther", "February 18",
               "German monk and theologian (1483–1546) whose Ninety-five Theses of 1517 opened the Protestant "
               "Reformation. He taught that people are saved by grace through faith, translated the Bible into "
               "German so ordinary people could read it, and wrote hymns including “A Mighty Fortress Is Our "
               "God.” Remembered by Lutheran and Anglican churches on the day of his death.",
               "Lutheran churches, Bible translators, hymn writers"),
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
        "en": ("John Wesley", "May 24",
               "Anglican priest (1703–1791) and founder of the Methodist movement. On May 24, 1738, at a meeting "
               "on Aldersgate Street in London, he felt his heart “strangely warmed” by trust in Christ. He "
               "went on to preach across Britain, often outdoors, to people the church had not reached. Remembered "
               "by Methodist and Anglican churches.",
               "Methodist churches, traveling preachers, the poor"),
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
        "en": ("Dietrich Bonhoeffer", "April 9",
               "German Lutheran pastor and theologian (1906–1945) who opposed the Nazi regime and helped lead the "
               "Confessing Church. Arrested in 1943, he was executed at Flossenbürg concentration camp on April 9, "
               "1945. His books The Cost of Discipleship and Life Together are still widely read.",
               "Lutheran and Anglican churches, persecuted Christians, prisoners"),
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
        "en": ("Charles Wesley", "March 29",
               "English Anglican priest and hymn writer (1707–1788), brother of John Wesley. He wrote thousands of "
               "hymns, among them “Hark! The Herald Angels Sing” and “Love Divine, All Loves "
               "Excelling.”",
               "Hymn writers, church musicians"),
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


def dump(rows, seps):
    body = ",\r\n".join("  " + json.dumps(o, ensure_ascii=False, separators=seps) for o in rows)
    return ("[\r\n" + body + "\r\n]\r\n").encode("utf-8")


def main():
    scripture = json.loads(Path(sys.argv[1]).read_text(encoding="utf-8"))
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
            name, feast, desc, patron = entry[lang]
            if (lang, feast, name) in have_saints:
                continue
            saints.append({"name": name, "feastDate": feast, "description": desc, "patronOf": patron,
                           "language": lang, "tradition": entry["tradition"]})
            added_s += 1

    (SEED / "prayers.json").write_bytes(dump(prayers, (",", ":")))
    (SEED / "saints.json").write_bytes(dump(saints, (", ", ": ")))
    print("added prayers:", added_p, "saints:", added_s)


if __name__ == "__main__":
    main()
