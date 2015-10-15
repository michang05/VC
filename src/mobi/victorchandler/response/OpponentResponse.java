package mobi.victorchandler.response;
/**
 * Class for storing Opponents response attributes
 */
public class OpponentResponse {

    private String eventId;
    private String opponentsId;
    private String opponentsDescription;
    
    
    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public String getOpponentsId() {
        return opponentsId;
    }
    public void setOpponentsId(String opponentsId) {
        this.opponentsId = opponentsId;
    }
    public String getOpponentsDescription() {
        return opponentsDescription;
    }
    public void setOpponentsDescription(String opponentsDescription) {
        this.opponentsDescription = opponentsDescription;
    }
    
    
}
