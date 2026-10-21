# -*- coding: utf-8 -*-
"""
Generates play-store-listings-all-languages.csv for Sanctus covering all 87 official Google Play languages.
Optimized with top viral keywords:
- Title (max 30 chars): Sanctus: Daily Prayer & Bible (localized)
- Short Description (max 80 chars): Daily Christian prayer, Bible verses, guided meditation & devotional for peace (localized)
- Full Description: High-conversion, fully structured copy covering Prayer, Bible, Catholic Rosary, Liturgy of the Hours, Christian Meditation, Journal & Reminders.
"""
import csv
import os

# Base English Full Description
BASE_EN_FULL = """Find peace, build a daily spiritual habit, and deepen your walk with God with Sanctus.

Sanctus is your all-in-one Christian prayer, Bible devotional, and meditation app designed to calm your heart, overcome anxiety, and connect with Scripture every day. Whether you need morning prayer, an uplifting Verse of the Day, the Holy Rosary, the Liturgy of the Hours, or soothing bedtime Bible audio to sleep peacefully, Sanctus guides your path with Christ.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
KEY FEATURES FOR YOUR FAITH
━━━━━━━━━━━━━━━━━━━━━━━━━━━

✨ DAILY PRAYER & DEVOTIONAL
Start every morning with guided Christian prayers, daily devotionals, and inspiring Scripture verses tailored to your spiritual growth. Cultivate gratitude, find hope, and start your day with divine purpose.

📖 HOLY BIBLE & SCRIPTURE REFLECTIONS
Read and reflect on God’s Word anywhere. Deepen your faith with audio Scripture readings, reflections, and chapter-by-chapter Bible study from the Old and New Testaments.

🧘 CHRISTIAN MEDITATION & PEACE
Biblical mindfulness rooted in the Word of God. Reduce stress, relieve anxiety, and replace overwhelm with divine peace and spiritual strength through guided Christian meditation and worship reflections.

📿 THE HOLY ROSARY (GUIDED AUDIO)
Pray the daily mysteries of the Catholic Holy Rosary (Joyful, Sorrowful, Glorious, Luminous) completely offline. Track your decades easily with our interactive beads.

⏰ LITURGY OF THE HOURS & BREVIARY
Join the universal Church in daily prayer. Sanctus guides you through all canonical hours:
• Lauds (Morning Prayer)
• Terce, Sext, None (Daytime Prayers)
• Vespers (Evening Prayer)
• Compline (Night Prayer) — serene prayer before sleep
Set custom reminders for your canonical hours so you never miss prayer time.

🌙 BEDTIME BIBLE STORIES & CALMING SLEEP
Drift off peacefully in God’s presence with soothing Christian sleep stories, bedtime Psalms, and relaxing prayer audio designed to calm racing thoughts.

✍️ PRAYER JOURNAL, LIST & STREAK TRACKER
Organize your spiritual routine. Write in your private prayer journal, manage your prayer requests list, and celebrate answered prayers. Track your daily prayer streaks and watch your faith grow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
DAILY TOPICS COVERED:
━━━━━━━━━━━━━━━━━━━━━━━━━━━
• Overcoming Anxiety & Stress
• Healing, Hope, Gratitude & Forgiveness
• Daily Bible Verse of the Day (Shareable graphics)
• Holy Rosary & Catholic Novenas
• Bedtime Prayers & Sleep Stories
• Marriage, Relationships & Family Blessings
• Saint of the Day & Liturgical Calendar

━━━━━━━━━━━━━━━━━━━━━━━━━━━
ROOTED IN FAITH FOR ALL BELIEVERS
━━━━━━━━━━━━━━━━━━━━━━━━━━━
Sanctus is created for all Christians—Catholic, Protestant, Evangelical, Orthodox, and anyone seeking God's love and grace.

Download Sanctus: Daily Prayer & Bible now and take the first step toward a more peaceful, faithful life with God."""

