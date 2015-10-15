
package mobi.victorchandler;

import mobi.victorchandler.database.BetsDb;
import mobi.victorchandler.listeners.ErrorListenerManager;
import mobi.victorchandler.model.Multiple;
import mobi.victorchandler.model.Single;
import mobi.victorchandler.preferences.BasePreferences;
import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.OutcomesResponse;
import mobi.victorchandler.task.PlaceBetTask;
import mobi.victorchandler.task.RefreshTask;
import mobi.victorchandler.util.Calculations;
import mobi.victorchandler.util.NetworkHelper;
import mobi.victorchandler.view.ActionBar;
import mobi.victorchandler.view.ActionBar.Action;
import mobi.victorchandler.view.ActionBar.IntentAction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Screen for viewing bets made(singles and multiples) this is where you place
 * your bets
 * 
 * @author riveram
 */
public class BetSlip extends Activity {

    public static final int DIALOG_LOADING = 0;
    public static final int DIALOG_SUBMITTING = 1;
    public static final int DIALOG_ERROR = 2;
    public static final int DIALOG_ADDCREDIT = 3;
    public static final int DIALOG_PROCESSCREDIT = 4;
    public static final int DIALOG_CREDITERROR = 5;

    private double totalstakes;
    private double totalreturns;

    private TextView tvTotalStake;
    private TextView tvTotalReturns;
    private Button btnPlaceBet;
    private Button btnCalculateRetuns;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    private ArrayList<Multiple> multiples;
    private ArrayList<String> multiplestakes;

    private ArrayList<Single> singles;
    private ArrayList<String> singlestakes;

    private LinearLayout layoutSingle;
    private LinearLayout layoutMultiple;

