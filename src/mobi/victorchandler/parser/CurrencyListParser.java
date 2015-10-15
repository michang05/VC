
package mobi.victorchandler.parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
/**
 * Class for parsing CurrencyList
 * @author riveram
 *
 */
public class CurrencyListParser extends BaseVcParser {

    private HashMap<Integer, String> mapCurrency;
    private String result;

    public CurrencyListParser(String result) {
        this.result = result;
    }
/**
 * Method for getting CurrencyList
 * @return HashMap<Integer,String>
 */
    public HashMap<Integer, String> getCurrencyList() {

        mapCurrency = new HashMap<Integer, String>();

        try {
            JSONObject json = new JSONObject(result);
            JSONArray currencies = json.getJSONArray(CURRENCIES);
            for (int i = 0; i < currencies.length(); i++) {
                JSONObject currency = currencies.getJSONObject(i);
                mapCurrency.put(currency.getInt(ID), currency.getString(DESCRIPTION));

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mapCurrency;
    }

    @Override
    public List<?> parseJsonResponse() {
        // TODO Auto-generated method stub
        return null;
    }

}
