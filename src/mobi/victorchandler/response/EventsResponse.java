
package mobi.victorchandler.response;

import java.util.ArrayList;
/**
 * Class for storing Events response attributes
 */
public class EventsResponse {

    private String eventId;
    private String eventName;
    private String eventDate;
    private String earlyPrice;
    private ArrayList<MarketsResponse> marketsList;
    private ArrayList<OpponentResponse> opponentsList;

    private String betStatementId;
    private String meetingId;
    private String outcomeId;

    public ArrayList<MarketsResponse> getMarketsList() {
        return marketsList;
    }

    public void setMarketsList(ArrayList<MarketsResponse> marketsList) {
        this.marketsList = marketsList;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getBetStatementId() {
        return betStatementId;
    }

    public void setBetStatementId(String betStatementId) {
        this.betStatementId = betStatementId;
    }

    public ArrayList<OpponentResponse> getOpponentsList() {
        return opponentsList;
    }

    public void setOpponentsList(ArrayList<OpponentResponse> opponentsList) {
        this.opponentsList = opponentsList;
    }

    public String getEarlyPrice() {
        return earlyPrice;
    }

    public void setEarlyPrice(String earlyPrice) {
        this.earlyPrice = earlyPrice;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getOutcomeId() {

        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }
}
