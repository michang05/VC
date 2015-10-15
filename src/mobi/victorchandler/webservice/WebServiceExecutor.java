
package mobi.victorchandler.webservice;

import java.util.Locale;
/**
 * Interface for adding URL of services
 * @author riveram
 *
 */
interface WebServiceExecutor {

    public static final String BASE_URL = "http://172.23.0.128:8081/"
            + Locale.getDefault().toString();
    public static final String TOP_BETS_SERVICE = BASE_URL + "/betslip/bets/top";
    public static final String NEXT_EVENTS_SERVICE = BASE_URL + "/upcoming";
    public static final String REFRESH_BETSLIP = BASE_URL + "/betslip/refresh";
    public static final String ALL_SPORTS_SERVICE = BASE_URL + "/sports";
    public static final String PLACE_BET_SERVICE = BASE_URL + "/betslip";
    public static final String LOGIN_SERVICE = BASE_URL + "/account/authenticate";
    public static final String STATEMENTS_ALL_BETS = BASE_URL + "/statements/all";
    public static final String STATEMENTS_SETTLED_BETS = BASE_URL + "/statements/settled";
    public static final String STATEMENTS_UNSETTLED_BETS = BASE_URL + "/statements/unsettled";
    public static final String STATEMENTS_TRANSACTIONS = BASE_URL + "/statements/transactions";
    public static final String LIST_CURRENCIES = BASE_URL + "/currencies";
    public static final String LIST_COUNTRIES = BASE_URL + "/countries";
    public static final String LIST_SECURITY_QUESTIONS = BASE_URL + "/security_questions";
    public static final String CHECK_ACCOUNT_AVAILABILITY = BASE_URL+"/account/check_availability";
    public static final String CREATE_ACCOUNT_SERVICE = BASE_URL +"/account";
    public static final String REFRESH_BALANCE_SERVICE = BASE_URL+"/account/balance";

    /**
     * Load from Database
     */
    public void loadFromDb();

    /**
     * executes the POST request
     * 
     * @return boolean
     */
    public boolean executeRequest();
}
