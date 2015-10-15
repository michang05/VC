package mobi.victorchandler.view;

import mobi.victorchandler.R;
import mobi.victorchandler.database.BetsDb;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.OutcomesResponse;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Used in the NextEvents TAB , having the MarketOutcomesResponse passed into
 * View
 * 
 * @author riveram
 */
public class MarketOutComesArrayAdapter extends ArrayAdapter<MarketsResponse> {

	private int resourceId;
	private Activity context;
	private LayoutInflater inflator;
	private BetsDb db;
	private String eventname;

	public MarketOutComesArrayAdapter(Activity context, int textViewResourceId,
			List<MarketsResponse> objects, String eventname) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.eventname = eventname;
		resourceId = textViewResourceId;

		inflator = LayoutInflater.from(context);

		db = new BetsDb(context);

	}

	static class ViewHolder {
		protected TextView text;
		protected ToggleButton toggle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = null;
		MarketsResponse item = getItem(position);

		if (convertView == null) {
			view = inflator.inflate(resourceId, null);

			final ViewHolder holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.tvMO_Name);
			holder.text.setText(item.getDescription());

			holder.toggle = (ToggleButton) view
					.findViewById(R.id.tbMO_AddDelete);

			db.open();

			for (OutcomesResponse or : item.getOutcomeList()) {
				if (db.hasBet(or.getId())) {
					holder.toggle.setChecked(true);
					item.setSelected(true);
				}
			}
			db.close();

			holder.toggle.setTextColor(Color.parseColor("#FF6600"));
			for (OutcomesResponse or : item.getOutcomeList()) {
				holder.toggle.setText(or.getPriceFormatted());
				holder.toggle.setTextOff(or.getPriceFormatted());
				holder.toggle.setTextOn(or.getPriceFormatted());
			}
			holder.toggle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					MarketsResponse mor = (MarketsResponse) holder.toggle
							.getTag();
					mor.setSelected(holder.toggle.isChecked());
					String outcomename = null, price = null, priceid = null, outcomeid = null, formattedprice = null;
					for (OutcomesResponse or : mor.getOutcomeList()) {
						if (mor.isSelected()) {

							outcomename = or.getDescription();
							price = or.getPriceStarting();
							priceid = or.getPriceId();
							outcomeid = or.getId();
							formattedprice = or.getPriceFormatted();

							String canew = mor.getEachWay();
							String marketname = mor.getDescription();
							String marketperiod = mor.getPeriodDescription();

							db.open();
							if (!db.hasBet(outcomeid)) {
								db.insertBet(eventname, marketname,
										marketperiod, outcomename, price,
										priceid, outcomeid, formattedprice,
										canew, mor.getPlaceTermsDeduction(),
										mor.getPlaceTermsDescription(),
										or.getEventId());
								Toast.makeText(context, R.string.betadded,
										Toast.LENGTH_SHORT).show();
							} else {

								Toast.makeText(context,
										"Already in the Bet Slip",
										Toast.LENGTH_SHORT).show();
							}

						} else {

							db.open();
							db.deleteBet(or.getId());
							Toast.makeText(context, R.string.betremoved,
									Toast.LENGTH_SHORT).show();
						}
						db.close();
					}
				}
			});

			view.setTag(holder);
			holder.toggle.setTag(item);

		} else {
			view = convertView;
			ViewHolder viewHolder = (ViewHolder) view.getTag();
			viewHolder.toggle.setTag(item);
		}

		ViewHolder vh = (ViewHolder) view.getTag();

		vh.text.setText(item.getDescription());

		vh.toggle.setChecked(item.isSelected());
		for (OutcomesResponse or : item.getOutcomeList()) {
			vh.toggle.setText(or.getPriceFormatted());
			vh.toggle.setTextOff(or.getPriceFormatted());
			vh.toggle.setTextOn(or.getPriceFormatted());
		}

		return view;
	}

}
