
package mobi.victorchandler.view;

import mobi.victorchandler.R;
import mobi.victorchandler.response.SportsBookResponse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
/**
 * Class that extends ArrayAdapter<SportsBookResponse>
 * @author riveram
 *
 */
public class SportsBookArrayAdapter extends ArrayAdapter<SportsBookResponse> {
    
    
    private int resourceId;

    public SportsBookArrayAdapter(Context context, int textViewResourceId,
            List<SportsBookResponse> objects) {
        super(context, textViewResourceId, objects);

        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       
        LinearLayout linearLayout;
        SportsBookResponse item = getItem(position);

        String sportName = item.getSportName();
      

        if (convertView == null) {
            linearLayout = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resourceId, linearLayout, true);
        } else {
            linearLayout = (LinearLayout) convertView;
        }

        TextView tvSportName = (TextView) linearLayout.findViewById(R.id.tvSportName);
      
        tvSportName.setText(sportName);

        return linearLayout;
    }

}
