package com.sanctus.christian.prayer.util;

import java.util.Calendar;

public class LiturgicalSeasonHelper {
    public enum Season { ADVENT, CHRISTMAS, ORDINARY, LENT, EASTER }

    public static Season getCurrentSeason() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1; // 1-12
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Simplified liturgical season detection
        if ((month == 12 && day >= 1) || (month == 12 && day <= 24)) return Season.ADVENT;
        if ((month == 12 && day >= 25) || (month == 1)) return Season.CHRISTMAS;
        if (month == 2 && day >= 14 && month == 4) return Season.LENT;
        if (month >= 4 && month <= 5) return Season.EASTER;
        return Season.ORDINARY;
    }

    public static String getSeasonColor(Season season) {
        switch (season) {
            case ADVENT: return "#4A148C";   // Purple
            case LENT: return "#4A148C";     // Purple
            case CHRISTMAS: return "#F5F5DC"; // White/Gold
            case EASTER: return "#FFFFFF";    // White
            default: return "#2E7D32";        // Green (Ordinary)
        }
    }

    public static String getSeasonName(Season season) {
        switch (season) {
            case ADVENT: return "Advent";
            case CHRISTMAS: return "Christmas Season";
            case LENT: return "Lent";
            case EASTER: return "Easter Season";
            default: return "Ordinary Time";
        }
    }
}
