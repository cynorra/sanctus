# -*- coding: utf-8 -*-
"""
Full Google Play Store 87 Language Localized Metadata Generator for Sanctus
Injects top viral keywords:
- Title (<= 30 chars): Sanctus: Daily Prayer & Bible (localized)
- Short Description (<= 80 chars): Daily Christian prayer, Bible verses, guided meditation & devotional for peace
- Full Description: Rich, formatted copy covering Holy Bible, Daily Prayer, Holy Rosary, Liturgy of the Hours, Christian Meditation & Sleep.
"""

import csv
import os

ALL_87_LOCALES = [
    'en-US', 'tr-TR', 'es-ES', 'de-DE', 'fr-FR', 'pt-BR', 'it-IT', 'ru-RU', 'ar', 'ja-JP',
    'ko-KR', 'es-419', 'es-US', 'pt-PT', 'fr-CA', 'zh-CN', 'zh-TW', 'zh-HK', 'hi-IN', 'id',
    'nl-NL', 'pl-PL', 'sv-SE', 'uk', 'vi', 'th', 'el-GR', 'cs-CZ', 'hu-HU', 'ro',
    'fi-FI', 'da-DK', 'no-NO', 'en-GB', 'en-CA', 'en-AU', 'en-IN', 'af', 'am', 'sq',
    'az-AZ', 'eu-ES', 'be', 'bn-BD', 'my-MM', 'bg', 'hy-AM', 'et', 'fa', 'fa-AE',
    'fa-AF', 'fa-IR', 'fil', 'gl-ES', 'gu', 'ka-GE', 'hr', 'kn-IN', 'ca', 'kk',
    'km-KH', 'ky-KG', 'lo-LA', 'lv', 'lt', 'mk-MK', 'ml-IN', 'ms', 'ms-MY', 'mr-IN',
    'mn-MN', 'ne-NP', 'pa', 'rm', 'si-LK', 'sk', 'sl', 'sw', 'sr', 'ta-IN',
    'te-IN', 'ur', 'zu', 'iw-IL', 'is-IS', 'en-SG', 'en-ZA'
]

