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

        return list;
    }

    private static SaintEntity saint(String name, String feastDate, String description, String patronOf) {
        SaintEntity e = new SaintEntity();
        e.name = name;
        e.feastDate = feastDate;
        e.description = description;
        e.patronOf = patronOf;
        return e;
    }
}
