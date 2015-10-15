
package mobi.victorchandler.activitygroup;

import mobi.victorchandler.TopBets;

import android.content.Intent;
import android.os.Bundle;
/**
 * Tab that starts the Top Bets List
 * @author riveram
 */
public class TopBetsTab extends TabGroupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent topBets = new Intent(this, TopBets.class);

        startChildActivity("TopBets", topBets);
    }
    
 
}
