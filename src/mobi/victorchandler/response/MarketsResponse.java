
package mobi.victorchandler.response;

import java.util.ArrayList;
/**
 * Class for storing Markets response attributes
 */
public class MarketsResponse {

    private String id;
    private String typeId;
    private String eachWay;
    private String placeTermsDescription;
    private String placeTermsDeduction;
    private String description;
    private String periodId;
    private String periodDescription;
    private String eventId;
    private String meetingId;
    private String sportId;
    private boolean selected;
    private ArrayList<OutcomesResponse> outcomeList;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getEachWay() {
        return eachWay;
    }

    public void setEachWay(String eachWay) {
        this.eachWay = eachWay;
    }

    public String getPlaceTermsDeduction() {
        return placeTermsDeduction;
    }

    public void setPlaceTermsDeduction(String placeTermsDeduction) {
        this.placeTermsDeduction = placeTermsDeduction;
    }

    public String getPlaceTermsDescription() {
        return placeTermsDescription;
    }

    public void setPlaceTermsDescription(String placeTermsDescription) {
        this.placeTermsDescription = placeTermsDescription;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getPeriodDescription() {
        return periodDescription;
    }

    public void setPeriodDescription(String periodDescription) {
        this.periodDescription = periodDescription;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setOutcomeList(ArrayList<OutcomesResponse> outcomeList) {
        this.outcomeList = outcomeList;

    }

    public ArrayList<OutcomesResponse> getOutcomeList() {
        return outcomeList;
    }

}
