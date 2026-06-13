package com.sanctus.christian.prayer.data.seed;

import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import java.util.ArrayList;
import java.util.List;

/** Additional prayers for the 20 new language locales */
public class PrayerSeedDataExtra {

    public static List<PrayerEntity> getAll() {
        List<PrayerEntity> list = new ArrayList<>();

        // ── GERMAN ──────────────────────────────────────────────
        list.add(p("Vater Unser", "morning",
            "Vater unser im Himmel,\ngeheiligt werde dein Name.\nDein Reich komme.\nDein Wille geschehe,\nwie im Himmel so auf Erden.\nUnser tägliches Brot gib uns heute.\nUnd vergib uns unsere Schuld,\nwie auch wir vergeben unsern Schuldigern.\nUnd führe uns nicht in Versuchung,\nsondern erlöse uns von dem Bösen.\nAmen.", "de"));

        list.add(p("Gegrüßet seist du, Maria", "rosary",
            "Gegrüßet seist du, Maria,\nvoll der Gnade,\nder Herr ist mit dir.\nDu bist gebenedeit unter den Frauen,\nund gebenedeit ist die Frucht\ndeines Leibes, Jesus.\nHeilige Maria, Mutter Gottes,\nbitte für uns Sünder\njetzt und in der Stunde unseres Todes.\nAmen.", "de"));

        list.add(p("Ehre sei dem Vater", "rosary",
            "Ehre sei dem Vater\nund dem Sohn\nund dem Heiligen Geist,\nwie es war im Anfang,\njetzt und immerdar\nund von Ewigkeit zu Ewigkeit.\nAmen.", "de"));

        list.add(p("Morgengebet", "morning",
            "Herr, mein Gott,\nch danke dir, dass du mich diese Nacht\nbehütet hast.\nIch bitte dich, beschütze mich auch heute,\nlass mich nach deinem Willen leben\nund führe mich auf dem Weg zum ewigen Leben.\nAmen.", "de"));

        list.add(p("Abendgebet", "evening",
            "Herr, ich danke dir für diesen Tag.\nVergib mir alle Sünden,\ndie ich heute begangen habe,\nim Denken, Reden und Handeln.\nBehüte mich in dieser Nacht\nund lass mich morgen neu beginnen.\nAmen.", "de"));

        // ── FRENCH ──────────────────────────────────────────────
        list.add(p("Notre Père", "morning",
            "Notre Père, qui es aux cieux,\nque ton nom soit sanctifié,\nque ton règne vienne,\nque ta volonté soit faite\nsur la terre comme au ciel.\nDonne-nous aujourd\'hui notre pain de ce jour.\nPardonne-nous nos offenses,\ncomme nous pardonnons aussi\nà ceux qui nous ont offensés.\nEt ne nous soumets pas à la tentation,\nmais délivre-nous du mal.\nAmen.", "fr"));

        list.add(p("Je vous salue, Marie", "rosary",
            "Je vous salue, Marie,\npleine de grâce,\nle Seigneur est avec vous.\nVous êtes bénie entre toutes les femmes,\net Jésus, le fruit de vos entrailles,\nest béni.\nSainte Marie, Mère de Dieu,\npriez pour nous, pauvres pécheurs,\nmaintenant et à l\'heure de notre mort.\nAmen.", "fr"));

        list.add(p("Gloire au Père", "rosary",
            "Gloire au Père,\net au Fils,\net au Saint-Esprit.\nComme il était au commencement,\nmaintenant et toujours,\ndans les siècles des siècles.\nAmen.", "fr"));

        list.add(p("Prière du Matin", "morning",
            "Mon Dieu, je vous offre cette journée.\nAcceptez mes prières, mes travaux,\nmes joies et mes peines,\nen union avec votre Cœur Sacré.\nGuide mes pas sur le chemin\nqui mène à la vie éternelle.\nAmen.", "fr"));

        // ── ITALIAN ──────────────────────────────────────────────
        list.add(p("Padre Nostro", "morning",
            "Padre nostro, che sei nei cieli,\nsia santificato il tuo nome.\nVenga il tuo regno.\nSia fatta la tua volontà,\ncome in cielo così in terra.\nDacci oggi il nostro pane quotidiano.\nE rimetti a noi i nostri debiti,\ncome noi li rimettiamo ai nostri debitori.\nE non ci indurre in tentazione,\nma liberaci dal male.\nAmen.", "it"));

        list.add(p("Ave Maria", "rosary",
            "Ave, o Maria, piena di grazia,\nil Signore è con te.\nTu sei benedetta fra le donne\ne benedetto è il frutto\ndel tuo seno, Gesù.\nSanta Maria, Madre di Dio,\nprega per noi peccatori,\nadesso e nell\'ora della nostra morte.\nAmen.", "it"));

        list.add(p("Gloria al Padre", "rosary",
            "Gloria al Padre,\nal Figlio\ned allo Spirito Santo.\nCome era in principio,\nor è e sempre sarà,\nnei secoli dei secoli.\nAmen.", "it"));

        // ── POLISH ──────────────────────────────────────────────
        list.add(p("Ojcze Nasz", "morning",
            "Ojcze nasz, któryś jest w niebie,\nświęć się imię Twoje.\nPrzyjdź królestwo Twoje,\nbądź wola Twoja jako w niebie,\ntak i na ziemi.\nChleba naszego powszedniego\ndaj nam dzisiaj.\nI odpuść nam nasze winy,\njako i my odpuszczamy naszym winowajcom.\nI nie wódź nas na pokuszenie,\nale nas zbaw ode złego.\nAmen.", "pl"));

        list.add(p("Zdrowaś Mario", "rosary",
            "Zdrowaś Maryjo, łaski pełna,\nPan z Tobą.\nBłogosławionaś Ty między niewiastami\ni błogosławiony owoc żywota Twojego, Jezus.\nŚwięta Maryjo, Matko Boża,\nmódl się za nami grzesznymi\nteraz i w godzinę śmierci naszej.\nAmen.", "pl"));

        list.add(p("Chwała Ojcu", "rosary",
            "Chwała Ojcu i Synowi,\ni Duchowi Świętemu.\nJak była na początku,\nteraz i zawsze,\ni na wieki wieków.\nAmen.", "pl"));

        // ── UKRAINIAN ──────────────────────────────────────────────
        list.add(p("Отче Наш", "morning",
            "Отче наш, що єси на небесах,\nхай святиться ім'я Твоє,\nхай прийде Царство Твоє,\nхай буде воля Твоя,\nяк на небі, так і на землі.\nХліб наш насущний дай нам сьогодні,\nі прости нам провини наші,\nяк і ми прощаємо винуватцям нашим,\nі не введи нас у спокусу,\nале визволи нас від лукавого.\nАминь.", "uk"));

        list.add(p("Радуйся, Маріє", "rosary",
            "Радуйся, Маріє, благодаті повна,\nГосподь з Тобою.\nБлагословенна Ти між жінками\nі благословенний плід утроби Твоєї, Ісус.\nСвята Маріє, Мати Божа,\nмолися за нас грішних\nнині і в годину смерті нашої.\nАминь.", "uk"));

        // ── RUSSIAN ──────────────────────────────────────────────
        list.add(p("Отче Наш", "morning",
            "Отче наш, сущий на небесах!\nДа святится имя Твоё;\nда приидет Царствие Твоё;\nда будет воля Твоя\nи на земле, как на небе;\nхлеб наш насущный дай нам на сей день;\nи прости нам долги наши,\nкак и мы прощаем должникам нашим;\nи не введи нас в искушение,\nно избавь нас от лукавого.\nАминь.", "ru"));

        list.add(p("Богородице Дево, радуйся", "rosary",
            "Богородице Дево, радуйся,\nблагодатная Марие, Господь с Тобою;\nблагословена Ты в женах,\nи благословен Плод чрева Твоего,\nяко Спаса родила еси душ наших.\nАминь.", "ru"));

        list.add(p("Молитва утренняя", "morning",
            "Господи Боже мой,\nблагодарю Тебя за этот день.\nПомоги мне прожить его\nпо Твоей воле,\nсохранить мир в сердце\nи служить ближним с любовью.\nАминь.", "ru"));

        // ── ROMANIAN ──────────────────────────────────────────────
        list.add(p("Tatăl Nostru", "morning",
            "Tatăl nostru, care ești în ceruri,\nsfințească-se numele Tău.\nVie împărăția Ta.\nFacă-se voia Ta,\nprecum în cer, așa și pe pământ.\nPâinea noastră cea de toate zilele\ndă-ne-o nouă astăzi.\nȘi ne iartă nouă greșelile noastre,\nprecum și noi iertăm\ngreșiților noștri.\nȘi nu ne duce pe noi în ispită,\nci ne izbăvește de cel rău.\nAmin.", "ro"));

        list.add(p("Bucură-te, Marie", "rosary",
            "Bucură-te, Marie,\ncela ce ești plină de har,\nDomnul este cu tine.\nBinecuvântată ești tu între femei\nși binecuvântat este rodul pântecelui tău, Iisus.\nSfântă Marie, Maica lui Dumnezeu,\nroagă-te pentru noi, păcătoșii,\nacum și în ceasul morții noastre.\nAmin.", "ro"));

        // ── DUTCH ──────────────────────────────────────────────
        list.add(p("Onze Vader", "morning",
            "Onze Vader, die in de hemelen zijt,\nuw naam worde geheiligd.\nUw koninkrijk kome.\nUw wil geschiede,\ngelijk in de hemel, alzo ook op de aarde.\nGeef ons heden ons dagelijks brood.\nEn vergeef ons onze schulden,\ngelijk ook wij vergeven aan onze schuldenaren.\nEn leid ons niet in verzoeking,\nmaar verlos ons van de boze.\nAmen.", "nl"));

        list.add(p("Wees gegroet, Maria", "rosary",
            "Wees gegroet, Maria,\nvol van genade.\nDe Heer is met U.\nGij zijt de gezegende onder de vrouwen\nen gezegend is Jezus,\nde vrucht van uw schoot.\nHeilige Maria, Moeder van God,\nbid voor ons zondaars,\nnu en in het uur van onze dood.\nAmen.", "nl"));

        // ── GREEK ──────────────────────────────────────────────
        list.add(p("Πάτερ ἡμῶν", "morning",
            "Πάτερ ἡμῶν ὁ ἐν τοῖς οὐρανοῖς,\nἁγιασθήτω τὸ ὄνομά σου.\nἐλθέτω ἡ βασιλεία σου.\nγενηθήτω τὸ θέλημά σου,\nὡς ἐν οὐρανῷ καὶ ἐπὶ τῆς γῆς.\nτὸν ἄρτον ἡμῶν τὸν ἐπιούσιον\nδὸς ἡμῖν σήμερον.\nκαὶ ἄφες ἡμῖν τὰ ὀφειλήματα ἡμῶν,\nὡς καὶ ἡμεῖς ἀφήκαμεν\nτοῖς ὀφειλέταις ἡμῶν.\nκαὶ μὴ εἰσενέγκῃς ἡμᾶς εἰς πειρασμόν,\nἀλλὰ ῥῦσαι ἡμᾶς ἀπὸ τοῦ πονηροῦ.\nΑμήν.", "el"));

        list.add(p("Χαῖρε Μαρία", "rosary",
            "Χαῖρε Μαρία, κεχαριτωμένη,\nο Κύριος μετά Σου.\nΕυλογημένη Συ εν γυναιξί\nκαι ευλογημένος ο καρπός\nτης κοιλίας Σου, Ιησούς.\nΑγία Μαρία, Μήτερ Θεού,\nπρέσβευε υπέρ ημών των αμαρτωλών,\nνυν και εν τη ώρα του θανάτου ημών.\nΑμήν.", "el"));

        // ── FILIPINO ──────────────────────────────────────────────
        list.add(p("Ama Namin", "morning",
            "Ama namin, sumasalangit Ka,\nsambahin ang ngalan Mo.\nmapasaamin ang kaharian Mo.\nsundin ang loob Mo\ndito sa lupa para nang sa langit.\nbigyan Mo kami ngayon ng aming kakanin sa araw-araw.\nat patawarin Mo kami sa aming mga sala,\npara nang pagpapatawad namin\nsa nagkakasala sa amin.\nat huwag Mo kaming ipahintulot\nsa tukso.\nat iadya Mo kami sa lahat ng masama.\nAmen.", "fil"));

        list.add(p("Aba, Ginoong Maria", "rosary",
            "Aba, Ginoong Maria, napupuno ka ng grasya,\nang Panginoon ay sumasaiyo.\nIkaw ay pinagpala sa babaeng lahat\nat pinagpala naman ang iyong anak na si Hesus.\nSantang Maria, Ina ng Diyos,\nipanalangin mo kaming makasalanan\nngayon at kung kami\'y mamamatay.\nAmen.", "fil"));

        list.add(p("Luwalhati sa Ama", "rosary",
            "Luwalhati sa Ama,\nat sa Anak,\nat sa Espiritu Santo.\nKapara ng sa simula,\ngayon at magpakailanman,\nmagpasawalang hanggan.\nAmen.", "fil"));

        // ── CHINESE ──────────────────────────────────────────────
        list.add(p("天主经", "morning",
            "我们在天上的父，\n愿人都尊你的名为圣，\n愿你的国降临，\n愿你的旨意行在地上，\n如同行在天上。\n我们日用的饮食，今日赐给我们。\n免我们的债，\n如同我们免了人的债。\n不叫我们遇见试探，\n救我们脱离凶恶。\n阿们。", "zh"));

        list.add(p("万福玛利亚", "rosary",
            "万福玛利亚，\n你充满圣宠，\n主与你同在，\n你在女人中受赞颂，\n你的亲子耶稣同受赞颂。\n天主圣母玛利亚，\n求你现在和我们临终时，\n为我们罪人祈求天主。\n阿们。", "zh"));

        list.add(p("光荣经", "rosary",
            "愿光荣归于父，\n及子，\n及圣神，\n起初如何，\n今日亦然，\n直到永远，\n世世代代，\n阿们。", "zh"));

        // ── HINDI ──────────────────────────────────────────────
        list.add(p("हमारे पिता", "morning",
            "हे हमारे पिता, जो स्वर्ग में है,\nतेरा नाम पवित्र माना जाए।\nतेरा राज्य आए।\nतेरी इच्छा जैसे स्वर्ग में,\nवैसे पृथ्वी पर भी पूरी हो।\nहमारी दिनभर की रोटी आज हमें दे।\nहमारे अपराध क्षमा कर,\nजैसे हम अपने अपराधियों को क्षमा करते हैं।\nहमें परीक्षा में मत डाल,\nबल्कि बुराई से बचा।\nआमीन।", "hi"));

        list.add(p("प्रणाम, मरियम", "rosary",
            "प्रणाम, मरियम, अनुग्रह से परिपूर्ण,\nप्रभु तुम्हारे साथ है।\nतुम स्त्रियों में धन्य हो,\nऔर धन्य है तुम्हारे गर्भ का फल ईसा।\nपवित्र मरियम, ईश्वर की माँ,\nहमारे लिए प्रार्थना कर,\nअभी और मृत्यु की घड़ी में।\nआमीन।", "hi"));

        // ── KOREAN ──────────────────────────────────────────────
        list.add(p("주님의 기도", "morning",
            "하늘에 계신 우리 아버지,\n아버지의 이름이 거룩히 빛나시며,\n아버지의 나라가 오시며,\n아버지의 뜻이 하늘에서와 같이\n땅에서도 이루어지소서.\n오늘 저희에게 일용할 양식을 주시고,\n저희에게 잘못한 이를\n저희가 용서하오니,\n저희 죄를 용서하시고,\n저희를 유혹에 빠지지 않게 하시고,\n악에서 구하소서.\n아멘.", "ko"));

        list.add(p("성모송", "rosary",
            "은총이 가득하신 마리아님,\n기뻐하소서.\n주님께서 함께 계시니,\n여인 중에 복되시며\n태중의 아들 예수님 또한 복되시나이다.\n천주의 성모 마리아님,\n이제와 저희 죽을 때에\n저희 죄인을 위하여 빌어주소서.\n아멘.", "ko"));

        list.add(p("영광송", "rosary",
            "영광이 성부와 성자와 성령께,\n처음과 같이 이제와 항상\n영원히. 아멘.", "ko"));

        // ── INDONESIAN ──────────────────────────────────────────────
        list.add(p("Bapa Kami", "morning",
            "Bapa kami yang ada di surga,\ndikuduskanlah nama-Mu.\nDatanglah kerajaan-Mu.\nJadilah kehendak-Mu\ndi bumi seperti di surga.\nBerikanlah kami pada hari ini\nmakanan kami yang secukupnya.\nAmpunilah kami akan kesalahan kami,\nseperti kami juga mengampuni\norang yang bersalah kepada kami.\nJanganlah membawa kami ke dalam pencobaan,\ntetapi lepaskanlah kami dari yang jahat.\nAmen.", "id"));

        list.add(p("Salam Maria", "rosary",
            "Salam Maria, penuh rahmat,\nTuhan sertamu.\nEngkau terberkati di antara wanita\ndan terberkati buah tubuhmu, Yesus.\nSanta Maria, Bunda Allah,\ndoakanlah kami yang berdosa ini,\nsekarang dan waktu kami mati.\nAmen.", "id"));

        list.add(p("Kemuliaan", "rosary",
            "Kemuliaan kepada Bapa,\ndan Putera,\ndan Roh Kudus.\nSeperti pada permulaan,\nsekarang, selalu,\ndan sepanjang segala abad.\nAmen.", "id"));

        // ── VIETNAMESE ──────────────────────────────────────────────
        list.add(p("Kinh Lạy Cha", "morning",
            "Lạy Cha chúng con ở trên trời,\nchúng con nguyện danh Cha cả sáng,\nnước Cha trị đến,\ný Cha thể hiện dưới đất\ncũng như trên trời.\nXin Cha cho chúng con\nhôm nay lương thực hằng ngày,\nvà tha nợ chúng con\nnhư chúng con cũng tha\nkẻ có nợ chúng con.\nXin chớ để chúng con sa chước cám dỗ,\nnhưng cứu chúng con cho khỏi sự dữ.\nAmen.", "vi"));

        list.add(p("Kinh Kính Mừng", "rosary",
            "Kính mừng Maria đầy ơn phúc,\nĐức Chúa Trời ở cùng Bà,\nBà có phúc lạ hơn mọi người nữ,\nvà Giêsu con lòng Bà\ngồm phúc lạ.\nThánh Maria Đức Mẹ Chúa Trời,\ncầu cho chúng con là kẻ có tội,\nkhi nay và trong giờ lâm tử.\nAmen.", "vi"));

        list.add(p("Kinh Sáng Danh", "rosary",
            "Sáng danh Đức Chúa Cha,\nvà Đức Chúa Con,\nvà Đức Chúa Thánh Thần.\nBây giờ và hằng có,\nvà đời đời chẳng cùng.\nAmen.", "vi"));

        // ── SWAHILI ──────────────────────────────────────────────
        list.add(p("Baba Yetu", "morning",
            "Baba yetu uliye mbinguni,\nJina lako litakaswe.\nUfalme wako uje.\nMatakwa yako yatimizwe\nDuniani kama mbinguni.\nUtupe leo mkate wetu\nwa kila siku.\nUtusamehe makosa yetu,\nkama sisi tunavyowasamehe\nwale wanaotukosea.\nUsitutie majaribuni,\nbali utuokoe na yule mwovu.\nAmeni.", "sw"));

        list.add(p("Salamu Maria", "rosary",
            "Salamu Maria, umejawa neema,\nBwana yuko nawe.\nUmebarikiwa miongoni mwa wanawake\nna amebarikiwa\nmatunda ya tumbo lako, Yesu.\nMariam Mtakatifu, Mama wa Mungu,\ntuombee sisi wenye dhambi\nsasa na wakati wa kifo chetu.\nAmeni.", "sw"));

        // ── AMHARIC ──────────────────────────────────────────────
        list.add(p("አባታችን", "morning",
            "በሰማያት የምትኖር አባታችን ሆይ፣\nስምህ ይቀደስ፤\nመንግሥትህ ትምጣ፤\nፈቃድህ በሰማይ እንደሆነ\nበምድርም ይሁን፤\nዕለታዊ እንጀራችንን ዛሬ ስጠን፤\nእኛ ደግሞ የበደሉንን\nይቅር እንደምንል፣\nበደላችንን ይቅር በለን፤\nወደ ፈተና አትምራን፣\nከክፉ ግን አድነን።\nአሜን።", "am"));

        list.add(p("ሰላም ለኪ ማርያም", "rosary",
            "ሰላም ለኪ ማርያም፣\nጸጋ የሞላሽ፣\nጌታ ከአንቺ ጋር ነው።\nከሴቶች ሁሉ ትብረከሪ፣\nከፍሬ ማኅፀንሽ\nኢየሱስ ክርስቶስ ይብረክ።\nቅዱስ ማርያም፣ የእግዚአብሔር እናት፣\nለኛ ኃጢአተኞች ለምኝልን\nአሁንና ስንሞት ጊዜ።\nአሜን።", "am"));

        // ── HUNGARIAN ──────────────────────────────────────────────
        list.add(p("Miatyánk", "morning",
            "Mi Atyánk, aki a mennyekben vagy,\nszenteltessék meg a te neved.\nJöjjön el a te országod.\nLegyen meg a te akaratod,\nmiképpen mennyben,\núgy a földön is.\nMindennapi kenyerünket add meg nekünk ma.\nÉs bocsásd meg a mi vétkeinket,\nmiképpen mi is megbocsátunk\naz ellenünk vétkezőknek.\nÉs ne vigy minket a kísértésbe,\nde szabadíts meg a gonosztól.\nÁmen.", "hu"));

        list.add(p("Üdvözlégy Mária", "rosary",
            "Üdvözlégy Mária, kegyelemmel teljes,\naz Úr van teveled.\nÁldott vagy te az asszonyok között,\nés áldott a te méhednek gyümölcse, Jézus.\nAsszonyunk Szűz Mária, Istennek szent Anyja,\nimádkozzál érettünk, bűnösökért,\nmost és halálunk óráján.\nÁmen.", "hu"));

        // ── CZECH ──────────────────────────────────────────────
        list.add(p("Otče náš", "morning",
            "Otče náš, jenž jsi na nebesích,\nposvěť se jméno tvé.\nPřijď království tvé.\nBuď vůle tvá\njako v nebi, tak i na zemi.\nChléb náš vezdejší dej nám dnes.\nA odpusť nám naše viny,\njako i my odpouštíme\nnaším viníkům.\nA neuveď nás v pokušení,\nale zbav nás od zlého.\nAmen.", "cs"));

        list.add(p("Zdrávas Maria", "rosary",
            "Zdrávas, Maria, milostiplná,\nPán s tebou,\njsi požehnaná mezi ženami\na požehnaný plod života tvého, Ježíš.\nSvatá Maria, Matko Boží,\npros za nás hříšné\nnyní i v hodinu smrti naší.\nAmen.", "cs"));

        // ── SLOVAK ──────────────────────────────────────────────
        list.add(p("Otče náš", "morning",
            "Otče náš, ktorý si na nebesiach,\nposväť sa meno tvoje,\nprij nám kráľovstvo tvoje,\nbuď vôľa tvoja ako v nebi,\ntak i na zemi.\nChlieb náš každodenný daj nám dnes\na odpusť nám naše viny,\nako i my odpúšťame\nnaším vinníkom.\nA neuveď nás do pokušenia,\nale zbav nás Zlého.\nAmen.", "sk"));

        list.add(p("Zdravas' Mária", "rosary",
            "Zdravas', Mária, milosti plná,\nPán s tebou.\nRequiescat in pace benedictá si medzi ženami\na požehnaný je plod života tvojho, Ježiš.\nSvätá Mária, Matka Božia,\npros za nás hriešnych\nteraz i v hodinu smrti našej.\nAmen.", "sk"));

        // ── CROATIAN ──────────────────────────────────────────────
        list.add(p("Oče naš", "morning",
            "Oče naš, koji jesi na nebesima,\nsveti se ime tvoje.\nDođi kraljevstvo tvoje.\nBudi volja tvoja\nkako na nebu tako i na zemlji.\nKruh naš svagdašnji daj nam danas.\nI otpusti nam duge naše\nkako i mi otpuštamo\ndužnicima našim.\nI ne uvedi nas u napast,\nego izbavi nas od zla.\nAmen.", "hr"));

        list.add(p("Zdravo Marijo", "rosary",
            "Zdravo Marijo, milosti puna,\nGospodin s tobom.\nBlagovana si ti između žena\ni blagovan je plod utrobe tvoje, Isus.\nSveta Marijo, Majko Božja,\nmoli za nas grješnike\nsada i u čas smrti naše.\nAmen.", "hr"));

        return list;
    }

    private static PrayerEntity p(String name, String category, String content, String lang) {
        PrayerEntity e = new PrayerEntity();
        e.name = name;
        e.category = category;
        e.content = content;
        e.language = lang;
        e.isFavorite = false;
        e.lastReadAt = 0;
        return e;
    }
}
