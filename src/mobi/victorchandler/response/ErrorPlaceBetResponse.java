
package mobi.victorchandler.response;

/**
 * Class for storing Error Place Bet response attributes
 */
public class ErrorPlaceBetResponse {

  
    private String betDescription;
    private String description;
    private String code;
    private String failedTransactionId;
    private String reOfferedStake;
    private String sportsBookReference;

    public String getBetDescription() {
        return betDescription;
    }

    public void setBetDescription(String betDescription) {
        this.betDescription = betDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFailedTransactionId() {
        return failedTransactionId;
    }

    public void setFailedTransactionId(String failedTransactionId) {
        this.failedTransactionId = failedTransactionId;
    }

    public String getReOfferedStake() {
        return reOfferedStake;
    }

    public void setReOfferedStake(String reOfferedStake) {
        this.reOfferedStake = reOfferedStake;
    }

    public String getSportsBookReference() {
        return sportsBookReference;
    }

    public void setSportsBookReference(String sportsBookReference) {
        this.sportsBookReference = sportsBookReference;
    }

}
