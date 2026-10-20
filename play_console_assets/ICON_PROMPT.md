# Gemini prompts: Sanctus app icon (2026 redesign)

Why a new icon: the current one has the word "SANCTUS" inside it (unreadable at launcher size) and a busy
stained-glass background that looks dated next to 2026 launcher icons. The new app palette is ink black
(#08080F), candlelight gold (#E6BC5C) and a single violet (#6A4DF4) for depth.

Where to put the results: `play_console_assets/icon_new/`. Use these file names so they can be wired up
without renaming:

- `A_full.png`, `B_full.png`, `C_full.png`: the full icon (symbol + background) of each concept
- `A_symbol.png`, `B_symbol.png`, `C_symbol.png`: the same symbol on a flat solid background (used to cut the
  adaptive-icon foreground)

Say which concept you picked and the icon gets wired into the app (all launcher sizes, round icon,
monochrome themed icon and the 512 px Play Store icon).

---

## Shared brief (paste first, then add ONE concept block below)

```
Design a premium Android app icon for "Sanctus", a daily Christian prayer app used by Catholic, Orthodox,
Protestant and Anglican people. Modern 2026 style: restrained, confident, calm and reverent. Not a stock
religious clip-art look.

STRICT RULES:
- Exactly square, 1:1, 1024x1024. Full-bleed square artwork: do NOT draw rounded corners, a phone frame, a
  mockup, a border, a drop shadow or any outer background. The operating system applies the shape.
- NO text, NO letters, NO numbers, NO watermark anywhere.
- ONE single centered symbol. Keep every important part of the symbol inside the central 60% of the image
  (the outer 20% on each side may only contain background glow). It must still read clearly when shrunk to
  48x48 pixels, so: simple shapes, bold contrast, no thin hairlines, no tiny details.
- Colors only: ink black #08080F, deep violet #1A1438 to #3B2A9E for the background glow, and warm
  candlelight gold #E6BC5C (highlights #F5E3B3, shadows #A87C2C) for the symbol.
- Style: soft gradients and a gentle inner glow, subtle depth. Flat vector feeling with premium lighting.
  NOT photographic, NOT stained glass, NOT 3D plastic, NOT clip-art, NO sparkle clutter, NO lens flare
  explosion.
- Background: a very dark ink-black base with one soft radial violet glow behind the symbol.
Create 4 variations of the concept.
```

---

## Concept A: Luminous cross

```
CONCEPT: a slender, elegant gold Latin cross with slightly flared ends, centered. A thin soft halo ring of
gold light circles the crossing point, as if the cross is gently lit from behind. Minimal and symmetrical.
The cross has a subtle vertical gold gradient (lighter at the top).
```

## Concept B: Arch of light

```
CONCEPT: a simple Romanesque church doorway arch in solid gold, seen straight on, centered. Inside the arch
opening there is a single small glowing candle flame in warm light-gold, so the arch reads as a doorway
into prayer. The arch is a clean bold silhouette with a soft gold edge light; the interior is deep violet.
```

## Concept C: Flame and halo

```
CONCEPT: one single gold candle flame (teardrop shape, calm and upright) floating in the center, encircled
by a thin but bold gold ring (a halo). Below the flame, nothing. The ring is slightly open at the top-right
like a breath of light. Very minimal, very iconic, strong silhouette.
```

---

## Symbol-only version (generate right after you pick or while exploring)

Paste this after the matching concept block. It produces the same symbol on a flat background so the
adaptive-icon foreground can be cut cleanly.

```
Now render the SAME symbol again, identical shape and colors, but on a perfectly flat solid #08080F
background with NO background glow, NO vignette and NO gradient behind it. The gold symbol keeps its own
inner lighting. Keep the symbol inside the central 56% of the canvas. Same 1024x1024 square, no text.
```

---

## Notes

- If Gemini adds text or a rounded frame, reply: "Remove all text and the rounded frame. Full-bleed square
  artwork only."
- If the symbol is too small or thin, reply: "Make the symbol 25% larger and the strokes bolder. It must read
  at 48x48."
- The Play Store listing screenshots in this folder still show an older 4-tab layout (Home, Prayers, Bible,
  Settings). The app now has Home, Prayers, Rosary, Saints, Feed, so those screenshots need regenerating once
  the redesign is final (`prompt.md` has the screenshot prompts).
