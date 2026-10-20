package com.sanctus.christian.prayer.util;

import android.content.Context;
import com.sanctus.christian.prayer.R;

/** Localised display names for the prayer category ids stored in the database. */
public final class CategoryLabels {

    private CategoryLabels() {
    }

    public static String label(Context context, String category) {
        if (category == null) return "";
        switch (category) {
            case "morning": return context.getString(R.string.category_morning);
            case "evening": return context.getString(R.string.category_evening);
            case "mass": return context.getString(R.string.category_mass);
            case "rosary": return context.getString(R.string.category_rosary);
            case "devotional": return context.getString(R.string.category_devotional);
            case "scripture": return context.getString(R.string.category_scripture);
            default: return category;
        }
    }
}
