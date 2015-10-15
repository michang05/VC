package mobi.victorchandler.view;

import mobi.victorchandler.R;
import mobi.victorchandler.response.EventsResponse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
/**
 * Class that extends ArrayAdapter<EventsResponse>
 * @author riveram
 *
 */
public class EventsArrayAdapter extends ArrayAdapter<EventsResponse> {

    private int resourceId;

    public EventsArrayAdapter(Context context, int textViewResourceId, List<EventsResponse> objects) {
        super(context, textViewResourceId, objects);

        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout;
        EventsResponse item = getItem(position);

        
        String eventName = item.getEventName();
        String eventDate = item.getEventDate();
      
        if (convertView == null) {
            linearLayout = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resourceId, linearLayout, true);
        } else {
            linearLayout = (LinearLayout) convertView;
        }

        TextView tvEventName = (TextView) linearLayout.findViewById(R.id.tvEventsName);
        TextView tvEventDate = (TextView) linearLayout.findViewById(R.id.tvEventDateTime);
      
        
        tvEventName.setText(eventName);
        tvEventDate.setText(eventDate);
     
        return linearLayout;
    }
    
}
