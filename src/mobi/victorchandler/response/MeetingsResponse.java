
package mobi.victorchandler.response;

import java.util.ArrayList;
/**
 * Class for storing Meetings response attributes
 */
public class MeetingsResponse {

    private String meetingId;
    private String meetingHeader;
    private String meetingDescription;

    private ArrayList<EventsResponse> eventsList;
    
    
    
    
    public ArrayList<EventsResponse> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<EventsResponse> eventsList) {
        this.eventsList = eventsList;
    }

    private String betStatementId;

    public String getBetStatementId() {
        return betStatementId;
    }

    public void setBetStatementId(String betStatementId) {
        this.betStatementId = betStatementId;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingHeader() {
        return meetingHeader;
    }

    public void setMeetingHeader(String meetingHeader) {
        this.meetingHeader = meetingHeader;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

}
