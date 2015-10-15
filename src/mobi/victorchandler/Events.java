
package mobi.victorchandler;

import com.flurry.android.FlurryAgent;

import mobi.victorchandler.activitygroup.TabGroupActivity;
import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.task.GetEventsTask;
import mobi.victorchandler.util.FavoriteTool;
import mobi.victorchandler.util.NetworkHelper;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Screen that Lists the Events from the Sport selected.
 * 
 * @author riveram
 *
 */
public class Events extends ListActivity {

    public static final String EXTRA_EVENTID = "event";
    public static final String EXTRA_EVENTNAME = "eventname";

    private String sportId;
    private String meetingId;
    private String sportname;
    private String meetingname;
    private Bundle bundle;
    private GetEventsTask mGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.events_activity);

        bundle = getIntent().getExtras();
        sportId = bundle.getString(Sports.EXTRA_SPORTID);
        meetingId = bundle.getString(Meetings.EXTRA_MEETINGID);
        sportname = bundle.getString(Sports.EXTRA_SPORTNAME);
        meetingname = bundle.getString(Meetings.EXTRA_MEETINGNAME);

        setUpTask();

        FavoriteTool tool = new FavoriteTool(this, sportId, meetingname, meetingId);
        tool.processFavorites((ToggleButton) findViewById(R.id.favbtn));

        TextView tvEventHeader = (TextView) findViewById(R.id.tvEventNameHeader);
        tvEventHeader.setText(meetingname);

        Button btnRefreshEvents = (Button) findViewById(R.id.btnRefreshEvents);
        btnRefreshEvents.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setUpTask();
            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        EventsResponse er = (EventsResponse) l.getItemAtPosition(position);
        Intent marketOutcomes = new Intent(this.getApplicationContext(), MarketOutcomes.class);
        marketOutcomes.putExtra(Sports.EXTRA_SPORTID, sportId);
        marketOutcomes.putExtra(Meetings.EXTRA_MEETINGID, meetingId);
        marketOutcomes.putExtra(Events.EXTRA_EVENTID, er.getEventId());
        marketOutcomes.putExtra(Sports.EXTRA_SPORTNAME, sportname);
        marketOutcomes.putExtra(Meetings.EXTRA_MEETINGNAME, meetingname);
        marketOutcomes.putExtra(Events.EXTRA_EVENTNAME, er.getEventName());

        TabGroupActivity parentActivity = (TabGroupActivity) getParent();
        if (parentActivity == null) {
            startActivity(marketOutcomes);
        } else {
            parentActivity.startChildActivity("MarketOutcomes", marketOutcomes);
        }
    }

    /**
     * Set up the task for executing AsyncTask in the UI Thread.
     */
    private void setUpTask() {
        NetworkHelper nh = new NetworkHelper(this);
        if (!nh.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.newtwork_unavailable), Toast.LENGTH_LONG).show();
        } else {
            mGet = new GetEventsTask(this, sportId, meetingId, sportname, getListView());
            mGet.execute();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, "F1YP4KBJZ1MK8EEZAY2Q");
    }

    @Override
    protected void onStop() {
        FlurryAgent.onEndSession(this);
        super.onStop();
    }

}
