
package mobi.victorchandler.view;

import mobi.victorchandler.R;
import mobi.victorchandler.database.BetsDb;
import mobi.victorchandler.database.SportsBook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Used in the All Sports TAB, whenever there is an available Market Name , thus
 * listing out its Outcome names and outcome formatted price.
 * 
 * @author riveram
 */
public class MarketOutcomesBaseExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<HashMap<String, String>> groupData;// = new ArrayList<HashMap<String, String>>();
    private ArrayList<ArrayList<HashMap<String, String>>> childData ;//= new ArrayList<ArrayList<HashMap<String, String>>>();

    private LayoutInflater li;
    private Activity ctx;
    private String eventname;
    private BetsDb db;
    private LinearLayout ll;

    public MarketOutcomesBaseExpandableListAdapter(Activity ctx,
            ArrayList<HashMap<String, String>> groupData,
            ArrayList<ArrayList<HashMap<String, String>>> childData, String eventname) {
        this.groupData = groupData;
        this.childData = childData;
        db = new BetsDb(ctx);

        this.ctx = ctx;
        this.eventname = eventname;
        li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return Long.parseLong(childData.get(groupPosition).get(childPosition)
                .get(SportsBook.KEY_OUTCOME_ID));
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {

        View view = li.inflate(R.layout.market_outcomes_activity, null);
        db.open();
        ll = (LinearLayout) view.findViewById(R.id.linMarketOutcomes);
        ll.invalidate();
        // removed for being a child
        ll.removeView(ll.findViewById(R.id.linMarketOutcomesHeader));
        ll.removeView(ll.findViewById(R.id.linearUpdatePanel));

        for (int i = 0; i < childData.get(groupPosition).size(); i++) {

            TableLayout row = (TableLayout)
                    li.inflate(R.layout.couponvariable, null);

            TextView tvdescription = (TextView)
                    row.findViewById(R.id.tvMO_Name);
            ToggleButton tvbutton = (ToggleButton)
                    row.findViewById(R.id.tbMO_AddDelete);

            tvdescription.setText(childData.get(groupPosition).get(i)
                    .get(SportsBook.KEY_OUTCOME_DESCRIPTION));
            tvdescription.setTextColor(Color.WHITE);

            tvbutton.setTextColor(Color.parseColor("#FF6600"));
            tvbutton.setText(childData.get(groupPosition).get(i)
                    .get(SportsBook.KEY_OUTCOME_FORMATTED_PRICE));
            tvbutton.setTextOn(tvbutton.getText());
            tvbutton.setTextOff(tvbutton.getText());
            tvbutton.setChecked(db.hasBet(childData.get(groupPosition).get(i)
                    .get(SportsBook.KEY_OUTCOME_ID)));
            tvbutton.setOnCheckedChangeListener(makeOnCheckedChangeListener(groupPosition,
                    i));

            ll.addView(row);

        }
        db.close();
        view = ll;

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return Long.parseLong(groupData.get(groupPosition).get(SportsBook.KEY_MARKET_ID));
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
      LinearLayout tlli = (LinearLayout) li.inflate(
                R.layout.market_outcomes_group, null);
        TextView line1 = (TextView) tlli.findViewById(R.id.txtMarketName);
        TextView line2 = (TextView) tlli.findViewById(R.id.txtMarketDescription);
        TextView line3 = (TextView) tlli.findViewById(R.id.txtEachWay);
        line1.setText(groupData.get(groupPosition).get(SportsBook.KEY_MARKET_DESCRIPTION));
        line1.setTextColor(Color.parseColor("#CCE821"));
        line2.setText(groupData.get(groupPosition).get(SportsBook.KEY_MARKET_PERIOD_DESCRIPTION));
        line2.setTextColor(Color.WHITE);
        line3.setText(groupData.get(groupPosition).get(SportsBook.KEY_MARKET_PLACE_TERMS_DESCRIPTION));
        line3.setTextColor(Color.parseColor("#FFCC00"));

        return tlli;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private OnCheckedChangeListener makeOnCheckedChangeListener(final int groupPosition,
            final int childPosition) {
        return new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.open();
                if (isChecked) {
                    String outcomename = childData.get(groupPosition).get(childPosition)
                            .get(SportsBook.KEY_OUTCOME_DESCRIPTION);
                    String price = childData.get(groupPosition).get(childPosition)
                            .get(SportsBook.KEY_OUTCOME_DECIMAL_PRICE);
                    String priceid = childData.get(groupPosition).get(childPosition)
                            .get(SportsBook.KEY_OUTCOME_PRICE_ID);
                    String outcomeid = childData.get(groupPosition).get(childPosition)
                            .get(SportsBook.KEY_OUTCOME_ID);
                    String formattedprice = childData.get(groupPosition).get(childPosition)
                            .get(SportsBook.KEY_OUTCOME_FORMATTED_PRICE);
                    String canew = groupData.get(groupPosition).get(SportsBook.KEY_MARKET_EACHWAY);
                    String marketname = groupData.get(groupPosition).get(
                            SportsBook.KEY_MARKET_DESCRIPTION);
                    String marketperiod = groupData.get(groupPosition).get(
                            SportsBook.KEY_MARKET_PERIOD_DESCRIPTION);
                    String ptDeduction = groupData.get(groupPosition).get(SportsBook.KEY_MARKET_PLACE_TERMS_DEDUCTION);
                    String ptDescription = groupData.get(groupPosition).get(
                            SportsBook.KEY_MARKET_PLACE_TERMS_DESCRIPTION);
                    

                    String eventId = groupData.get(groupPosition).get(SportsBook.KEY_EVENT_ID);

                    if (!db.hasBet(outcomeid)) {
                        db.insertBet(eventname, marketname, marketperiod, outcomename, price,
                                priceid, outcomeid, formattedprice, canew, ptDeduction,
                                ptDescription, eventId);
                        db.close();
                    }
                    Toast.makeText(ctx, R.string.betadded, Toast.LENGTH_SHORT).show();
                } else {
                    db.deleteBet(childData.get(groupPosition).get(childPosition)
                            .get(SportsBook.KEY_OUTCOME_ID));
                    db.close();
                    Toast.makeText(ctx, R.string.betremoved, Toast.LENGTH_SHORT).show();
                }

            }
        };
    }
}
