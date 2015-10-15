
package mobi.victorchandler.response;

import java.util.ArrayList;
/**
 * Class for storing Bet statements response attributes
 */
public class BetStatementsResponse {

    private String id;
    private String typeId;
    private String typeDescription;
    private String subTypeId;
    private String subTypeDescription;
    private String settled;
    private String date;
    private String description;
    private String debit_decimal;
    private String debit_formatted;
    private String credit_decimal;
    private String credit_formatted;
    private String outcomeId;
    private String marketId;
    private String eventId;
    private String meetingId;

    private ArrayList<MeetingsResponse> meetingsList;

    private String status;

    public ArrayList<MeetingsResponse> getMeetingsList() {
        return meetingsList;
    }

    public void setMeetingsList(ArrayList<MeetingsResponse> meetingsList) {
        this.meetingsList = meetingsList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(String subTypeId) {
        this.subTypeId = subTypeId;
    }

    public String getSubTypeDescription() {
        return subTypeDescription;
    }

    public void setSubTypeDescription(String subTypeDescription) {
        this.subTypeDescription = subTypeDescription;
    }

    public String getSettled() {
        return settled;
    }

    public void setSettled(String settled) {
        this.settled = settled;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDebit_decimal() {
        return debit_decimal;
    }

    public void setDebit_decimal(String debit_decimal) {
        this.debit_decimal = debit_decimal;
    }

    public String getDebit_formatted() {
        return debit_formatted;
    }

    public void setDebit_formatted(String debit_formatted) {
        this.debit_formatted = debit_formatted;
    }

    public String getCredit_decimal() {
        return credit_decimal;
    }

    public void setCredit_decimal(String credit_decimal) {
        this.credit_decimal = credit_decimal;
    }

    public String getCredit_formatted() {
        return credit_formatted;
    }

    public void setCredit_formatted(String credit_formatted) {
        this.credit_formatted = credit_formatted;
    }

    public String getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
