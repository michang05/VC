
package mobi.victorchandler;

import mobi.victorchandler.task.GetTransactionsTask;
import mobi.victorchandler.util.NetworkHelper;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
/**
 * Transactions List Tab inside Statements.
 * @author riveram
 *
 */
public class TransactionsTab extends ExpandableListActivity {
    private static final int REFRESH = Menu.FIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setUpTask();

    }

    private void setUpTask() {
        NetworkHelper nh = new NetworkHelper(this);
        if (!nh.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.newtwork_unavailable), Toast.LENGTH_LONG).show();
        } else {
            new GetTransactionsTask(this).execute();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, REFRESH, 1, "Refresh");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case REFRESH:
                setUpTask();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
