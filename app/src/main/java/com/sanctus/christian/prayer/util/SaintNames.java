package com.sanctus.christian.prayer.util;

import java.util.Locale;

/** Small helpers for showing a saint's name. */
public final class SaintNames {

    private SaintNames() {
    }

    /** First letter of the saint's own name, skipping honorifics like "Saint" or "Aziz". */
    public static String initialOf(String name) {
        if (name == null || name.trim().isEmpty()) return "";
        for (String word : name.trim().split("\\s+")) {
            String w = word.toLowerCase(Locale.ROOT);
            if (w.equals("saint") || w.equals("st.") || w.equals("blessed") || w.equals("archangel")
                    || w.equals("aziz") || w.equals("azize")) continue;
            if (!word.isEmpty()) {
                return word.substring(0, word.offsetByCodePoints(0, 1)).toUpperCase(Locale.getDefault());
            }
        }
        String trimmed = name.trim();
        return trimmed.substring(0, trimmed.offsetByCodePoints(0, 1));
    }
}