# Raw title mappings - will be auto-trimmed to 30 chars safely
RAW_TITLES = {
    'en-US': "Sanctus: Daily Prayer & Bible",
    'en-GB': "Sanctus: Daily Prayer & Bible",
    'en-CA': "Sanctus: Daily Prayer & Bible",
    'en-AU': "Sanctus: Daily Prayer & Bible",
    'en-IN': "Sanctus: Daily Prayer & Bible",
    'en-SG': "Sanctus: Daily Prayer & Bible",
    'en-ZA': "Sanctus: Daily Prayer & Bible",
    'tr-TR': "Sanctus: Günlük Dua & İncil",
    'es-ES': "Sanctus: Oración y Biblia",
    'es-419': "Sanctus: Oración y Biblia",
    'es-US': "Sanctus: Oración y Biblia",
    'de-DE': "Sanctus: Gebet & Bibel",
    'fr-FR': "Sanctus : Prière & Bible",
    'fr-CA': "Sanctus : Prière & Bible",
    'pt-BR': "Sanctus: Oração e Bíblia",
    'pt-PT': "Sanctus: Oração e Bíblia",
    'it-IT': "Sanctus: Preghiera e Bibbia",
    'ru-RU': "Sanctus: Молитва и Библия",
    'ar': "Sanctus: صلاة مسيحية والكتاب",
    'ja-JP': "Sanctus: 毎日の祈りと聖書",
    'ko-KR': "Sanctus: 매일 기도와 성경",
    'zh-CN': "Sanctus：每日祷告与圣经",
    'zh-TW': "Sanctus：每日禱告與聖經",
    'zh-HK': "Sanctus：每日禱告與聖經",
    'hi-IN': "Sanctus: प्रार्थना व बाइबल",
    'id': "Sanctus: Doa Harian & Alkitab",
    'nl-NL': "Sanctus: Gebed & Bijbel",
    'pl-PL': "Sanctus: Modlitwa i Biblia",
    'sv-SE': "Sanctus: Daglig Bön & Bibel",
    'uk': "Sanctus: Молитва та Біблія",
    'vi': "Sanctus: Cầu Nguyện & Kinh Thánh",
    'th': "Sanctus: อธิษฐานและไบเบิล",
    'el-GR': "Sanctus: Προσευχή & Αγία Γραφή",
    'cs-CZ': "Sanctus: Modlitba a Bible",
    'hu-HU': "Sanctus: Napi Ima és Biblia",
    'ro': "Sanctus: Rugăciune și Biblia",
    'fi-FI': "Sanctus: Rukous ja Raamattu",
    'da-DK': "Sanctus: Daglig Bøn & Bibel",
    'no-NO': "Sanctus: Daglig Bønn & Bibel",
    'af': "Sanctus: Gebed & Bybel",
    'am': "Sanctus: ጸሎትና መጽሐፍ ቅዱስ",
    'sq': "Sanctus: Lutje Ditore & Bibla",
    'az-AZ': "Sanctus: Dua və Müqəddəs Kitab",
    'eu-ES': "Sanctus: Otoitza eta Biblia",
    'be': "Sanctus: Малітва і Біблія",
    'bn-BD': "Sanctus: प्रार्थना ও বাইবেল",
    'my-MM': "Sanctus: ဆုတောင်းနှင့်ကျမ်းစာ",
    'bg': "Sanctus: Молитва и Библия",
    'hy-AM': "Sanctus: Աղոթք և Աստվածաշունչ",
    'et': "Sanctus: Palve ja Piibel",
    'fa': "Sanctus: دعا و کتاب مقدس",
    'fa-AE': "Sanctus: دعا و کتاب مقدس",
    'fa-AF': "Sanctus: دعا و کتاب مقدس",
    'fa-IR': "Sanctus: دعا و کتاب مقدس",
    'fil': "Sanctus: Panalangin at Bibliya",
    'gl-ES': "Sanctus: Oración e Biblia",
    'gu': "Sanctus: પ્રાર્થના અને બાઇબલ",
    'ka-GE': "Sanctus: ლოცვა და ბიბლია",
    'hr': "Sanctus: Molitva i Biblija",
    'kn-IN': "Sanctus: ಪ್ರಾರ್ಥನೆ ಬೈಬಲ್",
    'ca': "Sanctus: Pregària i Bíblia",
    'kk': "Sanctus: Дұға және Киелі кітап",
    'km-KH': "Sanctus: ការអធិស្ឋាន និងគម្ពីរ",
    'ky-KG': "Sanctus: Сыйынуу жана Китеп",
    'lo-LA': "Sanctus: ການອະທິຖານ ແລະ ພຣະຄຳພີ",
    'lv': "Sanctus: Lūgšana un Bībele",
    'lt': "Sanctus: Malda ir Biblija",
    'mk-MK': "Sanctus: Молитва и Библија",
    'ml-IN': "Sanctus: പ്രാർത്ഥന ബൈബിൾ",
    'ms': "Sanctus: Doa Harian & Alkitab",
    'ms-MY': "Sanctus: Doa Harian & Alkitab",
    'mr-IN': "Sanctus: प्रार्थना व बायबल",
    'mn-MN': "Sanctus: Залбирал ба Библи",
    'ne-NP': "Sanctus: प्रार्थना र बाइबल",
    'pa': "Sanctus: ਪ੍ਰਾਰਥਨਾ ਤੇ ਬਾਈਬਲ",
    'rm': "Sanctus: Uraziun Diaria & Bibla",
    'si-LK': "Sanctus: යාච්ඤාව සහ බයිබලය",
    'sk': "Sanctus: Modlitba a Biblia",
    'sl': "Sanctus: Molitev in Biblija",
    'sw': "Sanctus: Sala na Biblia",
    'sr': "Sanctus: Молитва и Библија",
    'ta-IN': "Sanctus: ஜெபம் மற்றும் பைபிள்",
    'te-IN': "Sanctus: ప్రార్థన బైబిల్",
    'ur': "Sanctus: دعا اور بائبل",
    'zu': "Sanctus: Umthandazo NeBhayibheli",
    'iw-IL': "Sanctus: תפילה ותנ\"ך",
    'is-IS': "Sanctus: Bæn og Biblía",
}

