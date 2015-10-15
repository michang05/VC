
package mobi.victorchandler.response;
/**
 * Class for storing Next Events response attributes
 */
public class NextEventsResponse {

    private String sportId;
    private String sportName;
    private String eventId;
    private String eventName;
    private String eventDate;
    private String meetingId;
    private String meetingName;

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
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

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Sport Name: " + sportName);
        sb.append("\n");
        sb.append("Meeting Name: " + meetingName);
        sb.append("\n");
        sb.append("Event Name: " + eventName);
        sb.append("\n");
        sb.append("Sport Id: " + sportId);
        sb.append("\n");
        sb.append("Event Id: " + eventId);
        sb.append("\n");
        sb.append("Event Date: " + eventDate);
        sb.append("\n");
        sb.append("Meeting Id: " + meetingId);

        return sb.toString();
    }
}
