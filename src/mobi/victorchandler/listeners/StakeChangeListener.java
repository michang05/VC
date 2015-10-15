
package mobi.victorchandler.listeners;

import mobi.victorchandler.BetSlip;
import mobi.victorchandler.model.Single;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;
/**
 * Class for processing stake changes
 */

public class StakeChangeListener implements OnClickListener {

    private BetSlip betslip;
    private ArrayList<String> errorMessages;
    private String reofferedstakeid;
    private String newstake;
    private ArrayList<OnClickListener> changelisteners;
    private ArrayList<String> reOfferedStakeIds;

    public StakeChangeListener(BetSlip betslip, ArrayList<String> errorMessages,
            ArrayList<OnClickListener> changelisteners, String newstake, String reofferedstakeid) {

        this.betslip = betslip;
        this.errorMessages = errorMessages;
        this.changelisteners = changelisteners;

        this.reofferedstakeid = reofferedstakeid;
        this.newstake = newstake;

        reOfferedStakeIds = new ArrayList<String>();
    }

    @Override
    public void onClick(View v) {

        reOfferedStakeIds.add(reofferedstakeid);

        errorMessages = null;
      ArrayList<String> stakes = betslip.getSingleStakes();
        stakes.add(newstake);

        ArrayList<Single> singleStakes = new ArrayList<Single>();

        betslip.addSingles(singleStakes, stakes);

        betslip.addMultiples();
        betslip.setFooter();
    }

}
