
package mobi.victorchandler.activitygroup;

import mobi.victorchandler.NextEvents;

import android.content.Intent;
import android.os.Bundle;
/**
 * Tab that starts the NextEvents List
 * @author riveram
 */
public class NextEventsTab extends TabGroupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent nextEvents = new Intent(this, NextEvents.class);

        startChildActivity("NextEvents", nextEvents);
    }

}