# Base Turkish Full Description
BASE_TR_FULL = """Huzur bulun, günlük bir dua alışkanlığı kazanın ve Sanctus ile Tanrı'ya daha da yakınlaşın.

Sanctus; ruhunuzu dinlendirmek, kaygıyı yenmek ve her gün Kutsal Kitap ile bağ kurmanızı sağlamak için tasarlanmış kapsamlı Hristiyan dua, İncil ve meditasyon uygulamasıdır. İster sabah duasıyla güne başlayın, ister Günün Ayetini okuyun, ister Kutsal Rozari (Tespih) ve Saatler Litürjisi ile dua edin; Sanctus Mesih ile olan ruhsal yolculuğunuza rehberlik eder.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
ÖNE ÇIKAN ÖZELLİKLER
━━━━━━━━━━━━━━━━━━━━━━━━━━━

✨ GÜNLÜK DUA VE ADANMIŞLIK
Güne rehberli Hristiyan duaları, günlük adanmışlık yazıları ve ilham verici Kutsal Kitap ayetleriyle başlayın. Şükran duygusunu pekiştirin ve güne ilahi bir amaçla adım atın.

📖 KUTSAL KİTAP VE AYET DÜŞÜNCELERİ
Tanrı'nın Sözü'nü her yerde okuyun ve tefekkür edin. Eski ve Yeni Ahit'ten seçilmiş ayetler ve okuma planlarıyla inancınızı derinleştirin.

🧘 HRİSTİYAN MEDİTASYONU VE İÇ HUZUR
Tanrı Sözü'ne dayanan Hristiyan farkındalığı. Stresi azaltın, kaygıdan arının ve rehberli Hristiyan tefekkürü ile kalbinizi ilahi barışla doldurun.

📿 KUTSAL ROZARİ (REHBERLİ TESBİH DUASI)
Kutsal Rozari'nin Sevinçli, Kederli, Şanlı ve Aydınlık Gizemlerini tamamen çevrimdışı dua edin. Etkileşimli tespih taneleriyle dualarınızı kolayca takip edin.

⏰ SAATLER LİTÜRJİSİ (BREVIARY)
Evrensel Kilise ile birlikte her gün dua edin:
• Laudes (Sabah Duası)
• Tersiya, Seksta, Nona (Gün Ortası Duaları)
• Vesperae (Akşam Duası)
• Completorium (Gece Duası) — huzurlu uyku öncesi dua
Kanonik saatleriniz için hatırlatıcılar kurarak dua saatlerinizi asla aksatmayın.

🌙 UYKU ÖNCESİ DUALAR VE RAHATLATICI SESLER
Sakinleştirici Hristiyan uyku hikayeleri, Mezurlar ve dinlendirici gece duaları ile zihninizi sakinleştirerek Tanrı'nın huzurunda derin bir uykuya dalın.

✍️ DUA GÜNLÜĞÜ, LİSTE VE ALIŞKANLIK TAKİBİ
Ruhsal yaşamınızı organize edin. Kişisel dua günlüğünüze yazın, dua listenizi yönetin ve kabul olan dualarınızı kaydedin. Günlük dua serinizi (streak) takip ederek istikrarınızı koruyun.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
HER GÜN SİZİNLE OLAN TEMALAR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━
• Kaygı ve Stresi Aşma
• Şifa, Umut, Şükran ve Bağışlanma
• Günün Ayeti (Sosyal medyada paylaşılabilir)
• Kutsal Rozari ve Novenalar
• Uyku Duaları ve Gece Tefekkürleri
• Günün Azizi ve Litürjik Takvim

━━━━━━━━━━━━━━━━━━━━━━━━━━━
TÜM İNANANLAR İÇİN
━━━━━━━━━━━━━━━━━━━━━━━━━━━
Sanctus; Katolik, Protestan, Ortodoks veya Tanrı'yı arayan tüm Hristiyanlar için sevgiyle hazırlandı.

Sanctus: Günlük Dua & İncil uygulamasını hemen indirin, Tanrı'nın Sözü ile hayatınıza huzur katın."""