SHORT_DESCRIPTIONS = {
    'en-US': "Daily Christian prayer, Bible verses, guided meditation & devotional for peace.",
    'en-GB': "Daily Christian prayer, Bible verses, guided meditation & devotional for peace.",
    'en-CA': "Daily Christian prayer, Bible verses, guided meditation & devotional for peace.",
    'en-AU': "Daily Christian prayer, Bible verses, guided meditation & devotional for peace.",
    'en-IN': "Daily Christian prayer, Bible verses, guided meditation & devotional for peace.",
    'en-SG': "Daily Christian prayer, Bible verses, guided meditation & devotional for peace.",
    'en-ZA': "Daily Christian prayer, Bible verses, guided meditation & devotional for peace.",
    'tr-TR': "Günlük Hristiyan duası, İncil ayetleri, meditasyon ve huzur veren adanmışlık.",
    'es-ES': "Oración cristiana diaria, versículos bíblicos, meditación guiada y paz interior.",
    'es-419': "Oración cristiana diaria, versículos bíblicos, meditación guiada y paz interior.",
    'es-US': "Oración cristiana diaria, versículos bíblicos, meditación guiada y paz interior.",
    'de-DE': "Tägliches christliches Gebet, Bibelverse, Meditation und Andachten für Frieden.",
    'fr-FR': "Prière chrétienne quotidienne, versets bibliques, méditation guidée et paix.",
    'fr-CA': "Prière chrétienne quotidienne, versets bibliques, méditation guidée et paix.",
    'pt-BR': "Oração cristã diária, versículos da Bíblia, meditação guiada e paz interior.",
    'pt-PT': "Oração cristã diária, versículos da Bíblia, meditação guiada e paz interior.",
    'it-IT': "Preghiera cristiana quotidiana, versetti biblici, meditazione guidata e pace.",
    'ru-RU': "Ежедневная христианская молитва, стихи из Библии, медитация и душевный покой.",
    'ar': "صلاة مسيحية يومية، آيات الكتاب المقدس، تأمل روحي موجه وسلام داخلي.",
    'ja-JP': "毎日のキリスト教の祈り、聖書の言葉、瞑想、安らぎの祈りアプリ。",
    'ko-KR': "매일 기독교 기도, 성경 구절, 안내 명상, 평화를 위한 묵상 앱.",
    'zh-CN': "每日基督徒祈祷、圣经金句、指引冥想与带来平安的灵修应用。",
    'zh-TW': "每日基督徒禱告、聖經金句、引導冥想與帶來平安的靈修程式。",
    'zh-HK': "每日基督徒禱告、聖經金句、引導冥想與帶來平安的靈修程式。",
    'hi-IN': "दैनिक मसीही प्रार्थना, बाइबल वचन, निर्देशित ध्यान और आंतरिक शांति।",
    'id': "Doa Kristen harian, ayat Alkitab, meditasi terpandu dan renungan damai.",
    'nl-NL': "Dagelijks christelijk gebed, bijbelverzen, geleide meditatie en innerlijke rust.",
    'pl-PL': "Codzienna chrześcijańska modlitwa, wersety biblijne, medytacja i spokój ducha.",
    'sv-SE': "Daglig kristen bön, bibelord, guidad meditation och andakt för inre frid.",
    'uk': "Щоденна християнська молитва, вірші з Біблії, керована медитація та мир.",
    'vi': "Cầu nguyện Kitô giáo hằng ngày, câu Kinh Thánh, thiền định và bình an nội tâm.",
    'th': "การอธิษฐานคริสเตียนประจำวัน ข้อพระคัมภีร์ การใคร่ครවญเพื่อสันติสุข",
    'el-GR': "Καθημερινή χριστιανική προσευχή, εδάφια Αγίας Γραφής, διαλογισμός και γαλήνη.",
    'cs-CZ': "Denní křesťanská modlitba, biblické verše, meditace a ztišení pro vnitřní klid.",
    'hu-HU': "Napi keresztény ima, bibliai versek, vezetett meditáció és lelki béke.",
    'ro': "Rugăciune creștină zilnică, versete biblice, meditație ghidată și pace divină.",
    'fi-FI': "Päivittäinen kristillinen rukous, raamatunjakeet, ohjattu meditaatio ja rauha.",
    'da-DK': "Daglig kristen bøn, bibelvers, guidet meditation og andagt for indre fred.",
    'no-NO': "Daglig kristen bønn, bibelvers, veiledet meditasjon og andakt for indre fred.",
    'af': "Daaglikse Christelike gebed, Bybelverse, geleide meditasie en vrede vir die siel.",
    'am': "ዕለታዊ የክርስቲያን ጸሎት፣ የመጽሐፍ ቅዱስ ጥቅሶች፣ የተመራ ማሰላሰል እና ሰላም",
    'sq': "Lutje e përditshme e krishterë, vargje nga Bibla, meditim i udhëhequr dhe paqe.",
    'az-AZ': "Gündəlik xristian duası, Müqəddəs Kitab ayələri, meditasiya və daxili hüzur.",
    'eu-ES': "Eguneroko kristau otoitza, Bibliako bertsoak, meditazioa eta barne bakea.",
    'be': "Штодзённая хрысціянская малітва, вершы з Бібліі, медытацыя і спакой душы.",
    'bn-BD': "দৈনিক খ্রিস্টান प्रार्थना, বাইবেলের আয়াত, নির্দেশিত ধ্যান ও অন্তরের শান্তি।",
    'my-MM': "နေ့စဉ် ခရစ်ယာန် ဆုတောင်းချက်၊ ကျမ်းစာစကားတော်နှင့် တရားထိုင်ခြင်း",
    'bg': "Ежедневна християнска молитва, библейски стихове, медитация и вътрешен мир.",
    'hy-AM': "Ամենօրյա քրիստոնեական աղոթք, աստվածաշնչյան համարներ, մեդիտացիա և խաղաղություն",
    'et': "Igapäevane kristlik palve, piiblisalmid, juhitud meditatsioon ja hingemahu.",
    'fa': "دعای روزانه مسیحی، آیات کتاب مقدس، مراقبه هدایت‌شده و آرامش الهی.",
    'fa-AE': "دعای روزانه مسیحی، آیات کتاب مقدس، مراقبه هدایت‌شده و آرامش الهی.",
    'fa-AF': "دعای روزمره مسیحی، آیات کتاب مقدس، مراقبه هدایت‌شده و آرامش الهی.",
    'fa-IR': "دعای روزانه مسیحی، آیات کتاب مقدس، مراقبه هدایت‌شده و آرامش الهی.",
    'fil': "Araw-araw na panalanging Kristiyano, talata sa Bibliya, pagninilay at kapayapaan.",
    'gl-ES': "Oración cristiá diaria, versículos da Biblia, meditación guiada e paz interior.",
    'gu': "દૈનિક ખ્રિસ્તી પ્રાર્થના, બાઇબલ વચનો, ધ્યાન અને શાંતિ માટેની ભક્તિ એપ્લિકેશન.",
    'ka-GE': "ყოველდღიური ქრისტიანული ლოცვა, ბიბლიის მუხლები, მედიტაცია და მშვიდობა",
    'hr': "Dnevna kršćanska molitva, biblijski stihovi, vođena meditacija i duhovni mir.",
    'kn-IN': "ದೈನಂದಿನ ಕ್ರೈಸ್ತ ಪ್ರಾರ್ಥನೆ, ಬೈಬಲ್ ವಚನಗಳು, ಮಾರ್ಗದರ್ಶಿ ಧ್ಯಾನ ಮತ್ತು ನೆಮ್ಮದಿ.",
    'ca': "Pregària cristiana diària, versicles bíblics, meditació guiada i pau interior.",
    'kk': "Күнделікті христиан дұғасы, Киелі кітап тармақтары, медитация және тыныштық.",
    'km-KH': "ការអធិស្ឋានគ្រីស្ទានប្រចាំថ្ងៃ ខគម្ពីរ ការធ្វើសមាធិ និងសន្តិភាពខាងវិញ្ញាណ",
    'ky-KG': "Күнүмдүк христиан сыйынуусу, Ыйык Китеп аяттары, медитация жана тынчтык.",
    'lo-LA': "ການອະທິຖານຄຣິດສະຕຽນປະຈຳວັນ ຂໍ້ພຣະຄຳພີ ການໄຕ່ຕອງ ແລະ ຄວາມສະຫງົບສຸກ",
    'lv': "Ikdienas kristīgā lūgšana, Bībeles panti, vadīta meditācija un dvēseles miers.",
    'lt': "Kasdienė krikščioniška malda, Biblijos eilutės, meditacija ir dvasinė ramybė.",
    'mk-MK': "Секојдневна христијанска молитва, библиски стихови, медитација и духовен мир.",
    'ml-IN': "ദൈനംദിന ക്രിസ്തീയ പ്രാർത്ഥന, ബൈബിൾ വാക്യങ്ങൾ, ധ്യാനം, ആത്മീയ സമാധാനം.",
    'ms': "Doa Kristian harian, ayat Alkitab, meditasi terpandu dan renungan damai.",
    'ms-MY': "Doa Kristian harian, ayat Alkitab, meditasi terpandu dan renungan damai.",
    'mr-IN': "दैनिक ख्रिश्चन प्रार्थना, बायबल वचने, ध्यान आणि मनःशांतीसाठी उपासना.",
    'mn-MN': "Өдөр тутмын христийн залбирал, Библийн эшлэл, бясалгал ба сэтгэлийн амар амгалан.",
    'ne-NP': "दैनिक इसाई प्रार्थना, बाइबलका पदहरू, निर्देशित ध्यान र हृदयको शान्ति।",
    'pa': "ਰੋਜ਼ਾਨਾ ਈਸਾਈ ਪ੍ਰਾਰਥਨਾ, ਬਾਈਬਲ ਦੀਆਂ ਆਇਤਾਂ, ਮਾਰਗਦਰਸ਼ਨ ਧਿਆਨ ਅਤੇ ਆਤਮਿਕ ਸ਼ਾਂਤੀ।",
    'rm': "Uraziun cristiana diaria, versets da la Bibla, meditaziun e pasch per l'olma.",
    'si-LK': "දෛනික කිතුනු යාච්ඤාව, බයිබල් පද, මඟපෙන්වන මෙනෙහි කිරීම සහ ආධ්‍යාත්මික සාමය.",
    'sk': "Denná kresťanská modlitba, biblické verše, meditácia a pokoj pre vašu dušu.",
    'sl': "Dnevna krščanska molitev, svetopisemski vrstici, meditacija in notranji mir.",
    'sw': "Sala ya Kikristo ya kila siku, mistari ya Biblia, tafakari na amani moyoni.",
    'sr': "Дневна хришћанска молитва, библијски стихови, вођена медитација и душевни мир.",
    'ta-IN': "தினசரி கிறிஸ்தவ ஜெபம், பைபிள் வசனங்கள், தியானம் மற்றும் ஆவிக்குரிய அமைதி.",
    'te-IN': "రోజువారీ క్రైస్తవ ప్రార్థన, బైబిల్ వచనాలు, మార్గదర్శక ధ్యానం మరియు మనశ్శాంతి.",
    'ur': "روزانہ مسیحی دعا، بائبل کی آیات، رہنمائی مراقبہ اور دل کا اطمینان و سکون۔",
    'zu': "Umthandazo wansuku zonke, amavesi eBhayibheli nokuthula komphefumulo.",
    'iw-IL': "תפילה נוצרית יומית, פסוקי תנ\"ך, מדיטציה מודרכת ושלווה פנימית בלב.",
    'is-IS': "Dagleg kristin bæn, biblíuvers, hugleiðsla og andlegur friður fyrir sálina.",
}

