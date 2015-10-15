
package mobi.victorchandler.webservice;

import mobi.victorchandler.BetSlip;
import mobi.victorchandler.parser.PlaceBetParser;
import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.response.PlaceBetResponse;
import mobi.victorchandler.util.DataHelper;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Class that connects to PlaceBet Service
 * @author riveram
 *
 */
public class PlaceBetService implements WebServiceExecutor {

    private PlaceBetParser parser;
    private PlaceBetResponse responseData;
    private BetSlip betslip;
    private ArrayList<String> multipleStakes;
    private HttpURLConnection con;

    public PlaceBetService(BetSlip betSlip) {
        this.betslip = betSlip;
        LoginPreferences.load(betSlip);
    }

    @Override
    public void loadFromDb() {

    }

    @Override
    public boolean executeRequest() {

        try {
            System.setProperty("http.keepAlive", "false");

            URL url = new URL(PLACE_BET_SERVICE);
            con = (HttpURLConnection) url.openConnection();
            con.addRequestProperty("Authorization", "Basic " + LoginPreferences.getToken());
            con.setDoOutput(true);

            multipleStakes = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            // Singles
            boolean singleIsAvailable = false;

            for (int i = 0; i < betslip.getSingleStakes().size(); i++) {

                // if (betslip.getSingleStakes().get(i).length() > 0) {
                singleIsAvailable = true;
                sb.append("singles[" + i + "][outcomeId]=");
                sb.append(betslip.getSingles().get(i).getOutcome().getId());
                sb.append("&");
                sb.append("singles[" + i + "][priceId]=");
                sb.append(betslip.getSingles().get(i).getOutcome().getPriceId());
                sb.append("&");
                sb.append("singles[" + i + "][eachWay]=");
                sb.append(String.valueOf(betslip.getSingleEWs()[i]));
                sb.append("&");
                sb.append("singles[" + i + "][stake]=");

                if (betslip.getSingleEWs()[i]) {
                    int val = Integer.parseInt(betslip.getSingleStakes().get(i)) * 2;
                    sb.append(String.valueOf(val));
                } else {
                    sb.append(betslip.getSingleStakes().get(i));
                }

                if (betslip.reofferedstakeids != null &&
                        betslip.reofferedstakeids.length > i
                        && betslip.reofferedstakeids[i] != null) {

                    sb.append("singles[" + i + "][reOfferedStakeId]=");
                    sb.append(betslip.reofferedstakeids[i]);

                }
                if (i != betslip.getSingleStakes().size() - 1) {
                    sb.append("&");
                }
                // }
            }

            // Multiples
            if (betslip.getMultipleStakes() != null) {
                for (int i = 0; i < betslip.getMultipleStakes().size(); i++) {
                    if (betslip.getMultipleStakes().get(i).length() > 0) {
                        if (singleIsAvailable) {
                            sb.append("&");
                        }
                        sb.append("multiples[" + i + "][eachWay]=");
                        sb.append(String.valueOf(betslip.getMultipleEWs()[i]));
                        sb.append("&");
                        sb.append("multiples[" + i + "][stake]=");
                        sb.append(betslip.getMultipleStakes().get(i));

                        multipleStakes.add(betslip
                                .getMultipleStakes().get(i));

                        if (i != betslip.getMultipleStakes().size() - 1) {
                            sb.append("&");
                        }
                    }
                }
            }

            Log.d("Placebet", sb.toString());

            OutputStream wr = new BufferedOutputStream(con
                    .getOutputStream());
            // this is were we're adding post data to the request
            wr.write(sb.toString().getBytes());
            wr.flush();

            InputStream in = new BufferedInputStream(con.getInputStream());
            String result = DataHelper.convertStreamToString(in);
            
            Log.d("ERROR", result);
            
            in.close();

            parser = new PlaceBetParser(result);
            responseData = parser.parsePlaceBetResponse();

        } catch (MalformedURLException e) {
            InputStream in = new BufferedInputStream(con.getErrorStream());
            String result = DataHelper.convertStreamToString(in);
            Log.d("PlaceBetSerivce Malformed", result);
            e.printStackTrace();

            return false;

        } catch (IOException e) {
            InputStream in = new BufferedInputStream(con.getErrorStream());
            String result = DataHelper.convertStreamToString(in);
            Log.d("PlaceBetSerivce IO", result);
            e.printStackTrace();
            return false;
        } finally {

            con.disconnect();
        }

        return true;
    }

    public ArrayList<String> getMultipleStakes() {
        return multipleStakes;
    }

    public void setMultipleStakes(ArrayList<String> multipleStakes) {
        this.multipleStakes = multipleStakes;
    }

    public PlaceBetResponse getResponseData() {
        return responseData;
    }

    public void setResponseData(PlaceBetResponse responseData) {
        this.responseData = responseData;
    }

}
