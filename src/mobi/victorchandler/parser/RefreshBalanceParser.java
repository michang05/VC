
package mobi.victorchandler.parser;

import mobi.victorchandler.preferences.AccountPreferences;
import mobi.victorchandler.preferences.BasePreferences;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
/**
 * Class that parses Refreshe Balance parser
 * @author riveram
 *
 */
public class RefreshBalanceParser extends BaseVcParser {

    private String result;
    private Boolean boolresult;
    private Activity activity;
    private NumberFormat df = NumberFormat.getCurrencyInstance(Locale.getDefault());
    
    public RefreshBalanceParser(String result, Activity activity) {
        this.result = result;
        this.activity = activity;
    }
/**
 * Method that parses refresh balance
 * @return
 */
    public boolean parseRefreshBalance() {
        try {
            JSONObject json = new JSONObject(result);
            JSONObject status = json.getJSONObject("status");

            if (status.getBoolean("success")) {
             
                //Balance
                String balance = df.format(Double.parseDouble(json.getJSONObject("balance")
                        .getString("balance")));
                String availBalance = df.format(Double.parseDouble(json.getJSONObject("balance")
                        .getString("availableBalance")));
                String promotionalBalance = df.format(Double.parseDouble(json.getJSONObject("balance")
                        .getString("promotionalBalance")));

                BasePreferences.load(activity);
                
               
                AccountPreferences.setBalance(balance);
                AccountPreferences.setAvailableBalance(availBalance);
                AccountPreferences.setPromotionalBalance(promotionalBalance);
              
                Log.d("REFRESH_BALANCE", "Balance: "+balance+" | Available: "+availBalance+" | Promotional: "+promotionalBalance);
                
                BasePreferences.save();

                boolresult = new Boolean(true);
                
                Intent i = new Intent("mobi.victorchandler.BALANCES");
                i.putExtra("balance", balance);
                i.putExtra("availBalance", availBalance);
                i.putExtra("promotionalBalance", promotionalBalance);
                activity.sendBroadcast(i);

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
