
package mobi.victorchandler;

import com.flurry.android.FlurryAgent;

import mobi.victorchandler.activitygroup.TabGroupActivity;
import mobi.victorchandler.response.SportsBookResponse;
import mobi.victorchandler.util.NetworkHelper;
import mobi.victrorchandler.loader.SportsBookLoader;

import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Sports List Screen
 * 
 * @author riveram
 */
public class Sports extends ListActivity implements LoaderCallbacks<Boolean> {

    public static final String EXTRA_SPORTID = "sport";
    public static final String EXTRA_SPORTNAME = "sportname";
    private Button btnRefreshSports;

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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.all_sports_activity);

        setUpTask();

        btnRefreshSports = (Button) findViewById(R.id.btnRefreshAllSports);
        btnRefreshSports.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpTask();
            }
        });
    }

    private void setUpTask() {
        NetworkHelper nh = new NetworkHelper(this);
        if (!nh.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.newtwork_unavailable), Toast.LENGTH_LONG)
                    .show();
        } else {

            // new GetSportsBookTask(getListView(), getParent(),
            // this).execute();

            // Testing the Loader implementation instead of AsyncTask
            getLoaderManager().initLoader(0, null, this);

        }

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Get the item that was clicked

        SportsBookResponse sbr = (SportsBookResponse) l.getItemAtPosition(position);

        Intent intent = new Intent(getParent(), Meetings.class);
        intent.putExtra(EXTRA_SPORTID, sbr.getSportId());
        intent.putExtra(EXTRA_SPORTNAME, sbr.getSportName());

        // startActivity(intent);

        TabGroupActivity parentActivity = (TabGroupActivity) getParent();
        parentActivity.startChildActivity("Meetings", intent);
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {

        return new SportsBookLoader(getListView(), getParent(), this);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {
        // TODO Auto-generated method stub

    }

}