# Base Spanish Full Description
BASE_ES_FULL = """Encuentra paz, crea un hábito de oración diario y acércate a Dios con Sanctus.

Sanctus es tu aplicación cristiana todo en uno de oración, devocional bíblico y meditación, diseñada para calmar tu corazón, superar la ansiedad y conectar con la Santa Biblia todos los días. Ya sea que necesites una oración matutina, el Versículo del Día, rezar el Santo Rosario, la Liturgia de las Horas o audios relajantes para dormir en la paz de Dios, Sanctus guía tu caminar con Cristo.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
CARACTERÍSTICAS PRINCIPALES
━━━━━━━━━━━━━━━━━━━━━━━━━━━

✨ ORACIÓN DIARIA Y DEVOCIONAL
Comienza cada mañana con oraciones cristianas guiadas, devocionales diarios y versículos bíblicos inspiradores para tu crecimiento espiritual. Cultiva la gratitud y vive con propósito divino.

📖 SANTA BIBLIA Y REFLEXIONES BÍBLICAS
Lee y medita la Palabra de Dios en cualquier lugar. Profundiza tu fe con lecturas de la Biblia, planes devocionales y reflexiones del Antiguo y Nuevo Testamento.

🧘 MEDITACIÓN CRISTIANA Y PAZ
Mindfulness bíblico arraigado en la Palabra de Dios. Reduce el estrés, alivia la ansiedad y encuentra paz interior con meditaciones guiadas y reflexiones de alabanza.

📿 EL SANTO ROSARIO (GUIADO)
Reza los misterios del Santo Rosario católico (Gozosos, Dolorosos, Gloriosos y Luminosos) completamente sin conexión. Sigue las cuentas fácilmente con nuestra guía interactiva.

⏰ LITURGIA DE LAS HORAS Y BREVIARIO
Únete a la Iglesia universal en la oración diaria:
• Laudes (Oración de la mañana)
• Tercia, Sexta, Nona (Oraciones del día)
• Vísperas (Oración de la tarde)
• Completas (Oración de la noche) — paz antes de dormir
Configura recordatorios diarios para tus horas canónicas.

🌙 HISTORIAS BÍBLICAS PARA DORMIR Y PAZ NOCTURNA
Duerme profundamente en la presencia de Dios con hermosas reflexiones bíblicas para dormir, Salmos de calma y oraciones nocturnas reconfortantes.

✍️ DIARIO DE ORACIÓN Y SEGUIMIENTO DE HÁBITOS
Organiza tu vida espiritual. Escribe en tu diario de oración privado, guarda tus peticiones y registra oraciones respondidas. Mantén tu racha diaria de oración.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
TEMAS PARA CADA DÍA:
━━━━━━━━━━━━━━━━━━━━━━━━━━━
• Vencer la ansiedad y el estrés
• Sanación, esperanza, gratitud y perdón
• Versículo del Día (imágenes listas para compartir)
• Santo Rosario y Novenas católicas
• Oraciones de la noche y sueño reparador
• Santo del Día y Calendario Litúrgico

━━━━━━━━━━━━━━━━━━━━━━━━━━━
PARA TODOS LOS CRISTIANOS
━━━━━━━━━━━━━━━━━━━━━━━━━━━
Sanctus está creada para todos los creyentes: católicos, protestantes, evangélicos, ortodoxos y todos los que buscan a Dios.

Descarga Sanctus: Oración Diaria y Biblia hoy y transforma tu vida espiritual."""

