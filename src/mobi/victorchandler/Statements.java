
package mobi.victorchandler;

import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.task.RefreshBalanceTask;
import mobi.victorchandler.view.ActionBar;
import mobi.victorchandler.view.ActionBar.IntentAction;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Screen with Tabs listing settled,open and transactions
 * 
 * @author riveram
 *
 */
public class Statements extends TabActivity implements OnTabChangeListener {
 
    private ActionBar actionBar;
    private IntentAction favoritesAction;
    private IntentAction accountAction;
    private IntentAction betSlipAction;
    private TabHost tabHost;
    private View tabIndicator;
    private TabSpec spec;

    private static final String TAG = "Statements";

    private TimerTask balanceTask;
    private final Handler handler = new Handler();
    private Timer t = new Timer();
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statements);

        setActionBar();
        setTabs();

        getNewBalances();
    }

    private void getNewBalances() {
        balanceTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        new RefreshBalanceTask(Statements.this).execute();
                    }
                });
            }
        };

        t.schedule(balanceTask, 300, 60000);
    }
    
    private void setActionBar() {
        actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setHomeAction(new IntentAction(this, Home.createIntent(this), R.drawable.home));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Statements");

        favoritesAction = new IntentAction(this, new Intent(this, Favorites.class),
                R.drawable.favorites);
        actionBar.addAction(favoritesAction);

        if (!LoginPreferences.getToken().equals("")) {

            accountAction = new IntentAction(this, new Intent(this, MyAccountHome.class),
                    R.drawable.account);
            actionBar.addAction(accountAction);
        }
        Intent betslip = new Intent(this, BetSlip.class);
        betSlipAction = new IntentAction(this, betslip,
                R.drawable.betslip);

        actionBar.addAction(betSlipAction);

    }

    private void setTabs() {

        tabHost = getTabHost();

        addTab("Open Bets", R.drawable.tab_info, new Intent(this, OpenBetsTab.class));

        addTab("Settled Bets", R.drawable.tab_info, new Intent(this, SettledBetsTab.class));

        addTab("Transactions", R.drawable.tab_info, new Intent(this, TransactionsTab.class));

        tabHost.setOnTabChangedListener(this);

    }

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

    @Override
    public void onTabChanged(String tabId) {

    
        if (tabId.equals("Open Bets")) {
          
            Log.d(TAG, tabId);
        }

        if (tabId.equals("Settled Bets")) {
         
            Log.d(TAG, tabId);
        }

        if (tabId.equals("Transactions")) {
          
            Log.d(TAG, tabId);
        }

    }

    @Override
    protected void onRestart() {
    
        super.onRestart();
        getNewBalances();
    }
    
    @Override
    protected void onStop() {

        super.onStop();
        balanceTask.cancel();
      
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        balanceTask.cancel();
       

    }
    
}