from generate_play_store_listings import (
    BASE_EN_FULL, BASE_TR_FULL, BASE_ES_FULL, BASE_PT_FULL,
    BASE_FR_FULL, BASE_DE_FULL, BASE_IT_FULL, BASE_RU_FULL, BASE_AR_FULL
)

batch2_full = {}
with open(r'd:\android-projeler\sanctus-mvp\metadata\store-listing-batch2.csv', encoding='utf-8', errors='ignore') as f:
    r = csv.reader(f)
    next(r)
    for row in r:
        if row and len(row) >= 4:
            batch2_full[row[0].strip()] = row[3].strip()

def get_full_description(loc):
    if loc in batch2_full and len(batch2_full[loc]) > 200:
        return batch2_full[loc]
    if loc == 'tr-TR':
        return BASE_TR_FULL
    if loc.startswith('es'):
        return BASE_ES_FULL
    if loc.startswith('pt'):
        return BASE_PT_FULL
    if loc.startswith('fr'):
        return BASE_FR_FULL
    if loc == 'de-DE':
        return BASE_DE_FULL
    if loc == 'it-IT':
        return BASE_IT_FULL
    if loc in ('ru-RU', 'be', 'uk'):
        return BASE_RU_FULL
    if loc.startswith('fa') or loc in ('ar', 'ur'):
        return BASE_AR_FULL
    return BASE_EN_FULL

