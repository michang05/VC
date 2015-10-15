package mobi.victorchandler.preferences;
/**
 * Class for Timezone Preference
 * @author riveram
 *
 */
public class TimezonePreferences extends BasePreferences {

    private static final String TIME_ZONE = "time_zone";
    private static final String PRICE_FORMAT_ID= "price_format_id";
    private static final String LOCALE ="locale";

    private static final String DEFAULT_TIME_ZONE = "";
    private static final String DEFAULT_PRICE_FORMAT_ID = "";
    private static final String DEFAULT_LOCALE = "";
 
//Timezone
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
    
    
    //PriceFormatId
    public static String getPriceFormatId() {
        String priceFormatId = settings.getString(PRICE_FORMAT_ID, DEFAULT_PRICE_FORMAT_ID);
        if (priceFormatId == null || priceFormatId.equals("")) {
            return "";
        }
        return priceFormatId;
    }

    public static void setPriceFormatId(String priceFormatId) {

        if (priceFormatId == null || priceFormatId.equals("")) {
            priceFormatId = "";
        }
        editor.putString(PRICE_FORMAT_ID, priceFormatId);
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
