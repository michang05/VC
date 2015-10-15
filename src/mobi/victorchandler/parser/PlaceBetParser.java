
package mobi.victorchandler.parser;

import mobi.victorchandler.response.ErrorPlaceBetResponse;
import mobi.victorchandler.response.PlaceBetResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that parses PlaceBet responses
 * @author riveram
 *
 */
public class PlaceBetParser extends BaseVcParser {

    private ErrorPlaceBetResponse errorPlaceBetResponse;

    private PlaceBetResponse placeBetResponse;

    private String mResult;

    public PlaceBetParser(String result) {
        this.mResult = result;
    }
/**
 * Method that parses responses for placing a bet(success or not)
 * @return
 */
    public PlaceBetResponse parsePlaceBetResponse() {

        placeBetResponse = new PlaceBetResponse();

        try {
            JSONObject json = new JSONObject(mResult);
            JSONObject status = json.getJSONObject("status");

            boolean isSuccess = status.getBoolean("success");
            placeBetResponse.setSuccess(isSuccess);

            JSONObject betslip = json.getJSONObject("betslip");

            if (isSuccess) {
                Log.d("PlaceBetParser", "Success");
                parseSuccess(betslip);
            } else {
                Log.d("PlaceBetParser", "Error Found");
                parseError(betslip);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return placeBetResponse;

    }

    private void parseSuccess(JSONObject betslip) {
        try {

            String id = betslip.getString(ID);
            String referenceId = betslip.getString("reference");

            placeBetResponse.setBetslipId(id);
            placeBetResponse.setBetslipReference(referenceId);

        } catch (JSONException jse) {
            jse.printStackTrace();
        }
    }

    private void parseError(JSONObject betslip) {
        try {

            ArrayList<ErrorPlaceBetResponse> listErrors = new ArrayList<ErrorPlaceBetResponse>();
        

            JSONArray betSaveResponses = betslip.getJSONArray("betSaveResponses");
            for (int x = 0; x < betSaveResponses.length(); x++) {

                JSONObject bet = betSaveResponses.getJSONObject(x);
                String betDescription = bet.getString("betDescription");
                String code = bet.getString("code");
                String description = bet.getString("description");
                String failedTransactionId = bet.getString("failedTransactionId");
                String reOfferedStake = bet.getString("reOfferedStake");
                String sportsBookReference = bet.getString("sportsBookReference");
                
                errorPlaceBetResponse = new ErrorPlaceBetResponse();
                errorPlaceBetResponse.setBetDescription(betDescription);
                errorPlaceBetResponse.setCode(code);
                errorPlaceBetResponse.setDescription(description);
                errorPlaceBetResponse.setFailedTransactionId(failedTransactionId);
                errorPlaceBetResponse.setReOfferedStake(reOfferedStake);
                errorPlaceBetResponse.setSportsBookReference(sportsBookReference);

                listErrors.add(errorPlaceBetResponse);
             
            }
            
            placeBetResponse.setListErrorsPlaceBets(listErrors);
          
            String betslipId = betslip.getString("betslipId");
            String betslipReference = betslip.getString("betslipReference");
            String delay = betslip.getString("delay");

            placeBetResponse.setBetslipId(betslipId);
            placeBetResponse.setBetslipReference(betslipReference);
            placeBetResponse.setDelay(delay);

        } catch (JSONException jse) {
            jse.printStackTrace();
            Log.d("VC", jse.toString());
        }
    }

    @Override
    public List<?> parseJsonResponse() {
        // TODO Auto-generated method stub
        return null;
    }

}