# Validate & adjust
for loc in ALL_87_LOCALES:
    t = RAW_TITLES.get(loc, "Sanctus: Daily Prayer & Bible")
    if len(t) > 30:
        t = t[:30].strip()
    RAW_TITLES[loc] = t

    s = SHORT_DESCRIPTIONS.get(loc, "Daily Christian prayer, Bible verses, guided meditation & devotional for peace.")
    if len(s) > 80:
        s = s[:80].strip()
    SHORT_DESCRIPTIONS[loc] = s

# Final validation
for loc in ALL_87_LOCALES:
    assert len(RAW_TITLES[loc]) <= 30, f"Title >30 in {loc}: {len(RAW_TITLES[loc])}"
    assert len(SHORT_DESCRIPTIONS[loc]) <= 80, f"Short >80 in {loc}: {len(SHORT_DESCRIPTIONS[loc])}"

out_path = r"d:\android-projeler\sanctus-mvp\metadata\play-store-listings-all-languages.csv"
with open(out_path, 'w', encoding='utf-8-sig', newline='') as f:
    writer = csv.writer(f)
    writer.writerow(["Language Code", "Title", "Short Description", "Full Description"])
    for loc in ALL_87_LOCALES:
        writer.writerow([loc, RAW_TITLES[loc], SHORT_DESCRIPTIONS[loc], get_full_description(loc)])

print(f"ALL_DONE: Generated {len(ALL_87_LOCALES)} listings in {out_path}")
