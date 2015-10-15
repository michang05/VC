package mobi.victorchandler.view;

import mobi.victorchandler.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
/**
 * Class that extends SimpleAdapter for Meetings
 * @author riveram
 *
 */
public class MeetingsSimpleAdapter extends SimpleAdapter {

	public MeetingsSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
	    View view = super.getView(position, convertView, parent);
		
	    TextView text1 = (TextView) view.findViewById(R.id.tvMeetingNameSingle);
		text1.setTextSize(20);
		
		return view;
	}

}
