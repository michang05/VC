package mobi.victorchandler.listeners;

import mobi.victorchandler.BetSlip;
import mobi.victorchandler.database.BetsDb;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;
/**
 * Class for Checking the Price Change
 * this class can be improved and depends on the json response.
 * @author riveram
 *
 */
public class PriceChangeListener implements OnClickListener {

	private BetSlip betslip;
	private ArrayList<String> errorMessages;
	private int index;
	private String outcomeid;
	private String priceid;
	private String returns;
	private String formatted;
//	private  ArrayList<OnClickListener> changelisteners;

	public PriceChangeListener(BetSlip betslip, ArrayList<String> errorMessages, ArrayList<OnClickListener> changelisteners,
	        int index, String outcomeid, String priceid, String returns, String formatted) {
		this.betslip = betslip;
		this.errorMessages = errorMessages;
		this.index = index;
		this.priceid = priceid;
		this.outcomeid = outcomeid;
		this.returns = returns;
		this.formatted = formatted;
//		this.changelisteners = changelisteners;
	}

	@Override
	public void onClick(View v) {
		BetsDb db = new BetsDb(betslip);
		db.open();
		db.updateBet(outcomeid, priceid, returns, formatted);
		db.close();

		errorMessages.add(index,null);

		betslip.addSingles( betslip.getSingles(),betslip.getSingleStakes());
		betslip.addMultiples();
		betslip.setFooter();
	}

}
