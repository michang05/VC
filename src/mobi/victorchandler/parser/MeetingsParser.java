
package mobi.victorchandler.parser;

import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.response.MeetingsResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.util.ArrayList;
/**
 * Class that parses Meetings
 * @author riveram
 *
 */
public class MeetingsParser extends BaseVcParser {

    private String mResult;
    private Context ctx;
    private String sportId;
    private MeetingsResponse meetingsResponse;
    private ArrayList<MeetingsResponse> meetingsdata;

    public MeetingsParser(String mResult, Context ctx, String sportId) {
        this.mResult = mResult;
        this.ctx = ctx;
        this.sportId = sportId;
    }

    @Override
    public ArrayList<MeetingsResponse> parseJsonResponse() {

        meetingsdata = new ArrayList<MeetingsResponse>();

        try {
            JSONObject jsonobject = new JSONObject(mResult);
            JSONArray meetings = jsonobject.getJSONArray(MEETINGS);
            if (meetings.length() > 0) {
                for (int i = 0; i < meetings.length(); i++) {
                    JSONObject header = meetings.getJSONObject(i);

                    meetingsResponse = new MeetingsResponse();

                    meetingsResponse.setMeetingId(header.getString(ID));
                    meetingsResponse.setMeetingHeader(header.getString(HEADER));
                    meetingsResponse.setMeetingDescription(header.getString(DESCRIPTION));

                    meetingsdata.add(meetingsResponse);

                }
                SportsBook db = new SportsBook(ctx);
                db.open();
                db.deleteMeetings(sportId);
                db.insertMeetings(meetingsdata, sportId);
                db.close();
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return meetingsdata;
    }

}