# Base Portuguese Full Description
BASE_PT_FULL = """Encontre paz, construa um hábito diário de oração e aproxime-se de Deus com Sanctus.

Sanctus é o seu aplicativo cristão completo de oração diária, devocional da Bíblia Sagrada e meditação cristã, desenvolvido para acalmar a mente, vencer a ansiedade e fortalecer sua fé todos os dias. Seja com orações matinais, o Versículo do Dia, a oração do Santo Terço / Rosário, a Liturgia das Horas ou reflexões para dormir em paz, Sanctus guia seus passos com Cristo.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
RECURSOS PRINCIPAIS
━━━━━━━━━━━━━━━━━━━━━━━━━━━

✨ ORAÇÃO DIÁRIA E DEVOCIONAL
Comece todas as manhãs com orações guiadas, devocionais diários e versículos bíblicos inspiradores para o seu crescimento espiritual. Cultive a gratidão e viva com propósito sagrado.

📖 BÍBLIA SAGRADA E REFLEXÕES
Leia e medite na Palavra de Deus em qualquer lugar. Aprofunde sua fé com planos de leitura e reflexões bíblicas do Antigo e Novo Testamento.

🧘 MEDITAÇÃO CRISTÃ E PAZ INTERIOR
Mindfulness bíblico baseado na Palavra de Deus. Reduza o estresse, alivie a ansiedade e encontre verdadeira paz espiritual através de orações guiadas e momentos de adoração.

📿 O SANTO ROSÁRIO / TERÇO (GUIADO)
Reze os mistérios do Santo Terço Católico (Gozosos, Dolorosos, Gloriosos e Luminosos) totalmente offline. Acompanhe as contas com facilidade.

⏰ LITURGIA DAS HORAS E BREVIÁRIO
Una-se à Igreja universal na oração diária com todas as horas canônicas:
• Laudes (Oração da manhã)
• Terça, Sexta, Noa (Orações do dia)
• Vésperas (Oração da tarde)
• Completas (Oração da noite) — descanso sereno
Defina lembretes personalizados para nunca perder seus momentos de oração.

🌙 HISTÓRIAS BÍBLICAS PARA DORMIR E DESCANSO
Adormeça na paz de Deus com Salmos de consolo, orações da noite e reflexões bíblicas relaxantes para acalmar a mente.

✍️ DIÁRIO DE ORAÇÃO E HÁBITOS
Escreva no seu diário de oração particular, organize suas intenções e registre orações atendidas. Mantenha seu hábito diário (streak) ativo.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
PARA TODOS OS CRISTÃOS
━━━━━━━━━━━━━━━━━━━━━━━━━━━
Feito com carinho para católicos, evangélicos, protestantes e todos os fiéis que buscam a Deus.

Baixe Sanctus: Oração Diária e Bíblia agora e viva com mais paz e propósito."""

# Base French Full Description
BASE_FR_FULL = """Trouvez la paix intérieure, créez une habitude quotidienne de prière et rapprochez-vous de Dieu avec Sanctus.

Sanctus est votre application chrétienne complète de prière quotidienne, dévotion biblique et méditation chrétienne, conçue pour apaiser votre esprit, surmonter l’anxiété et vous connecter à la Parole de Dieu chaque jour. Qu’il s’agisse d’une prière du matin, du Verset du Jour, du Saint Rosaire, de la Liturgie des Heures ou de prières apaisantes pour le sommeil, Sanctus vous accompagne dans votre vie spirituelle avec le Christ.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
POINTS FORTS POUR VOTRE FOI
━━━━━━━━━━━━━━━━━━━━━━━━━━━

✨ PRIÈRE QUOTIDIENNE & DÉVOTION
Commencez chaque matinée avec des prières guidées, des dévotions quotidiennes et des versets bibliques inspirants pour nourrir votre foi et cultiver la paix.

📖 SAINTE BIBLE & MÉDITATION DE LA PAROLE
Lisez et méditez les Saintes Écritures où que vous soyez. Approfondissez votre chemin de foi avec des réflexions sur l’Ancien et le Nouveau Testament.

🧘 MÉDITATION CHRÉTIENNE & PAIX
Une méditation biblique enracinée dans la Parole de Dieu. Réduisez le stress, soulagez l’anxiété et trouvez le calme grâce à des méditations guidées et des temps de louange.

📿 LE SAINT ROSAIRE (GUIDÉ HORS-LIGNE)
Priez les mystères du Saint Rosaire (Joyeux, Douloureux, Glorieux, Lumineux) totalement hors ligne. Suivez facilement votre chapelet grâce à notre guide interactif.

⏰ LITURGIE DES HEURES & BRÉVIAIRE
Rejoignez la prière de l'Église universelle tout au long de la journée :
• Laudes (Prière du matin)
• Tierce, Sexte, None (Prières de la journée)
• Vêpres (Prière du soir)
• Complies (Prière de la nuit) — paix avant le sommeil
Activez vos rappels quotidiens pour vos heures canoniques.

🌙 HISTOIRES BIBLIQUES POUR DORMIR & NUIT SEREINE
Endormez-vous paisiblement dans la présence de Dieu grâce à des psaumes de réconfort et des prières relaxantes du soir.

✍️ CARNET DE PRIÈRE & SUIVI SPIRITUEL
Notez vos intentions de prière, tenez votre journal spirituel et célébrez les grâces reçues. Développez une habitude régulière de prière chaque jour.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
POUR TOUS LES CHRÉTIENS
━━━━━━━━━━━━━━━━━━━━━━━━━━━
Sanctus s'adresse à tous les croyants : catholiques, protestants, évangéliques, orthodoxes et toute personne en quête de Dieu.

Téléchargez Sanctus : Prière Quotidienne & Bible et commencez aujourd'hui votre chemin vers la paix divine."""

