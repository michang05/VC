
package mobi.victorchandler.parser;

import mobi.victorchandler.database.NextEventsDb;
import mobi.victorchandler.response.NextEventsResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.util.ArrayList;
/**
 * Class that parses NextEvents
 * @author riveram
 *
 */
public class NextEventsParser extends BaseVcParser {

    private String mResult;
    private ArrayList<NextEventsResponse> eventsdata;
    private Context ctx;
    private NextEventsResponse mNextEventsResponse;

    public NextEventsParser(String result, Context ctx) {
        this.mResult = result;
        this.ctx = ctx;
    }

    @Override
    public ArrayList<NextEventsResponse> parseJsonResponse() {

        eventsdata = new ArrayList<NextEventsResponse>();
        
        try {
            JSONObject json = new JSONObject(mResult);
            JSONArray events = json.getJSONArray(UPCOMING);
            for (int i = 0; i < events.length(); i++) {

                mNextEventsResponse = new NextEventsResponse();

                JSONObject event = events.getJSONObject(i).getJSONObject(EVENT);
                JSONObject meeting = events.getJSONObject(i).getJSONObject(MEETING);
                JSONObject sport = events.getJSONObject(i).getJSONObject(SPORT);

                mNextEventsResponse.setEventId(event.getString(ID));
               
                mNextEventsResponse.setEventDate(event.getString(DATE));
                mNextEventsResponse.setEventName(event.getString(DESCRIPTION));

                mNextEventsResponse.setMeetingId(meeting.getString(ID));
                mNextEventsResponse.setMeetingName(meeting.getString(DESCRIPTION));

                mNextEventsResponse.setSportId(sport.getString(ID));
                mNextEventsResponse.setSportName(sport.getString(DESCRIPTION));

                eventsdata.add(mNextEventsResponse);
            }
            NextEventsDb db = new NextEventsDb(ctx);
            db.deleteEvents();
            db.insertEvents(eventsdata);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventsdata;

    }

}
