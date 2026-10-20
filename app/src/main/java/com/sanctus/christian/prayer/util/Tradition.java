package com.sanctus.christian.prayer.util;

import com.sanctus.christian.prayer.R;

/**
 * The Christian traditions the app can tailor itself to. Content rows carry either {@link #ALL}
 * (shared by every tradition) or a comma-separated list of the tradition ids they belong to, e.g.
 * "catholic,orthodox". The user's own choice is stored as one id, or {@link #GENERAL} to see
 * everything.
 */
public final class Tradition {

    public static final String ALL = "all";
    public static final String GENERAL = "general";
    public static final String CATHOLIC = "catholic";
    public static final String ORTHODOX = "orthodox";
    public static final String PROTESTANT = "protestant";
    public static final String ANGLICAN = "anglican";

    /** Order shown in the picker. */
    public static final String[] CHOICES = {CATHOLIC, ORTHODOX, PROTESTANT, ANGLICAN, GENERAL};

    private Tradition() {
    }

    /** Theme overlay carrying the accent, glow and surface tints of a tradition. */
    public static int themeOverlay(String id) {
        if (ORTHODOX.equals(id)) return R.style.ThemeOverlay_Sanctus_Orthodox;
        if (PROTESTANT.equals(id)) return R.style.ThemeOverlay_Sanctus_Protestant;
        if (ANGLICAN.equals(id)) return R.style.ThemeOverlay_Sanctus_Anglican;
        return R.style.ThemeOverlay_Sanctus_Catholic;
    }

    public static int labelRes(String id) {
        if (CATHOLIC.equals(id)) return R.string.tradition_catholic;
        if (ORTHODOX.equals(id)) return R.string.tradition_orthodox;
        if (PROTESTANT.equals(id)) return R.string.tradition_protestant;
        if (ANGLICAN.equals(id)) return R.string.tradition_anglican;
        return R.string.tradition_general;
    }

    public static int descriptionRes(String id) {
        if (CATHOLIC.equals(id)) return R.string.tradition_catholic_desc;
        if (ORTHODOX.equals(id)) return R.string.tradition_orthodox_desc;
        if (PROTESTANT.equals(id)) return R.string.tradition_protestant_desc;
        if (ANGLICAN.equals(id)) return R.string.tradition_anglican_desc;
        return R.string.tradition_general_desc;
    }

    /**
     * The value bound into the DAO filter: empty means "no filtering", otherwise the tradition id
     * is matched against each row's tradition list.
     */
    public static String filterValue(String userTradition) {
        if (userTradition == null || GENERAL.equals(userTradition)) return "";
        return userTradition;
    }

    /** Whether a row tagged {@code rowTradition} should be shown to a user who chose {@code userTradition}. */
    public static boolean matches(String rowTradition, String userTradition) {
        String filter = filterValue(userTradition);
        if (filter.isEmpty() || rowTradition == null || ALL.equals(rowTradition)) return true;
        return ("," + rowTradition + ",").contains("," + filter + ",");
    }
}
