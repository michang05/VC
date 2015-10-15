
package mobi.victorchandler;

import mobi.victorchandler.preferences.AccountPreferences;
import mobi.victorchandler.preferences.BasePreferences;
import mobi.victorchandler.task.RefreshBalanceTask;
import mobi.victorchandler.util.NetworkHelper;
import mobi.victorchandler.view.ActionBar;
import mobi.victorchandler.view.ActionBar.IntentAction;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Screen for viewing My Account section
 * 
 * @author riveram
 *
 */
public class MyAccountHome extends Activity {

    private ActionBar actionBar;
    private IntentAction favoritesAction;
    private IntentAction betSlipAction;
    private Button btnStatements;

    private TimerTask balanceTask;
    private final Handler handler = new Handler();
    private Timer t = new Timer();
    private BalancesReceiver br;
   
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.myaccount);

        BasePreferences.load(this);

        setActionBar();
        
        TextView tvBalance = (TextView) findViewById(R.id.tvBalance);
      
        tvBalance.setText(AccountPreferences.getBalance());
        
        TextView tvAvailBalance = (TextView) findViewById(R.id.tvAvailableBalance);
        tvAvailBalance.setText(AccountPreferences.getAvailableBalance());

        TextView tvPromoBal = (TextView) findViewById(R.id.tvPromoBalance);
        tvPromoBal.setText(AccountPreferences.getPromotionalBalance());

        TextView tvAccountNumber = (TextView) findViewById(R.id.tvAccountNumber);
        tvAccountNumber.setText(AccountPreferences.getAccountNumber());

        TextView tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvFirstName.setText(AccountPreferences.getFirstName());

        TextView tvLastName = (TextView) findViewById(R.id.tvLastName);
        tvLastName.setText(AccountPreferences.getLastName());

        btnStatements = (Button) findViewById(R.id.btnStatementsHome);
        btnStatements.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                NetworkHelper nh = new NetworkHelper(MyAccountHome.this);
                if (!nh.isNetworkAvailable()) {
                    Toast.makeText(MyAccountHome.this, getString(R.string.newtwork_unavailable),
                            Toast.LENGTH_LONG)
                            .show();
                } else {

                    Intent statementsIntent = new Intent(MyAccountHome.this, Statements.class);
                    startActivity(statementsIntent);
                }
            }
        });
        
        // Refresh Balances
        getNewBalances();
        br = new BalancesReceiver();
        registerReceiver(br, new IntentFilter("mobi.victorchandler.BALANCES"));
    }
    /**
     * Receiver for getting the Balances and updating the Balances TextFields in the My Account
     * 
     * @author riveram
     *
     */
    public class BalancesReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
         
          if(intent.getAction().equals("mobi.victorchandler.BALANCES")) {
              
              String bal = intent.getStringExtra("balance");
              String availBal = intent.getStringExtra("availBalance");
              String promBal = intent.getStringExtra("promotionalBalance");
              
              TextView tvBalance = (TextView)  findViewById(R.id.tvBalance);
              tvBalance.setText(bal);

              TextView tvAvailBalance = (TextView) findViewById(R.id.tvAvailableBalance);
              tvAvailBalance.setText(availBal);

              TextView tvPromoBal = (TextView) findViewById(R.id.tvPromoBalance);
              tvPromoBal.setText(promBal);   
          }

        }

    }

    private void getNewBalances() {
        balanceTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        new RefreshBalanceTask(MyAccountHome.this).execute();
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

        actionBar.setTitle("MyAccount");

        favoritesAction = new IntentAction(this, new Intent(this, Favorites.class),
                R.drawable.favorites);
        actionBar.addAction(favoritesAction);

        Intent betslip = new Intent(this, BetSlip.class);
        betSlipAction = new IntentAction(this, betslip,
                R.drawable.betslip);

        actionBar.addAction(betSlipAction);

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
        unregisterReceiver(br);

    }
}