    private ActionBar actionBar;
    public String[] reofferedstakeids;
    public ArrayList<String> dates;
    private BetsDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.betslip);

        BasePreferences.load(this);
        setActionBar();

        btnPlaceBet = (Button) findViewById(R.id.btnPlaceBet);

        if (!LoginPreferences.getToken().equals("")) {
            btnPlaceBet.setEnabled(true);
        } else {
            btnPlaceBet.setEnabled(false);
        }

        setUpTask();

        // ==== Calculate Returns ====
        btnCalculateRetuns = (Button) findViewById(R.id.btnCalculateReturns);
        btnCalculateRetuns.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                calculateReturns();
            }
        });

        // ==== Submit Bet ====
        btnPlaceBet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new PlaceBetTask(BetSlip.this).execute();
            }
        });

    }

    private void setUpTask() {
        NetworkHelper nh = new NetworkHelper(this);
        if (!nh.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.newtwork_unavailable), Toast.LENGTH_LONG)
                    .show();
        } else {
            new RefreshTask(this).execute();
        }
    }

    /**
     * calculating Total Stakes and Possible Returns
     */
    protected void calculateReturns() {

        totalstakes = 0;
        totalreturns = 0;

        singlestakes = getSingleStakes();
        multiplestakes = getMultipleStakes();

        boolean[] singleews = getSingleEWs();
        boolean[] multipleews = getMultipleEWs();

        if (singlestakes != null) {
            for (int i = 0; i < singlestakes.size(); i++) {
                double stake = 0;
                try {
                    stake = Double.parseDouble(singlestakes.get(i).toString());
                } catch (NumberFormatException nfe) {
                }
                if (singleews[i]) {
                    totalstakes += (stake * 2);
                    totalreturns += stake
                            * Double.parseDouble((singles.get(i).getOutcome().getPriceDecimal()));
                    totalreturns += Calculations.getEacWayWinnings(this, stake,
                            Double.parseDouble((singles.get(i).getOutcome().getPriceDecimal())));
                } else {
                    totalstakes += stake;
                    totalreturns += stake
                            * Double.parseDouble((singles.get(i).getOutcome().getPriceDecimal()));
                }
            }
        }

        if (multiplestakes != null) {
            for (int i = 0; i < multiplestakes.size(); i++) {
                double stake = 0;
                try {
                    stake = Double.parseDouble(multiplestakes.get(i).toString())
                            * multiples.get(i).getMultiplicity();

                } catch (NumberFormatException nfe) {
                }
                if (multipleews[i]) {
                    totalstakes += (stake * 2);
                    totalreturns += stake * multiples.get(i).getEwfactor();

                } else {
                    totalstakes += stake;
                    totalreturns += stake * multiples.get(i).getWinfactor();
                }
            }

        }

        // clearFields();

        setFooter();
    }

    // private void clearFields() {
    // LinearLayout layoutSingle = (LinearLayout) findViewById(R.id.linSingles);
    // for (int i = 1; i < layoutSingle.getChildCount(); i++) {
    //
    // EditText txtSingleStake = (EditText)
    // layoutSingle.getChildAt(i).findViewById(
    // R.id.stakeet);
    // txtSingleStake.setText("");
    // }
    //
    // LinearLayout layoutMultiple = (LinearLayout)
    // findViewById(R.id.linMultiples);
    // for (int i = 1; i < layoutMultiple.getChildCount(); i++) {
    //
    // EditText txtMultipleStake = (EditText)
    // layoutMultiple.getChildAt(i).findViewById(
    // R.id.multipleet);
    // txtMultipleStake.setText("");
    // }
    // }

    /**
     * Getting the single stakes in the UI TextField
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> getSingleStakes() {

        LinearLayout layoutSingle = (LinearLayout) findViewById(R.id.linSingles);

        if (layoutSingle.getChildCount() == 0) {
            return null;
        }

        ArrayList<String> stakesSingle = new ArrayList<String>();
        for (int i = 1; i < layoutSingle.getChildCount(); i++) {

            EditText txtSingleStake = (EditText) layoutSingle.getChildAt(i).findViewById(
                    R.id.stakeet);
            stakesSingle.add(i - 1, txtSingleStake.getText().toString());
        }

        return stakesSingle;
    }

    /**
     * Getting the multiple stakes in the UI TextField
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> getMultipleStakes() {

        LinearLayout layoutMultiple = (LinearLayout) findViewById(R.id.linMultiples);

        if (layoutMultiple.getChildCount() == 0) {
            return null;
        }

        ArrayList<String> stakesMultiple = new ArrayList<String>();
        for (int i = 1; i < layoutMultiple.getChildCount(); i++) {

            EditText txtMultipleStake = (EditText) layoutMultiple.getChildAt(i)
                    .findViewById(R.id.multipleet);
            stakesMultiple.add(i - 1, txtMultipleStake.getText().toString());
        }

        return stakesMultiple;
    }

    /**
     * Getting the singles each way in the UI Checkbox
     * 
     * @return boolean[]
     */
    public boolean[] getSingleEWs() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linSingles);
        if (layout.getChildCount() == 0)
            return null;
        boolean[] singleEWs = new boolean[layout.getChildCount() - 1];
        for (int i = 1; i < layout.getChildCount(); i++) {
            CheckBox ewcb = (CheckBox) layout.getChildAt(i).findViewById(R.id.ewcb);
            singleEWs[i - 1] = ewcb.isChecked();
        }
        return singleEWs;
    }

    /**
     * Getting the multiples each way in the UI Checkbox
     * 
     * @return boolean[]
     */
    public boolean[] getMultipleEWs() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linMultiples);
        if (layout.getChildCount() == 0)
            return null;
        boolean[] multipleEWs = new boolean[layout.getChildCount() - 1];
        for (int i = 1; i < layout.getChildCount(); i++) {
            CheckBox ewcb = (CheckBox) layout.getChildAt(i).findViewById(R.id.ewcb);
            multipleEWs[i - 1] = ewcb.isChecked();
        }
        return multipleEWs;
    }

    /**
     * Method for adding Single bets into the bet slip
     * 
     * @param ArrayList<Single> singles
     * @param ArrayList<String> stakes
     */
    public void addSingles(ArrayList<Single> singles,
            ArrayList<String> stakes) {

        db = new BetsDb(this);
        db.open();
        Cursor cursor = db.getCursor();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            singles = new ArrayList<Single>(cursor.getCount());

            LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            layoutSingle = (LinearLayout) findViewById(R.id.linSingles);
            layoutSingle.removeAllViews();

            if (cursor.getCount() > 0) {

                TextView headertv = new TextView(this);
                headertv.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.head_background));
                headertv.setTextColor(Color.WHITE);
                headertv.setGravity(Gravity.CENTER);
                headertv.setText(R.string.singlesheader);
                headertv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                layoutSingle.addView(headertv);

            }

            for (int i = 0; i < cursor.getCount(); i++) {

                View singleView = li.inflate(R.layout.singlebet, null);

                if (i % 2 == 0) {
                    singleView.setBackgroundColor(Color.WHITE);
                } else {
                    singleView.setBackgroundColor(Color.LTGRAY);
                }

                TextView datetv = (TextView) singleView.findViewById(R.id.tvDate);
                datetv.setText(DateFormat.getDateTimeInstance().format(
                        new Date(cursor.getString((BetsDb.EVENT_DATE_COLUMN)))));

                TextView eventtv = (TextView) singleView.findViewById(R.id.eventtv);
                eventtv.setText(cursor.getString(BetsDb.EVENTNAME_COLUMN));

                TextView outcometv = (TextView) singleView.findViewById(R.id.outcometv);
                outcometv.setText(cursor.getString(BetsDb.OUTCOMENAME_COLUMN));

                TextView markettv = (TextView) singleView.findViewById(R.id.markettv);
                markettv.setText(cursor.getString(BetsDb.MARKETNAME_COLUMN) + " "
                        + cursor.getString(BetsDb.MARKETPERIOD_COLUMN));

                TextView pricetv = (TextView) singleView.findViewById(R.id.pricetv);
                if (cursor.getString(BetsDb.OUTCOMEFORMATTEDPRICE_COLUMN).equals("")) {
                    pricetv.setText("SP");
                    pricetv.setTextColor(Color.parseColor("#FF9933"));
                } else {
                    pricetv.setText(cursor.getString(BetsDb.OUTCOMEFORMATTEDPRICE_COLUMN));
                }

                EditText txtSingleStake = (EditText) singleView.findViewById(R.id.stakeet);
                if (stakes != null && stakes.size() > i && stakes.get(i) != null) {
                    txtSingleStake.setText(stakes.get(i));
                }

                CheckBox ewcb = (CheckBox) singleView.findViewById(R.id.ewcb);
                ewcb.setVisibility(cursor.getString(BetsDb.CANEW_COLUMN).equals("false") ? View.GONE
                        : View.VISIBLE);
                ewcb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                        calculateReturns();
                        setFooter();
                    }
                });

                ImageButton deletebutton = (ImageButton) singleView.findViewById(R.id.delbutton);
                final int id = cursor.getInt(BetsDb.ID_COLUMN);
                deletebutton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        BetsDb deldb = new BetsDb(BetSlip.this);
                        deldb.open();
                        deldb.delete(id);
                        deldb.close();

                        layoutSingle.removeAllViews();

                        if (layoutMultiple != null) {
                            layoutMultiple.removeAllViews();
                        }

                        setUpTask();

                    }
                });

                layoutSingle.addView(singleView);

                Single single = new Single();

                EventsResponse er = new EventsResponse();
                er.setEventDate(cursor.getString(BetsDb.EVENT_DATE_COLUMN));
                er.setEventName(cursor.getString(BetsDb.EVENTNAME_COLUMN));
                single.setEvent(er);

                MarketsResponse mr = new MarketsResponse();
                mr.setDescription(cursor.getString(BetsDb.MARKETNAME_COLUMN));
                mr.setEachWay(cursor.getString(BetsDb.CANEW_COLUMN));
                single.setMarket(mr);

                OutcomesResponse or = new OutcomesResponse();
                or.setPriceDecimal(cursor
                        .getString(BetsDb.OUTCOMEPRICE_COLUMN));
                or.setPriceFormatted(cursor.getString(BetsDb.OUTCOMEFORMATTEDPRICE_COLUMN));
                or.setPriceId(cursor.getString(BetsDb.OUTCOMEPRICEID_COLUMN));
                or.setDescription(cursor.getString(BetsDb.OUTCOMENAME_COLUMN));
                or.setId(cursor.getString(BetsDb.OUTCOMEID_COLUMN));
                single.setOutcome(or);

                singles.add(single);

                cursor.moveToNext();
            }

        }

        this.singles = singles;
        cursor.close();
        db.close();
    }

    /**
     * Method for adding Multiple bets into the bet slip
     */
    public void addMultiples() {

        // boolean errorfound = hasErrors();

        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        layoutMultiple = (LinearLayout) findViewById(R.id.linMultiples);
        layoutMultiple.removeAllViews();
        if (multiples.size() == 0)
            return;

        TextView headertv = new TextView(this);
        headertv.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.head_background));
        headertv.setTextColor(Color.WHITE);
        headertv.setGravity(Gravity.CENTER);
        headertv.setText(R.string.multipleheader);
        headertv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        layoutMultiple.addView(headertv);

        for (int i = 0; i < multiples.size(); i++) {

            View multipleView = li.inflate(R.layout.multiplebet, null);
            if (i % 2 == 0) {
                multipleView.setBackgroundColor(0xffd8d6d6);
            } else {
                multipleView.setBackgroundColor(0xffffffff);
            }

            TextView multipletv = (TextView) multipleView.findViewById(R.id.multipletv);
            multipletv.setText(multiples.get(i).getDescription() + " / "
                    + multiples.get(i).getMultiplicity());
            multipletv.setTextColor(0xff000000);

            EditText stakeet = (EditText) multipleView.findViewById(R.id.multipleet);
            // stakeet.setEnabled(!errorfound);
            if (getMultipleStakes() != null &&
                    getMultipleStakes().size() > i
                    && getMultipleStakes().get(i) != null) {

                stakeet.setText(getMultipleStakes().get(i));
            }

            CheckBox eachway = (CheckBox) multipleView.findViewById(R.id.ewcb);
            // eachway.setEnabled(!errorfound);
            eachway.setVisibility(multiples.get(i).isEachWayEnabled() ? View.VISIBLE : View.GONE);
            eachway.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    calculateReturns();
                    setFooter();
                }
            });

            layoutMultiple.addView(multipleView);

        }

    }

    /**
     * Method to set and modify the Footer about the stakes made and possible
     * returns of it.
     */
    public void setFooter() {

        tvTotalStake = (TextView) findViewById(R.id.tvTotalStake);
        tvTotalReturns = (TextView) findViewById(R.id.tvTotalReturn);

        if (LoginPreferences.getToken().equals("")) {
            btnPlaceBet.setEnabled(false);
        } else {
            btnPlaceBet.setEnabled(true);
        }

        tvTotalStake.setText(currencyFormat.format(totalstakes));
        tvTotalReturns.setText(currencyFormat.format(totalreturns));
        if (singlestakes != null) {
            for (int i = 0; i < singlestakes.size(); i++)
                if (Double.parseDouble((singles.get(i).getOutcome().getPriceDecimal())) == 0)
                    tvTotalReturns.setText(R.string.notavailable);
        } else
            tvTotalReturns.setText(R.string.notavailable);
    }

    /**
     * Method to identify the errors received from the requested bets.
     * 
     * @param HashMap<Integer,String> errorMap
     */
    public void hasErrors(HashMap<Integer, String> errorMap) {

        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout betslipView = (LinearLayout) findViewById(R.id.betslip);

        if (errorMap != null) {
            LinearLayout errorLayout = (LinearLayout) betslipView
                    .findViewById(R.id.errorBetLayout);
            errorLayout.removeAllViews();
            TextView tvSummary = new TextView(this);
            tvSummary.setText("Errors Summary");
            tvSummary.setTextColor(Color.RED);
            tvSummary.setTextAppearance(this, android.R.attr.textAppearanceLarge);
            tvSummary.setGravity(Gravity.CENTER);
            errorLayout.addView(tvSummary);

            errorLayout.setVisibility(View.VISIBLE);

            Set<Integer> s = errorMap.keySet();
            Iterator<Integer> it = s.iterator();
            while (it.hasNext()) {
                Integer code = it.next();
                String errorMessage = errorMap.get(code);
                View errorLine = li.inflate(R.layout.error_line, null);

                TextView message = (TextView) errorLine.findViewById(R.id.errortv);
                message.setText(errorMessage);
                Button changeButton = (Button) errorLine.findViewById(R.id.acceptbutton);

                // Type or code must come from the response attached to the
                // error message
                int errorType = code.intValue();
                String outcomeId = "";

                Log.d("BETSLIP ERROR", "Code: " + errorType + " | error: " + errorMessage);

                ErrorListenerManager errorManager = new ErrorListenerManager(this, errorType,
                        outcomeId);

                changeButton.setOnClickListener(errorManager.getListener());

                errorLayout.addView(errorLine);

            }

        }
    }

    public void setActionBar() {

        actionBar = (ActionBar) findViewById(R.id.actionbar);

        actionBar.setHomeAction(new IntentAction(this, Home.createIntent(this), R.drawable.home));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("BET SLIP");
        LoginPreferences.load(this);
        if (!LoginPreferences.getToken().equals("")) {

            Action accountAction = new IntentAction(this, new Intent(this, MyAccountHome.class),
                    R.drawable.account);
            actionBar.addAction(accountAction);
            Action favoritesAction = new IntentAction(this, new Intent(this, Favorites.class),
                    R.drawable.favorites);
            actionBar.addAction(favoritesAction);
        }

    }

    public void setEmptyBetSlipView() {

        setContentView(R.layout.betslip_empty);
        setActionBar();

    }

    /**
     * Sets the screen with the ActionBar and TextView for telling the user the
     * unavailability
     */
    public void setUnavailableBetSlipView() {

        setContentView(R.layout.betslip_unavailable);
        setActionBar();

    }

    // These Dialogs can be used or modified for a better implementation.
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog d = new AlertDialog.Builder(this).create();
        switch (id) {
            case DIALOG_LOADING:
                d = new ProgressDialog(this);
                ((ProgressDialog) d).setMessage(getString(R.string.loadmultiples));
                break;
            case DIALOG_SUBMITTING:
                d = new ProgressDialog(this);
                ((ProgressDialog) d).setMessage(getString(R.string.placebet));
                break;
            case DIALOG_ERROR:
                ((AlertDialog) d).setMessage("Bet Submission contains error");
                ((AlertDialog) d).setButton(AlertDialog.BUTTON_NEUTRAL, "Check Summary",
                        new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismissDialog(DIALOG_ERROR);
                            }
                        });
                break;
            case DIALOG_ADDCREDIT:
                d = new Dialog(this);
                d.setContentView(R.layout.deposit);
                Button deposit = (Button) d.findViewById(R.id.deposit);
                d.setTitle("Add moar moneh!!1!");
                deposit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // new DepositTask(BetSlip.this).execute();
                    }
                });
                break;
            case DIALOG_PROCESSCREDIT:
                d = new ProgressDialog(this);
                ((ProgressDialog) d).setMessage("Depositing..");
                break;
            case DIALOG_CREDITERROR:
                ((AlertDialog) d).setMessage("could not add credit! try other card");
                ((AlertDialog) d).setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                dismissDialog(DIALOG_CREDITERROR);
                            }
                        });
                break;

            default:
                ((AlertDialog) d).setMessage("Whoa.. something went very wrong!");
                break;
        }
        return d;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        if (id == DIALOG_ADDCREDIT) {
            Spinner creditcards = (Spinner) dialog.findViewById(R.id.creditcard);
            ArrayList<String> data = new ArrayList<String>();
            data.add("creditcard1");
            data.add("creditcard2");
            data.add("creditcard3");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            creditcards.setAdapter(adapter);
        }
        super.onPrepareDialog(id, dialog);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    public ArrayList<Multiple> getMultiples() {
        return multiples;
    }

    public void setMultiples(ArrayList<Multiple> multiples) {
        this.multiples = multiples;
    }

    public ArrayList<Single> getSingles() {
        return singles;
    }

    public void setSingles(ArrayList<Single> singles) {
        this.singles = singles;
    }

}
