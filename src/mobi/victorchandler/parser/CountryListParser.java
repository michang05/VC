
package mobi.victorchandler.parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
/**
 * Class for Parsing CountryList
 * @author riveram
 *
 */
public class CountryListParser extends BaseVcParser {

    private HashMap<Integer, String> mapCountry;
    private String result;

    public CountryListParser(String result) {
        this.result = result;
    }
    /**
     * Method for getting CountryList
     * @return HashMap<Integer,String>
     */
    public HashMap<Integer, String> getCountryList(){
        mapCountry = new HashMap<Integer, String>();

        try {
            JSONObject json = new JSONObject(result);
            JSONArray countries = json.getJSONArray(COUNTRIES);
            for (int i = 0; i < countries.length(); i++) {
                JSONObject country = countries.getJSONObject(i);
                mapCountry.put(country.getInt(ID), country.getString(DESCRIPTION));

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mapCountry;
    }

    @Override
    public List<?> parseJsonResponse() {
        // TODO Auto-generated method stub
        return null;
    }


}
