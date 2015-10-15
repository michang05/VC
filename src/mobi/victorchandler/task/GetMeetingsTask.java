
package mobi.victorchandler.task;

import mobi.victorchandler.BetSlip;
import mobi.victorchandler.Home;
import mobi.victorchandler.Meetings;
import mobi.victorchandler.MyAccountHome;
import mobi.victorchandler.R;
import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.response.MeetingsResponse;
import mobi.victorchandler.util.FavoriteTool;
import mobi.victorchandler.view.ActionBar;
import mobi.victorchandler.view.ActionBar.Action;
import mobi.victorchandler.view.ActionBar.IntentAction;
import mobi.victorchandler.view.MeetingsSimpleAdapter;
import mobi.victorchandler.view.MeetingsSimpleExpandableListAdapter;
import mobi.victorchandler.webservice.MeetingsService;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
/**
 * Task for Meetings service
 * @author riveram
 *
 */
public class GetMeetingsTask extends AsyncTask<Void, Void, Boolean> {

    private Meetings meetings;
    private ProgressDialog progressDialog;
    private String sportId;
    private MeetingsService meetingsService;

    private ArrayList<HashMap<String, String>> groupData;
    private ArrayList<ArrayList<HashMap<String, String>>> childData;

    private MeetingsSimpleExpandableListAdapter adapter;
    private MeetingsSimpleAdapter adapterNormal;
    private ExpandableListView elv;
    private ListView lv;
    private String sportName;
    private ActionBar actionBar;

    public GetMeetingsTask(Meetings meetings, String sportId, String sportName) {

        this.meetings = meetings;
        this.sportId = sportId;
        this.sportName = sportName;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (meetings.getParent() == null) {
            progressDialog = new ProgressDialog(meetings);
        } else {
            progressDialog = new ProgressDialog(meetings.getParent());
        }
        progressDialog.setMessage("Fetching Meetings ...");
        progressDialog.show();

        meetingsService = new MeetingsService(meetings, sportId);

    }

    private void reorderdata() {

        HashMap<String, String> mapGroup = null;
        groupData = new ArrayList<HashMap<String, String>>();

        childData = new ArrayList<ArrayList<HashMap<String, String>>>();

        // Sort the Region
        List<MeetingsResponse> sortList = new ArrayList<MeetingsResponse>();
        for (MeetingsResponse mr : meetingsService.getMeetingsdata()) {
            sortList.add(mr);
        }

        Comparator<MeetingsResponse> comparatorRegion = new Comparator<MeetingsResponse>() {

            @Override
            public int compare(MeetingsResponse object1, MeetingsResponse object2) {
                return object1.getMeetingDescription().compareTo(object2.getMeetingDescription());
            }
        };

        Collections.sort(sortList, comparatorRegion);

        for (MeetingsResponse mrSort : sortList) {
            boolean found = false;
            mapGroup = new HashMap<String, String>();
            mapGroup.put(SportsBook.KEY_MEETINGDESCRIPTION, mrSort.getMeetingDescription());

            for (int j = 0; j < groupData.size(); j++) {
                String tregion = groupData.get(j).get(SportsBook.KEY_MEETINGDESCRIPTION);
                if (tregion.equals(mrSort.getMeetingHeader())) {
                    found = true;
                    ArrayList<HashMap<String, String>> child = childData.get(j);
                    HashMap<String, String> hmchild = new HashMap<String, String>();
                    hmchild.put(SportsBook.KEY_MEETINGHEADER,
                            mrSort.getMeetingDescription());
                    hmchild.put(SportsBook.KEY_MEETING_ID, mrSort.getMeetingId());
                    child.add(hmchild);
                    break;
                }
            }

            if (!found) {
                HashMap<String, String> mapGroupUnfound = new HashMap<String, String>();
                mapGroupUnfound.put(SportsBook.KEY_MEETINGDESCRIPTION, mrSort.getMeetingHeader());
                groupData.add(mapGroupUnfound);

                HashMap<String, String> mapChild = new HashMap<String, String>();
                mapChild.put(SportsBook.KEY_MEETINGHEADER, mrSort.getMeetingDescription());
                mapChild.put(SportsBook.KEY_MEETING_ID, mrSort.getMeetingId());
                ArrayList<HashMap<String, String>> listChild =
                        new ArrayList<HashMap<String, String>>();
                listChild.add(mapChild);
                childData.add(listChild);
            }
        }

    }

