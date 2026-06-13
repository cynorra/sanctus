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
