package mobi.victorchandler;


import mobi.victorchandler.activitygroup.TabGroupActivity;
import mobi.victorchandler.response.NextEventsResponse;
import mobi.victorchandler.task.GetNextEventsTask;
import mobi.victorchandler.util.NetworkHelper;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
/**
 * 
 * Screen for Listing Next Events.
 * 
 * @author riveram
 *
 */
public class NextEvents extends ListActivity {
    
    private Button btnRefreshNextEvents;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.next_events_activity);
        
        setUpTask();

        btnRefreshNextEvents = (Button)findViewById(R.id.btnRefreshNextEvents);
        btnRefreshNextEvents.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
              setUpTask();
             
            }
        });
    }

    private void setUpTask() {
        NetworkHelper nh = new NetworkHelper(this);
        if (!nh.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.newtwork_unavailable), Toast.LENGTH_LONG).show();
        } else {
            new GetNextEventsTask(getListView(), getParent(),this).execute();
        }
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
       
        super.onListItemClick(l, v, position, id);
        // Get the item that was clicked
        NextEventsResponse o = (NextEventsResponse) l.getItemAtPosition(position);
        
        Intent intent = new Intent(getParent(), MarketOutcomes.class);
        intent.putExtra("sport", o.getSportId());
        intent.putExtra("meeting",o.getMeetingId());
        intent.putExtra("event", o.getEventId());
        intent.putExtra("sportname", o.getSportName());
        intent.putExtra("meetingname", o.getMeetingName());
        intent.putExtra("eventname",o.getEventName());
        
        TabGroupActivity parentActivity = (TabGroupActivity) getParent();
        parentActivity.startChildActivity("MarketOutcomes", intent);
     
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // preventing default implementation previous to
            // android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
     * all systems call onBackPressed().
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            TabGroupActivity parentActivity = (TabGroupActivity) getParent();
            parentActivity.onBackPressed();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    
}
