package com.sanctus.christian.prayer.data.seed;

import com.sanctus.christian.prayer.data.local.entity.CalendarEventEntity;
import java.util.ArrayList;
import java.util.List;

public class CalendarSeedData {

    public static List<CalendarEventEntity> getAll() {
        List<CalendarEventEntity> list = new ArrayList<>();

        // ── JANUARY ──
        e(list, "Mary, Mother of God", "01-01", "CHRISTMAS", "white", "Holy Day of Obligation. New Year's Day and Solemnity of Mary, the Holy Mother of God.", true, true, true);
        e(list, "Epiphany of the Lord", "01-06", "CHRISTMAS", "white", "The visit of the Magi to the infant Jesus. Celebrated as a holy day of obligation in many countries.", true, true, true);
        e(list, "Baptism of the Lord", "01-12", "CHRISTMAS", "white", "Feast marking the Baptism of Jesus by John the Baptist in the Jordan River. Closes the Christmas season.", true, false, false);
        e(list, "Saint Thomas Aquinas", "01-28", "ORDINARY", "white", "Feast of the Angelic Doctor, greatest philosopher-theologian of the Church.", true, false, false);

        // ── FEBRUARY ──
        e(list, "Presentation of the Lord", "02-02", "ORDINARY", "white", "Candlemas. Jesus is presented in the Temple 40 days after birth. Candles are blessed.", true, false, false);
        e(list, "Saint Blase & Saint Ansgar", "02-03", "ORDINARY", "red", "Optional memorial. Throats are blessed in honour of Saint Blase.", false, false, false);
        e(list, "Saint Valentine", "02-14", "ORDINARY", "red", "Optional memorial of the martyr Valentine, patron of lovers.", false, false, false);
        e(list, "Chair of Saint Peter", "02-22", "ORDINARY", "white", "Feast celebrating the authority of the papacy instituted by Christ.", true, false, false);

        // ── MARCH ──
        e(list, "Saints Perpetua and Felicity", "03-07", "LENT", "white", "Memorial of Saints Perpetua and Felicity, martyred in Carthage.", false, false, false);
        e(list, "Saint Patrick", "03-17", "LENT", "white", "Memorial of the patron saint of Ireland, Apostle to the Irish.", false, false, false);
        e(list, "Saint Joseph, Spouse of Mary", "03-19", "LENT", "white", "Solemnity of Saint Joseph. Patron of the universal Church, workers, and families.", true, true, false);
        e(list, "Annunciation of the Lord", "03-25", "LENT", "white", "Solemnity. The Angel Gabriel announces to Mary that she will conceive and bear the Son of God.", true, true, false);

        // ── MOVABLE FEASTS (relative to Easter Sunday, computed for the current year) ──
        eMovable(list, "Ash Wednesday", -46, "LENT", "purple", "Beginning of Lent. Ashes are received as a sign of penance and mortality.", true, false, false);
        eMovable(list, "Palm Sunday", -7, "LENT", "red", "Beginning of Holy Week. Jesus enters Jerusalem in triumph. Red vestments for Passion Sunday.", true, true, false);
        eMovable(list, "Holy Thursday", -3, "LENT", "white", "Mass of the Lord's Supper. Jesus institutes the Eucharist and the priesthood.", true, true, false);
        eMovable(list, "Good Friday", -2, "LENT", "red", "The Passion and Death of Jesus Christ. Day of fasting and abstinence. No Mass is celebrated.", true, true, false);
        eMovable(list, "Holy Saturday / Easter Vigil", -1, "LENT", "white", "The Easter Vigil is the greatest night of the Church year. New fire, water, and Word.", true, true, false);
        eMovable(list, "Easter Sunday", 0, "EASTER", "white", "Solemnity of the Resurrection of the Lord. Alleluia! The central feast of the Christian year.", true, true, true);
        eMovable(list, "Easter Monday", 1, "EASTER", "white", "Octave of Easter.", false, false, false);
        eMovable(list, "Ascension of the Lord", 39, "EASTER", "white", "Holy Day of Obligation. Jesus ascends to the Father 40 days after Easter.", true, true, true);
        eMovable(list, "Pentecost Sunday", 49, "EASTER", "red", "Solemnity. The Holy Spirit descends on the Apostles. Birthday of the Church.", true, true, true);
        eMovable(list, "Trinity Sunday", 56, "ORDINARY", "white", "Solemnity of the Most Holy Trinity. The central mystery of Christian faith.", true, true, false);
        eMovable(list, "Corpus Christi", 63, "ORDINARY", "white", "Solemnity of the Most Holy Body and Blood of Christ. Eucharistic processions.", true, true, true);
        eMovable(list, "Sacred Heart of Jesus", 68, "ORDINARY", "white", "Solemnity. Devotion to the loving heart of Jesus as a symbol of divine love.", true, true, false);
        eMovable(list, "Immaculate Heart of Mary", 69, "ORDINARY", "white", "Memorial established by Pope Pius XII in 1944.", false, false, false);

        // ── APRIL ──
        e(list, "Saint George", "04-23", "EASTER", "red", "Optional memorial. Patron of England.", false, false, false);
        e(list, "Saint Mark the Evangelist", "04-25", "EASTER", "red", "Feast of the author of the second Gospel.", true, false, false);
        e(list, "Saint Catherine of Siena", "04-29", "EASTER", "white", "Feast of the Doctor of the Church and co-patron of Europe.", true, false, false);

        // ── MAY ──
        e(list, "Saint Joseph the Worker", "05-01", "EASTER", "white", "Optional memorial on International Workers' Day, honouring Joseph's labour.", false, false, false);

        // ── JUNE ──
        e(list, "Saints Peter and Paul", "06-29", "ORDINARY", "red", "Solemnity of the chief Apostles and founders of the Church of Rome.", true, true, true);

        // ── JULY ──
        e(list, "Saint Thomas the Apostle", "07-03", "ORDINARY", "red", "Feast of the Apostle Thomas, who proclaimed 'My Lord and my God!'", true, false, false);
        e(list, "Saint Benedict", "07-11", "ORDINARY", "white", "Feast of the Father of Western Monasticism and Patron of Europe.", true, false, false);
        e(list, "Our Lady of Mount Carmel", "07-16", "ORDINARY", "white", "Optional memorial. Foundation of the Carmelite Order.", false, false, false);
        e(list, "Saint Mary Magdalene", "07-22", "ORDINARY", "white", "Feast (raised from memorial in 2016). Apostle to the Apostles.", true, false, false);
        e(list, "Saint James the Apostle", "07-25", "ORDINARY", "red", "Feast of the son of Zebedee, brother of John, first Apostle martyred.", true, false, false);

        // ── AUGUST ──
        e(list, "Transfiguration of the Lord", "08-06", "ORDINARY", "white", "Feast. Jesus reveals His divine glory to Peter, James, and John on Mount Tabor.", true, false, false);
        e(list, "Saint Dominic", "08-08", "ORDINARY", "white", "Memorial of the founder of the Order of Preachers and promoter of the Rosary.", false, false, false);
        e(list, "Saint Clare of Assisi", "08-11", "ORDINARY", "white", "Memorial of the founder of the Poor Clares, companion of Saint Francis.", false, false, false);
        e(list, "Assumption of the Blessed Virgin Mary", "08-15", "ORDINARY", "white", "Solemnity. Holy Day of Obligation. Mary is taken body and soul into heaven.", true, true, true);
        e(list, "Saint Bartholomew the Apostle", "08-24", "ORDINARY", "red", "Feast of the Apostle also known as Nathanael.", true, false, false);
        e(list, "Saint Augustine of Hippo", "08-28", "ORDINARY", "white", "Memorial of the Doctor of the Church and bishop of Hippo.", false, false, false);
        e(list, "Beheading of John the Baptist", "08-29", "ORDINARY", "red", "Memorial of the martyrdom of the last and greatest of the prophets.", false, false, false);

        // ── SEPTEMBER ──
        e(list, "Birth of the Virgin Mary", "09-08", "ORDINARY", "white", "Feast. One of only three birthdays celebrated by the Church (Jesus, Mary, John the Baptist).", true, false, false);
        e(list, "Exaltation of the Holy Cross", "09-14", "ORDINARY", "red", "Feast celebrating the finding and glorification of the True Cross.", true, false, false);
        e(list, "Our Lady of Sorrows", "09-15", "ORDINARY", "white", "Memorial. The seven sorrows of Mary, also called the Mater Dolorosa.", false, false, false);
        e(list, "Saints Michael, Gabriel, Raphael", "09-29", "ORDINARY", "white", "Feast of the Archangels. Michaelmas.", true, false, false);

        // ── OCTOBER ──
        e(list, "Our Lady of the Rosary", "10-07", "ORDINARY", "white", "Memorial. Commemorates the Battle of Lepanto (1571) and Rosary devotion.", false, false, false);
        e(list, "Saint Teresa of Ávila", "10-15", "ORDINARY", "white", "Memorial of the Doctor of the Church and reformer of Carmel.", false, false, false);
        e(list, "Saint Ignatius of Antioch", "10-17", "ORDINARY", "red", "Memorial of the early bishop and martyr who wrote seven important letters.", false, false, false);
        e(list, "Saint Luke the Evangelist", "10-18", "ORDINARY", "red", "Feast of the physician, companion of Paul, and author of the third Gospel.", true, false, false);
        e(list, "Saint John Paul II", "10-22", "ORDINARY", "white", "Optional memorial of the beloved pope who served from 1978–2005.", false, false, false);
        e(list, "Saints Simon and Jude", "10-28", "ORDINARY", "red", "Feast of the Apostles.", true, false, false);

        // ── NOVEMBER ──
        e(list, "All Saints' Day", "11-01", "ORDINARY", "white", "Solemnity. Holy Day of Obligation. Honour all the saints in heaven.", true, true, true);
        e(list, "All Souls' Day", "11-02", "ORDINARY", "black", "Commemoration of all the faithful departed. The Church prays for the souls in purgatory.", false, false, false);
        e(list, "Saint Charles Borromeo", "11-04", "ORDINARY", "white", "Memorial of the great reformer and Archbishop of Milan.", false, false, false);
        e(list, "Dedication of Lateran Basilica", "11-09", "ORDINARY", "white", "Feast of the mother church of all churches, the cathedral of the Bishop of Rome.", true, false, false);
        e(list, "Saint Martin of Tours", "11-11", "ORDINARY", "white", "Memorial of the soldier-bishop of Tours.", false, false, false);
        e(list, "Saint Elizabeth of Hungary", "11-17", "ORDINARY", "white", "Memorial of the princess who devoted herself to the poor.", false, false, false);
        e(list, "Christ the King", "11-23", "ORDINARY", "white", "Solemnity of Our Lord Jesus Christ, King of the Universe. Last Sunday of the liturgical year.", true, true, false);
        e(list, "Saint Catherine of Alexandria", "11-25", "ORDINARY", "red", "Optional memorial of the 4th-century virgin martyr.", false, false, false);
        e(list, "First Sunday of Advent", "11-30", "ADVENT", "purple", "Beginning of the liturgical year. A season of joyful waiting and preparation for Christmas.", true, false, false);

        // ── DECEMBER ──
        e(list, "Saint Francis Xavier", "12-03", "ADVENT", "white", "Memorial of the great Jesuit missionary to Asia, co-founder of the Jesuits.", false, false, false);
        e(list, "Immaculate Conception", "12-08", "ADVENT", "white", "Solemnity. Holy Day of Obligation. Mary conceived without original sin.", true, true, true);
        e(list, "Our Lady of Guadalupe", "12-12", "ADVENT", "white", "Feast. Mary appeared to Juan Diego in Mexico in 1531. Patron of the Americas.", true, false, false);
        e(list, "Saint Lucy", "12-13", "ADVENT", "red", "Memorial of the Sicilian martyr, patron of the blind.", false, false, false);
        e(list, "Saint John of the Cross", "12-14", "ADVENT", "white", "Memorial of the Carmelite mystic and Doctor of the Church.", false, false, false);
        e(list, "Christmas Eve", "12-24", "ADVENT", "white", "Vigil Mass of the Nativity of the Lord.", true, true, false);
        e(list, "Nativity of the Lord", "12-25", "CHRISTMAS", "white", "Solemnity. Holy Day of Obligation. The birth of Jesus Christ.", true, true, true);
        e(list, "Saint Stephen, First Martyr", "12-26", "CHRISTMAS", "red", "Feast of the protomartyr of the Church.", true, false, false);
        e(list, "Saint John the Evangelist", "12-27", "CHRISTMAS", "white", "Feast of the beloved disciple and author of the fourth Gospel.", true, false, false);
        e(list, "Holy Innocents", "12-28", "CHRISTMAS", "red", "Feast of the children martyred by Herod in his search for the infant Jesus.", true, false, false);
        e(list, "Holy Family of Jesus, Mary and Joseph", "12-29", "CHRISTMAS", "white", "Feast. Model of family life for all Christian families.", true, false, false);
        e(list, "Saint Sylvester I, Pope", "12-31", "CHRISTMAS", "white", "Optional memorial of the Pope during the Council of Nicaea.", false, false, false);

        // ══════════════════════════════════════════════════════════════════
        // TURKISH TRANSLATIONS (same canonical date/easterOffset keys as above)
        // ══════════════════════════════════════════════════════════════════

        // ── JANUARY ──
        e(list, "Meryem, Tanrı'nın Anası", "01-01", "CHRISTMAS", "white", "Zorunlu bayram. Yılbaşı ve Kutsal Tanrı Anası Meryem'in Büyük Bayramı.", true, true, true, "tr");
        e(list, "Rab'bin Görünmesi (Epifani)", "01-06", "CHRISTMAS", "white", "Müneccimlerin bebek İsa'yı ziyareti. Birçok ülkede zorunlu bayram olarak kutlanır.", true, true, true, "tr");
        e(list, "Rab'bin Vaftizi", "01-12", "CHRISTMAS", "white", "İsa'nın Vaftizci Yahya tarafından Şeria Nehri'nde vaftiz edilişini anan bayram. Noel dönemini kapatır.", true, false, false, "tr");
        e(list, "Aziz Thomas Aquinas", "01-28", "ORDINARY", "white", "Melek Doktor'un bayramı, Kilise'nin en büyük filozof-ilahiyatçısı.", true, false, false, "tr");

        // ── FEBRUARY ──
        e(list, "Rab'bin Mabede Sunuluşu", "02-02", "ORDINARY", "white", "Kandil Günü. İsa, doğumundan 40 gün sonra Tapınak'ta sunulur. Mumlar kutsanır.", true, false, false, "tr");
        e(list, "Aziz Blasius ve Aziz Ansgar", "02-03", "ORDINARY", "red", "İsteğe bağlı anma günü. Aziz Blasius onuruna boğazlar kutsanır.", false, false, false, "tr");
        e(list, "Aziz Valentinus", "02-14", "ORDINARY", "red", "Âşıkların koruyucusu şehit Valentinus'un isteğe bağlı anma günü.", false, false, false, "tr");
        e(list, "Aziz Petrus'un Papalık Makamı", "02-22", "ORDINARY", "white", "Mesih tarafından kurulan papalık otoritesini kutlayan bayram.", true, false, false, "tr");

        // ── MARCH ──
        e(list, "Azizeler Perpetua ve Felicitas", "03-07", "LENT", "white", "Kartaca'da şehit edilen Azizeler Perpetua ve Felicitas'ın anma günü.", false, false, false, "tr");
        e(list, "Aziz Patrick", "03-17", "LENT", "white", "İrlanda'nın koruyucu azizi, İrlandalılara Havari, anma günü.", false, false, false, "tr");
        e(list, "Aziz Yusuf, Meryem'in Eşi", "03-19", "LENT", "white", "Aziz Yusuf'un Büyük Bayramı. Evrensel Kilise'nin, işçilerin ve ailelerin koruyucusu.", true, true, false, "tr");
        e(list, "Rab'bin Müjdelenmesi", "03-25", "LENT", "white", "Büyük Bayram. Baş Melek Cebrail, Meryem'e Tanrı'nın Oğlu'nu gebe kalıp doğuracağını müjdeler.", true, true, false, "tr");

        // ── MOVABLE FEASTS ──
        eMovable(list, "Kül Çarşambası", -46, "LENT", "purple", "Büyük Perhiz'in başlangıcı. Tövbe ve ölümlülüğün işareti olarak küller alınır.", true, false, false, "tr");
        eMovable(list, "Hurma Pazarı", -7, "LENT", "red", "Kutsal Hafta'nın başlangıcı. İsa Kudüs'e zaferle girer. Çile Pazarı için kırmızı cübbeler giyilir.", true, true, false, "tr");
        eMovable(list, "Kutsal Perşembe", -3, "LENT", "white", "Rab'bin Akşam Yemeği Ayini. İsa, Efkaristiya'yı ve rahipliği kurar.", true, true, false, "tr");
        eMovable(list, "Kutsal Cuma", -2, "LENT", "red", "İsa Mesih'in Çilesi ve Ölümü. Oruç ve perhiz günü. Ayin kutlanmaz.", true, true, false, "tr");
        eMovable(list, "Kutsal Cumartesi / Paskalya Nöbeti", -1, "LENT", "white", "Paskalya Nöbeti, kilise yılının en büyük gecesidir. Yeni ateş, su ve Söz.", true, true, false, "tr");
        eMovable(list, "Paskalya Pazarı", 0, "EASTER", "white", "Rab'bin Dirilişinin Büyük Bayramı. Alleluya! Hristiyan yılının en merkezi bayramı.", true, true, true, "tr");
        eMovable(list, "Paskalya Pazartesi", 1, "EASTER", "white", "Paskalya'nın Sekizinci Günü.", false, false, false, "tr");
        eMovable(list, "Rab'bin Göğe Yükselişi", 39, "EASTER", "white", "Zorunlu bayram. İsa, Paskalya'dan 40 gün sonra Baba'nın yanına yükselir.", true, true, true, "tr");
        eMovable(list, "Pentekost Pazarı", 49, "EASTER", "red", "Büyük Bayram. Kutsal Ruh, Havariler'in üzerine iner. Kilise'nin doğum günü.", true, true, true, "tr");
        eMovable(list, "Kutsal Üçlübirlik Pazarı", 56, "ORDINARY", "white", "En Kutsal Üçlübirlik'in Büyük Bayramı. Hristiyan imanının merkezi gizemi.", true, true, false, "tr");
        eMovable(list, "Kutsal Efkaristiya Bayramı (Corpus Christi)", 63, "ORDINARY", "white", "İsa Mesih'in En Kutsal Bedeni ve Kanı'nın Büyük Bayramı. Efkaristiya alayları.", true, true, true, "tr");
        eMovable(list, "İsa'nın Kutsal Yüreği", 68, "ORDINARY", "white", "Büyük Bayram. İsa'nın sevgi dolu yüreğine, ilahi sevginin simgesi olarak duyulan bağlılık.", true, true, false, "tr");
        eMovable(list, "Meryem'in Kusursuz Yüreği", 69, "ORDINARY", "white", "Papa XII. Pius tarafından 1944'te kurulan anma günü.", false, false, false, "tr");

        // ── APRIL ──
        e(list, "Aziz Georgios", "04-23", "EASTER", "red", "İsteğe bağlı anma günü. İngiltere'nin koruyucusu.", false, false, false, "tr");
        e(list, "İncilci Aziz Markos", "04-25", "EASTER", "red", "İkinci İncil'in yazarının bayramı.", true, false, false, "tr");
        e(list, "Sienalı Azize Catherine", "04-29", "EASTER", "white", "Kilise Doktoru ve Avrupa'nın ortak koruyucusunun bayramı.", true, false, false, "tr");

        // ── MAY ──
        e(list, "İşçi Aziz Yusuf", "05-01", "EASTER", "white", "Uluslararası İşçi Günü'nde, Yusuf'un emeğini onurlandıran isteğe bağlı anma günü.", false, false, false, "tr");

        // ── JUNE ──
        e(list, "Havariler Petrus ve Pavlus", "06-29", "ORDINARY", "red", "Roma Kilisesi'nin kurucuları, baş Havarilerin Büyük Bayramı.", true, true, true, "tr");

        // ── JULY ──
        e(list, "Havari Aziz Thomas", "07-03", "ORDINARY", "red", "'Rabbim ve Tanrım!' diye haykıran Havari Thomas'ın bayramı.", true, false, false, "tr");
        e(list, "Aziz Benedictus", "07-11", "ORDINARY", "white", "Batı Manastırcılığının Babası ve Avrupa'nın koruyucusunun bayramı.", true, false, false, "tr");
        e(list, "Karmel Dağı'nın Hanımı", "07-16", "ORDINARY", "white", "İsteğe bağlı anma günü. Karmelit Tarikatı'nın kuruluşu.", false, false, false, "tr");
        e(list, "Azize Meryem Magdalena", "07-22", "ORDINARY", "white", "Büyük Bayram (2016'da anma gününden yükseltildi). Havarilerin Havarisi.", true, false, false, "tr");
        e(list, "Havari Büyük Aziz Yakup", "07-25", "ORDINARY", "red", "Zebedi'nin oğlu, Yuhanna'nın kardeşi, şehit edilen ilk Havari'nin bayramı.", true, false, false, "tr");

        // ── AUGUST ──
        e(list, "Rab'bin Başkalaşımı", "08-06", "ORDINARY", "white", "Büyük Bayram. İsa, Tabor Dağı'nda ilahi görkemini Petrus, Yakup ve Yuhanna'ya gösterir.", true, false, false, "tr");
        e(list, "Aziz Dominic", "08-08", "ORDINARY", "white", "Vaizler Tarikatı'nın kurucusu ve Tespih'in yaygınlaştırıcısının anma günü.", false, false, false, "tr");
        e(list, "Assisili Azize Clare", "08-11", "ORDINARY", "white", "Yoksul Hanımlar Tarikatı'nın kurucusu, Aziz Francis'in yol arkadaşının anma günü.", false, false, false, "tr");
        e(list, "Kutsal Bakire Meryem'in Göğe Alınışı", "08-15", "ORDINARY", "white", "Büyük Bayram. Zorunlu bayram. Meryem, beden ve ruhuyla göğe alınır.", true, true, true, "tr");
        e(list, "Havari Aziz Bartolomeus", "08-24", "ORDINARY", "red", "Nathanael olarak da bilinen Havari'nin bayramı.", true, false, false, "tr");
        e(list, "Hippolu Aziz Augustinus", "08-28", "ORDINARY", "white", "Kilise Doktoru ve Hippo piskoposunun anma günü.", false, false, false, "tr");
        e(list, "Vaftizci Yahya'nın Başının Kesilmesi", "08-29", "ORDINARY", "red", "Peygamberlerin sonuncusu ve en büyüğünün şehadetinin anma günü.", false, false, false, "tr");

        // ── SEPTEMBER ──
        e(list, "Bakire Meryem'in Doğumu", "09-08", "ORDINARY", "white", "Büyük Bayram. Kilise'nin kutladığı yalnızca üç doğum gününden biri (İsa, Meryem, Vaftizci Yahya).", true, false, false, "tr");
        e(list, "Kutsal Haç'ın Yüceltilmesi", "09-14", "ORDINARY", "red", "Gerçek Haç'ın bulunuşunu ve yüceltilmesini kutlayan bayram.", true, false, false, "tr");
        e(list, "Acılı Meryem Ana", "09-15", "ORDINARY", "white", "Anma günü. Meryem'in yedi acısı, Mater Dolorosa olarak da bilinir.", false, false, false, "tr");
        e(list, "Başmelekler Mikail, Cebrail ve Rafail", "09-29", "ORDINARY", "white", "Başmeleklerin Büyük Bayramı.", true, false, false, "tr");

        // ── OCTOBER ──
        e(list, "Tespih Hanımı", "10-07", "ORDINARY", "white", "Anma günü. Lepanto Savaşı'nı (1571) ve Tespih ibadetini anar.", false, false, false, "tr");
        e(list, "Avilalı Azize Teresa", "10-15", "ORDINARY", "white", "Kilise Doktoru ve Karmel'in reformcusunun anma günü.", false, false, false, "tr");
        e(list, "Antakyalı Aziz Ignatius", "10-17", "ORDINARY", "red", "Yedi önemli mektup yazan erken dönem piskopos ve şehidin anma günü.", false, false, false, "tr");
        e(list, "İncilci Aziz Luka", "10-18", "ORDINARY", "red", "Hekim, Pavlus'un yol arkadaşı ve üçüncü İncil'in yazarının bayramı.", true, false, false, "tr");
        e(list, "Aziz II. Yuhanna Pavlus", "10-22", "ORDINARY", "white", "1978–2005 yılları arasında görev yapan sevilen papanın isteğe bağlı anma günü.", false, false, false, "tr");
        e(list, "Havariler Simon ve Yahuda", "10-28", "ORDINARY", "red", "Havarilerin bayramı.", true, false, false, "tr");

        // ── NOVEMBER ──
        e(list, "Bütün Azizler Günü", "11-01", "ORDINARY", "white", "Büyük Bayram. Zorunlu bayram. Gökteki tüm azizleri onurlandırır.", true, true, true, "tr");
        e(list, "Bütün Ölüler Günü", "11-02", "ORDINARY", "black", "Tüm imanlı ölülerin anılması. Kilise, Araf'taki ruhlar için dua eder.", false, false, false, "tr");
        e(list, "Aziz Carolus Borromeo", "11-04", "ORDINARY", "white", "Büyük reformcu ve Milano Başpiskoposu'nun anma günü.", false, false, false, "tr");
        e(list, "Laterano Bazilikası'nın Adanması", "11-09", "ORDINARY", "white", "Tüm kiliselerin anası, Roma Piskoposu'nun katedralinin bayramı.", true, false, false, "tr");
        e(list, "Tourslu Aziz Martin", "11-11", "ORDINARY", "white", "Asker-piskopos Martin'in anma günü.", false, false, false, "tr");
        e(list, "Macaristanlı Azize Elizabeth", "11-17", "ORDINARY", "white", "Kendini yoksullara adayan prensesin anma günü.", false, false, false, "tr");
        e(list, "Mesih Kral", "11-23", "ORDINARY", "white", "Evrenin Kralı Rabbimiz İsa Mesih'in Büyük Bayramı. Liturjik yılın son Pazarı.", true, true, false, "tr");
        e(list, "İskenderiyeli Azize Catherine", "11-25", "ORDINARY", "red", "4. yüzyıl bakire şehidinin isteğe bağlı anma günü.", false, false, false, "tr");
        e(list, "Advent'in İlk Pazarı", "11-30", "ADVENT", "purple", "Liturjik yılın başlangıcı. Noel'e sevinçli bekleyiş ve hazırlık dönemi.", true, false, false, "tr");

        // ── DECEMBER ──
        e(list, "Aziz Francis Xavier", "12-03", "ADVENT", "white", "Asya'ya giden büyük Cizvit misyoneri ve Cizvitlerin kurucu ortağının anma günü.", false, false, false, "tr");
        e(list, "Meryem'in Günahsız Gebeliği", "12-08", "ADVENT", "white", "Büyük Bayram. Zorunlu bayram. Meryem, aslî günah olmadan gebe kalındı.", true, true, true, "tr");
        e(list, "Guadalupe Meryem Ana'sı", "12-12", "ADVENT", "white", "Büyük Bayram. Meryem, 1531'de Meksika'da Juan Diego'ya göründü. Amerika kıtasının koruyucusu.", true, false, false, "tr");
        e(list, "Azize Lucia", "12-13", "ADVENT", "red", "Sicilyalı şehit, körlerin koruyucusunun anma günü.", false, false, false, "tr");
        e(list, "Haçlı Aziz Yuhanna", "12-14", "ADVENT", "white", "Karmelit mistik ve Kilise Doktoru'nun anma günü.", false, false, false, "tr");
        e(list, "Noel Arifesi", "12-24", "ADVENT", "white", "Rab'bin Doğuşu'nun Nöbet Ayini.", true, true, false, "tr");
        e(list, "Rab'bin Doğuşu", "12-25", "CHRISTMAS", "white", "Büyük Bayram. Zorunlu bayram. İsa Mesih'in doğumu.", true, true, true, "tr");
        e(list, "İlk Şehit Aziz İstefanos", "12-26", "CHRISTMAS", "red", "Kilise'nin protomartirinin bayramı.", true, false, false, "tr");
        e(list, "İncilci Aziz Yuhanna", "12-27", "CHRISTMAS", "white", "Sevgili öğrenci ve dördüncü İncil'in yazarının bayramı.", true, false, false, "tr");
        e(list, "Masum Yavrular", "12-28", "CHRISTMAS", "red", "Herod tarafından bebek İsa'yı ararken şehit edilen çocukların bayramı.", true, false, false, "tr");
        e(list, "İsa, Meryem ve Yusuf'un Kutsal Ailesi", "12-29", "CHRISTMAS", "white", "Büyük Bayram. Tüm Hristiyan aileleri için aile yaşamının modeli.", true, false, false, "tr");
        e(list, "Papa I. Sylvester", "12-31", "CHRISTMAS", "white", "İznik Konsili döneminin Papası'nın isteğe bağlı anma günü.", false, false, false, "tr");

        return list;
    }

