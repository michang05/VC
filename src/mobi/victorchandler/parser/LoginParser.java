
package mobi.victorchandler.parser;

import mobi.victorchandler.preferences.AccountPreferences;
import mobi.victorchandler.preferences.BasePreferences;
import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.preferences.TimezonePreferences;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import java.util.List;
/**
 * Class for parsing login
 * @author riveram
 *
 */
public class LoginParser extends BaseVcParser {

    private String result;
    private Boolean boolresult;
    private Activity act;

    public LoginParser(String result, Activity act) {
        this.result = result;
        this.act = act;
    }
/**
 * Method that parses Login Responses
 * @return boolean
 */
    public boolean parseLogin() {
        try {
            JSONObject json = new JSONObject(result);
            JSONObject status = json.getJSONObject("status");

            if (status.getBoolean("success")) {
                String token = "X:" + json.getString("token");
                String accountNumber = json.getJSONObject("account").getString("number");
                String firstName = json.getJSONObject("account").getString("firstName");
                String lastName = json.getJSONObject("account").getString("lastName");

                // Preferences
                String timeZone = json.getJSONObject("account").getJSONObject("preferences")
                        .getString("timeZone");
                String priceFormatId = json.getJSONObject("account").getJSONObject("preferences")
                        .getString("priceFormat");
                String locale = json.getJSONObject("account").getJSONObject("preferences")
                        .getString("locale");
                //Balance
                String balance = json.getJSONObject("account").getJSONObject("balance")
                        .getString("balance");
                String availBalance = json.getJSONObject("account").getJSONObject("balance")
                        .getString("availableBalance");
                String promotionalBalance = json.getJSONObject("account").getJSONObject("balance")
                        .getString("promotionalBalance");

                BasePreferences.load(act);
                
                LoginPreferences.setToken(token);
                AccountPreferences.setAccountNumber(accountNumber);
                AccountPreferences.setFirstName(firstName);
                AccountPreferences.setLastName(lastName);
                AccountPreferences.setBalance(balance);
                AccountPreferences.setAvailableBalance(availBalance);
                AccountPreferences.setPromotionalBalance(promotionalBalance);
                
                TimezonePreferences.setTimeZone(timeZone);
                TimezonePreferences.setLocale(locale);
                TimezonePreferences.setPriceFormatId(priceFormatId);
                
                BasePreferences.save();

                boolresult = new Boolean(true);

            } else {
                boolresult = new Boolean(false);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return boolresult;
    }

    @Override
    public List<?> parseJsonResponse() {
        return null;
    }

}
