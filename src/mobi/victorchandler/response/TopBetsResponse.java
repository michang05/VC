
package mobi.victorchandler.response;
/**
 * Class for storing Top Bets response attributes
 */
public class TopBetsResponse {

    private String sportName;
    private String marketName;
    private String marketPeriod;
    private String meetingName;
    private String eventName;
    private String outcomeName;
    private String outcomeFormattedPrice;
    private String outcomePrice;
    private String outcomePriceId;
    private String outcomeId;
    private String marketEw;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketPeriod() {
        return marketPeriod;
    }

    public void setMarketPeriod(String marketPeriod) {
        this.marketPeriod = marketPeriod;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public String getOutcomeFormattedPrice() {
        return outcomeFormattedPrice;
    }

    public void setOutcomeFormattedPrice(String outcomeFormattedPrice) {
        this.outcomeFormattedPrice = outcomeFormattedPrice;
    }

    public String getOutcomePrice() {
        return outcomePrice;
    }

    public void setOutcomePrice(String outcomePrice) {
        this.outcomePrice = outcomePrice;
    }

    public String getOutcomePriceId() {
        return outcomePriceId;
    }

    public void setOutcomePriceId(String outcomePriceId) {
        this.outcomePriceId = outcomePriceId;
    }

    public String getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getMarketEw() {
        return marketEw;
    }

    public void setMarketEw(String marketEw) {
        this.marketEw = marketEw;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Sport Name: " + sportName);
        sb.append("\n");
        sb.append("Market Name: " + marketName);
        sb.append("\n");
        sb.append("Market Period: "+marketPeriod);
        sb.append("\n");
        sb.append("Meeting Name: "+meetingName);
        sb.append("\n");
        sb.append("Event Name: "+eventName);
        sb.append("\n");
        sb.append("Outcome Name: "+outcomeName);
        sb.append("\n");
        sb.append("Outcome formatted price: "+outcomeFormattedPrice);
        sb.append("\n");
        sb.append("Outcome Price: "+outcomePrice);
        sb.append("\n");
        sb.append("Outcome Price Id: "+outcomePriceId);
        sb.append("\n");
        sb.append("Outcome Id: "+outcomeId);
        sb.append("\n");
        sb.append("Market EW: "+marketEw);

        return sb.toString();
    }
}
