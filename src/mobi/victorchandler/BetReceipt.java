
package mobi.victorchandler;

import com.flurry.android.FlurryAgent;
import com.pinkelstar.android.server.PinkelStarException;
import com.pinkelstar.android.server.Server;

import mobi.victorchandler.database.BetsDb;
import mobi.victorchandler.task.PlaceBetTask;
import mobi.victorchandler.view.ActionBar;
import mobi.victorchandler.view.ActionBar.AbstractAction;
import mobi.victorchandler.view.ActionBar.Action;
import mobi.victorchandler.view.ActionBar.IntentAction;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Screen for validating the bets made
 * @author riveram
 *
 */
public class BetReceipt extends Activity {

    // TODO set official VC's pinkelstar account data
    public static String KEY = "f96b269b9adfe04ae013a805fef656b8ee2d99";
    public static String SECRET = "10962c837affede9886b0c24968e26e7b9b475";

    private String sharemsg;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        String refId = extras.getString("reference_id");

        final ArrayList<String> singleevents = extras
                .getStringArrayList(PlaceBetTask.EXTRA_SINGLEEVENTS);
        ArrayList<String> singlemarkets = extras
                .getStringArrayList(PlaceBetTask.EXTRA_SINGLEMARKETS);
        ArrayList<String> singlemarketEW = extras
                .getStringArrayList(PlaceBetTask.EXTRA_SINGLEMARKET_EACH_WAY);
        ArrayList<String> singleoutcomes = extras
                .getStringArrayList(PlaceBetTask.EXTRA_SINGLEOUTCOMES);
        ArrayList<String> singleodds = extras.getStringArrayList(PlaceBetTask.EXTRA_SINGLEODDS);
        ArrayList<String> singlestakes = extras.getStringArrayList(PlaceBetTask.EXTRA_SINGLESTAKES);

        ArrayList<String> singleOutcomeIds = extras
                .getStringArrayList(PlaceBetTask.EXTRA_SINGLEOUTCOME_ID);

        // final ArrayList<String> singledates = extras
        // .getStringArrayList(PlaceBetTask.EXTRA_SINGLEDATES);

        // Multiples
        ArrayList<String> mStakes = extras
                .getStringArrayList(PlaceBetTask.EXTRA_MULTIPLE_STAKES);
        ArrayList<String> mDescriptions = extras
                .getStringArrayList(PlaceBetTask.EXTRA_MULTIPLE_DESCRIPTION);
        ArrayList<String> mMultiplicity = extras
                .getStringArrayList(PlaceBetTask.EXTRA_MULTIPLE_MULTIPLICITY);

        ArrayList<String> mEwEnabled = extras
                .getStringArrayList(PlaceBetTask.EXTRA_MULTIPLE_EACHWAY_ENABLED);

        setContentView(R.layout.betreceipt);

        final TextView tvRefId = (TextView) findViewById(R.id.tvReferenceId);
        tvRefId.setText(refId);

        setActionBar();

