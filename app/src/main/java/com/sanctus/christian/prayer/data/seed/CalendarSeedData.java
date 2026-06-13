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
        e(list, "Saint Thomas Aquinas", "03-07", "LENT", "white", "Memorial of Saints Perpetua and Felicity, martyred in Carthage.", false, false, false);
        e(list, "Saint Patrick", "03-17", "LENT", "white", "Memorial of the patron saint of Ireland, Apostle to the Irish.", false, false, false);
        e(list, "Saint Joseph, Spouse of Mary", "03-19", "LENT", "white", "Solemnity of Saint Joseph. Patron of the universal Church, workers, and families.", true, true, false);
        e(list, "Annunciation of the Lord", "03-25", "LENT", "white", "Solemnity. The Angel Gabriel announces to Mary that she will conceive and bear the Son of God.", true, true, false);

        // ── APRIL ──
        e(list, "Palm Sunday", "04-13", "LENT", "red", "Beginning of Holy Week. Jesus enters Jerusalem in triumph. Red vestments for Passion Sunday.", true, true, false);
        e(list, "Holy Thursday", "04-17", "LENT", "white", "Mass of the Lord's Supper. Jesus institutes the Eucharist and the priesthood.", true, true, false);
        e(list, "Good Friday", "04-18", "LENT", "red", "The Passion and Death of Jesus Christ. Day of fasting and abstinence. No Mass is celebrated.", true, true, false);
        e(list, "Holy Saturday / Easter Vigil", "04-19", "LENT", "white", "The Easter Vigil is the greatest night of the Church year. New fire, water, and Word.", true, true, false);
        e(list, "Easter Sunday", "04-20", "EASTER", "white", "Solemnity of the Resurrection of the Lord. Alleluia! The central feast of the Christian year.", true, true, true);
        e(list, "Easter Monday", "04-21", "EASTER", "white", "Octave of Easter.", false, false, false);
        e(list, "Saint George", "04-23", "EASTER", "red", "Optional memorial. Patron of England.", false, false, false);
        e(list, "Saint Mark the Evangelist", "04-25", "EASTER", "red", "Feast of the author of the second Gospel.", true, false, false);
        e(list, "Saint Catherine of Siena", "04-29", "EASTER", "white", "Feast of the Doctor of the Church and co-patron of Europe.", true, false, false);

        // ── MAY ──
        e(list, "Saint Joseph the Worker", "05-01", "EASTER", "white", "Optional memorial on International Workers' Day, honouring Joseph's labour.", false, false, false);
        e(list, "Ascension of the Lord", "05-29", "EASTER", "white", "Holy Day of Obligation. Jesus ascends to the Father 40 days after Easter.", true, true, true);

        // ── JUNE ──
        e(list, "Pentecost Sunday", "06-08", "EASTER", "red", "Solemnity. The Holy Spirit descends on the Apostles. Birthday of the Church.", true, true, true);
        e(list, "Trinity Sunday", "06-15", "ORDINARY", "white", "Solemnity of the Most Holy Trinity. The central mystery of Christian faith.", true, true, false);
        e(list, "Corpus Christi", "06-22", "ORDINARY", "white", "Solemnity of the Most Holy Body and Blood of Christ. Eucharistic processions.", true, true, true);
        e(list, "Sacred Heart of Jesus", "06-27", "ORDINARY", "white", "Solemnity. Devotion to the loving heart of Jesus as a symbol of divine love.", true, true, false);
        e(list, "Immaculate Heart of Mary", "06-28", "ORDINARY", "white", "Memorial established by Pope Pius XII in 1944.", false, false, false);
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

        return list;
    }

    private static void e(List<CalendarEventEntity> list, String title, String date,
                          String season, String color, String desc,
                          boolean isFeast, boolean isSolemnity, boolean isHoly) {
        CalendarEventEntity ev = new CalendarEventEntity();
        ev.title = title;
        ev.date = date;
        ev.season = season;
        ev.color = color;
        ev.description = desc;
        ev.isFeastDay = isFeast;
        ev.isSolemnity = isSolemnity;
        ev.isHolyDayOfObligation = isHoly;
        list.add(ev);
    }
}