    @Override
    protected Boolean doInBackground(Void... arg0) {

        if (!meetingsService.executeRequest()) {
            return false;
        }

        return meetingsService.executeRequest();
    }

    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        if (!result) {

            meetings.setMeetingEmpty();

        } else {

            reorderdata();

            LayoutInflater li = LayoutInflater.from(meetings);
            View linearMeeting = li.inflate(R.layout.meetings_activity, null);

            actionBar = (ActionBar) meetings.findViewById(R.id.actionbar);

            TextView tvMeetingHeader = (TextView) linearMeeting.findViewById(R.id.tvMeetingHeader);
            tvMeetingHeader.setText(sportName);

            FavoriteTool tool = new FavoriteTool(meetings, sportId, sportName, "-1");
            tool.processFavorites(linearMeeting);

            Button btnRefresh = (Button) linearMeeting.findViewById(R.id.btnRefreshMeetings);
            btnRefresh.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    new GetMeetingsTask(meetings, sportId, sportName).execute();
                }
            });

            LinearLayout ll = (LinearLayout) linearMeeting.findViewById(R.id.linMeetings);
            ll.invalidate();
            if (groupData.size() > 1) {

                elv = new ExpandableListView(meetings);
                adapter = new MeetingsSimpleExpandableListAdapter(meetings, groupData,
                        android.R.layout.simple_expandable_list_item_1, new String[] {
                                SportsBook.KEY_MEETINGDESCRIPTION
                        }, new int[] {
                                android.R.id.text1
                        }, childData, R.layout.meetings_child, new String[] {
                                SportsBook.KEY_MEETINGHEADER
                        }, new int[] {
                                R.id.tvMeetingNameChild
                        });

                elv.setAdapter(adapter);
                elv.setOnChildClickListener(meetings);
                ll.addView(elv);

            } else {
                lv = new ListView(meetings);
              
                adapterNormal = new MeetingsSimpleAdapter(meetings, childData.get(0),
                        R.layout.meetings_row, new String[] {
                                SportsBook.KEY_MEETINGHEADER
                        }, new int[] {
                                R.id.tvMeetingNameSingle
                        });

                lv.setAdapter(adapterNormal);
                lv.setOnItemClickListener(meetings);
                ll.addView(lv);
            }

            meetings.setContentView(ll);
            if (meetings.getParent() == null) {
                setActionBar();
                actionBar.setVisibility(View.VISIBLE);
            }
        }

        progressDialog.dismiss();

    }

    public void setActionBar() {

        actionBar = (ActionBar) meetings.findViewById(R.id.actionbar);

        actionBar.setHomeAction(new IntentAction(meetings, Home.createIntent(meetings),
                R.drawable.home));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Meetings");

        Action accountAction = new IntentAction(meetings,
                new Intent(meetings, MyAccountHome.class),
                R.drawable.account);
        actionBar.addAction(accountAction);

        Intent betslip = new Intent(meetings, BetSlip.class);
        Action betSlipAction = new IntentAction(meetings, betslip,
                R.drawable.betslip);

        actionBar.addAction(betSlipAction);

    }

    public ArrayList<ArrayList<HashMap<String, String>>> getChildData() {
        return childData;
    }

    public void setChildData(ArrayList<ArrayList<HashMap<String, String>>> childData) {
        this.childData = childData;
    }

}
