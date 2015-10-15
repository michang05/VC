
package mobi.victorchandler.preferences;
/**
 * Class for Settings Preference
 * @author riveram
 *
 */
public class SettingsPreferences extends BasePreferences {

    private static final String LANGUAGE = "language";
    private static final String ODD_DISPLAY = "odd_display";
    private static final String TIME_ZONE = "time_zone";
    private static final String LOCALE = "locale";

    private static final String DEFAULT_LANGUAGE = "english";
    private static final String DEFAULT_ODD_DISPLAY = "uk_odd";
    private static final String DEFAULT_TIME_ZONE = "";
    private static final String DEFAULT_LOCALE = "";

    //Language
    public static String getLanguage() {
        String language = settings.getString(LANGUAGE, DEFAULT_LANGUAGE);
        if (language == null || language.equals("")) {
            return "";
        }
        return language;
    }

    public static void setLanguage(String language) {

        if (language == null || language.equals("")) {
            language = "";
        }
        editor.putString(LANGUAGE, language);
    }

    // Odd Display
    public static String getOddDisplay() {
        String oddDisplay = settings.getString(ODD_DISPLAY, DEFAULT_ODD_DISPLAY);
        if (oddDisplay == null || oddDisplay.equals("")) {
            return "";
        }
        return oddDisplay;
    }

    public static void setOddDisplay(String oddDisplay) {

        if (oddDisplay == null || oddDisplay.equals("")) {
            oddDisplay = "";
        }
        editor.putString(ODD_DISPLAY, oddDisplay);
    }
    
    //TimeZone
    public static String getTimeZone() {
        String timeZone = settings.getString(TIME_ZONE, DEFAULT_TIME_ZONE);
        if (timeZone == null || timeZone.equals("")) {
            return "";
        }
        return timeZone;
    }

    public static void setTimeZone(String timeZone) {

        if (timeZone == null || timeZone.equals("")) {
            timeZone = "";
        }
        editor.putString(TIME_ZONE, timeZone);
    }
    
    //Locale
    public static String getLocale() {
        String locale = settings.getString(LOCALE, DEFAULT_LOCALE);
        if (locale == null || locale.equals("")) {
            return "";
        }
        return locale;
    }

    public static void setLocale(String locale) {

        if (locale == null || locale.equals("")) {
            locale = "";
        }
        editor.putString(LOCALE, locale);
    }
}