        final LinearLayout singles = (LinearLayout) findViewById(R.id.singles);
        final LinearLayout multiples = (LinearLayout) findViewById(R.id.multiples);

        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        if (singlestakes != null) {
            for (int m = 0; m < singlestakes.size(); m++) {

                if (singlestakes.get(m) != null) {

                    if (m == 0) {
                        TextView header = new TextView(this);
                        header.setText(R.string.singlesheader);
                        header.setBackgroundDrawable(getResources().getDrawable(
                                R.drawable.head_background));
                        header.setTextColor(Color.WHITE);
                        header.setGravity(Gravity.CENTER);
                        header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                        header.setTypeface(header.getTypeface(), Typeface.BOLD);
                        singles.addView(header);
                    }
                    BetsDb db = new BetsDb(this);
                    db.open();
                    Cursor c = db.getCursor();
                    if (c.moveToFirst()) {
                        do {
                            if (singleOutcomeIds.get(m).equals(
                                    c.getString(c.getColumnIndex(BetsDb.KEY_OUTCOMEID)))) {
                                LinearLayout single = (LinearLayout) li.inflate(
                                        R.layout.singlereceipt,
                                        null);

                                if (m % 2 == 0)
                                    single.setBackgroundColor(Color.WHITE);
                                else
                                    single.setBackgroundColor(Color.LTGRAY);

                                TextView meeting = (TextView) single.findViewById(R.id.meetingtv);
                                TextView market = (TextView) single.findViewById(R.id.markettv);
                                TextView outcome = (TextView) single.findViewById(R.id.outcometv);

                                TextView odds = (TextView) single.findViewById(R.id.oddstv);
                                TextView stake = (TextView) single.findViewById(R.id.stakestv);

                                TextView tvAlarmDate = (TextView) single
                                        .findViewById(R.id.tvAlarmDate);

                                tvAlarmDate.setText(c.getString(c
                                        .getColumnIndex(BetsDb.KEY_EVENT_DATE)));

                                meeting.setText(c.getString(c.getColumnIndex(BetsDb.KEY_EVENTNAME)));
                                market.setText(c.getString(c.getColumnIndex(BetsDb.KEY_MARKETNAME)));
                                String sinOutcomes = "";
                                if (Boolean.parseBoolean(c.getString(c
                                        .getColumnIndex(BetsDb.KEY_CANEW)))) {
                                    sinOutcomes = c.getString(c
                                            .getColumnIndex(BetsDb.KEY_OUTCOMENAME)) + " (E/W)";
                                } else {
                                    sinOutcomes = c.getString(c
                                            .getColumnIndex(BetsDb.KEY_OUTCOMENAME));
                                }
                                outcome.setText(sinOutcomes);

                                odds.setText(c.getString(c
                                        .getColumnIndex(BetsDb.KEY_OUTCOMEFORMATTEDPRICE)));
                                stake.setText(singlestakes.get(m));

                                singles.addView(single);

                            }
                        } while (c.moveToNext());
                    }
                    c.close();
                    db.close();

                }
            }
        }

        // ====== Multiples
        if (mStakes != null) {
            for (int m = 0; m < mStakes.size(); m++) {

                if (mStakes.get(m) != null) {
                    if (m == 0) {
                        TextView header = new TextView(this);
                        header.setText(R.string.multipleheader);
                        header.setBackgroundDrawable(getResources().getDrawable(
                                R.drawable.head_background));
                        header.setTextColor(Color.WHITE);
                        header.setGravity(Gravity.CENTER);
                        header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                        header.setTypeface(header.getTypeface(), Typeface.BOLD);
                        multiples.addView(header);

                        for (int i = 0; i < singleevents.size(); i++) {

                            LinearLayout multipleheader = (LinearLayout) li.inflate(
                                    R.layout.multiplereceiptheader,
                                    null);
                            TextView meeting = (TextView)
                                    multipleheader.findViewById(R.id.meetingtv);
                            TextView market = (TextView)
                                    multipleheader.findViewById(R.id.markettv);
                            TextView outcome = (TextView)
                                    multipleheader.findViewById(R.id.outcometv);
                            if (i % 2 == 0)
                                multipleheader.setBackgroundResource(android.R.color.white);
                            else
                                multipleheader.setBackgroundResource(android.R.color.darker_gray);

                            meeting.setText(singleevents.get(i));
                            market.setText(singlemarkets.get(i));
                            String sinOutcomes = "";
                            if (Boolean.parseBoolean(singlemarketEW.get(i))) {
                                sinOutcomes = singleoutcomes.get(i) + " (E/W)";
                            } else {
                                sinOutcomes = singleoutcomes.get(i);
                            }
                            outcome.setText(sinOutcomes + " @ " + singleodds.get(i));
                            multiples.addView(multipleheader);

                        }

                        for (int x = 0; x < mStakes.size(); x++) {
                            LinearLayout multiple = (LinearLayout)
                                    li.inflate(R.layout.multiplereceipt, null);

                            if (x % 2 == 0)
                                multiple.setBackgroundColor(Color.WHITE);
                            else
                                multiple.setBackgroundColor(Color.LTGRAY);

                            TextView type = (TextView)
                                    multiple.findViewById(R.id.multipletypetv);
                            TextView stake = (TextView) multiple.findViewById(R.id.stakestv);

                            if (Boolean.parseBoolean(mEwEnabled.get(x))) {
                                type.setText(mDescriptions.get(x) + " (E/W)");
                            } else {
                                type.setText(mDescriptions.get(x));
                            }
                            int finStake = Integer.parseInt(mStakes.get(x))
                                    * Integer.parseInt(mMultiplicity.get(x));
                            stake.setText(String.valueOf(finStake));

                            multiples.addView(multiple);

                        }
                    }
                }
            }
        }

        //
        // This button should be placed and implemented whenever you want a
        // notification on a particular bet.
        //
         // Button notify = (Button) findViewById(R.id.buttonnotify);
        
