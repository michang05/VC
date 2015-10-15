
package mobi.victorchandler.view;

import mobi.victorchandler.R;
import mobi.victorchandler.response.TopBetsResponse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
/**
 * Class that extends ArrayAdapter<TopBetsResponse>
 * @author riveram
 *
 */
public class TopBetsArrayAdapter extends ArrayAdapter<TopBetsResponse> {

    private int resourceId;

    public TopBetsArrayAdapter(Context context, int textViewResourceId, List<TopBetsResponse> objects) {
        super(context, textViewResourceId, objects);

        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout;
        TopBetsResponse item = getItem(position);

        
        String eventName = item.getEventName();
        String marketName = item.getMarketName();
        String outcomeName = item.getOutcomeName();
        String outcomeFormattedPrice = item.getOutcomeFormattedPrice().length()<1?"SP":
            item.getOutcomeFormattedPrice();

        if (convertView == null) {
            linearLayout = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resourceId, linearLayout, true);
        } else {
            linearLayout = (LinearLayout) convertView;
        }

        TextView tvEventName = (TextView) linearLayout.findViewById(R.id.tvTopBetsEventName);
        TextView tvMarketName = (TextView) linearLayout.findViewById(R.id.tvTopBetsMarketName);
        TextView tvOutcomeName = (TextView)linearLayout.findViewById(R.id.tvOutcomeName);
        TextView tvOutcomeFormattedPrice = (TextView)linearLayout.findViewById(R.id.tvOutcomeFormattedPrice);
        
        tvEventName.setText(eventName);
        tvMarketName.setText(marketName);
        tvOutcomeName.setText(outcomeName);
        tvOutcomeFormattedPrice.setText(outcomeFormattedPrice);
 
        return linearLayout;
    }
    
}
