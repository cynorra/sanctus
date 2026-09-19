# Gemini prompts — Sanctus Play Store assets

Her ekranı ayrı sohbette / ayrı istekte üret. Önce "Ortak stil" bloğunu yapıştır, ardından ilgili ekranı ekle.
Sonuç 1:1 gelirse kırpılıp 1080x1920 (veya 1080x2160) yapılabilir.

---

## Ortak stil (her ekran için önce bunu yapıştır)

```
Generate a single full-screen Android app UI screenshot for a Christian prayer app called "Sanctus".

STRICT FORMAT RULES:
- Aspect ratio exactly 9:16 portrait (1080x1920).
- The image IS the app screen itself, edge to edge. NO phone frame, NO device mockup, NO bezel, NO outer background, NO drop shadow, NO margins, NO multiple phones. The UI must touch all four edges of the image.
- Flat, straight-on, perfectly vertical view. No perspective, no tilt, no 3D.
- Text must be crisp, correctly spelled and fully legible. Use ONLY the exact text I list below. Do NOT invent extra paragraphs or filler text; if a text block would be long, make it a short 2-line excerpt only.
- Design style: deep navy blue background (#0B1B3D), gold accents (#D4AF37), elegant serif headings (Playfair Display style), clean sans-serif body (Inter style), soft glassmorphism cards, rounded corners, subtle gold glow. Premium, calm, reverent mood.
- Include an Android status bar at the top (time 9:41, battery, signal) and a bottom navigation bar with 4 tabs: Home, Prayers, Bible, Settings (Home active in gold).
```

---

## Ekran 1 — Ana sayfa

```
SCREEN: Home. Header: cross icon + "SANCTUS" wordmark, search icon on the right. Large glass card titled "Prayer of the Day" with this short text only: "Almighty God, grant us the grace to walk this day in humility and faith. Amen." A round gold badge "14 Days Streak" with a flame icon on the card's corner. A gold button "PRAY NOW". Below: section "RECENT PRAYERS" with three list items: "The Morning Prayer", "Evening Blessings", "Guidance".
```

## Ekran 2 — Rosary

```
SCREEN: Rosary player. Top glass card: "The Holy Rosary" and subtitle "Joyful Mysteries". Below: "1. The Annunciation" and "Pray for Humility". Center: a glowing golden rosary bead circle with a cross in the middle. Under it, only this text: "Hail Mary, full of grace, the Lord is with thee." Bottom: progress bar (0:40 / 5:12) and media controls (shuffle, previous, back 10s, large pause button, forward 10s, next, volume).
```

## Ekran 3 — Saint of the Day

```
SCREEN: Saint of the Day. Title "Saint of the Day". A card with a painted-style portrait of St. Francis of Assisi holding a cross and a lily, halo, forest with animals. Under it: "ST. FRANCIS OF ASSISI", "FEAST DAY: October 4", "PATRON SAINT OF: Animals, Ecology". A gold button "READ NOVENA". Below, one collapsed card "LIFE & LEGACY" with max 2 short lines: "Founder of the Franciscan Order, devoted to peace and creation."
```

## Ekran 4 — Streak / paylaşım

> Sadece uygulamada gerçekten varsa kullan. Yoksa Play "yanıltıcı görsel" diye işaretleyebilir.

```
SCREEN: Prayer streak. Title "Your Streak", big number "14 Days" with a flame. A weekly row Mon–Sun with flame icons, today highlighted in gold. A gold button "Share to Instagram Story". Nothing else — no people, no avatars, no community section.
```

---

## Feature graphic (1024x500)

```
Google Play feature graphic, exactly 1024x500 landscape (2.05:1), edge to edge, no border, no device frame. Deep navy background with golden light rays and a glowing golden cross on the right. On the left, large serif gold text "SANCTUS" and below it in white/gold sans-serif: "Daily Christian Prayer & Rosary". Clean, symmetrical, premium, plenty of empty space, text perfectly spelled.
```

---

## İpuçları

- Aynı istekte birden fazla ekran isteme; yan yana üç telefon üretebilir.
- Yazı bozuk çıkarsa ekle: `Re-render; fix the misspelled text, use only the exact strings I provided.`
- Ekranlardaki özellikler uygulamada gerçekten olmalı (streak, paylaşım, Saint of the Day).
- En sağlam yol: gerçek uygulama ekran görüntüleri; Gemini sadece feature graphic için.
