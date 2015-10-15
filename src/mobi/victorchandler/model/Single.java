package mobi.victorchandler.model;

import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.MeetingsResponse;
import mobi.victorchandler.response.OutcomesResponse;
/**
 * Class that uses attributes for Single
 * @author riveram
 *
 */
public class Single {

	private OutcomesResponse outcome;
	private MarketsResponse market;
	private EventsResponse event;
	private MeetingsResponse meeting;
	
	
	
    public MarketsResponse getMarket() {
        return market;
    }
    public void setMarket(MarketsResponse market) {
        this.market = market;
    }
    public OutcomesResponse getOutcome() {
        return outcome;
    }
    public void setOutcome(OutcomesResponse outcome) {
        this.outcome = outcome;
    }
    public EventsResponse getEvent() {
        return event;
    }
    public void setEvent(EventsResponse event) {
        this.event = event;
    }
    public MeetingsResponse getMeeting() {
        return meeting;
    }
    public void setMeeting(MeetingsResponse meeting) {
        this.meeting = meeting;
    }
	
	
	
}
