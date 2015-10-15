
package mobi.victorchandler.task;

import mobi.victorchandler.BetSlip;
import mobi.victorchandler.Home;
import mobi.victorchandler.MarketOutcomes;
import mobi.victorchandler.MyAccountHome;
import mobi.victorchandler.R;
import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.OutcomesResponse;
import mobi.victorchandler.util.DataHelper;
import mobi.victorchandler.view.ActionBar;
import mobi.victorchandler.view.ActionBar.Action;
import mobi.victorchandler.view.ActionBar.IntentAction;
import mobi.victorchandler.view.MarketOutComesArrayAdapter;
import mobi.victorchandler.view.MarketOutcomesBaseExpandableListAdapter;
import mobi.victorchandler.webservice.MarketOutcomesService;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/**
 * Task for MarketOutcomes service
 * @author riveram
 *
 */
public class GetMarketOutcomesTask extends AsyncTask<Void, Void, Boolean> {

    private ListView lv;
    private ProgressDialog progressDialog;

    private MarketOutcomesService marketOutcomeService;
    private String[] market;
    private MarketOutcomes marketOutcome;

    private ArrayList<HashMap<String, String>> groupData = new ArrayList<HashMap<String, String>>();
    private ArrayList<ArrayList<HashMap<String, String>>> childData = new ArrayList<ArrayList<HashMap<String, String>>>();

    private MarketOutcomesBaseExpandableListAdapter adapterExpandable;
    private MarketOutComesArrayAdapter adapterNormal;
    private ExpandableListView elv;
    private View linearMarketOutcomes;
    private LinearLayout ll;
    private List<MarketsResponse> sortList;
    private ActionBar actionBar;

    public GetMarketOutcomesTask(String[] market,
            MarketOutcomes marketOutcome) {

        this.market = market;
        this.marketOutcome = marketOutcome;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        if (marketOutcome.getParent() == null) {
            progressDialog = new ProgressDialog(marketOutcome);
        } else {
            progressDialog = new ProgressDialog(marketOutcome.getParent());
        }
        progressDialog.setMessage("Fetching Market Outcomes ...");
        progressDialog.show();

        marketOutcomeService = new MarketOutcomesService(marketOutcome, market);
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        if (!marketOutcomeService.executeRequest()) {
            return false;
        }

        return true;
    }

