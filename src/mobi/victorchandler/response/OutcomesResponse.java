
package mobi.victorchandler.response;

import java.util.ArrayList;
/**
 * Class for storing Outcomes response attributes
 */
public class OutcomesResponse {

    private String id;
    private String description;
    private String priceId;
    private String priceDecimal;
    private String priceStartingPrice;
    private String priceFormatted;
    private String previousPriceId;
    private String marketId;
    private String eventId;
    private String meetingId;
    private String sportId;
    private ArrayList<PreviousPriceResponse> prevPriceList;
  
    private String betStatementId;

    public String getBetStatementId() {
        return betStatementId;
    }

    public void setBetStatementId(String betStatementId) {
        this.betStatementId = betStatementId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getPriceDecimal() {
        return priceDecimal;
    }

    public void setPriceDecimal(String priceDecimal) {
        this.priceDecimal = priceDecimal;
    }

    public String getPriceStarting() {
        return priceStartingPrice;
    }

    public void setPriceStarting(String priceStartingPrice) {
        this.priceStartingPrice = priceStartingPrice;
    }

    public String getPriceFormatted() {
        return priceFormatted;
    }

    public void setPriceFormatted(String priceFormatted) {
        this.priceFormatted = priceFormatted;
    }

    public String getPreviousPriceId() {
        return previousPriceId;
    }

    public void setPreviousPriceId(String previousPriceId) {
        this.previousPriceId = previousPriceId;
    }

    public ArrayList<PreviousPriceResponse> getPrevPriceList() {
        return prevPriceList;
    }

    public void setPrevPriceList(ArrayList<PreviousPriceResponse> prevPriceList) {
        this.prevPriceList = prevPriceList;
    }

}
