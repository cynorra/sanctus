"""Downloads public-domain King James Version passages from bible-api.com into a JSON cache file.

Run once; scripts/add-content.py reads the cache. Nothing here is typed from memory: every verse comes
from the API response, so the app never ships a misquoted passage.

    python scripts/fetch-scripture.py <output.json>
"""
import json
import sys
import time
import urllib.parse
import urllib.request

# (display name, reference, tradition tag)
PASSAGES = [
    ("Psalm 8 — How Excellent Is Thy Name", "psalms 8", "all"),
    ("Psalm 19 — The Heavens Declare the Glory of God", "psalms 19", "all"),
    ("Psalm 23 — The Lord Is My Shepherd", "psalms 23", "all"),
    ("Psalm 27 — The Lord Is My Light", "psalms 27", "all"),
    ("Psalm 46 — God Is Our Refuge", "psalms 46", "all"),
    ("Psalm 51 — Have Mercy Upon Me, O God", "psalms 51", "all"),
    ("Psalm 63 — My Soul Thirsteth for Thee", "psalms 63:1-8", "all"),
    ("Psalm 91 — He That Dwelleth in the Secret Place", "psalms 91", "all"),
    ("Psalm 100 — Make a Joyful Noise", "psalms 100", "all"),
    ("Psalm 103 — Bless the Lord, O My Soul", "psalms 103:1-13", "all"),
    ("Psalm 121 — I Will Lift Up Mine Eyes", "psalms 121", "all"),
    ("Psalm 139 — O Lord, Thou Hast Searched Me", "psalms 139", "all"),
    ("Psalm 150 — Praise Ye the Lord", "psalms 150", "all"),
    ("The Beatitudes", "matthew 5:3-12", "all"),
    ("The Lord's Prayer (with the Doxology)", "matthew 6:9-13", "protestant,orthodox,anglican"),
    ("The Love Chapter (1 Corinthians 13)", "1 corinthians 13", "all"),
    ("Nothing Can Separate Us (Romans 8:31-39)", "romans 8:31-39", "all"),
    ("The Word Became Flesh (John 1:1-14)", "john 1:1-14", "all"),
    ("Let Not Your Heart Be Troubled (John 14:1-6)", "john 14:1-6", "all"),
    ("Rejoice in the Lord Always (Philippians 4:4-9)", "philippians 4:4-9", "all"),
    ("They That Wait Upon the Lord (Isaiah 40:28-31)", "isaiah 40:28-31", "all"),
    ("Fear Thou Not (Isaiah 41:10)", "isaiah 41:10", "all"),
    ("Trust in the Lord (Proverbs 3:5-6)", "proverbs 3:5-6", "all"),
    ("Be Strong and of a Good Courage (Joshua 1:9)", "joshua 1:9", "all"),
    ("What Doth the Lord Require (Micah 6:8)", "micah 6:8", "all"),
    ("The Magnificat (Luke 1:46-55)", "luke 1:46-55", "all"),
    ("The Benedictus (Luke 1:68-79)", "luke 1:68-79", "all"),
    ("The Nunc Dimittis (Luke 2:29-32)", "luke 2:29-32", "all"),
    ("Rooted and Grounded in Love (Ephesians 3:14-21)", "ephesians 3:14-21", "all"),
    ("Put On Love (Colossians 3:12-17)", "colossians 3:12-17", "all"),
]


def fetch(reference):
    url = "https://bible-api.com/" + urllib.parse.quote(reference) + "?translation=kjv"
    req = urllib.request.Request(url, headers={"User-Agent": "sanctus-content-script"})
    with urllib.request.urlopen(req, timeout=30) as resp:
        return json.loads(resp.read().decode("utf-8"))


def main():
    out_path = sys.argv[1]
    result = []
    for name, reference, tradition in PASSAGES:
        data = None
        for attempt in range(4):
            try:
                data = fetch(reference)
                break
            except Exception as exc:  # network or rate limit: back off and retry
                print("retry", reference, exc, file=sys.stderr)
                time.sleep(8 * (attempt + 1))
        if data is None:
            print("FAILED", reference, file=sys.stderr)
            sys.exit(1)
        verses = [v["text"].strip() for v in data["verses"]]
        result.append({
            "name": name,
            "reference": data["reference"],
            "translation": data.get("translation_id", "kjv"),
            "tradition": tradition,
            "verses": verses,
        })
        print("ok", reference, len(verses))
        time.sleep(2.5)
    with open(out_path, "w", encoding="utf-8", newline="\n") as fh:
        json.dump(result, fh, ensure_ascii=False, indent=1)


if __name__ == "__main__":
    main()
