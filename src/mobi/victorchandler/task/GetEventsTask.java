
package mobi.victorchandler.task;

import mobi.victorchandler.BetSlip;
import mobi.victorchandler.Home;
import mobi.victorchandler.MyAccountHome;
import mobi.victorchandler.R;
import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.util.DataHelper;
import mobi.victorchandler.view.ActionBar;
import mobi.victorchandler.view.ActionBar.Action;
import mobi.victorchandler.view.ActionBar.IntentAction;
import mobi.victorchandler.view.EventsArrayAdapter;
import mobi.victorchandler.webservice.EventsService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Task for Events service
 * @author riveram
 *
 */
public class GetEventsTask extends AsyncTask<Void, Void, Void> {

    private Activity events;
    private String sportId;
    private String meetingId;
    private ProgressDialog progressDialog;
    private EventsService eventsService;
    private ListView listView;
    private ActionBar actionBar;
    private String sportName;

    public GetEventsTask(Activity events, String sportId, String meetingId, String sportName,
            ListView listView) {

        this.events = events;
        this.sportId = sportId;
        this.meetingId = meetingId;
        this.sportName = sportName;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (events.getParent() == null) {
            progressDialog = new ProgressDialog(events);
        } else {
            progressDialog = new ProgressDialog(events.getParent());
        }

        progressDialog.setMessage("Fetching Events ...");
        progressDialog.show();

        eventsService = new EventsService(events, sportId, meetingId);
    }

    @Override
    protected Void doInBackground(Void... params) {

        eventsService.executeRequest();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);

        ArrayList<EventsResponse> alEventsResp = new ArrayList<EventsResponse>();

        if (eventsService.getEventsdata() == null) {

            Toast.makeText(events,events.getString(R.string.service_unavailable),
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        if (eventsService.getEventsdata().size() == 0) {
            new GetEventsTask(events, sportId, meetingId, sportName, listView).execute();

        } else {

            for (EventsResponse er : eventsService.getEventsdata()) {

                alEventsResp.add(er);
            }
        }
        EventsArrayAdapter aa = new EventsArrayAdapter(events, R.layout.events_row, alEventsResp);
        listView.invalidate();
        listView.setAdapter(aa);

        final TextView tvLastUpdate = (TextView) events.findViewById(R.id.tvLastUpdateEvents);
        tvLastUpdate.setText("Updated: "
                + DataHelper.getFormattedTimeNow(System.currentTimeMillis()));

        if (events.getParent() == null) {
            setActionBar();
            actionBar.setVisibility(View.VISIBLE);
        }

        progressDialog.dismiss();
    }

    public void setActionBar() {

        actionBar = (ActionBar) events.findViewById(R.id.actionbar);
        if (actionBar.isShown()) {
            return;
        }
        actionBar
                .setHomeAction(new IntentAction(events, Home.createIntent(events), R.drawable.home));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Events");
     
        Action accountAction = new IntentAction(events, new Intent(events, MyAccountHome.class),
                R.drawable.account);
        actionBar.addAction(accountAction);

        Intent betslip = new Intent(events, BetSlip.class);
        Action betSlipAction = new IntentAction(events, betslip,
                R.drawable.betslip);

        actionBar.addAction(betSlipAction);

    }

}
