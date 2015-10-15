
package mobi.victorchandler.activitygroup;

import mobi.victorchandler.LiveBetting;

import android.content.Intent;
import android.os.Bundle;
/**
 * Tab that starts the Live betting List
 * @author riveram
 */
public class LiveBettingTab extends TabGroupActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent liveBetting = new Intent(this, LiveBetting.class);

        startChildActivity("LiveBetting", liveBetting);
    }
}