    private static void e(List<CalendarEventEntity> list, String title, String date,
                          String season, String color, String desc,
                          boolean isFeast, boolean isSolemnity, boolean isHoly) {
        e(list, title, date, season, color, desc, isFeast, isSolemnity, isHoly, "en");
    }

    private static void e(List<CalendarEventEntity> list, String title, String date,
                          String season, String color, String desc,
                          boolean isFeast, boolean isSolemnity, boolean isHoly, String language) {
        CalendarEventEntity ev = new CalendarEventEntity();
        ev.title = title;
        ev.date = date;
        ev.easterOffset = null;
        ev.season = season;
        ev.color = color;
        ev.description = desc;
        ev.isFeastDay = isFeast;
        ev.isSolemnity = isSolemnity;
        ev.isHolyDayOfObligation = isHoly;
        ev.language = language;
        list.add(ev);
    }

    /** Movable feast: date is computed at runtime from Easter Sunday via EasterCalculator. */
    private static void eMovable(List<CalendarEventEntity> list, String title, int easterOffset,
                          String season, String color, String desc,
                          boolean isFeast, boolean isSolemnity, boolean isHoly) {
        eMovable(list, title, easterOffset, season, color, desc, isFeast, isSolemnity, isHoly, "en");
    }

    private static void eMovable(List<CalendarEventEntity> list, String title, int easterOffset,
                          String season, String color, String desc,
                          boolean isFeast, boolean isSolemnity, boolean isHoly, String language) {
        CalendarEventEntity ev = new CalendarEventEntity();
        ev.title = title;
        ev.date = null;
        ev.easterOffset = easterOffset;
        ev.season = season;
        ev.color = color;
        ev.description = desc;
        ev.isFeastDay = isFeast;
        ev.isSolemnity = isSolemnity;
        ev.isHolyDayOfObligation = isHoly;
        ev.language = language;
        list.add(ev);
    }
}
