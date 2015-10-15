
package mobi.victorchandler.parser;

import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.response.OpponentResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Class for Parsing Events
 * @author riveram
 *
 */
public class EventsParser extends BaseVcParser {

    private String mResult;
    private Context ctx;
    private String sportId;
    private String meetingId;
    private EventsResponse eventsResponse;
    private OpponentResponse opponentResponse;
    private ArrayList<EventsResponse> eventsList;
    private ArrayList<OpponentResponse> opponentsList;

    public EventsParser(String mResult, Context ctx, String sportId, String meetingId) {
        this.mResult = mResult;
        this.ctx = ctx;
        this.sportId = sportId;
        this.meetingId = meetingId;
    }

    @Override
    public ArrayList<EventsResponse> parseJsonResponse() {

        eventsList = new ArrayList<EventsResponse>();
        opponentsList = new ArrayList<OpponentResponse>();

        try {

            JSONObject json = new JSONObject(mResult);
            
            JSONArray events = json.getJSONArray(EVENTS);
            for (int i = 0; i < events.length(); i++) {

                eventsResponse = new EventsResponse();
                JSONObject object = events.getJSONObject(i);

                eventsResponse.setEventId(String.valueOf(object.getLong(ID)));
                eventsResponse.setEventDate(DateFormat.getDateTimeInstance().format(
                        new Date(object.getLong(DATE))));
                eventsResponse.setEventName(object.getString(DESCRIPTION));
                eventsResponse.setEarlyPrice(String.valueOf(object.getBoolean(EARLY_PRICE)));
                JSONArray opponents = object.getJSONArray(OPPONENTS);
                for (int x = 0; x < opponents.length(); x++) {
                    JSONObject team = opponents.getJSONObject(x);
                    opponentResponse = new OpponentResponse();
                    opponentResponse.setEventId(String.valueOf(object.getLong(ID)));
                    opponentResponse.setOpponentsId(String.valueOf(team.getLong(ID)));
                    opponentResponse.setOpponentsDescription(team.getString(DESCRIPTION));
                    opponentsList.add(opponentResponse);

                }
                eventsResponse.setOpponentsList(opponentsList);
                eventsList.add(eventsResponse);
            }

            SportsBook db = new SportsBook(ctx);
            db.open();
            db.deleteEvents(meetingId, sportId); 
            db.insertOpponents(opponentsList);
            db.insertEvents(eventsList, meetingId, sportId);
            db.close();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventsList;
    }

}
