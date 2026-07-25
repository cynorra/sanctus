package com.sanctus.christian.prayer.data.seed;

import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import java.util.ArrayList;
import java.util.List;

public class SaintSeedData {

    public static List<SaintEntity> getAll() {
        List<SaintEntity> list = new ArrayList<>();

        list.add(saint("Saint Joseph", "March 19",
            "Patron of workers, the universal Church, and fathers. Husband of the Blessed Virgin Mary and foster father of Jesus. A carpenter in Nazareth, he protected the Holy Family during their flight into Egypt and raised the Son of God with love and humility.",
            "Workers, fathers, families, the Church, dying people"));

        list.add(saint("Saint Patrick", "March 17",
            "Patron saint of Ireland, known for bringing Christianity to the Irish people in the 5th century. Born in Roman Britain, he was captured by Irish pirates and enslaved. After escaping, he returned as a bishop to evangelize Ireland.",
            "Ireland, engineers, excluded people"));

        list.add(saint("Saint Francis of Assisi", "October 4",
            "Founder of the Franciscan Order, lover of poverty and nature. He received the stigmata — the wounds of Christ — in 1224. His Canticle of the Sun is one of the first known works of Italian literature.",
            "Animals, ecology, merchants, Italy"));

        list.add(saint("Saint Teresa of Ávila", "October 15",
            "Doctor of the Church and mystic. She reformed the Carmelite Order and wrote profound spiritual classics including The Interior Castle and The Way of Perfection. Her teachings on contemplative prayer remain foundational.",
            "Headache sufferers, Spain, chess players"));

        list.add(saint("Saint Anthony of Padua", "June 13",
            "Doctor of the Church, Franciscan friar, and one of the most beloved saints. Known for his powerful preaching and miraculous deeds. He is invoked for finding lost items and aiding the poor.",
            "Lost items, poor people, travelers, Portugal, Brazil"));

        list.add(saint("Saint Peter the Apostle", "June 29",
            "Chief of the Apostles, to whom Jesus gave the keys of the Kingdom of Heaven. First Bishop of Rome and first Pope. Martyred by crucifixion upside-down under Emperor Nero around 64–68 AD.",
            "Fishermen, the papacy, net-makers, Rome"));

        list.add(saint("Saint Paul the Apostle", "June 29",
            "Apostle to the Gentiles, missionary, and author of much of the New Testament. Born Saul of Tarsus, he persecuted early Christians before his dramatic conversion on the road to Damascus. He traveled the Mediterranean world founding churches.",
            "Writers, theologians, missionaries, Greece"));

        list.add(saint("Saint Mary Magdalene", "July 22",
            "A devoted disciple of Jesus Christ. She was present at the crucifixion and burial of Jesus, and was the first to witness the Resurrection — earning her the title 'Apostle to the Apostles.'",
            "Contemplatives, women, perfumers, converts"));

        list.add(saint("Saint Augustine of Hippo", "August 28",
            "Doctor of the Church and philosopher. His autobiography Confessions is one of the greatest spiritual writings in history. He wrestled deeply with sin before his conversion, writing: 'Our heart is restless until it rests in You.'",
            "Theologians, brewers, printers, sore eyes"));

        list.add(saint("Saint Thérèse of Lisieux", "October 1",
            "The 'Little Flower,' she entered Carmel at 15 and died of tuberculosis at 24. Her autobiography Story of a Soul introduced her 'little way' — doing small things with great love — which transformed Catholic spirituality worldwide.",
            "Missions, France, AIDS sufferers, florists"));

        list.add(saint("Saint Nicholas", "December 6",
            "Bishop of Myra in the 4th century, known for his generosity to the poor. He secretly provided dowries for three impoverished sisters, throwing bags of gold through their window at night — a story that inspired the legend of Santa Claus.",
            "Children, sailors, merchants, Russia"));

        list.add(saint("Saint John the Baptist", "June 24",
            "Prophet and precursor of Jesus Christ. He baptized Jesus in the Jordan River, crying 'Behold the Lamb of God!' He was beheaded by Herod Antipas at the request of Salome.",
            "Baptism, Jordan, tailors, farriers"));

        list.add(saint("Saint Catherine of Siena", "April 29",
            "Doctor of the Church, mystic, and political reformer. She successfully urged Pope Gregory XI to return the papacy to Rome from Avignon. Her spiritual masterwork The Dialogue remains a treasury of mystical theology.",
            "Italy, nurses, fire prevention, Rome"));

        list.add(saint("Saint Ignatius of Loyola", "July 31",
            "Founder of the Society of Jesus (Jesuits). A Spanish soldier who underwent a spiritual conversion during recovery from battle wounds, he composed the Spiritual Exercises — a 30-day program of prayer still used worldwide.",
            "Jesuits, soldiers, retreats, Spain"));

        list.add(saint("Saint Dominic", "August 8",
            "Founder of the Order of Preachers (Dominicans) and promoter of the Rosary. He established a mendicant order dedicated to preaching and scholarship to combat heresy through truth rather than force.",
            "Astronomers, falsely accused, scientists"));

        list.add(saint("Saint Clare of Assisi", "August 11",
            "Founder of the Order of Poor Ladies (Poor Clares). Inspired by Francis of Assisi, she embraced radical poverty. She is patroness of television because she reportedly witnessed the Christmas Mass on the wall of her cell when too ill to attend.",
            "Television, eye disease, needleworkers"));

        list.add(saint("Saint John Paul II", "October 22",
            "Pope from 1978 to 2005, one of the most traveled and influential popes in history. He played a pivotal role in ending Communism in Poland. Authored numerous encyclicals and beatified and canonized more saints than any previous pope.",
            "World Youth Day, Poland"));

        list.add(saint("Saint Benedict of Nursia", "July 11",
            "Founder of Western monasticism and the Benedictine Order. His Rule of Saint Benedict — which calls monks to a life of ora et labora (pray and work) — became the foundation of monastic life in the Western Church.",
            "Europe, students, monks, farmers"));

        list.add(saint("Saint Luke the Evangelist", "October 18",
            "Physician, companion of Paul, and author of the third Gospel and the Acts of the Apostles. His Gospel emphasizes mercy, the Holy Spirit, prayer, and the role of women. Tradition holds he painted the first icon of the Virgin Mary.",
            "Physicians, surgeons, artists, painters"));

        list.add(saint("Saint John the Evangelist", "December 27",
            "The 'beloved disciple' and youngest Apostle. Author of the fourth Gospel, three epistles, and the Book of Revelation. He stood at the foot of the Cross and was entrusted with the care of the Virgin Mary.",
            "Authors, theologians, art dealers, Turkey"));

        list.add(saint("Saint Stephen", "December 26",
            "The first Christian martyr (protomartyr). One of the seven deacons chosen by the Apostles, he was stoned to death after delivering a powerful speech before the Sanhedrin. Saul of Tarsus (later Paul) witnessed his martyrdom.",
            "Deacons, masons, headache sufferers"));

        list.add(saint("Saint Valentine", "February 14",
            "A Roman priest and physician martyred in 269 AD. He is associated with love and courtly romance, though little historical detail is known. His feast day became linked to romantic love in 14th-century English poetry.",
            "Love, engaged couples, happy marriages, beekeepers"));

        list.add(saint("Saint Christopher", "July 25",
            "A giant of a man who carried a child across a river, only to discover the child was Christ carrying the weight of the world. His name means 'Christ-bearer.' Despite being removed from the general Roman calendar in 1969, he remains beloved.",
            "Travelers, drivers, surfers, storms"));

        list.add(saint("Blessed Virgin Mary (Assumption)", "August 15",
            "The Assumption of Mary celebrates the dogma that Mary was assumed body and soul into heavenly glory. One of the most ancient feasts of the Church, it is celebrated with great solemnity as a holy day of obligation in most countries.",
            "All humanity, the Church"));

        list.add(saint("Saint Thomas Aquinas", "January 28",
            "Doctor of the Church and the greatest theologian of the Middle Ages. His masterwork Summa Theologiae synthesized Aristotelian philosophy with Christian theology and remains the foundation of Catholic intellectual tradition.",
            "Students, schools, universities, Italy"));

        list.add(saint("Saint Teresa of Calcutta", "September 5",
            "Founder of the Missionaries of Charity. She devoted her life to the poorest of the poor in Calcutta's slums. Her simple words — 'Not all of us can do great things. But we can do small things with great love' — echo worldwide.",
            "World Youth Day, missionaries of charity"));

        list.add(saint("Saint George", "April 23",
            "Soldier-martyr venerated since the 3rd century. The legend of slaying a dragon to rescue a princess symbolizes the triumph of good over evil and faith over fear. Patron of England, Georgia, and many other nations.",
            "England, soldiers, scouts, farmers"));

        list.add(saint("Saint Elizabeth of Hungary", "November 17",
            "Princess of Hungary who gave up royal life to care for the poor. Widowed at 20, she joined the Third Order of Saint Francis and spent her remaining years in service to the sick and destitute, dying at 24.",
            "The poor, hospitals, bakers, nurses"));

        list.add(saint("Saint Faustina Kowalska", "October 5",
            "Polish mystic and apostle of Divine Mercy. Her diary, Divine Mercy in My Soul, records visions of Jesus who asked her to spread the message of His mercy. The Divine Mercy devotion now reaches millions worldwide.",
            "Mercy, Poland, Divine Mercy devotion"));

        list.add(saint("Archangel Michael", "September 29",
            "Chief among the angels, defender of the Church, and patron of soldiers. He leads the heavenly army in the defeat of Satan in the Book of Revelation. The Prayer to Saint Michael is said after Mass in many parishes.",
            "Soldiers, police, paramedics, grocers"));

        // ── TURKISH TRANSLATIONS (same canonical feastDate keys as above) ──
        list.add(saint("Aziz Yusuf", "March 19",
            "İşçilerin, evrensel Kilise'nin ve babaların koruyucusu. Kutsal Bakire Meryem'in eşi ve İsa'nın manevi babası. Nasıra'da bir marangozdu; Kutsal Aile'yi Mısır'a kaçışları sırasında korudu ve Tanrı'nın Oğlu'nu sevgi ve alçakgönüllülükle büyüttü.",
            "İşçiler, babalar, aileler, Kilise, ölmekte olanlar", "tr"));

        list.add(saint("Aziz Patrick", "March 17",
            "İrlanda'nın koruyucu azizi, 5. yüzyılda Hristiyanlığı İrlanda halkına getirmesiyle tanınır. Roma Britanyası'nda doğdu, İrlandalı korsanlar tarafından esir alınıp köle edildi. Kaçtıktan sonra İrlanda'yı Hristiyanlaştırmak için piskopos olarak geri döndü.",
            "İrlanda, mühendisler, dışlanmışlar", "tr"));

        list.add(saint("Assisili Aziz Francis", "October 4",
            "Fransisken Tarikatı'nın kurucusu, yoksulluk ve doğa sevgisiyle tanınır. 1224'te Mesih'in yaralarını, yani stigmata'yı aldı. Güneş Ezgisi, İtalyan edebiyatının bilinen ilk eserlerinden biridir.",
            "Hayvanlar, ekoloji, tüccarlar, İtalya", "tr"));

        list.add(saint("Avilalı Azize Teresa", "October 15",
            "Kilise Doktoru ve mistik. Karmelit Tarikatı'nı reforme etti ve İç Kale ile Kemal Yolu gibi derin ruhsal klasikler yazdı. Tefekkür duası üzerine öğretileri hâlâ temel niteliktedir.",
            "Baş ağrısı çekenler, İspanya, satranç oyuncuları", "tr"));

        list.add(saint("Padualı Aziz Anthony", "June 13",
            "Kilise Doktoru, Fransisken keşişi ve en sevilen azizlerden biri. Güçlü vaazları ve mucizevi işleriyle tanınır. Kayıp eşyaların bulunması ve yoksullara yardım için kendisine dua edilir.",
            "Kayıp eşyalar, yoksullar, yolcular, Portekiz, Brezilya", "tr"));

        list.add(saint("Havari Aziz Petrus", "June 29",
            "İsa'nın Göklerin Egemenliği'nin anahtarlarını verdiği Havarilerin başı. Roma'nın ilk Piskoposu ve ilk Papa. İmparator Nero döneminde, MS 64–68 civarında baş aşağı çarmıha gerilerek şehit edildi.",
            "Balıkçılar, papalık, ağ yapımcıları, Roma", "tr"));

        list.add(saint("Havari Aziz Pavlus", "June 29",
            "Milletlere Havari, misyoner ve Yeni Ahit'in büyük bölümünün yazarı. Tarsuslu Saul olarak doğdu, Şam yolunda yaşadığı çarpıcı dönüşümden önce ilk Hristiyanlara zulmetti. Akdeniz dünyasını dolaşarak kiliseler kurdu.",
            "Yazarlar, ilahiyatçılar, misyonerler, Yunanistan", "tr"));

        list.add(saint("Azize Meryem Magdalena", "July 22",
            "İsa Mesih'in sadık bir öğrencisi. İsa'nın çarmıha gerilişinde ve gömülüşünde hazır bulundu ve Dirilişe tanıklık eden ilk kişi oldu — bu ona 'Havarilerin Havarisi' unvanını kazandırdı.",
            "Tefekkür edenler, kadınlar, parfümcüler, din değiştirenler", "tr"));

        list.add(saint("Hippolu Aziz Augustinus", "August 28",
            "Kilise Doktoru ve filozof. Otobiyografisi İtiraflar, tarihin en büyük ruhsal eserlerinden biridir. Dönüşümünden önce günahla derin bir mücadele verdi ve şöyle yazdı: 'Yüreğimiz, Sende huzur bulana dek huzursuzdur.'",
            "İlahiyatçılar, biracılar, matbaacılar, göz ağrısı", "tr"));

        list.add(saint("Lisieuxlu Azize Thérèse", "October 1",
            "'Küçük Çiçek' olarak bilinir, 15 yaşında Karmel'e girdi ve 24 yaşında veremden öldü. Otobiyografisi Bir Ruhun Öyküsü, dünya çapında Katolik maneviyatını dönüştüren 'küçük yolunu' — küçük şeyleri büyük sevgiyle yapmayı — tanıttı.",
            "Misyonlar, Fransa, AIDS hastaları, çiçekçiler", "tr"));

        list.add(saint("Aziz Nikolaos", "December 6",
            "4. yüzyılda Myra Piskoposu, yoksullara cömertliğiyle tanınır. Üç fakir kız kardeşe gizlice çeyiz sağladı, geceleyin pencerelerinden altın torbaları attı — bu hikaye Noel Baba efsanesine ilham verdi.",
            "Çocuklar, denizciler, tüccarlar, Rusya", "tr"));

        list.add(saint("Vaftizci Yahya", "June 24",
            "İsa Mesih'in habercisi ve peygamberi. Şeria Nehri'nde İsa'yı vaftiz etti ve 'İşte Tanrı Kuzusu!' diye haykırdı. Salome'nin isteği üzerine Herod Antipas tarafından başı kesilerek şehit edildi.",
            "Vaftiz, Şeria Nehri, terziler, nalbantlar", "tr"));

        list.add(saint("Sienalı Azize Catherine", "April 29",
            "Kilise Doktoru, mistik ve siyasi reformcu. Papa XI. Gregorius'u papalığı Avignon'dan Roma'ya geri getirmeye ikna etmeyi başardı. Ruhsal başyapıtı Diyalog, mistik teolojinin bir hazinesi olmaya devam ediyor.",
            "İtalya, hemşireler, yangın önleme, Roma", "tr"));

        list.add(saint("Loyolalı Aziz Ignatius", "July 31",
            "İsa Cemiyeti'nin (Cizvitler) kurucusu. Savaş yaralarından iyileşirken ruhsal bir dönüşüm yaşayan İspanyol bir askerdi; dünya çapında hâlâ kullanılan 30 günlük bir dua programı olan Ruhsal Alıştırmalar'ı kaleme aldı.",
            "Cizvitler, askerler, ruhsal çekilişler, İspanya", "tr"));

        list.add(saint("Aziz Dominic", "August 8",
            "Vaizler Tarikatı'nın (Dominikenler) kurucusu ve Tespih'in yaygınlaştırıcısı. Sapkınlığa karşı güç yerine gerçekle mücadele etmek için vaaz ve bilime adanmış bir dilenci tarikatı kurdu.",
            "Astronomlar, haksız suçlananlar, bilim insanları", "tr"));

        list.add(saint("Assisili Azize Clare", "August 11",
            "Yoksul Hanımlar Tarikatı'nın (Clarisler) kurucusu. Assisili Francis'ten ilham alarak radikal yoksulluğu benimsedi. Hastalığı nedeniyle kiliseye gidemediğinde Noel Ayini'ni hücresinin duvarında gördüğü rivayet edildiğinden televizyonun koruyucu azizesidir.",
            "Televizyon, göz hastalıkları, elişi yapanlar", "tr"));

        list.add(saint("II. Yuhanna Pavlus", "October 22",
            "1978'den 2005'e kadar Papa'lık yaptı, tarihin en çok seyahat eden ve etkili papalarından biri. Polonya'da Komünizmin sona ermesinde kilit bir rol oynadı. Sayısız genelge yazdı ve önceki hiçbir papadan daha fazla azizi kutsadı ve azizlik ilan etti.",
            "Dünya Gençlik Günü, Polonya", "tr"));

        list.add(saint("Nursialı Aziz Benedictus", "July 11",
            "Batı manastırcılığının ve Benediktin Tarikatı'nın kurucusu. Keşişleri ora et labora (dua et ve çalış) yaşamına çağıran Aziz Benedictus Kuralı, Batı Kilisesi'nde manastır yaşamının temeli oldu.",
            "Avrupa, öğrenciler, keşişler, çiftçiler", "tr"));

        list.add(saint("İncilci Aziz Luka", "October 18",
            "Hekim, Pavlus'un yol arkadaşı, üçüncü İncil'in ve Elçilerin İşleri'nin yazarı. İncili merhameti, Kutsal Ruh'u, duayı ve kadınların rolünü vurgular. Geleneğe göre Bakire Meryem'in ilk ikonasını o resmetti.",
            "Hekimler, cerrahlar, sanatçılar, ressamlar", "tr"));

        list.add(saint("İncilci Aziz Yuhanna", "December 27",
            "'Sevgili öğrenci' ve en genç Havari. Dördüncü İncil'in, üç mektubun ve Vahiy Kitabı'nın yazarı. Çarmıhın dibinde durdu ve Bakire Meryem'in bakımıyla görevlendirildi.",
            "Yazarlar, ilahiyatçılar, sanat tüccarları, Türkiye", "tr"));

        list.add(saint("Aziz İstefanos", "December 26",
            "İlk Hristiyan şehidi (protomartir). Havariler tarafından seçilen yedi diyakozdan biri, Sanhedrin önünde güçlü bir konuşma yaptıktan sonra taşlanarak öldürüldü. Tarsuslu Saul (sonradan Pavlus) şehadetine tanık oldu.",
            "Diyakozlar, duvarcılar, baş ağrısı çekenler", "tr"));

        list.add(saint("Aziz Valentinus", "February 14",
            "MS 269'da şehit edilen Romalı bir rahip ve hekim. Aşk ve romantizmle ilişkilendirilir, ancak tarihsel ayrıntısı azdır. Bayram günü, 14. yüzyıl İngiliz şiirinde romantik aşkla bağlantılı hale geldi.",
            "Aşk, nişanlı çiftler, mutlu evlilikler, arıcılar", "tr"));

        list.add(saint("Aziz Christophoros", "July 25",
            "Bir çocuğu nehirden taşıyan dev cüsseli bir adam; sonradan çocuğun dünyanın ağırlığını taşıyan Mesih olduğunu keşfetti. Adı 'Mesih taşıyıcısı' anlamına gelir. 1969'da genel Roma takviminden çıkarılmasına rağmen hâlâ sevilir.",
            "Yolcular, sürücüler, sörfçüler, fırtınalar", "tr"));

        list.add(saint("Kutsal Bakire Meryem (Göğe Alınış)", "August 15",
            "Meryem'in Göğe Alınışı, Meryem'in beden ve ruhuyla göksel görkeme alındığı dogmasını kutlar. Kilise'nin en eski bayramlarından biridir; çoğu ülkede zorunlu bir bayram olarak büyük görkemle kutlanır.",
            "Tüm insanlık, Kilise", "tr"));

        list.add(saint("Aziz Thomas Aquinas", "January 28",
            "Kilise Doktoru ve Orta Çağ'ın en büyük ilahiyatçısı. Başyapıtı Summa Theologiae, Aristoteles felsefesini Hristiyan teolojisiyle birleştirdi ve Katolik entelektüel geleneğinin temeli olmaya devam ediyor.",
            "Öğrenciler, okullar, üniversiteler, İtalya", "tr"));

        list.add(saint("Kalkütalı Azize Teresa", "September 5",
            "Sevgi Misyonerleri'nin kurucusu. Hayatını Kalküta'nın gecekondularındaki en yoksullara adadı. Sade sözleri — 'Hepimiz büyük şeyler yapamayız. Ama küçük şeyleri büyük sevgiyle yapabiliriz.' — dünya çapında yankılanmaya devam ediyor.",
            "Dünya Gençlik Günü, sevgi misyonerleri", "tr"));

        list.add(saint("Aziz Georgios", "April 23",
            "3. yüzyıldan beri saygı gösterilen asker-şehit. Bir prensesi kurtarmak için ejderha öldürme efsanesi, iyinin kötüye ve imanın korkuya karşı zaferini simgeler. İngiltere, Gürcistan ve birçok başka ulusun koruyucusudur.",
            "İngiltere, askerler, izciler, çiftçiler", "tr"));

        list.add(saint("Macaristanlı Azize Elizabeth", "November 17",
            "Yoksullara bakmak için kraliyet hayatından vazgeçen Macaristan Prensesi. 20 yaşında dul kaldı, Aziz Francis'in Üçüncü Tarikatı'na katıldı ve kalan yıllarını hastalara ve yoksullara hizmet ederek geçirdi, 24 yaşında öldü.",
            "Yoksullar, hastaneler, fırıncılar, hemşireler", "tr"));

        list.add(saint("Azize Faustina Kowalska", "October 5",
            "Polonyalı mistik ve İlahi Merhamet'in havarisi. Günlüğü, Ruhumda İlahi Merhamet, kendisinden merhametinin mesajını yaymasını isteyen İsa'nın görümlerini kaydeder. İlahi Merhamet ibadeti bugün dünya çapında milyonlara ulaşıyor.",
            "Merhamet, Polonya, İlahi Merhamet ibadeti", "tr"));

        list.add(saint("Başmelek Mikail", "September 29",
            "Meleklerin başı, Kilise'nin savunucusu ve askerlerin koruyucusu. Vahiy Kitabı'nda Şeytan'ın yenilgisinde göksel orduya öncülük eder. Aziz Mikail Duası birçok cemaatte Ayin sonrasında okunur.",
            "Askerler, polisler, sağlık görevlileri, bakkallar", "tr"));

        return list;
    }

    private static SaintEntity saint(String name, String feastDate, String description, String patronOf) {
        return saint(name, feastDate, description, patronOf, "en");
    }

    private static SaintEntity saint(String name, String feastDate, String description, String patronOf, String language) {
        SaintEntity e = new SaintEntity();
        e.name = name;
        e.feastDate = feastDate;
        e.description = description;
        e.patronOf = patronOf;
        e.language = language;
        return e;
    }
}
