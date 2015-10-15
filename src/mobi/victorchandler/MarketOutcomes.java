
package mobi.victorchandler;

import mobi.victorchandler.task.GetMarketOutcomesTask;
import mobi.victorchandler.util.NetworkHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
/**
 * Screen for showing the Market Outcomes from the events.
 * 
 * @author riveram
 *
 */
public class MarketOutcomes extends Activity {

    private String sportId;
    private String meetingId;
    private String eventId;
    private String sportName;
    private String meetingName;
    private String eventName;
    private Bundle bundle;
    private String[] market;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (intent.getExtras().getString("sport") == null) {
            // parse data from QSB
            String data = intent.getDataString();
            String[] parts = data.split(",");
            sportId = parts[0].split("=")[1];
            sportName = parts[1].split("=")[1];
            meetingId = parts[2].split("=")[1];
            meetingName = parts[3].split("=")[1];
            eventId = parts[4].split("=")[1];
            eventName = parts[5].split("=")[1];
        } else {
            sportId = bundle.getString(Sports.EXTRA_SPORTID);
            meetingId = bundle.getString(Meetings.EXTRA_MEETINGID);
            eventId = bundle.getString(Events.EXTRA_EVENTID);
            sportName = bundle.getString(Sports.EXTRA_SPORTNAME);
            meetingName = bundle.getString(Meetings.EXTRA_MEETINGNAME);
            eventName = bundle.getString(Events.EXTRA_EVENTNAME);
        }

        market = new String[] {
                sportId, meetingId, eventId, sportName, meetingName, eventName
        };

        setUpTask();

    }

    private void setUpTask() {
        NetworkHelper nh = new NetworkHelper(this);
        if (!nh.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.newtwork_unavailable), Toast.LENGTH_LONG).show();
        } else {
            new GetMarketOutcomesTask(market, this).execute();
        }

    }

    /**
     * method for setting the screen with an empty 
     */
    public void setMeetingEmpty() {
        setContentView(R.layout.meetings_empty);
    }
}
