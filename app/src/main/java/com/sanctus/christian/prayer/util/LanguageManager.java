package com.sanctus.christian.prayer.util;

import android.content.Context;
import android.content.res.Configuration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageManager {

    public static class Language {
        public final String code;
        public final String nativeName;
        public final String englishName;
        public final int christianMillions;
        public final boolean isRtl;

        public Language(String code, String nativeName, String englishName,
                        int christianMillions, boolean isRtl) {
            this.code = code;
            this.nativeName = nativeName;
            this.englishName = englishName;
            this.christianMillions = christianMillions;
            this.isRtl = isRtl;
        }
    }

    // Ordered by Christian population
    public static final List<Language> SUPPORTED_LANGUAGES = Arrays.asList(
        // Americas
        new Language("en",  "English",           "English",           230, false),
        new Language("pt",  "Português",          "Portuguese",        216, false),
        new Language("es",  "Español",            "Spanish",           180, false),
        // Africa
        new Language("ig",  "Igbo",               "Igbo",               30, false),
        new Language("yo",  "Yorùbá",             "Yoruba",             25, false),
        new Language("sw",  "Kiswahili",          "Swahili",            70, false),
        new Language("am",  "አማርኛ",               "Amharic",            62, false),
        // Europe
        new Language("ru",  "Русский",            "Russian",           101, false),
        new Language("de",  "Deutsch",            "German",             53, false),
        new Language("fr",  "Français",           "French",            133, false),
        new Language("it",  "Italiano",           "Italian",            48, false),
        new Language("pl",  "Polski",             "Polish",             36, false),
        new Language("uk",  "Українська",         "Ukrainian",          36, false),
        new Language("ro",  "Română",             "Romanian",           19, false),
        new Language("nl",  "Nederlands",         "Dutch",              10, false),
        new Language("el",  "Ελληνικά",           "Greek",              10, false),
        new Language("hu",  "Magyar",             "Hungarian",           8, false),
        new Language("bg",  "Български",          "Bulgarian",           5, false),
        new Language("sr",  "Srpski",             "Serbian",             5, false),
        new Language("cs",  "Čeština",            "Czech",               4, false),
        new Language("sk",  "Slovenčina",         "Slovak",              4, false),
        new Language("hr",  "Hrvatski",           "Croatian",            4, false),
        new Language("lt",  "Lietuvių",           "Lithuanian",          2, false),
        // Asia-Pacific
        new Language("fil", "Filipino",           "Filipino",          102, false),
        new Language("zh",  "中文",                "Chinese",            72, false),
        new Language("ml",  "മലയാളം",              "Malayalam",           7, false),
        new Language("hi",  "हिन्दी",              "Hindi",              34, false),
        new Language("ta",  "தமிழ்",               "Tamil",               5, false),
        new Language("te",  "తెలుగు",              "Telugu",              4, false),
        new Language("ko",  "한국어",               "Korean",             15, false),
        new Language("id",  "Bahasa Indonesia",   "Indonesian",         27, false),
        new Language("vi",  "Tiếng Việt",         "Vietnamese",          7, false),
        // Middle East
        new Language("ar",  "العربية",            "Arabic",             20, true),
        new Language("hy",  "Հայերեն",            "Armenian",            7, false),
        new Language("ka",  "ქართული",            "Georgian",            4, false),
        // Turkey
        new Language("tr",  "Türkçe",             "Turkish",             0, false)
    );

    public static Context applyLanguage(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration(context.getResources().getConfiguration());
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }

    public static Language getLanguage(String code) {
        for (Language l : SUPPORTED_LANGUAGES) {
            if (l.code.equals(code)) return l;
        }
        return SUPPORTED_LANGUAGES.get(0);
    }

    public static String getSystemLocaleCode() {
        String sys = Locale.getDefault().getLanguage();
        for (Language l : SUPPORTED_LANGUAGES) {
            if (l.code.equals(sys)) return l.code;
        }
        return "en";
    }

    public static boolean isRtl(String code) {
        Language l = getLanguage(code);
        return l != null && l.isRtl;
    }
}
