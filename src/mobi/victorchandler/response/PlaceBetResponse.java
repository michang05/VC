
package mobi.victorchandler.response;

import java.util.ArrayList;

/**
 * Class for storing PlaceBet response attributes
 */
public class PlaceBetResponse {

  
    private String betslipId;
    private String betslipReference;
    private String delay;
    private boolean success;
    private ArrayList<ErrorPlaceBetResponse> listErrorsPlaceBets;

  

    public ArrayList<ErrorPlaceBetResponse> getListErrorsPlaceBets() {
        return listErrorsPlaceBets;
    }

    public void setListErrorsPlaceBets(ArrayList<ErrorPlaceBetResponse> listErrorsPlaceBets) {
        this.listErrorsPlaceBets = listErrorsPlaceBets;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBetslipId() {
        return betslipId;
    }

    public void setBetslipId(String betslipId) {
        this.betslipId = betslipId;
    }

    public String getBetslipReference() {
        return betslipReference;
    }

    public void setBetslipReference(String betslipReference) {
        this.betslipReference = betslipReference;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

   

}
