
package mobi.victorchandler.activitygroup;

import mobi.victorchandler.Sports;

import android.content.Intent;
import android.os.Bundle;
/**
 * Tab that starts the Child Sports List
 * @author riveram
 *
 */
public class AllSportsTab extends TabGroupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent allSports = new Intent(this, Sports.class);

        startChildActivity("AllSports", allSports);
    }
}
