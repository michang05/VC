
package mobi.victorchandler.view;

import mobi.victorchandler.R;
import mobi.victorchandler.response.NextEventsResponse;
import mobi.victorchandler.util.DataHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
/**
 * Class that extends ArrayAdapter<NextEventsResponse>
 * @author riveram
 *
 */
public class NextEventsArrayAdapter extends ArrayAdapter<NextEventsResponse> {

    private int resourceId;

    public NextEventsArrayAdapter(Context context, int textViewResourceId, List<NextEventsResponse> objects) {
        super(context, textViewResourceId, objects);

        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout;
        NextEventsResponse item = getItem(position);
        
        
        String eventName = item.getEventName();
        String sportName = item.getSportName();
        String meetingName = item.getMeetingName();
        String eventDate = " in " +DataHelper.timeFromNow(Long.parseLong(item.getEventDate()));
     
        if (convertView == null) {
            linearLayout = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resourceId, linearLayout, true);
        } else {
            linearLayout = (LinearLayout) convertView;
        }

        TextView tvEventName = (TextView) linearLayout.findViewById(R.id.tvNextEventsName);
        TextView tvSportName = (TextView) linearLayout.findViewById(R.id.tvSportName);
        TextView tvMeetingName = (TextView)linearLayout.findViewById(R.id.tvNextEventsMeetingName);
     
        tvEventName.setText(eventName);
        tvSportName.setText(sportName+ eventDate);
        tvMeetingName.setText(meetingName);
     
       
 
        return linearLayout;
    }
    
}