       /**
        *  notify.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationDB db = new NotificationDB(BetReceipt.this);
                db.open();
                AlarmManager am = (AlarmManager)
                        getSystemService(Context.ALARM_SERVICE);
                int adds = 0;
                // skip header
                for (int i = 1; i < singles.getChildCount(); i++) {

                    LinearLayout single = (LinearLayout) singles.getChildAt(i);

                    ToggleButton cb = (ToggleButton)
                            single.findViewById(R.id.tbAlarm);

                    if (cb.isChecked()) {
                        if (!db.hasNotification(singledates.get(i - 1))) {
                            String id = String.valueOf(db.insertNotification(
                                    singledates.get(i - 1),
                                    singleevents.get(i - 1)));
                            Intent intent = new Intent("com.victorchandler.notify");
                            intent.putExtra("date", singledates.get(i - 1));
                            intent.putExtra("event", singleevents.get(i - 1));
                            intent.putExtra("id", id);

                            PendingIntent pi = PendingIntent.getBroadcast(BetReceipt.this, 0,
                                    intent, PendingIntent.FLAG_ONE_SHOT);
                            am.set(AlarmManager.RTC_WAKEUP, Long.parseLong(singledates.get(i -
                                    1)),
                                    pi);

                        }
                        adds++;

                    }
                }
                db.close();
                Toast.makeText(BetReceipt.this,
                        adds + " " + getString(R.string.notificationsadded),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        */

    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, Home.FLURRYKEY);
    }

    @Override
    protected void onStop() {
        FlurryAgent.onEndSession(this);
        super.onStop();
    }

    public static final int DIALOG_INIT = 0;
    public static final int DIALOG_POST = 1;

    @Override
    protected Dialog onCreateDialog(int id) {
        ProgressDialog pd = new ProgressDialog(this);
        switch (id) {
            case DIALOG_INIT:
                pd.setMessage(getString(R.string.initsharing));
                break;
            case DIALOG_POST:
                pd.setMessage(getString(R.string.postsharing));
                break;
        }
        return pd;
    }

    /**
     * Inner class for initializing server of Pinklestar
     * @author riveram
     *
     */
    class InitTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            try {
                Server.initialize(getApplication(), KEY, SECRET);
            } catch (PinkelStarException pse) {
            } catch (IllegalStateException ise) {

            }
            return null;
        }

        protected void onPostExecute(Object result) {
            removeDialog(DIALOG_INIT);
            Server server = Server.getInstance();
            String[] networks = server.getKnownNetworks();
            if (!server.isNetworkAuthenticated(networks[0])) {
                server.startAuthentication(BetReceipt.this, server.getKnownNetworks()[0]);
            } else {
                new PostTask().execute();
            }
        }

        protected void onPreExecute() {
            showDialog(DIALOG_INIT);
        };
    }

    /*
     * called when authentication process completes.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        new PostTask().execute();
    }

    /**
     * Inner Class for executing UI thread of posting a bet.
     * @author riveram
     *
     */
    class PostTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            try {
                Server server = Server.getInstance();
                String[] networks = server.getKnownNetworks();
                server.publishMessage(networks, sharemsg, "Click here to place your own bet",
                        "http://www.victorchandler.com");
            } catch (PinkelStarException pse) {

            }
            return null;
        }

        protected void onPostExecute(Object result) {
            removeDialog(DIALOG_POST);
        }

        protected void onPreExecute() {
            showDialog(DIALOG_POST);
        };
    }

    /**
     * places Actions on top of the screen.
     */
    public void setActionBar() {

        actionBar = (ActionBar) findViewById(R.id.actionbar);

        actionBar.setHomeAction(new IntentAction(this, Home.createIntent(this), R.drawable.home));
        actionBar.setDisplayHomeAsUpEnabled(true);
        Action accountAction = new IntentAction(this, new Intent(this, MyAccountHome.class),
                R.drawable.account);
        actionBar.addAction(accountAction);
        Action favoritesAction = new IntentAction(this, new Intent(this, Favorites.class),
                R.drawable.favorites);
        actionBar.addAction(favoritesAction);

        actionBar.addAction(new ShareAction());

    }

    /**
     * Uses share functionality probably much better implementation should be placed here.
     * @author riveram
     *
     */
    private class ShareAction extends AbstractAction {

        public ShareAction() {
            super(R.drawable.facebook);
        }

        @Override
        public void performAction(View view) {
            new InitTask().execute();
        }

    }
}
