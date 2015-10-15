
package mobi.victorchandler;

import mobi.victorchandler.activitygroup.AllSportsTab;
import mobi.victorchandler.activitygroup.LiveBettingTab;
import mobi.victorchandler.database.FavsDB;
import mobi.victorchandler.preferences.AccountPreferences;
import mobi.victorchandler.preferences.BasePreferences;
import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.task.RefreshBalanceTask;
import mobi.victorchandler.util.NetworkHelper;
import mobi.victorchandler.view.ActionBar;
import mobi.victorchandler.view.ActionBar.Action;
import mobi.victorchandler.view.ActionBar.IntentAction;
import mobi.victorchandler.view.ImageAdapter;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Home screen of the prototype, 
 * as for honeycomb or later TabActivity should be replaced with fragments and with the actionBar
 * 
 * @author riveram
 *
 */
public class Home extends TabActivity implements OnTabChangeListener {

    public static final int OPEN_ACCOUNT = 0;
    public static final int OPTION_LOGIN = 1;
    public static final int OPTION_LOGOUT = 2;
    public static final int OPTION_SETTINGS = 3;
    public static final int OPTION_HELP = 4;

    public static final String FLURRYKEY = "F1YP4KBJZ1MK8EEZAY2Q";
    private final String TAG = getClass().getName();
    private View tabIndicator;
    private TabHost tabHost;
    private TabHost.TabSpec spec;
    private ActionBar actionBar;
    private Action betSlipAction;
    private Action favoritesAction;
    private Action accountAction;

    private TimerTask balanceTask;
    private final Handler handler = new Handler();
    private Timer t = new Timer();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        BasePreferences.load(this);

        setTabs();

        // Reference the Gallery view
        Gallery g = (Gallery) findViewById(R.id.gallery);
        // Set the adapter to our custom adapter (below)
        g.setAdapter(new ImageAdapter(this));

        // Set a item click listener, and just Toast the clicked position
        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(Home.this, "" + position, Toast.LENGTH_LONG).show();
                switch (position) {
                    case 0:
                        tabHost.setCurrentTab(0);
                        break;
                    case 1:
                        tabHost.setCurrentTab(1);
                        break;
                    case 2:
                        tabHost.setCurrentTab(2);
                        break;
                    case 3:
                        tabHost.setCurrentTab(3);
                        break;
                }
            }
        });

        if (!LoginPreferences.getToken().equals("")) {
            // Refresh Balances
            getNewBalances();
        }
    }

    /**
     * method for getting new Balances from a 1 minute pooling request.
     */
    private void getNewBalances() {
        balanceTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        new RefreshBalanceTask(Home.this).execute();
                    }
                });
            }
        };

        t.schedule(balanceTask, 300, 60000);
    }

    @Override
    protected void onStart() {

        super.onStart();
        actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.removeAllActions();
        setActionBar();

    }

    private void setActionBar() {

        actionBar.setHomeLogo(R.drawable.vc_logo_home);

        if (!LoginPreferences.getToken().equals("")) {

            accountAction = new IntentAction(this, new Intent(this, MyAccountHome.class),
                    R.drawable.account);
            actionBar.addAction(accountAction);
            favoritesAction = new IntentAction(this, new Intent(this, Favorites.class),
                    R.drawable.favorites);
            actionBar.addAction(favoritesAction);
        }
        Intent betslip = new Intent(this, BetSlip.class);
        betslip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        betSlipAction = new IntentAction(this, betslip,
                R.drawable.betslip);

        actionBar.addAction(betSlipAction);

    }
/**
 * Method for setting up tabs
 */
    private void setTabs() {

        tabHost = getTabHost();
        Intent intent = null;

        intent = new Intent(this, LiveBettingTab.class);
        addTab(getResources().getString(R.string.tab_live_betting), R.drawable.tab_info, intent);

//        intent = new Intent(this, TopBetsTab.class);
        addTab(getResources().getString(R.string.tab_top_bets), R.drawable.tab_info, intent);

//        intent = new Intent(this, NextEventsTab.class);
        addTab(getResources().getString(R.string.tab_next_events), R.drawable.tab_info, intent);

        intent = new Intent(this, AllSportsTab.class);
        addTab(getResources().getString(R.string.tab_all_sports), R.drawable.tab_info, intent);

        tabHost.setOnTabChangedListener(this);

    }
/**
 * Method for addingTabs
 * 
 * @param tag
 * @param drawableId
 * @param intent
 */
    private void addTab(String tag, int drawableId, Intent intent) {

        spec = tabHost.newTabSpec(tag);

        tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator,
                getTabWidget(), false);

        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(tag);

        ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
        icon.setImageResource(drawableId);

        spec.setIndicator(tabIndicator);
        spec.setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);

    }

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, Home.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    @Override
    public void onTabChanged(String tabId) {

        if (tabId.equals("Live Betting")) {
            Log.d(TAG, tabId);
        }

        if (tabId.equals("Top Bets")) {
            Log.d(TAG, tabId);
        }

        if (tabId.equals("Next Events")) {
            Log.d(TAG, tabId);
        }

        if (tabId.equals("All Sports")) {
            Log.d(TAG, tabId);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, OPTION_SETTINGS, 3, "Settings").setIcon(
                android.R.drawable.ic_menu_preferences);
        menu.add(0, OPTION_HELP, 4, "Help");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (LoginPreferences.getToken().equals("")) {

            if (menu.findItem(OPTION_LOGIN) == null) {
                menu.add(0, OPTION_LOGIN, 0, "Log In");
                menu.add(0, OPEN_ACCOUNT, 1, "Open Account");
            }
            menu.removeItem(OPTION_LOGOUT);

            LoginPreferences.setUsername("");
            LoginPreferences.setPassword("");
            LoginPreferences.save();

        } else {

            if (menu.findItem(OPTION_LOGOUT) == null) {
                menu.add(0, OPTION_LOGOUT, 0, "Log Out");
            }
            menu.removeItem(OPTION_LOGIN);
            menu.removeItem(OPEN_ACCOUNT);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case OPEN_ACCOUNT:
                NetworkHelper nh = new NetworkHelper(this);
                if (!nh.isNetworkAvailable()) {
                    Toast.makeText(this, getString(R.string.newtwork_unavailable),
                            Toast.LENGTH_LONG).show();
                } else {

                    startActivity(new Intent(this, CreateAccount.class));
                }

                break;
            case OPTION_LOGIN:
                startActivity(new Intent(this, Login.class));
                break;
            case OPTION_LOGOUT:
                LoginPreferences.load(this);
                LoginPreferences.setToken("");
                LoginPreferences.save();
                // Clear Favorites
                FavsDB favs = new FavsDB(this);
                favs.open();
                favs.flagRemovedUserFavorites(AccountPreferences.getAccountNumber());
                favs.close();
                actionBar.removeAction(accountAction);
                actionBar.removeAction(favoritesAction);
                Toast.makeText(this, "Successfully Logged out!", Toast.LENGTH_SHORT).show();
                break;
            case OPTION_SETTINGS:
                startActivity(new Intent(this, Settings.class));
                finish();
                break;

            case OPTION_HELP:
                startActivity(new Intent(this, OtherActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        if (!LoginPreferences.getToken().equals("")) {
            // Refresh Balances
            getNewBalances();
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        if (balanceTask != null) {
            balanceTask.cancel();
        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (balanceTask != null) {
            balanceTask.cancel();
        }

    }

}
