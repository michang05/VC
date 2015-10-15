
package mobi.victorchandler.view;

import mobi.victorchandler.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
/**
 * Class that extends SimpleExpandableListAdapter for Meetings
 * @author riveram
 *
 */
public class MeetingsSimpleExpandableListAdapter extends SimpleExpandableListAdapter {

    
    /**
     * @param context Context
     * @param groupData List<? extends Map<String,?>>
     * @param groupLayout int
     * @param groupFrom String[]
     * @param groupTo int[]
     * @param childData List<? extends Map<String,?>>
     * @param childLayout int
     * @param childFrom String[]
     * @param childTo int[]
     */
    public MeetingsSimpleExpandableListAdapter(Context context,
            List<? extends Map<String, ?>> groupData, int groupLayout, String[] groupFrom,
            int[] groupTo, List<? extends List<? extends Map<String, ?>>> childData,
            int childLayout, String[] childFrom, int[] childTo) {

        super(context, groupData, groupLayout, groupFrom, groupTo, childData, childLayout,
                childFrom, childTo);

        
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean
            isLastChild,
            View convertView, ViewGroup parent) {

        View view = super.getChildView(groupPosition, childPosition, isLastChild,
                convertView,
                parent);
        


        TextView text1 = (TextView) view.findViewById(R.id.tvMeetingNameChild);
        text1.setPadding(50, 0, 0, 0);
        
        ImageView img = (ImageView) view.findViewById(R.id.image);
        img.setPadding(10, 0, 0, 0);
     
        return view;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View
            convertView,
            ViewGroup parent) {

        View view = super.getGroupView(groupPosition, isExpanded, convertView,
                parent);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);

        text1.setTextColor(Color.parseColor("#CCE821"));
    
        return view;

    }

}
