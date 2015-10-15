
package mobi.victorchandler;


import mobi.victorchandler.activitygroup.TabGroupActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
/**
 * Dummy Screen for Live Betting, this should be using C2DM
 * @author riveram
 *
 */
public class LiveBetting extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.live_betting_activity);

        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        aa1.add("Bart v Ralph");
        aa1.add("Sergey Brin v Steve Jobs");
        aa1.add("WTF v OMG");
        aa1.add("More fun...");
        aa1.add("Bart v Ralph");
        aa1.add("Sergey Brin v Steve Jobs");
        aa1.add("WTF v OMG");
        aa1.add("More fun...");
        setListAdapter(aa1);
        getListView().setBackgroundResource(R.drawable.list_item_background);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        // Get the item that was clicked
        Object o = this.getListAdapter().getItem(position);
        String keyword = o.toString();
        Toast.makeText(this, "You selected: " + keyword, Toast.LENGTH_LONG)
                .show();

        Intent previewMessage = new Intent(getParent(), OtherActivity.class);
        TabGroupActivity parentActivity = (TabGroupActivity) getParent();
        parentActivity.startChildActivity("OtherActivity", previewMessage);
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
