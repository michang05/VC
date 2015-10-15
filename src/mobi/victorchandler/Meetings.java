
package mobi.victorchandler;

import com.flurry.android.FlurryAgent;

import mobi.victorchandler.activitygroup.TabGroupActivity;
import mobi.victorchandler.database.FavsDB;
import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.task.GetMeetingsTask;
import mobi.victorchandler.util.NetworkHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
/**
 * Screen for showing Meetings.
 * 
 * @author riveram
 *
 */
public class Meetings extends Activity implements OnChildClickListener, OnItemClickListener {

    public static final String EXTRA_MEETINGID = "meeting";
    public static final String EXTRA_MEETINGNAME = "meetingname";

    String meetingsurl;
    String sportId;
    String sportName;
    private FavsDB db;

    private GetMeetingsTask gmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        db = new FavsDB(this);
        db.open();
        Intent intent = getIntent();
        sportId = intent.getExtras().getString(Sports.EXTRA_SPORTID);
        sportName = intent.getExtras().getString(Sports.EXTRA_SPORTNAME);

        setUpTask();

    }

   private void setUpTask() {
        NetworkHelper nh = new NetworkHelper(this);
        if (!nh.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.newtwork_unavailable), Toast.LENGTH_LONG).show();
        } else {
            gmt = new GetMeetingsTask(this, sportId, sportName);
            gmt.execute();
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
            int childPosition, long id) {
        Intent intent = new Intent(this.getApplicationContext(), Events.class);
        intent.putExtra(Sports.EXTRA_SPORTID, sportId);
        intent.putExtra(EXTRA_MEETINGID, gmt.getChildData().get(groupPosition).get(childPosition)
                .get(SportsBook.KEY_MEETING_ID));
        intent.putExtra(Sports.EXTRA_SPORTNAME, sportName);
        intent.putExtra(EXTRA_MEETINGNAME, gmt.getChildData().get(groupPosition).get(childPosition)
                .get(SportsBook.KEY_MEETINGHEADER));

        TabGroupActivity parentActivity = (TabGroupActivity) getParent();
        if (parentActivity == null) {
            startActivity(intent);
        } else {
            parentActivity.startChildActivity("Events", intent);
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Intent intent = new Intent(this.getApplicationContext(), Events.class);

        intent.putExtra(Sports.EXTRA_SPORTID, sportId);
        intent.putExtra(EXTRA_MEETINGID,
                gmt.getChildData().get(0).get(arg2).get(SportsBook.KEY_MEETING_ID));
        intent.putExtra(Sports.EXTRA_SPORTNAME, sportName);
        intent.putExtra(EXTRA_MEETINGNAME,
                gmt.getChildData().get(0).get(arg2).get(SportsBook.KEY_MEETINGHEADER));

        TabGroupActivity parentActivity = (TabGroupActivity) getParent();
        if (parentActivity == null) {
            startActivity(intent);
        } else {
            parentActivity.startChildActivity("Events", intent);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, Home.FLURRYKEY);
    }

    @Override
    protected void onStop() {
        FlurryAgent.onEndSession(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void setMeetingEmpty() {
        setContentView(R.layout.meetings_empty);
    }

}