# Base German Full Description
BASE_DE_FULL = """Finden Sie inneren Frieden, entwickeln Sie eine tägliche Gebetsgewohnheit und wachsen Sie im Glauben mit Sanctus.

Sanctus ist Ihre christliche Gebets-, Bibel- und Meditations-App, die Ihnen hilft, im Alltag innezuhalten, Sorgen und Ängste zu überwinden und täglich Gottes Wort zu begegnen. Ob Morgenlob, der Vers des Tages, das Beten des Heiligen Rosenkranzes, das Stundengebet oder beruhigende Bibel-Schlafgeschichten für eine friedliche Nacht – Sanctus begleitet Ihren geistlichen Weg mit Christus.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
HAUPTFUNKTIONEN FÜR IHREN GLAUBEN
━━━━━━━━━━━━━━━━━━━━━━━━━━━

✨ TÄGLICHES GEBET & ANDACHT
Beginnen Sie jeden Tag mit geführten christlichen Gebeten, täglichen Andachten und inspirierenden Bibelversen für Ihr geistliches Wachstum.

📖 HEILIGE BIBEL & SCHRIFTBETRACHTUNG
Lesen und betrachten Sie Gottes Wort überall. Vertiefen Sie Ihren Glauben durch Bibellesepläne und Betrachtungen zum Alten und Neuen Testament.

🧘 CHRISTLICHE MEDITATION & FRIEDEN
Biblische Achtsamkeit, verwurzelt in der Heiligen Schrift. Bauen Sie Stress und Angst ab und finden Sie göttlichen Frieden durch geführte Kontemplation und Gebet.

📿 DER HEILIGE ROSENKRANZ (GEFÜHRT)
Beten Sie die Geheimnisse des Heiligen Rosenkranzes (freudenreich, schmerzhaft, glorreich, lichtreich) jederzeit offline. Folgen Sie den Perlen mühelos mit unserer interaktiven Hilfe.

⏰ STUNDENGEBET (BREVIER)
Schließen Sie sich dem weltweiten Gebet der Kirche an:
• Laudes (Morgengebet)
• Terz, Sext, Non (Tageszeitengebete)
• Vesper (Abendgebet)
• Komplet (Nachtgebet) — Geborgenheit vor dem Einschlafen
Stellen Sie persönliche Erinnerungen für Ihre Gebetszeiten ein.

🌙 BIBEL-SCHLAFGESCHICHTEN & NACHTRUHE
Finden Sie erholsamen Schlaf in Gottes Nähe mit beruhigenden Psalmen und entspannenden Abendgebeten.

✍️ GEBETSTAGEBUCH & GEBETSGEWOHNHEIT
Halten Sie persönliche Gebetsanliegen fest, führen Sie Ihr Gebetstagebuch und danken Sie für erhörte Gebete. Verfolgen Sie Ihre tägliche Gebetsserie.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
FÜR ALLE CHRISTEN
━━━━━━━━━━━━━━━━━━━━━━━━━━━
Sanctus ist offen für alle Gläubigen – Katholiken, Protestanten, Freikirchler, Orthodoxe und alle Suchenden.

Laden Sie Sanctus: Tägliches Gebet & Bibel jetzt herunter und erfahren Sie Gottes Frieden im Alltag."""

