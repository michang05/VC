
package mobi.victorchandler.util;

import com.flurry.android.FlurryAgent;

import mobi.victorchandler.R;
import mobi.victorchandler.database.FavsDB;
import mobi.victorchandler.preferences.AccountPreferences;
import mobi.victorchandler.preferences.BasePreferences;

import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;
/**
 * Class for helping Favorites to be added/removed in the database
 * @author riveram
 *
 */
public class FavoriteTool {

    private Activity context;
    private FavsDB favsdb;
    private String sportId;
    private String meetingId;
    private String description;

    public FavoriteTool(Activity context, String sportId, String description, String meetingId) {

        this.context = context;
        this.sportId = sportId;
        this.description = description;
        this.meetingId = meetingId;

        BasePreferences.load(context);
    }
/**
 * Processing of favorites (removing and adding) by View
 * @param v
 */
    public void processFavorites(View v) {
        // setBackgroundColor(0xff000000);
        favsdb = new FavsDB(context);
        ToggleButton tb = (ToggleButton) v.findViewById(R.id.favbtn);
        if (description != null) {
            favsdb.open();
            boolean isfav = favsdb.isFav(description, sportId, meetingId);
            favsdb.close();
            tb.setChecked(isfav);
            tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("added", String.valueOf(isChecked));
                    params.put("description", description);
                    FlurryAgent.onEvent("favorite", params);
                    favsdb.open();
                    if (isChecked) {

                        favsdb.insertFav(description, sportId, meetingId,AccountPreferences.getAccountNumber());
                        favsdb.close();
                        Toast.makeText(context, R.string.favadded, Toast.LENGTH_SHORT).show();
                    } else {

                        favsdb.removeFav(description, sportId, meetingId);
                        favsdb.close();
                        Toast.makeText(context, R.string.favuned, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            tb.setVisibility(View.GONE);
        }

    }
    /**
     * Processing of favorites (removing and adding) by ToggleButton
     * @param v
     */
    public void processFavorites(ToggleButton tb) {
        // setBackgroundColor(0xff000000);
        favsdb = new FavsDB(context);
       
        if (description != null) {
            favsdb.open();
            boolean isfav = favsdb.isFav(description, sportId, meetingId);
            favsdb.close();
            tb.setChecked(isfav);
            tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("added",new Boolean(isChecked));
                    params.put("description", description);
                    FlurryAgent.onEvent("favorite", params);
                    favsdb.open();
                    if (isChecked) {

                        favsdb.insertFav(description, sportId, meetingId,AccountPreferences.getAccountNumber());
                        favsdb.close();
                        Toast.makeText(context, R.string.favadded, Toast.LENGTH_SHORT).show();
                    } else {

                        favsdb.removeFav(description, sportId, meetingId);
                        favsdb.close();
                        Toast.makeText(context, R.string.favuned, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            tb.setVisibility(View.GONE);
        }

    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getSportName() {
        return description;
    }

    public void setSportName(String sportName) {
        this.description = sportName;
    }

}
