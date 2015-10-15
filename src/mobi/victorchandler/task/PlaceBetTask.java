
package mobi.victorchandler.task;

import mobi.victorchandler.BetReceipt;
import mobi.victorchandler.BetSlip;
import mobi.victorchandler.Login;
import mobi.victorchandler.database.BetsDb;
import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.response.ErrorPlaceBetResponse;
import mobi.victorchandler.webservice.PlaceBetService;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Task for PlaceBet service
 * @author riveram
 *
 */
public class PlaceBetTask extends AsyncTask<Void, Void, Boolean> {

    public final static String EXTRA_SINGLEEVENTS = "singleevents";
    public final static String EXTRA_SINGLEMARKETS = "singlemarkets";
    public final static String EXTRA_SINGLEMARKET_EACH_WAY = "singlemarket_eachway";
    public final static String EXTRA_SINGLEOUTCOMES = "singleoutcomes";
    public final static String EXTRA_SINGLEODDS = "singleodds";
    public final static String EXTRA_SINGLESTAKES = "singlestakes";
    public final static String EXTRA_SINGLERETURNS = "singlereturns";
    public final static String EXTRA_SINGLEDATES = "singledates";
    public final static String EXTRA_SINGLEOUTCOME_ID = "singleoutcomeid";

    public final static String EXTRA_MULTIPLE_DESCRIPTION = "description";
    public final static String EXTRA_MULTIPLE_MULTIPLICITY = "multiplicity";

    public final static String EXTRA_MULTIPLE_EACHWAY_ENABLED = "ew_enabled";
    public final static String EXTRA_MULTIPLE_EACHWAY_MULTFACTOR = "ew_multiplication_factor";
    public final static String EXTRA_MULTIPLE_EACHWAY_BETTYPEID = "ew_bettype_id";

    public final static String EXTRA_MULTIPLE_WIN_ENABLED = "win_enabled";
    public final static String EXTRA_MULTIPLE_WIN_MULTFACTOR = "win_multiplication_factor";
    public final static String EXTRA_MULTIPLE_WIN_BETTYPEID = "win_bettype_id";

    public final static String EXTRA_MULTIPLE_STAKES = "multiplestakes";

    private BetSlip betSlip;
    private PlaceBetService placeBetService;

    public PlaceBetTask(BetSlip betSlip) {
        this.betSlip = betSlip;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        betSlip.showDialog(BetSlip.DIALOG_SUBMITTING);

        LoginPreferences.load(betSlip);
        if (LoginPreferences.getToken().equals("")) {
            betSlip.startActivity(new Intent(betSlip, Login.class));
        }

        placeBetService = new PlaceBetService(betSlip);

    }