# Base Italian Full Description
BASE_IT_FULL = """Trova la pace, coltiva l'abitudine della preghiera quotidiana e avvicinati a Dio con Sanctus.

Sanctus è la tua app cristiana completa per la preghiera quotidiana, la lettura della Sacra Bibbia e la meditazione, creata per donare serenità al cuore, superare l'ansia e vivere ogni giorno alla luce della Parola di Dio. Dalla preghiera del mattino al Versetto del Giorno, dalla recita del Santo Rosario alla Liturgia delle Ore, fino all'audio sereno per il sonno, Sanctus guida il tuo cammino con Cristo.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
CARATTERISTICHE PRINCIPALI
━━━━━━━━━━━━━━━━━━━━━━━━━━━

✨ PREGHIERA QUOTIDIANA E DEVOZIONE
Inizia ogni mattina con preghiere guidate, meditazioni quotidiane e versetti biblici ispiratori per la tua crescita spirituale. Coltiva la gratitudine verso Dio.

📖 SACRA BIBBIA E RIFLESSIONI
Leggi e medita la Sacra Scrittura ovunque ti trovi. Approfondisci la tua fede con letture e riflessioni tratte dall'Antico e Nuovo Testamento.

🧘 MEDITAZIONE CRISTIANA E PACE INTERIORE
Mindfulness biblica radicata nella Parola di Dio. Riduci lo stress, calma l'ansia e ritrova la vera pace interiore con meditazioni cristiane guidate.

📿 IL SANTO ROSARIO (GUIDATO)
Recita i misteri del Santo Rosario (Gaudiosi, Dolorosi, Gloriosi, Luminosi) completamente offline con una corona interattiva semplice e intuitiva.

⏰ LITURGIA DELLE ORE E BREVIARIO
Unisciti alla preghiera della Chiesa universale con tutte le ore canoniche:
• Lodi mattutine
• Terza, Sesta, Nona (Preghiere del giorno)
• Vespri (Preghiera della sera)
• Compieta (Preghiera della notte) — pace prima di dormire
Imposta promemoria personalizzati per non perdere mai i momenti di preghiera.

🌙 STORIE DELLA BIBBIA PER DORMIRE
Addormentati nella pace di Dio con Salmi rilassanti e preghiere serali create per distendere i pensieri e donare serenità.

✍️ DIARIO DI PREGHIERA E OBIETTIVI
Scrivi nel tuo diario personale, gestisci la tua lista di intenzioni e registra le preghiere esaudite. Mantieni viva la tua serie di preghiera quotidiana.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
PER TUTTI I CRISTIANI
━━━━━━━━━━━━━━━━━━━━━━━━━━━
Pensata per cattolici, protestanti, ortodossi e per chiunque desideri rafforzare il proprio legame con Dio.

Scarica Sanctus: Preghiera Quotidiana e Bibbia oggi e ritrova pace nella tua vita."""

# Base Russian Full Description
BASE_RU_FULL = """Обретите душевный покой, укрепите молитвенную привычку и станьте ближе к Богу с Sanctus.

Sanctus — это универсальное христианское приложение для ежедневной молитвы, чтения Библии и духовной медитации. Оно создано, чтобы успокоить сердце, преодолеть тревогу и ежедневно пребывать в Слове Божьем. Утренние молитвы, Стих дня, Святой Розарий, Литургия часов и умиротворяющие библейские истории для сна — Sanctus станет вашим надежным спутником на пути с Христом.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
ГЛАВНЫЕ ВОЗМОЖНОСТИ
━━━━━━━━━━━━━━━━━━━━━━━━━━━

✨ ЕЖЕДНЕВНАЯ МОЛИТВА И РАЗМЫШЛЕНИЯ
Начинайте утро с христианских молитв, вдохновляющих библейских стихов и духовных размышлений. Наполняйте каждый день благодарностью и святым смыслом.

📖 СВЯЩЕННОЕ ПИСАНИЕ И БИБЛИЯ
Читайте и слушайте Слово Божье в любое время. Углубляйте веру с чтениями Ветхого и Нового Заветов.

🧘 ХРИСТИАНСКАЯ МЕДИТАЦИЯ И ПОКОЙ
Библейская осознанность, основанная на Писании. Снижайте уровень стресса, побеждайте тревогу и находите мир в Божьем присутствии.

📿 СВЯТОЙ РОЗАРИЙ (ОФФЛАЙН)
Молитесь тайны Розария (Радостные, Скорбные, Славные, Светлые) полностью офлайн с удобными интерактивными четками.

⏰ ЛИТУРГИЯ ЧАСОВ И МОЛИТВОСЛОВ
Молитесь вместе со всей Церковью в канонические часы:
• Утреня (Лауды)
• Дневные часы (Терция, Секста, Нона)
• Вечерня
• Повечерие (молитва перед сном)
Настраивайте напоминания, чтобы не пропускать часы молитвы.

🌙 БИБЛЕЙСКИЕ ИСТОРИИ НА НОЧЬ И СПОКОЙНЫЙ СОН
Засыпайте с миром в сердце под умиротворяющие Псалмы и вечерние молитвы.

✍️ ДНЕВНИК МОЛИТВЫ И ТРЕКЕР
Ведите личный молитвенный дневник, составляйте списки прошений и отмечайте отвеченные молитвы. Сохраняйте непрерывную серию молитвенных дней.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
ДЛЯ ВСЕХ ХРИСТИАН
━━━━━━━━━━━━━━━━━━━━━━━━━━━
Создано с любовью для католиков, протестантов, православных и каждого, кто ищет Бога.

Скачайте Sanctus: Молитва и Библия прямо сейчас и обретите Божий мир в душе."""

