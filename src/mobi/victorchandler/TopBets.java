
package mobi.victorchandler;

import mobi.victorchandler.activitygroup.TabGroupActivity;
import mobi.victorchandler.database.BetsDb;
import mobi.victorchandler.response.TopBetsResponse;
import mobi.victorchandler.task.GetTopBetsTask;
import mobi.victorchandler.util.NetworkHelper;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
/**
 * List of Top Bets in the Screen
 * @author riveram
 *
 */
public class TopBets extends ListActivity {       

    private Button btnRefreshTopBets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.top_bets_activity);

        setUpTask();
        
        btnRefreshTopBets = (Button) findViewById(R.id.btnRefreshTopBets);
        btnRefreshTopBets.setOnClickListener(new OnClickListener() {
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
            new GetTopBetsTask(getListView(), getParent(),this).execute();
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        // Get the item that was clicked
        TopBetsResponse tbr = (TopBetsResponse) l.getItemAtPosition(position);

        String eventname = tbr.getEventName();
        String marketname = tbr.getMarketName();
        String marketperiod = tbr.getMarketPeriod();
        String description = tbr.getOutcomeName();
        String price = tbr.getOutcomePrice();
        String priceId = tbr.getOutcomePriceId();
        String outcomeId = tbr.getOutcomeId();
        String formattedprice = tbr.getOutcomeFormattedPrice();
        String marketEw = tbr.getMarketEw();

        BetsDb db = new BetsDb(this);
        db.open();

        // Check if Bet was currently saved in the Database
        if (!db.hasBet(outcomeId)) {
            
            Cursor c = db.getCursor();
            String ptDeduction = null;
            String ptDescription = null;
            String eventId = null; 
            if(c.moveToFirst()) {
                do {
                     ptDeduction = c.getString(c.getColumnIndex(BetsDb.KEY_PT_DEDUCTION));
                     ptDescription = c.getString(c.getColumnIndex(BetsDb.KEY_PT_DESCRIPTION));
                     eventId = c.getString(c.getColumnIndex(BetsDb.KEY_EVENT_ID));
                }while(c.moveToNext());
            }
            
            
            db.insertBet(eventname, marketname, marketperiod, description, price,
                    priceId, outcomeId, formattedprice, marketEw,ptDeduction,ptDescription,eventId);
        }

        db.close();

        Intent betslip = new Intent(this, BetSlip.class);
        startActivity(betslip);
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
