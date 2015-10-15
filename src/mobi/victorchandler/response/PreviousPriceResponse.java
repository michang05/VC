
package mobi.victorchandler.response;
/**
 * Class for storing PreviousPrice response attributes
 */
public class PreviousPriceResponse {

    private String id;
    private String decimal;
    private String formatted;
    private String outcomeId;

    public String getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

}