# Base Arabic Full Description
BASE_AR_FULL = """ابحث عن السلام الروحي، وابنِ عادة الصلاة اليومية، واقترب أكثر من الله مع Sanctus.

Sanctus هو تطبيقك المسيحي الشامل للصلاة اليومية، وتأملات الكتاب المقدس، والتأمل الروحي، المصمم لتهدئة القلب والتغلب على القلق والتواصل مع كلمة الله يومياً. سواء كنت تبحث عن صلاة الصباح، أو آية اليوم، أو المسبحة الوردية المقدسة، أو صلاة الساعات (الأجبية / الليتورجيا)، أو قصص النوم الهادئة، يرافقك تطبيق Sanctus في مسيرتك الإيمانية مع يسوع المسيح.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
أهم المزايا الروحية
━━━━━━━━━━━━━━━━━━━━━━━━━━━

✨ صلاة يومية وتأملات صباحية
ابدأ كل صباح مع صلوات مسيحية موجهة، وتأملات يومية، وآيات ملهمة من الإنجيل لنموك الروحي.

📖 الكتاب المقدس وتأملات كلمة الله
اقرأ واستمع لكلمة الله في كل مكان. عمّق إيمانك من خلال قراءات وتأملات في العهدين القديم والجديد.

🧘 تأمل مسيحي وسلام داخلي
سكينة روحية متجذرة في كلمة الله. تخلص من التوتر والقلق، واستبدل الضغوط بسلام الله الفائق.

📿 المسبحة الوردية المقدسة (بدون إنترنت)
تأمل في أسرار الوردية المقدسة (الفرح، الحزن، المجد، النور) في أي وقت دون اتصال بالإنترنت مع مسبحة تفاعلية ميسرة.

⏰ صلاة الساعات والليتورجيا
شارك الكنيسة الجامعة في الصلاة اليومية عبر الساعات القانونية مع منبهات خاصة لصلواتك.

🌙 قصص الكتاب المقدس للنوم وراحة البال
تمتع بنوم هادئ في محضر الرب مع مزامير السكينة وصلوات المساء المريحة.

✍️ مفكرة الصلاة ومتابعة العادات الروحية
سجل طلباتك وصلواتك المستجابة في مذكرتك الخاصة، وتابع التزامك بالصلاة اليومية.

━━━━━━━━━━━━━━━━━━━━━━━━━━━
لكل المؤمنين بالمسيح
━━━━━━━━━━━━━━━━━━━━━━━━━━━
متاح لجميع المسيحيين: كاثوليك، أرثوذكس، بروتستانت، ولكل باحث عن نعمة الله.

حمّل تطبيق Sanctus: صلاة مسيحية والكتاب المقدس الآن واختبر السلام الإلهي."""

print("Loaded base full descriptions.")
