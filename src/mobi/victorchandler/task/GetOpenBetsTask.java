
package mobi.victorchandler.task;

import mobi.victorchandler.OpenBetsTab;
import mobi.victorchandler.R;
import mobi.victorchandler.database.StatementsDb;
import mobi.victorchandler.response.BetStatementsResponse;
import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.MeetingsResponse;
import mobi.victorchandler.response.OutcomesResponse;
import mobi.victorchandler.webservice.BetStatementsService;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Task for OpenBets service
 * @author riveram
 *
 */
public class GetOpenBetsTask extends AsyncTask<Void, Void, Boolean> {

    private OpenBetsTab openBets;
    private ProgressDialog progressDialog;
    private BetStatementsService service;

    private ArrayList<HashMap<String, String>> groupData;
    private ArrayList<ArrayList<HashMap<String, String>>> childData;

    public GetOpenBetsTask(OpenBetsTab openBets) {
        this.openBets = openBets;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progressDialog = new ProgressDialog(openBets);
        progressDialog.setMessage("Fetching Open Bets ...");
        progressDialog.show();

        service = new BetStatementsService(openBets);
    }

    @Override
    protected Boolean doInBackground(Void... arg0) {

        return service.executeRequest();
    }

    private void reorderdata() {

        groupData = new ArrayList<HashMap<String, String>>();

        childData = new ArrayList<ArrayList<HashMap<String, String>>>();

        for (BetStatementsResponse mrSort : service.getOpenBets()) {

            HashMap<String, String> mapGroupUnfound = new HashMap<String, String>();
            mapGroupUnfound.put(StatementsDb.KEY_DATE, mrSort.getDate());
            mapGroupUnfound.put(StatementsDb.KEY_SUBTYPE_DESCRIPTION,
                    mrSort.getSubTypeDescription());
            mapGroupUnfound.put(StatementsDb.KEY_DEBIT_FORMATTED,
                    mrSort.getDebit_formatted());
            mapGroupUnfound.put(StatementsDb.KEY_CREDIT_FORMATTED,
                    mrSort.getCredit_formatted());
            groupData.add(mapGroupUnfound);

            ArrayList<HashMap<String, String>> listChild = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> mapChild = null;

            for (int x = 0; x < mrSort.getMeetingsList().size(); x++) {
                MeetingsResponse mr = mrSort.getMeetingsList().get(x);

                if (mrSort.getId().equals(mr.getBetStatementId())) {
                    mapChild = new HashMap<String, String>();
                    mapChild.put(StatementsDb.KEY_MEETINGDESCRIPTION,
                            mr.getMeetingDescription());

                    EventsResponse er = mr.getEventsList().get(x);

                    if (mrSort.getId().equals(er.getBetStatementId())) {

                        mapChild.put(StatementsDb.KEY_EVENTNAME,
                                er.getEventName());

                        MarketsResponse mark = er.getMarketsList().get(x);

                        OutcomesResponse or = mark.getOutcomeList().get(x);

                        if (mrSort.getId().equals(or.getBetStatementId())) {

                            mapChild.put(StatementsDb.KEY_OUTCOME_DESCRIPTION,
                                    or.getDescription());
                            mapChild.put(StatementsDb.KEY_OUTCOME_FORMATTED_PRICE,
                                    or.getPriceFormatted());
                            listChild.add(mapChild);
                        }
                    }
                }
            }

            childData.add(listChild);

        }

    }

    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        if (!result) {
            Toast.makeText(openBets, openBets.getString(R.string.service_unavailable),
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        if (service.getAlStatements().size() == 0) {
            new GetOpenBetsTask(openBets).execute();

        } else {
            reorderdata();

            SimpleExpandableListAdapter settleAdapter = new
                    SimpleExpandableListAdapter(openBets, groupData,
                            R.layout.bets_statement_group, new String[] {
                                    StatementsDb.KEY_DATE,
                                    StatementsDb.KEY_SUBTYPE_DESCRIPTION,
                                    StatementsDb.KEY_DEBIT_FORMATTED,
                                    StatementsDb.KEY_CREDIT_FORMATTED
                            }, new int[] {
                                    R.id.tvBsgDate,
                                    R.id.tvBetType,
                                    R.id.tvBsgDebit,
                                    R.id.tvBsgCredit
                            }, childData, R.layout.bets_statements_child, new String[] {
                                    StatementsDb.KEY_OUTCOME_DESCRIPTION,
                                    StatementsDb.KEY_OUTCOME_FORMATTED_PRICE,
                                    StatementsDb.KEY_EVENTNAME,
                                    StatementsDb.KEY_MEETINGDESCRIPTION
                            }, new int[] {
                                    R.id.tvBsgOutcomeName,
                                    R.id.tvPriceFormatted,
                                    R.id.tvEventNameStatementBet,
                                    R.id.tvMeetingNameStatementBet

                            });
            settleAdapter.notifyDataSetInvalidated();
            openBets.setListAdapter(settleAdapter);
        }
        progressDialog.dismiss();
    }

}
