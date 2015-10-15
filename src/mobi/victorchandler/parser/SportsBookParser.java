
package mobi.victorchandler.parser;

import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.response.SportsBookResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.util.ArrayList;
/**
 * Class that parses SportsBook Service
 * @author riveram
 *
 */
public class SportsBookParser extends BaseVcParser {

    private String mResult;
    private ArrayList<SportsBookResponse> mSportsData;
    private Context mContext;
    private SportsBookResponse mResponse;

    public SportsBookParser(String mResult, Context mContext) {

        this.mResult = mResult;
        this.mContext = mContext;

    }

    @Override
    public ArrayList<SportsBookResponse> parseJsonResponse() {

        mSportsData = new ArrayList<SportsBookResponse>();

        try {
            
            JSONObject jsonobject = new JSONObject(mResult);
            JSONArray json = jsonobject.getJSONArray(SPORTS);
            
            for (int i = 0; i < json.length(); i++) {
                
                JSONObject sport = json.getJSONObject(i);
                
                if (!sport.getBoolean(HAS_EVENTS))
                    continue;
                
                mResponse = new SportsBookResponse();
                mResponse.setSportName(sport.getString(DESCRIPTION));
                mResponse.setSportId(sport.getString(ID));
                mResponse.setHasEvents(sport.getBoolean(HAS_EVENTS));
                mSportsData.add(mResponse);
                
            }
            
            SportsBook db = new SportsBook(mContext);
            db.open();
            db.deleteSports();
            db.insertSports(mSportsData);
            db.close();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mSportsData;
    }

}
