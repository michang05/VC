
package mobi.victorchandler.listeners;

import mobi.victorchandler.BetSlip;
import mobi.victorchandler.R;
import mobi.victorchandler.database.BetsDb;
import mobi.victorchandler.task.RefreshTask;
import mobi.victorchandler.util.NetworkHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
/**
 * Class that process error messages only.
 * @author riveram
 *
 */
public class BetErrorListener implements OnClickListener {

    private BetSlip act;
    private BetsDb db;
    private String outcomeId;

    public BetErrorListener(BetSlip act, String outcomeId) {
        this.act = act;
        this.outcomeId = outcomeId;
    }

    @Override
    public void onClick(View v) {
        db = new BetsDb(act);
        db.open();

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage("Error Bet will be deleted?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteBet(outcomeId);
                        db.close();
                        refreshBetslip();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void refreshBetslip() {
        NetworkHelper nh = new NetworkHelper(act);
        if (!nh.isNetworkAvailable()) {
            Toast.makeText(act, act.getString(R.string.newtwork_unavailable), Toast.LENGTH_LONG)
                    .show();
        } else {
            new RefreshTask(act).execute();
        }
    }
}
