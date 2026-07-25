package com.sanctus.christian.prayer.data.seed;

import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import java.util.ArrayList;
import java.util.List;

public class PrayerSeedData {

    public static List<PrayerEntity> getAll() {
        List<PrayerEntity> list = new ArrayList<>();

        // ── ENGLISH ──────────────────────────────────────────────────────
        list.add(prayer("Our Father", "The Lord's Prayer", "morning",
            "Our Father, who art in heaven,\nhallowed be Thy name.\nThy kingdom come,\nThy will be done,\non earth as it is in heaven.\nGive us this day our daily bread,\nand forgive us our trespasses,\nas we forgive those who trespass against us.\nAnd lead us not into temptation,\nbut deliver us from evil.\nAmen.", "en"));

        list.add(prayer("Hail Mary", "Ave Maria", "rosary",
            "Hail Mary, full of grace,\nthe Lord is with thee.\nBlessed art thou among women,\nand blessed is the fruit of thy womb, Jesus.\nHoly Mary, Mother of God,\npray for us sinners,\nnow and at the hour of our death.\nAmen.", "en"));

        list.add(prayer("Glory Be", "Doxology", "rosary",
            "Glory be to the Father,\nand to the Son,\nand to the Holy Spirit,\nas it was in the beginning,\nis now, and ever shall be,\nworld without end.\nAmen.", "en"));

        list.add(prayer("Apostles' Creed", "Act of Faith", "morning",
            "I believe in God, the Father Almighty,\nCreator of heaven and earth.\nI believe in Jesus Christ, His only Son, our Lord,\nwho was conceived by the Holy Spirit,\nborn of the Virgin Mary,\nsuffered under Pontius Pilate,\nwas crucified, died, and was buried;\nHe descended into hell;\non the third day He rose again from the dead;\nHe ascended into heaven,\nand is seated at the right hand of God the Father Almighty;\nfrom there He will come to judge the living and the dead.\nI believe in the Holy Spirit,\nthe holy Catholic Church,\nthe communion of saints,\nthe forgiveness of sins,\nthe resurrection of the body,\nand life everlasting. Amen.", "en"));

        list.add(prayer("Act of Contrition", "Confession Prayer", "mass",
            "O my God, I am heartily sorry\nfor having offended Thee,\nand I detest all my sins,\nbecause of Thy just punishments,\nbut most of all because they offend Thee, my God,\nwho art all-good and deserving of all my love.\nI firmly resolve, with the help of Thy grace,\nto sin no more and to avoid the near occasions of sin.\nAmen.", "en"));

        list.add(prayer("Morning Offering", "Daily Morning Prayer", "morning",
            "O Jesus, through the Immaculate Heart of Mary,\nI offer You my prayers, works, joys and sufferings of this day\nfor all the intentions of Your Sacred Heart,\nin union with the Holy Sacrifice of the Mass throughout the world,\nin reparation for my sins,\nfor the intentions of all my associates,\nand in particular for the intentions of the Holy Father.\nAmen.", "en"));

        list.add(prayer("Evening Prayer", "Night Prayer — Compline", "evening",
            "Lord, as I come to the end of this day,\nI give thanks for all the blessings You have given me.\nForgive me for the times I have failed You.\nProtect me through the night,\nand let me rest in Your peace.\nKeep watch over those I love.\nMay I rise tomorrow renewed in Your grace.\nAmen.", "en"));

        list.add(prayer("Hail, Holy Queen", "Salve Regina", "rosary",
            "Hail, holy Queen, Mother of mercy,\nour life, our sweetness and our hope.\nTo thee do we cry,\npoor banished children of Eve.\nTo thee do we send up our sighs,\nmourning and weeping in this valley of tears.\nTurn then, most gracious advocate,\nthine eyes of mercy toward us,\nand after this our exile,\nshow unto us the blessed fruit of thy womb, Jesus.\nO clement, O loving, O sweet Virgin Mary.\nPray for us, O holy Mother of God,\nthat we may be made worthy of the promises of Christ.", "en"));

        list.add(prayer("Rosary Opening Prayer", "Before the Rosary", "rosary",
            "I believe in God…\n(Apostles' Creed)\nOur Father…\nHail Mary… (3 times, for Faith, Hope, and Charity)\nGlory Be…\n\nAnnounce the First Mystery.\nOur Father…\n10 Hail Marys…\nGlory Be…\nO My Jesus, forgive us our sins…", "en"));

        list.add(prayer("Fatima Prayer", "O My Jesus", "rosary",
            "O my Jesus, forgive us our sins,\nsave us from the fires of hell.\nLead all souls to heaven,\nespecially those in most need of Thy mercy.\nAmen.", "en"));

        list.add(prayer("Prayer to Saint Michael", "Exorcism Prayer", "devotional",
            "Saint Michael the Archangel,\ndefend us in battle.\nBe our protection against the wickedness\nand snares of the devil.\nMay God rebuke him, we humbly pray;\nand do Thou, O Prince of the Heavenly Host,\nby the power of God,\ncast into hell Satan and all evil spirits\nwho wander through the world seeking the ruin of souls.\nAmen.", "en"));

        list.add(prayer("Memorare", "Prayer to Our Lady", "devotional",
            "Remember, O most gracious Virgin Mary,\nthat never was it known\nthat anyone who fled to thy protection,\nimplored thy help, or sought thy intercession,\nwas left unaided.\nInspired by this confidence,\nI fly unto thee, O Virgin of virgins, my Mother.\nTo thee do I come, before thee I stand,\nsinful and sorrowful.\nO Mother of the Word Incarnate,\ndespise not my petitions,\nbut in thy mercy hear and answer me.\nAmen.", "en"));

        list.add(prayer("Anima Christi", "Soul of Christ", "mass",
            "Soul of Christ, sanctify me.\nBody of Christ, save me.\nBlood of Christ, inebriate me.\nWater from the side of Christ, wash me.\nPassion of Christ, strengthen me.\nO good Jesus, hear me.\nWithin Thy wounds, hide me.\nPermit me not to be separated from Thee.\nFrom the malicious enemy, defend me.\nIn the hour of my death, call me.\nAnd bid me come to Thee,\nthat with Thy saints I may praise Thee\nForever and ever.\nAmen.", "en"));

        list.add(prayer("Lauds — Morning Prayer", "Liturgy of the Hours", "morning",
            "Lord, open my lips.\nAnd my mouth shall proclaim Your praise.\nGlory to the Father, and to the Son,\nand to the Holy Spirit;\nas it was in the beginning, is now,\nand will be for ever. Amen. Alleluia.\n\nHymn:\nNow that the daylight fills the sky,\nWe lift our hearts to God on high,\nThat He, in all we do or say,\nWould keep us free from harm today.\nAmen.", "en"));

        list.add(prayer("Vespers — Evening Prayer", "Liturgy of the Hours", "evening",
            "God, come to my assistance.\nLord, make haste to help me.\nGlory to the Father, and to the Son,\nand to the Holy Spirit;\nas it was in the beginning, is now,\nand will be for ever. Amen.\n\nMagnificat:\nMy soul proclaims the greatness of the Lord,\nmy spirit rejoices in God my Saviour.\nFor He has looked with favour on His lowly servant.\nFrom this day all generations will call me blessed;\nthe Almighty has done great things for me\nand holy is His name.\nAmen.", "en"));

        list.add(prayer("Compline — Night Prayer", "Liturgy of the Hours", "evening",
            "May the Lord Almighty grant us a restful night\nand a peaceful death.\nAmen.\n\nI confess to almighty God\nand to you, my brothers and sisters,\nthat I have greatly sinned\nin my thoughts and in my words,\nin what I have done and in what I have failed to do.\nThrough my fault, through my fault,\nthrough my most grievous fault;\ntherefore I ask blessed Mary ever-Virgin,\nall the Angels and Saints,\nand you, my brothers and sisters,\nto pray for me to the Lord our God.", "en"));

        // ── TURKISH ──────────────────────────────────────────────────────
        list.add(prayer("Babamız Duası", "Pater Noster", "morning",
            "Göklerdeki Babamız,\nadın kutsal kılınsın.\nEgemenliğin gelsin,\nYerin üzerinde de gökteki gibi\nsenin istediğin olsun.\nGünlük ekmeğimizi bugün bize ver.\nBize karşı günah işleyenleri\nbağışladığımız gibi,\nsen de bizim günahlarımızı bağışla.\nBizi ayartmaya sürükleme,\nbizi kötü olandan kurtar.\nAmin.", "tr"));

        list.add(prayer("Selam Ey Meryem", "Ave Maria", "rosary",
            "Selam ey Meryem, lütufla dolu,\nRab seninle birlikte.\nSen kadınlar arasında kutsansın\nve rahmin meyvesi İsa da kutlu olsun.\nTanrı'nın kutsal anası Meryem,\nbizim için dua et,\nşimdi ve ölüm saatimizde.\nAmin.", "tr"));

        list.add(prayer("Sabah Duası", "Günlük Adanış", "morning",
            "Sevgili Rabbim,\nbugünkü tüm dualarımı, çalışmalarımı,\nsevinçlerimi ve acılarımı Sana sunuyorum.\nBana lütuf ver ki\nbugün Sana layık şekilde hizmet edebileyim.\nKalp ve ruhum Seninle olsun.\nAmin.", "tr"));

        list.add(prayer("Akşam Duası", "Gece İbadeti", "evening",
            "Sevgili Tanrım,\nbu günün sonunda Sana şükrediyorum.\nBana verdiğin her nimet için teşekkür ederim.\nSana karşı geldiğim her an için\naffımı diliyorum.\nGeceyi huzur içinde geçirmemi sağla.\nAmin.", "tr"));

        list.add(prayer("Ölülere Dua", "Requiem Aeternam", "devotional",
            "Ey Rabbim, onlara sonsuz dinçlik ver,\nve ebedi ışık onları aydınlatsın.\nRuhları ve tüm ölülerin ruhları\nTanrı'nın merhametiyle huzur bulsun.\nAmin.", "tr"));

        list.add(prayer("Şan Olsun", "Doksoloji", "rosary",
            "Şan olsun Baba'ya,\nOğul'a\nve Kutsal Ruh'a.\nEzelde olduğu gibi,\nşimdi ve her zaman\nve ebediyen.\nAmin.", "tr"));

        list.add(prayer("Havariler İnancı", "İman Duası", "morning",
            "Her şeye gücü yeten Baba,\ngöğün ve yerin yaratıcısı Tanrı'ya inanıyorum.\nO'nun biricik Oğlu, Rabbimiz İsa Mesih'e inanıyorum.\nO, Kutsal Ruh'tan vücut buldu,\nBakire Meryem'den doğdu.\nPontius Pilatus zamanında acı çekti,\nçarmıha gerildi, öldü ve gömüldü.\nÖlüler diyarına indi;\nüçüncü gün ölüler arasından dirildi;\ngöğe yükseldi,\nher şeye gücü yeten Baba Tanrı'nın sağında oturmaktadır;\noradan dirileri ve ölüleri yargılamak için yeniden gelecektir.\nKutsal Ruh'a,\nkutsal Katolik Kilise'ye,\naziz cemaatine,\ngünahların bağışlanmasına,\nbedenin dirilişine\nve sonsuz yaşama inanıyorum.\nAmin.", "tr"));

        list.add(prayer("Pişmanlık Duası", "İtiraf Duası", "mass",
            "Ey Tanrım, Seni gücendirdiğim için\nyürekten pişmanım,\nve tüm günahlarımdan nefret ediyorum,\nçünkü onlar adil cezanı hak ediyor,\nama en çok da Seni, tüm iyiliklerin ve\nsevgiye layık olan Tanrım'ı gücendirdikleri için.\nLütfunun yardımıyla\nbir daha günah işlememeye\nve günaha yaklaşan fırsatlardan kaçınmaya\nkesin karar veriyorum.\nAmin.", "tr"));

        list.add(prayer("Selam Ey Hükümdar Ana", "Salve Regina", "rosary",
            "Selam, ey kutsal Hükümdar Ana, merhamet Anası,\nhayatımız, tatlılığımız ve ümidimiz, selam sana.\nSana sesleniyoruz,\nHavva'nın sürgündeki evlatları.\nSana iç çekişlerimizi gönderiyoruz,\nbu gözyaşı vadisinde inleyerek ve ağlayarak.\nO halde, ey bizim savunucumuz,\nmerhamet dolu gözlerini bize çevir.\nVe bu sürgünden sonra,\nrahminin kutsal meyvesi İsa'yı bize göster.\nEy şefkatli, ey sevgi dolu, ey tatlı Bakire Meryem.\nBizim için dua et, ey Tanrı'nın kutsal Anası,\nMesih'in vaatlerine layık olalım.\nAmin.", "tr"));

        list.add(prayer("Tespihin Başlangıç Duası", "Tespihten Önce", "rosary",
            "İnanıyorum…\n(Havariler İnancı)\nBabamız…\nSelam Sana Meryem… (İman, Ümit ve Sevgi için 3 kez)\nŞan Olsun…\n\nBirinci Gizemi duyurun.\nBabamız…\n10 Selam Sana Meryem…\nŞan Olsun…\nEy İsa'm, günahlarımızı bağışla…", "tr"));

        list.add(prayer("Fatima Duası", "Ey İsa'm", "rosary",
            "Ey İsa'm, günahlarımızı bağışla,\nbizi cehennem ateşinden koru.\nTüm ruhları cennete götür,\nözellikle merhametine en çok ihtiyaç duyanları.\nAmin.", "tr"));

        list.add(prayer("Aziz Mikail Duası", "Koruma Duası", "devotional",
            "Başmelek Aziz Mikail,\nsavaşta bizi koru.\nŞeytanın kötülüğüne ve tuzaklarına karşı\nbize kalkan ol.\nTanrı onu azarlasın, alçakgönüllülükle dileriz;\nve sen, ey Göksel Ordular Prensi,\nTanrı'nın gücüyle,\ndünyada dolaşıp ruhların yıkımını arayan\nŞeytan'ı ve tüm kötü ruhları\ncehenneme at.\nAmin.", "tr"));

        list.add(prayer("Memorare", "Meryem'e Sığınış Duası", "devotional",
            "Hatırla, ey en şefkatli Bakire Meryem,\nkoruman altına sığınan,\nyardımını dileyen ya da şefaatini arayan\nhiç kimsenin yardımsız bırakılmadığını.\nBu güvenden ilham alarak,\nsana koşuyorum, ey bakirelerin Bakiresi, Anam.\nSana geliyorum, önünde duruyorum,\ngünahkâr ve kederli.\nEy vücut bulmuş Söz'ün Anası,\nyakarışlarımı hor görme,\nlütfen merhametle işit ve bana cevap ver.\nAmin.", "tr"));

        list.add(prayer("Mesih'in Ruhu", "Anima Christi", "mass",
            "Mesih'in ruhu, beni kutsa.\nMesih'in bedeni, beni kurtar.\nMesih'in kanı, beni sarhoş et.\nMesih'in yanından akan su, beni yıka.\nMesih'in çilesi, beni güçlendir.\nEy iyi İsa, beni işit.\nYaralarının içinde, beni sakla.\nSenden ayrılmama izin verme.\nHain düşmandan, beni koru.\nÖlüm saatimde, beni çağır.\nVe yanına gelmemi buyur,\nazizlerinle birlikte Seni övmem için\nsonsuza dek.\nAmin.", "tr"));

        list.add(prayer("Övgüler — Sabah İbadeti", "İbadet Saatleri", "morning",
            "Ya Rab, dudaklarımı aç.\nVe ağzım Sana övgüler duyursun.\nŞan olsun Baba'ya, Oğul'a\nve Kutsal Ruh'a;\nezelde olduğu gibi, şimdi ve her zaman\nve ebediyen. Amin. Alleluya.\n\nİlahi:\nGün ışığı gökyüzünü doldururken,\nyüreklerimizi yüce Tanrı'ya kaldırıyoruz,\nO'nun, yaptığımız ve söylediğimiz her şeyde,\nbizi bugün kötülükten korumasını dileriz.\nAmin.", "tr"));

        list.add(prayer("Akşam İbadeti — Vesperler", "İbadet Saatleri", "evening",
            "Ey Tanrım, yardımıma gel.\nYa Rab, imdadıma yetişmekte acele et.\nŞan olsun Baba'ya, Oğul'a\nve Kutsal Ruh'a;\nezelde olduğu gibi, şimdi ve her zaman\nve ebediyen. Amin.\n\nMagnificat:\nRuhum Rab'bin yüceliğini ilan eder,\nyüreğim Kurtarıcım Tanrı'da sevinir.\nÇünkü O, alçakgönüllü kulunun haline baktı.\nBundan böyle bütün kuşaklar beni mutlu sayacak;\nkudretli Olan büyük işler yaptı benim için,\nO'nun adı kutsaldır.\nAmin.", "tr"));

        list.add(prayer("Gece İbadeti — Komplin", "İbadet Saatleri", "evening",
            "Her şeye gücü yeten Rab bize huzurlu bir gece\nve barış içinde bir ölüm bahşetsin.\nAmin.\n\nHer şeye gücü yeten Tanrı'ya\nve sizlere, kardeşlerime,\ndüşüncelerimde ve sözlerimde,\nyaptıklarımda ve yapmadıklarımda\nçokça günah işlediğimi itiraf ediyorum.\nBenim suçumla, benim suçumla,\nbenim çok büyük suçumla;\nbu yüzden ebediyen bakire kutsal Meryem'den,\ntüm Melekler ve Azizlerden,\nve sizlerden, kardeşlerimden,\nbenim için Rabbimiz Tanrı'ya dua etmenizi diliyorum.", "tr"));

        // ── SPANISH ──────────────────────────────────────────────────────
        list.add(prayer("Padre Nuestro", "Oración del Señor", "morning",
            "Padre nuestro, que estás en los cielos,\nsantificado sea Tu nombre.\nVenga Tu reino,\nhágase Tu voluntad,\nasí en la tierra como en el cielo.\nDanos hoy nuestro pan de cada día.\nPerdona nuestras ofensas,\ncomo también nosotros perdonamos\na los que nos ofenden.\nNo nos dejes caer en tentación,\ny líbranos del mal.\nAmén.", "es"));

        list.add(prayer("Ave María", "Salutación Angélica", "rosary",
            "Dios te salve, María,\nllena eres de gracia;\nel Señor es contigo.\nBendita tú eres entre todas las mujeres,\ny bendito es el fruto de tu vientre, Jesús.\nSanta María, Madre de Dios,\nruega por nosotros pecadores,\nahora y en la hora de nuestra muerte.\nAmén.", "es"));

        list.add(prayer("Oración de la Mañana", "Ofrenda Diaria", "morning",
            "Oh Dios mío,\nte ofrezco este día,\ntodas mis oraciones, pensamientos,\nacciones y sufrimientos en unión\ncon el Sagrado Corazón de Jesús.\nQuiero amar a mi prójimo\ncomo me amas Tú.\nAmén.", "es"));

        list.add(prayer("Gloria al Padre", "Doxología", "rosary",
            "Gloria al Padre,\ny al Hijo,\ny al Espíritu Santo.\nComo era en el principio,\nahora y siempre,\npor los siglos de los siglos.\nAmén.", "es"));

        // ── PORTUGUESE ───────────────────────────────────────────────────
        list.add(prayer("Pai Nosso", "Oração do Senhor", "morning",
            "Pai nosso que estais no céu,\nsantificado seja o vosso nome.\nVenha a nós o vosso reino.\nSeja feita a vossa vontade,\nassim na terra como no céu.\nO pão nosso de cada dia nos dai hoje.\nPerdoai-nos as nossas ofensas,\nassim como nós perdoamos a quem nos tem ofendido.\nE não nos deixeis cair em tentação,\nmas livrai-nos do mal.\nAmém.", "pt"));

        list.add(prayer("Ave Maria", "Saudação Angélica", "rosary",
            "Ave Maria, cheia de graça,\no Senhor é convosco.\nBendita sois vós entre as mulheres,\ne bendito é o fruto do vosso ventre, Jesus.\nSanta Maria, Mãe de Deus,\nrogai por nós pecadores,\nagora e na hora de nossa morte.\nAmém.", "pt"));

        list.add(prayer("Glória ao Pai", "Doxologia", "rosary",
            "Glória ao Pai,\nao Filho\ne ao Espírito Santo.\nComo era no princípio,\nagora e sempre,\npor todos os séculos dos séculos.\nAmém.", "pt"));

        // ── CORE DEVOTIONS (English) ────────────────────────────────────
        list.add(prayer("The Way of the Cross", "Stations of the Cross", "devotional",
            "1. Jesus is condemned to death.\n2. Jesus carries His cross.\n3. Jesus falls the first time.\n4. Jesus meets His Mother.\n5. Simon of Cyrene helps Jesus carry the cross.\n6. Veronica wipes the face of Jesus.\n7. Jesus falls the second time.\n8. Jesus meets the women of Jerusalem.\n9. Jesus falls the third time.\n10. Jesus is stripped of His garments.\n11. Jesus is nailed to the cross.\n12. Jesus dies on the cross.\n13. Jesus is taken down from the cross.\n14. Jesus is laid in the tomb.\n\nWe adore You, O Christ, and we bless You,\nbecause by Your holy Cross You have redeemed the world.", "en"));

        list.add(prayer("Divine Mercy Chaplet", "Chaplet of the Divine Mercy", "devotional",
            "Eternal Father, I offer You the Body and Blood,\nSoul and Divinity of Your dearly beloved Son,\nOur Lord Jesus Christ,\nin atonement for our sins and those of the whole world.\n\n(On the 10 small beads, repeat 10 times:)\nFor the sake of His sorrowful Passion,\nhave mercy on us and on the whole world.\n\n(Conclude, three times:)\nHoly God, Holy Mighty One, Holy Immortal One,\nhave mercy on us and on the whole world.", "en"));

        list.add(prayer("The Angelus", "Noon Devotion", "devotional",
            "V. The Angel of the Lord declared unto Mary,\nR. And she conceived of the Holy Spirit.\nHail Mary…\n\nV. Behold the handmaid of the Lord,\nR. Be it done unto me according to Thy word.\nHail Mary…\n\nV. And the Word was made flesh,\nR. And dwelt among us.\nHail Mary…\n\nV. Pray for us, O holy Mother of God,\nR. That we may be made worthy of the promises of Christ.\n\nLet us pray:\nPour forth, we beseech Thee, O Lord,\nThy grace into our hearts,\nthat we, to whom the Incarnation of Christ, Thy Son,\nwas made known by the message of an angel,\nmay by His Passion and Cross\nbe brought to the glory of His Resurrection.\nThrough the same Christ our Lord.\nAmen.", "en"));

        list.add(prayer("Regina Coeli", "Easter Season Devotion", "devotional",
            "Queen of Heaven, rejoice, alleluia.\nFor He whom you did merit to bear, alleluia,\nHas risen, as He said, alleluia.\nPray for us to God, alleluia.\n\nRejoice and be glad, O Virgin Mary, alleluia.\nFor the Lord has truly risen, alleluia.\n\nLet us pray:\nO God, who through the resurrection of Your Son,\nour Lord Jesus Christ,\ndid vouchsafe to give joy to the world;\ngrant, we beseech You,\nthat through His Mother, the Virgin Mary,\nwe may obtain the joys of everlasting life.\nThrough the same Christ our Lord.\nAmen.", "en"));

        // ── CORE DEVOTIONS (Turkish) ────────────────────────────────────
        list.add(prayer("Çarmıh Yolu", "İstasyonlar", "devotional",
            "1. İsa ölüme mahkûm edilir.\n2. İsa çarmıhını yüklenir.\n3. İsa ilk kez düşer.\n4. İsa, Annesi'yle karşılaşır.\n5. Simon Kirineli, çarmıhı taşımasına yardım eder.\n6. Veronika, İsa'nın yüzünü siler.\n7. İsa ikinci kez düşer.\n8. İsa, Kudüs kadınlarıyla karşılaşır.\n9. İsa üçüncü kez düşer.\n10. İsa'nın giysileri soyulur.\n11. İsa çarmıha çivilenir.\n12. İsa çarmıhta ölür.\n13. İsa çarmıhtan indirilir.\n14. İsa mezara konulur.\n\nSana tapıyoruz, ey Mesih, ve Seni kutsuyoruz,\nçünkü kutsal Çarmıhınla dünyayı kurtardın.", "tr"));

        list.add(prayer("İlahi Merhamet Koroncası", "Merhamet Tespihi", "devotional",
            "Ebedi Baba, Sana sunuyorum\nçok sevgili Oğlun Rabbimiz İsa Mesih'in\nBedenini ve Kanını, Ruhunu ve Tanrılığını,\ngünahlarımızın ve tüm dünyanın günahlarının kefareti için.\n\n(10 küçük tanede, 10 kez tekrarlayın:)\nO'nun acılı Çilesi hatırına,\nbize ve tüm dünyaya merhamet et.\n\n(Üç kez tekrarlayarak bitirin:)\nKutsal Tanrı, Kutsal Kudretli, Kutsal Ölümsüz,\nbize ve tüm dünyaya merhamet et.", "tr"));

        list.add(prayer("Angelus (Rab'bin Melek Duası)", "Öğle Duası", "devotional",
            "Rab'bin meleği Meryem'e müjdeledi,\nve o, Kutsal Ruh'tan gebe kaldı.\nSelam Sana Meryem…\n\nİşte Rab'bin hizmetçisiyim,\nbana sözün gerçekleşsin.\nSelam Sana Meryem…\n\nVe Söz insan oldu,\nve aramızda yaşadı.\nSelam Sana Meryem…\n\nBizim için dua et, ey Tanrı'nın kutsal Anası,\nMesih'in vaatlerine layık olalım.\n\nDua edelim:\nYa Rab, sana yalvarıyoruz,\nlütfunu yüreklerimize dök,\nbiz ki Oğlun Mesih'in bedenlenişini\nbir meleğin haberiyle öğrendik,\nO'nun Çilesi ve Çarmıhı aracılığıyla\nDirilişinin görkemine erişelim.\nAynı Mesih Rabbimiz aracılığıyla.\nAmin.", "tr"));

        list.add(prayer("Regina Coeli (Göklerin Kraliçesi)", "Paskalya Dönemi Duası", "devotional",
            "Göklerin Kraliçesi, sevin, alleluya.\nÇünkü taşımaya layık görüldüğün O, alleluya,\nsöylediği gibi dirildi, alleluya.\nBizim için Tanrı'ya dua et, alleluya.\n\nSevin ve mutlu ol, ey Bakire Meryem, alleluya.\nÇünkü Rab gerçekten dirildi, alleluya.\n\nDua edelim:\nEy Tanrım, Oğlun Rabbimiz İsa Mesih'in\ndirilişiyle dünyaya sevinç bahşeden,\nsana yalvarıyoruz,\nAnası Bakire Meryem aracılığıyla\nsonsuz yaşamın sevincine erişelim.\nAynı Mesih Rabbimiz aracılığıyla.\nAmin.", "tr"));

        return list;
    }

    private static PrayerEntity prayer(String name, String category, String subcat,
                                        String content, String lang) {
        PrayerEntity e = new PrayerEntity();
        e.name = name;
        e.category = subcat;
        e.content = content;
        e.language = lang;
        e.isFavorite = false;
        e.lastReadAt = 0;
        return e;
    }
}
