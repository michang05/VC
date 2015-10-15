
package mobi.victorchandler.task;

import mobi.victorchandler.BetSlip;
import mobi.victorchandler.R;
import mobi.victorchandler.listeners.PriceChangeListener;
import mobi.victorchandler.response.RefreshBetSlipResponse;
import mobi.victorchandler.webservice.RefreshBetSlipService;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View.OnClickListener;

import java.util.ArrayList;
/**
 * Task for Refresh betslip service
 * @author riveram
 *
 */
public class RefreshTask extends AsyncTask<Void, Void, ArrayList<RefreshBetSlipResponse>> {

    private BetSlip betSlip;
    private RefreshBetSlipService mRefBetSlipService;
    private ProgressDialog progressDialog;

    public RefreshTask(BetSlip betSlip) {
        this.betSlip = betSlip;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progressDialog = new ProgressDialog(betSlip);
        progressDialog.setMessage("Refreshing Bet Slip ...");
        progressDialog.show();

        mRefBetSlipService = new RefreshBetSlipService(betSlip);
    }

    @Override
    protected ArrayList<RefreshBetSlipResponse> doInBackground(Void... v) {

        if (mRefBetSlipService.executeRequest()) {
            return mRefBetSlipService.getBetSlipResponses();
        } else {
            return null;
        }

    }

    @Override
    protected void onPostExecute(ArrayList<RefreshBetSlipResponse> result) {

        // No Bet Slips
        if (result == null) {
            if (mRefBetSlipService.isServiceError()) {
                betSlip.setUnavailableBetSlipView();
            } else {
                betSlip.setEmptyBetSlipView();
            }
            progressDialog.dismiss();

            return;
        }

        for (RefreshBetSlipResponse refreshBetSlipResponse : result) {

            ArrayList<String> errorMessages = new ArrayList<String>(refreshBetSlipResponse
                        .getSingles().size());
            ArrayList<OnClickListener> changeListeners = new ArrayList<OnClickListener>(
                        refreshBetSlipResponse.getSingles().size());

            for (int i = 0; i < errorMessages.size(); i++) {

                if (!refreshBetSlipResponse.getSingles().get(i).getOutcome().getPriceId()
                            .equals(mRefBetSlipService.getPriceids().get(i))) {

                    errorMessages.add(i, betSlip.getString(R.string.errorpricechanged));
                    changeListeners.add(
                                i,
                                new PriceChangeListener(betSlip, errorMessages,
                                        changeListeners, i, 
                                        refreshBetSlipResponse.getSingles()
                                        .get(i).getOutcome().getId(),
                                       
                                        refreshBetSlipResponse.getSingles()
                                        .get(i).getOutcome().getPriceId(),
                                       
                                        String.valueOf(refreshBetSlipResponse
                                                .getSingles().get(i).getOutcome().getPriceDecimal()),
                                       
                                        refreshBetSlipResponse.getSingles()
                                        .get(i).getOutcome().getPriceFormatted()));

                }
                // other errors can only be checked when submitting not here
            }

            betSlip.addSingles(refreshBetSlipResponse.getSingles(),
                    betSlip.getSingleStakes());

            betSlip.setMultiples(refreshBetSlipResponse.getMultiples());
            betSlip.addMultiples();

        }

        progressDialog.dismiss();

    }

}
