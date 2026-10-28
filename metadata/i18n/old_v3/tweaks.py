# -*- coding: utf-8 -*-
# Competitor-driven search tweaks (Play search results, hl/gl per language, 2026-10-28).
# Findings: prayer queries in tr/fr/de/nl/ar/hi/id return mostly Islamic apps -> add Christian/Catholic qualifier;
# leaders use "Bible + Audio", "Offline"; tr rosary = "Kutsal Tespih"; ru prayer = "Молитвослов"; uk = "Вервиця"/"Молитва Церкви".
T = {}

T['en-US'] = {
    'short': 'Daily prayer, Holy Rosary, Divine Office, Bible verse of the day & more',
    'sub': [
        ('follow the Liturgy of the Hours,', 'follow the Liturgy of the Hours (Divine Office),'),
        ('LITURGY OF THE HOURS (BREVIARY)', 'LITURGY OF THE HOURS (DIVINE OFFICE & BREVIARY)'),
    ],
}

T['tr-TR'] = {
    'short': 'Sanctus: Hristiyan günlük dua, Kutsal Tespih, Saatler Litürjisi, günün ayeti',
    'sub': [
        ('Sanctus, Hristiyanlar için günlük dua ve Kutsal Kitap uygulamasıdır.', 'Sanctus, Katolik ve Hristiyanlar için günlük dua ve sesli Kutsal Kitap uygulamasıdır.'),
        ('Rozario duasını edin', 'Rozario (Kutsal Tespih) duasını edin'),
        ('ROZARIO DUASI (SESLİ REHBER)', 'KUTSAL TESPİH / ROZARIO DUASI (SESLİ REHBER)'),
        ('KUTSAL KİTAP, İNCİL VE GÜNÜN AYETİ', 'SESLİ KUTSAL KİTAP, İNCİL VE GÜNÜN AYETİ'),
    ],
}

T['es-ES'] = {
    'sub': [('BIBLIA Y VERSÍCULO DEL DÍA', 'BIBLIA EN AUDIO Y VERSÍCULO DEL DÍA')],
}

T['pt-BR'] = {
    'sub': [
        ('O Sanctus é o seu app de Bíblia e oração diária', 'O Sanctus é o seu app de Bíblia Sagrada e oração diária'),
        ('BÍBLIA E VERSÍCULO DO DIA', 'BÍBLIA SAGRADA EM ÁUDIO E VERSÍCULO DO DIA'),
        ('SANTO TERÇO GUIADO (ÁUDIO)', 'SANTO TERÇO E ROSÁRIO GUIADO (ÁUDIO)'),
    ],
}

T['de-DE'] = {
    'short': 'Sanctus: christliches Gebet, Rosenkranz, Stundengebet, Bibelvers des Tages',
    'sub': [
        ('BIBEL UND BIBELVERS DES TAGES', 'BIBEL, HÖRBIBEL UND BIBELVERS DES TAGES'),
        ('STUNDENGEBET (BREVIER)', 'STUNDENGEBET (STUNDENBUCH & BREVIER)'),
    ],
}

T['fr-FR'] = {
    'short': 'Sanctus : prière catholique, chapelet, Liturgie des Heures, verset du jour',
    'sub': [('BIBLE ET VERSET DU JOUR', 'BIBLE AUDIO ET VERSET DU JOUR')],
}

T['it-IT'] = {
    'sub': [('BIBBIA E VERSETTO DEL GIORNO', 'BIBBIA AUDIO E VERSETTO DEL GIORNO')],
}

T['pl-PL'] = {
    'short': 'Sanctus: modlitwy katolickie, Różaniec, Liturgia Godzin, Biblia, werset dnia',
    'sub': [
        ('BIBLIA I WERSET DNIA', 'BIBLIA (PISMO ŚWIĘTE) I WERSET DNIA'),
        ('MODLITWA CODZIENNA I ROZWAŻANIE', 'MODLITWA CODZIENNA, MODLITEWNIK I ROZWAŻANIE'),
    ],
}

T['ru-RU'] = {
    'title': 'Библия, молитвослов и Розарий',
    'short': 'Sanctus: молитвослов, молитвы на каждый день, Розарий, стих дня',
    'sub': [
        ('приложение с Библией и молитвами на каждый день для христиан', 'приложение с Библией, молитвословом и молитвами на каждый день для христиан'),
        ('МОЛИТВЫ НА КАЖДЫЙ ДЕНЬ И РАЗМЫШЛЕНИЯ', 'МОЛИТВОСЛОВ: МОЛИТВЫ НА КАЖДЫЙ ДЕНЬ'),
    ],
}

