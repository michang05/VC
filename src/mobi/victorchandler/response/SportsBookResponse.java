
package mobi.victorchandler.response;
/**
 * Class for storing SportsBook response attributes
 */
public class SportsBookResponse {

    private String sportId;
    private String sportName;
    private boolean hasEvents;
    
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
    public boolean isHasEvents() {
        return hasEvents;
    }
    public void setHasEvents(boolean hasEvents) {
        this.hasEvents = hasEvents;
    }

    
}
