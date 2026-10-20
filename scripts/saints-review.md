# Saints content: what to double-check before release

English calendar now covers all 366 days for catholic, orthodox, protestant and anglican
(`python scripts/add-saints-daily.py` then `python scripts/balance-saint-traditions.py`; both rerunnable).

## Source and confidence

- **Catholic / shared (saints_daily_en_1-3.py):** General Roman Calendar and Martyrology dates, written
  from memory. A few lesser-known saints (e.g. Gildas, Severinus, Ivo, Antony Mary Gianelli) are the
  least certain - check the Roman Martyrology if one matters.
- **Orthodox (saints_daily_en_orth_1-2.py):** written from memory using the church-calendar date
  (the date the Greek / revised-calendar churches keep; Julian-calendar churches keep the same
  number 13 days later). Check against oca.org/saints or goarch.org before release. Lowest confidence:
  Apr 5, Apr 21, Jun 7, and the minor martyrs of Jul-Oct.
- **Protestant / Anglican (saints_daily_en_prot_1-2.py):** dates taken from the Lutheran (LCMS/ELCA)
  and Episcopal calendars via Wikipedia, plus well-known birth/death dates (Wesley, Spurgeon, Carver,
  Wilberforce, Newton, Bunyan...). Not verified against LSB / Lesser Feasts and Fasts.
- **Days no church calendar covers:** balance-saint-traditions.py lends the best existing entry for that
  day to the tradition (Episcopal entries, then biblical persons and early martyrs; Marian devotions
  and modern Catholic saints are never lent). So a Protestant user can see an early martyr on the
  Catholic or Orthodox date. Description text is not changed.

## Known soft spots

- Some descriptions are short on detail where little is known about the person.
- "Saint Xenia of Saint Petersburg" and similar later Orthodox saints may be lent to other traditions on
  days where nothing else was eligible.