    @Override
    protected Boolean doInBackground(Void... params) {

        if (!placeBetService.executeRequest()) {
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);
        betSlip.removeDialog(BetSlip.DIALOG_SUBMITTING);

        Intent intent = new Intent(betSlip, BetReceipt.class);

        if (!result) {
            Toast.makeText(betSlip, "Connection Error", Toast.LENGTH_LONG).show();
            return;
        }

        // === Start showing Bet Slips.

        // Singles
        if (!betSlip.getSingleStakes().isEmpty()) {

            ArrayList<String> eventNames = new ArrayList<String>();
            ArrayList<String> marketNames = new ArrayList<String>();
            ArrayList<String> marketEachWay = new ArrayList<String>();
            ArrayList<String> outcomeNames = new ArrayList<String>();
            ArrayList<String> singleOdds = new ArrayList<String>();
            ArrayList<String> singleEventDates = new ArrayList<String>();
            ArrayList<String> singleStakes = new ArrayList<String>();
            ArrayList<String> outcomeIds = new ArrayList<String>();

            for (int i = 0; i < betSlip.getSingleStakes().size(); i++) {

                String stake = null;
                if (betSlip.getSingleStakes().get(i).length() > 0) {

                    String outcomeId = betSlip.getSingles().get(i).getOutcome().getId();
                    String priceFormatted = betSlip.getSingles().get(i).getOutcome()
                            .getPriceFormatted();
                    String outcomeName = betSlip.getSingles().get(i).getOutcome()
                            .getDescription();
                    String date = betSlip.getSingles().get(i).getEvent().getEventDate();
                    String eventName = betSlip.getSingles().get(i).getEvent().getEventName();
                    String marketName = betSlip.getSingles().get(i).getMarket()
                            .getDescription();
                    String marketEw = betSlip.getSingles().get(i).getMarket().getEachWay();
                    String singleEw = String.valueOf(betSlip.getSingleEWs()[i]);

                    stake = betSlip
                            .getSingleStakes().get(i);

                    if (betSlip.getSingleEWs()[i]) {
                        int val = Integer.parseInt(stake) * 2;
                        stake = String
                                .valueOf(val);
                    }

                    Log.d("PlaceBetTask","====SINGLES====");
                    Log.d("PlaceBetTask",outcomeId);
                    Log.d("PlaceBetTask",outcomeName);
                    Log.d("PlaceBetTask",priceFormatted);
                    Log.d("PlaceBetTask",singleEw);
                    Log.d("PlaceBetTask",date);
                    Log.d("PlaceBetTask",stake);
                    Log.d("PlaceBetTask","--------------");

                    eventNames.add(eventName);
                    marketNames.add(marketName);
                    marketEachWay.add(marketEw);
                    outcomeNames.add(outcomeName);
                    singleOdds.add(priceFormatted);
                    singleEventDates.add(date);
                    singleStakes.add(stake);
                    outcomeIds.add(outcomeId);

                }
            }

            intent.putStringArrayListExtra(EXTRA_SINGLEEVENTS,
                    eventNames);
            intent.putStringArrayListExtra(EXTRA_SINGLEMARKETS,
                    marketNames);
            intent.putStringArrayListExtra(EXTRA_SINGLEMARKET_EACH_WAY,
                    marketEachWay);
            intent.putStringArrayListExtra(EXTRA_SINGLEOUTCOMES,
                    outcomeNames);
            intent.putStringArrayListExtra(EXTRA_SINGLEDATES,
                    singleEventDates);
            intent.putStringArrayListExtra(EXTRA_SINGLEODDS,
                    singleOdds);
            intent.putStringArrayListExtra(EXTRA_SINGLESTAKES,
                    singleStakes);
            intent.putStringArrayListExtra(EXTRA_SINGLEOUTCOME_ID,
                    outcomeIds);
        }
        // Multiples
        if (betSlip.getMultipleStakes() != null) {
            ArrayList<String> multDescriptions = new ArrayList<String>();
            ArrayList<String> multMultiplicities = new ArrayList<String>();
            ArrayList<String> multEwEnableds = new ArrayList<String>();
            ArrayList<String> multEwMultFactors = new ArrayList<String>();
            ArrayList<String> multEwBetTypeIds = new ArrayList<String>();

            ArrayList<String> multWinEnableds = new ArrayList<String>();
            ArrayList<String> multWinMultFactors = new ArrayList<String>();
            ArrayList<String> multWinBetTypeIds = new ArrayList<String>();
            ArrayList<String> multStakes = new ArrayList<String>();

            ArrayList<String> eventNames = new ArrayList<String>();
            ArrayList<String> marketNames = new ArrayList<String>();
            ArrayList<String> marketEachWay = new ArrayList<String>();
            ArrayList<String> outcomeNames = new ArrayList<String>();
            ArrayList<String> singleOdds = new ArrayList<String>();

            for (int y = 0; y < betSlip.getSingleStakes().size(); y++) {

                String priceFormatted = betSlip.getSingles().get(y)
                        .getOutcome()
                        .getPriceFormatted();
                String outcomeName = betSlip.getSingles().get(y).getOutcome()
                        .getDescription();
                String eventName = betSlip.getSingles().get(y).getEvent()
                        .getEventName();
                String marketName = betSlip.getSingles().get(y).getMarket()
                        .getDescription();
                String marketEw = betSlip.getSingles().get(y).getMarket()
                        .getEachWay();

                eventNames.add(eventName);
                marketNames.add(marketName);
                marketEachWay.add(marketEw);
                outcomeNames.add(outcomeName);
                singleOdds.add(priceFormatted);

            }

            intent.putStringArrayListExtra(EXTRA_SINGLEEVENTS,
                    eventNames);
            intent.putStringArrayListExtra(EXTRA_SINGLEMARKETS,
                    marketNames);
            intent.putStringArrayListExtra(EXTRA_SINGLEMARKET_EACH_WAY,
                    marketEachWay);
            intent.putStringArrayListExtra(EXTRA_SINGLEOUTCOMES,
                    outcomeNames);
            intent.putStringArrayListExtra(EXTRA_SINGLEODDS,
                    singleOdds);

            for (int i = 0; i < betSlip.getMultipleStakes().size(); i++) {

                String stake = null;
                if (betSlip.getMultipleStakes().get(i).length() > 0) {

                    String type = betSlip.getMultiples().get(i).getDescription();
                    String multipleEw = String.valueOf(betSlip.getMultipleEWs()[i]);
                    int multiplicity = betSlip.getMultiples().get(i).getMultiplicity();
                    boolean multEwEnabled = betSlip.getMultiples().get(i)
                            .isEachWayEnabled();
                    double multiEwFactors = betSlip.getMultiples().get(i).getEwfactor();
                    int multiEwBetTypeId = betSlip.getMultiples().get(i)
                            .getEachWayBetTypeId();

                    boolean multiWinEnabled = betSlip.getMultiples().get(i)
                            .isWinEnabled();
                    double multiWinFactors = betSlip.getMultiples().get(i)
                            .getWinfactor();
                    int multiWinId = betSlip.getMultiples().get(i).getWinid();

                    stake = betSlip.getMultipleStakes().get(i);

                    if (betSlip.getMultipleEWs()[i]) {
                        int val = Integer.parseInt(stake) * 2;
                        stake = String
                                .valueOf(val);
                    }

                    Log.d("PlaceBetTask","====MULTIPLES====");
                    Log.d("PlaceBetTask",type);
                    Log.d("PlaceBetTask",multipleEw);
                    Log.d("PlaceBetTask",stake);
                    Log.d("PlaceBetTask","--------------");

                    multDescriptions.add(type);
                    multMultiplicities.add(String.valueOf(multiplicity));
                    multEwEnableds.add(String.valueOf(multEwEnabled));
                    multEwMultFactors.add(String.valueOf(multiEwFactors));
                    multEwBetTypeIds.add(String.valueOf(multiEwBetTypeId));
                    multStakes.add(stake);

                    multWinEnableds.add(String.valueOf(multiWinEnabled));
                    multWinMultFactors.add(String.valueOf(multiWinFactors));
                    multWinBetTypeIds.add(String.valueOf(multiWinId));

                }
            }
            intent.putStringArrayListExtra(EXTRA_MULTIPLE_DESCRIPTION,
                    multDescriptions);
            intent.putStringArrayListExtra(EXTRA_MULTIPLE_MULTIPLICITY,
                    multMultiplicities);
            intent.putStringArrayListExtra(EXTRA_MULTIPLE_EACHWAY_ENABLED,
                    multEwEnableds);
            intent.putStringArrayListExtra(EXTRA_MULTIPLE_EACHWAY_MULTFACTOR,
                    multEwMultFactors);
            intent.putStringArrayListExtra(EXTRA_MULTIPLE_EACHWAY_BETTYPEID,
                    multEwBetTypeIds);
            intent.putStringArrayListExtra(EXTRA_MULTIPLE_WIN_ENABLED,
                    multWinEnableds);
            intent.putStringArrayListExtra(EXTRA_MULTIPLE_WIN_MULTFACTOR,
                    multWinMultFactors);
            intent.putStringArrayListExtra(EXTRA_MULTIPLE_WIN_BETTYPEID,
                    multWinBetTypeIds);
            intent.putStringArrayListExtra(EXTRA_MULTIPLE_STAKES, multStakes);

        }

        // === Check if the response was a success then print the receipt with
        // the reference.

        if (placeBetService.getResponseData().isSuccess()) {
            BetsDb db = new BetsDb(betSlip);
            db.open();
            Cursor c = db.getCursor();
            c.close();
            // db.clear();
            db.close();

            intent.putExtra("reference_id", placeBetService.getResponseData().getBetslipReference());

            betSlip.startActivityForResult(intent, 0);
        }
        // === if not, stay on the Bet slips and show the error layouts.
        else {

            Log.d("PlaceBetTask", "we got an error");

            HashMap<Integer, String> errorMap = new HashMap<Integer, String>();
            for (ErrorPlaceBetResponse er : placeBetService.getResponseData()
                    .getListErrorsPlaceBets()) {
                errorMap.put(Integer.parseInt(er.getCode()), er.getDescription());
            }

            betSlip.addSingles(betSlip.getSingles(), betSlip.getSingleStakes());
            betSlip.addMultiples();
            betSlip.setFooter();
            betSlip.hasErrors(errorMap);
            betSlip.showDialog(BetSlip.DIALOG_ERROR);

            // } else {
            // TODO find a way to distinguish errors which do not
            // show
            // error
            // messages in betslip
            // if no errors on singles, it must be insufficient
            // balance
            // betslip.showDialog(BetSlip.DIALOG_ADDCREDIT);
            // or the user is not logged in
            // betSlip.startActivity(new Intent(betSlip, Login.class));
            // }

        }

    }
}