T['uk'] = {
    'title': 'Біблія, молитва та Вервиця',
    'short': 'Sanctus: щоденна молитва, Вервиця (Розарій), Молитва Церкви, вірш дня',
    'sub': [
        ('РОЗАРІЙ / ВЕРВИЦЯ (АУДІОПРОВІДНИК)', 'ВЕРВИЦЯ / РОЗАРІЙ (АУДІОПРОВІДНИК)'),
        ('ЛІТУРГІЯ ГОДИН (БРЕВІАРІЙ)', 'ЛІТУРГІЯ ГОДИН / МОЛИТВА ЦЕРКВИ (БРЕВІАРІЙ)'),
    ],
}

T['ro'] = {
    'sub': [('RUGĂCIUNE ZILNICĂ ȘI MEDITAȚIE', 'RUGĂCIUNI ZILNICE (CARTE DE RUGĂCIUNI) ȘI MEDITAȚIE')],
}

T['ko-KR'] = {
    'short': 'Sanctus: 가톨릭 기도, 묵주기도, 성무일도, 오늘의 말씀, 오디오 성경',
    'sub': [('성경과 오늘의 말씀', '성경(오디오 성경)과 오늘의 말씀')],
}

T['ja-JP'] = {
    'short': 'Sanctus：カトリックの祈り、ロザリオ、教会の祈り、今日の聖句、音声聖書',
}

T['vi'] = {
    'short': 'Sanctus: Lời Chúa hằng ngày, kinh Mân Côi, Phụng vụ Giờ Kinh, Công giáo',
    'sub': [('KINH THÁNH VÀ LỜI CHÚA HÔM NAY', 'KINH THÁNH VÀ LỜI CHÚA HẰNG NGÀY')],
}

T['id'] = {
    'title': 'Alkitab, Renungan & Rosario',
    'short': 'Sanctus: renungan harian, doa Katolik, Rosario, Ibadat Harian, ayat hari ini',
}

T['fil'] = {
    'title': 'Bibliya, Dasal, Santo Rosaryo',
    'short': 'Sanctus: Bibliya sa Tagalog, dasal, Santo Rosaryo, Liturhiya ng mga Oras',
}

T['ar'] = {
    'title': 'الكتاب المقدس والمسبحة الوردية',
    'short': 'Sanctus: صلاة مسيحية يومية، المسبحة الوردية، ليتورجيا الساعات، آية اليوم',
    'sub': [('الكتاب المقدس وآية اليوم', 'الكتاب المقدس كل يوم وآية اليوم')],
}

T['hi-IN'] = {
    'title': 'Hindi Bible, प्रार्थना, Rosary',
    'short': 'Sanctus: हिंदी बाइबिल, दैनिक प्रार्थना, Rosary, घंटों की प्रार्थना, आज का वचन',
}

T['sw'] = {
    'title': 'Biblia Takatifu, Sala, Rozari',
    'short': 'Sanctus: Biblia Takatifu, sala za Kikatoliki, Rozari Takatifu, aya ya siku',
    'sub': [('BIBLIA NA AYA YA SIKU', 'BIBLIA TAKATIFU NA AYA YA SIKU')],
}

T['th'] = {
    'title': 'พระคัมภีร์ ภาวนา ลูกประคำ',
    'short': 'Sanctus: พระคัมภีร์ไบเบิล ภาวนาประจำวัน ลูกประคำ พิธีกรรมชั่วโมง',
}

T['el-GR'] = {
    'sub': [('ΚΑΘΗΜΕΡΙΝΗ ΠΡΟΣΕΥΧΗ ΚΑΙ ΣΤΟΧΑΣΜΟΣ', 'ΚΑΘΗΜΕΡΙΝΗ ΠΡΟΣΕΥΧΗ, ΠΡΟΣΕΥΧΗΤΑΡΙ ΚΑΙ ΣΤΟΧΑΣΜΟΣ')],
}

T['nl-NL'] = {
    'short': 'Sanctus: christelijk gebed, Rozenkrans, Getijdengebed, Bijbeltekst van de dag',
}
