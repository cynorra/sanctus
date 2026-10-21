# Saints content: what to double-check before release

English calendar now covers all 366 days for catholic, orthodox, protestant and anglican
(`python scripts/add-saints-daily.py` then `python scripts/balance-saint-traditions.py`; both rerunnable).

## Source and confidence

- **Catholic / shared (saints_daily_en_1-3.py):** General Roman Calendar and Martyrology dates, written
  from memory. A few lesser-known saints (e.g. Gildas, Severinus, Ivo, Antony Mary Gianelli) are the
  least certain - check the Roman Martyrology if one matters.
- **Orthodox (saints_daily_en_orth_1-2.py, orth_fix):** every entry was checked against the daily pages
  on oca.org/saints/lives/2026/MM/DD (these use the church-calendar number). Of ~190 entries, 9 were
  wrong and were corrected (Feb 13, Mar 24, Mar 29, Apr 14->15, May 27, Jun 6, Jul 23, Oct 13, Nov 24->25;
  see saints_daily_en_orth_fix.py). Several matched only by group or spelling (e.g. May 30 Isaac, Jun 18
  Leontius with Hypatius and Theodulus). The Orthodox descriptions themselves were written from memory
  and are generic for minor martyrs.
- **Protestant / Anglican (saints_daily_en_prot_1-2.py):** dates taken from the Lutheran (LCMS/ELCA)
  and Episcopal calendars via Wikipedia, plus well-known birth/death dates (Wesley, Spurgeon, Carver,
  Wilberforce, Newton, Bunyan...). Calendar dates were not verified against LSB / Lesser Feasts and Fasts.
  The birth/death dates were rechecked from memory; one error was found and fixed (Livingstone died May 1,
  not May 10).
- **Days no church calendar covers:** balance-saint-traditions.py lends the best existing entry for that
  day to the tradition (Episcopal entries, then biblical persons and early martyrs; Marian devotions
  and modern Catholic saints are never lent). So a Protestant user can see an early martyr on the
  Catholic or Orthodox date. Description text is not changed.

## Known soft spots

- Some descriptions are short on detail where little is known about the person.
- "Saint Xenia of Saint Petersburg" and similar later Orthodox saints may be lent to other traditions on
  days where nothing else was eligible.

## Verification log (2026-10-21)

- **Orthodox dates:** every entry checked against oca.org daily pages (9 fixed, see above).
- **Catholic/shared dates:** every entry checked against the catholic.org monthly pages
  (jan.php ... dec.php). That site omits many feasts of Christ/Mary, blesseds and obscure saints, so
  "not found" there was judged one by one. Fixed: Ivo of Kermartin (May 19), Raphael Kalinowski (Nov 15),
  duplicate Cabrini on Dec 22 removed, Charles de Foucauld is now "Saint" (canonised 2022), Henry of
  Treviso is "Blessed". Left as is on purpose where the Roman calendar differs from catholic.org's older one
  (Hyacinth Aug 17, Stephen of Hungary Aug 16, Apollinaris Jul 20, Sixtus III Mar 28, Heribert Mar 16).
- **Protestant/Anglican dates:** Lutheran and Episcopal list dates come from Wikipedia's calendar pages;
  the 44 birth/death dates added separately were each checked against the person's Wikipedia page.
  One fix: John Newton was born July 24 Old Style (August 4 today); the text now says so.
- **NOT verified:** the biographical text of each description (only dates were checked; a random sample of
  48 descriptions was read through and no factual error was seen), and the Turkish translations (written
  by the assistant, not reviewed by a Turkish-speaking theologian or translator).
