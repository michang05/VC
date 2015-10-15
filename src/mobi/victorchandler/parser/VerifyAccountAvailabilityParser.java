
package mobi.victorchandler.parser;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
/**
 * Class that parses VerifyAccountAvailability service
 * @author riveram
 *
 */
public class VerifyAccountAvailabilityParser extends BaseVcParser {

    private String result;

    private boolean isAvailable;

    public VerifyAccountAvailabilityParser(String result) {
        this.result = result;
    }
/**
 * Checks if Username is available in the system
 * @return boolean
 */
    public boolean isUsernameAvailable() {
        try {
            JSONObject json = new JSONObject(result);
            
            isAvailable = json.getBoolean(AVAILABLE);

        } catch (JSONException e) {
//            if (isAvailable) {
//                Log.i("VerifyParser", "Username Availability verified");
//            }else {
//                Log.e("VerifyParser", "Checking of username , caught an error");
//            }
            e.printStackTrace();
        }
       
        return isAvailable;
    }

    @Override
    public List<?> parseJsonResponse() {
        // TODO Auto-generated method stub
        return null;
    }

}