    private void reorderdata() {

        // Sort the Region
        sortList = new ArrayList<MarketsResponse>();
        for (MarketsResponse mr : marketOutcomeService.getMarketoutcomesdata()) {
            sortList.add(mr);
        }

        final Comparator<MarketsResponse> comparatorRegion = new Comparator<MarketsResponse>() {
            @Override
            public int compare(MarketsResponse object1, MarketsResponse object2) {
                return object1.getDescription().compareTo(object2.getDescription());
            }
        };

        Collections.sort(sortList, comparatorRegion);

        for (MarketsResponse mrSort : sortList) {

            // String region = mrSort.getId();
            // boolean found = false;
            //
            // for (int j = 0; j < groupData.size(); j++) {
            //
            // String tregion =
            // groupData.get(j).get(SportsBook.KEY_MARKET_ID);
            // if (tregion.equals(region)) {
            // found = true;
            // ArrayList<HashMap<String, String>> child = childData.get(j);
            //
            // for (OutcomesResponse or : mrSort.getOutcomeList()) {
            //
            // HashMap<String, String> hmchild = new HashMap<String,
            // String>();
            //
            // hmchild.put(SportsBook.KEY_OUTCOME_DESCRIPTION,
            // or.getDescription());
            // hmchild.put(SportsBook.KEY_OUTCOME_DECIMAL_PRICE,
            // or.getPriceDecimal());
            // hmchild.put(SportsBook.KEY_OUTCOME_PRICE_ID,
            // or.getPriceId());
            // hmchild.put(SportsBook.KEY_OUTCOME_ID, or.getId());
            // hmchild.put(SportsBook.KEY_OUTCOME_FORMATTED_PRICE,
            // or.getPriceFormatted());
            // hmchild.put(SportsBook.KEY_PREVIOUS_PRICE_ID,
            // or.getPreviousPriceId());
            // child.add(hmchild);
            //
            // }
            // break;
            // }
            // }
            //
            // if (!found) {

            HashMap<String, String> mapGroupUnfound = new HashMap<String, String>();

            mapGroupUnfound.put(SportsBook.KEY_MARKET_TYPE_ID, mrSort.getTypeId());
            mapGroupUnfound.put(SportsBook.KEY_MARKET_PERIOD_ID, mrSort.getPeriodId());
            mapGroupUnfound.put(SportsBook.KEY_MARKET_DESCRIPTION,
                    mrSort.getDescription());
            mapGroupUnfound.put(SportsBook.KEY_MARKET_EACHWAY, mrSort.getEachWay());
            mapGroupUnfound.put(SportsBook.KEY_MARKET_PERIOD_DESCRIPTION,
                    mrSort.getPeriodDescription());
            mapGroupUnfound.put(SportsBook.KEY_MARKET_ID, mrSort.getId());
            mapGroupUnfound.put(SportsBook.KEY_MARKET_PLACE_TERMS_DEDUCTION,
                    mrSort.getPlaceTermsDeduction());
            mapGroupUnfound.put(SportsBook.KEY_MARKET_PLACE_TERMS_DESCRIPTION,
                    mrSort.getPlaceTermsDescription());
            mapGroupUnfound.put(SportsBook.KEY_EVENT_ID, mrSort.getEventId());
            mapGroupUnfound.put(SportsBook.KEY_MEETING_ID, mrSort.getMeetingId());
            mapGroupUnfound.put(SportsBook.KEY_SPORT_ID, mrSort.getSportId());

            SportsBook sb = new SportsBook(marketOutcome);
            sb.open();
            EventsResponse er = sb.getEventById(mrSort.getEventId());
            sb.close();

            mapGroupUnfound.put(SportsBook.KEY_EVENTDATE, DateFormat.getDateTimeInstance().format(
                    new Date(er.getEventDate())));

            groupData.add(mapGroupUnfound);

            ArrayList<HashMap<String, String>> listChild =
                    new ArrayList<HashMap<String, String>>();
            HashMap<String, String> mapChild;

            for (OutcomesResponse or : mrSort.getOutcomeList()) {
                if (mrSort.getId().equals(or.getMarketId())) {
                    mapChild = new HashMap<String, String>();

                    mapChild.put(SportsBook.KEY_OUTCOME_DESCRIPTION, or.getDescription());
                    mapChild.put(SportsBook.KEY_OUTCOME_DECIMAL_PRICE, or.getPriceDecimal());
                    mapChild.put(SportsBook.KEY_OUTCOME_PRICE_ID, or.getPriceId());
                    mapChild.put(SportsBook.KEY_OUTCOME_ID, or.getId());
                    mapChild.put(SportsBook.KEY_OUTCOME_FORMATTED_PRICE,
                            or.getPriceFormatted());
                    mapChild.put(SportsBook.KEY_OUTCOME_STARTING_PRICE,
                            or.getPriceStarting());
                    mapChild.put(SportsBook.KEY_PREVIOUS_PRICE_ID, or.getPreviousPriceId());
                    listChild.add(mapChild);
                }

            }

            childData.add(listChild);
            // }

        }

    }

    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        if (result) {

            if (marketOutcomeService.getMarketoutcomesdata().isEmpty()) {

                marketOutcome.setMeetingEmpty();

            } else {

                reorderdata();

                LayoutInflater li = LayoutInflater.from(marketOutcome);
                linearMarketOutcomes = li.inflate(R.layout.market_outcomes_activity, null);

                TextView tvMeetingHeader = (TextView) linearMarketOutcomes
                        .findViewById(R.id.tvEventName_MO);
                tvMeetingHeader.setText(market[5]);

                Button btnRefreshMarketOutcomes = (Button) linearMarketOutcomes
                        .findViewById(R.id.btnRefreshMarketOutcomes);
                btnRefreshMarketOutcomes.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new GetMarketOutcomesTask(market, marketOutcome).execute();
                    }
                });

                TextView tvLastUpdate = (TextView) linearMarketOutcomes
                        .findViewById(R.id.tvLastUpdateMarketOutcomes);
                tvLastUpdate.setText("Updated: "
                        + DataHelper.getFormattedTimeNow(System.currentTimeMillis()));

                ll = (LinearLayout) linearMarketOutcomes.findViewById(R.id.linMarketOutcomes);
                ll.invalidate();

                if (groupData.size() > 0) {

                    elv = new ExpandableListView(marketOutcome);
                    adapterExpandable = new
                            MarketOutcomesBaseExpandableListAdapter(marketOutcome,
                                    groupData, childData, market[5]);

                    elv.setAdapter(adapterExpandable);
                    ll.addView(elv);

                } else {

                    // Sort the Region
                    sortList = new ArrayList<MarketsResponse>();
                    for (MarketsResponse mr : marketOutcomeService.getMarketoutcomesdata()) {
                        sortList.add(mr);
                    }

                    final Comparator<MarketsResponse> compareName = new Comparator<MarketsResponse>() {

                        @Override
                        public int compare(MarketsResponse object1, MarketsResponse object2) {
                            return object1.getDescription().compareTo(object2.getDescription());
                        }
                    };

                    Collections.sort(sortList, compareName);

                    lv = new ListView(marketOutcome);

                    adapterNormal = new
                            MarketOutComesArrayAdapter(marketOutcome,
                                    R.layout.couponvariable, sortList, market[5]);

                    lv.setAdapter(adapterNormal);
                    ll.addView(lv);
                }

                marketOutcome.setContentView(ll);
                if (marketOutcome.getParent() == null) {
                    setActionBar();
                    actionBar.setVisibility(View.VISIBLE);
                }
            }
        }else {
            marketOutcome.setMeetingEmpty();
        }
        progressDialog.dismiss();
    }

    public void setActionBar() {

        actionBar = (ActionBar) marketOutcome.findViewById(R.id.actionbar);

        actionBar.setHomeAction(new IntentAction(marketOutcome, Home.createIntent(marketOutcome),
                R.drawable.home));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Market Outcomes");

        Action accountAction = new IntentAction(marketOutcome, new Intent(marketOutcome,
                MyAccountHome.class),
                R.drawable.account);
        actionBar.addAction(accountAction);

        Intent betslip = new Intent(marketOutcome, BetSlip.class);
        Action betSlipAction = new IntentAction(marketOutcome, betslip,
                R.drawable.betslip);

        actionBar.addAction(betSlipAction);

    }

}
