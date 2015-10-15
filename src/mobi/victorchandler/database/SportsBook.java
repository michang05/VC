
package mobi.victorchandler.database;

import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.MeetingsResponse;
import mobi.victorchandler.response.OpponentResponse;
import mobi.victorchandler.response.OutcomesResponse;
import mobi.victorchandler.response.PreviousPriceResponse;
import mobi.victorchandler.response.SportsBookResponse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
/**
 * Sports Book database class
 * @author riveram
 *
 */
public class SportsBook {

    public static final String KEY_ID = "_id";
    public static final int ID_COLUMN = 0;

    public static final String KEY_SPORT_ID = "sport_id";
    public static final int SPORTID_COLUMN = 1;
    public static final String KEY_SPORTNAME = "sport_name";
    public static final int SPORTNAME_COLUMN = 2;
    public static final String KEY_HAS_EVENTS = "has_events";
    public static final int HAS_EVENTS_COLUMN = 3;

    public static final String KEY_MEETING_ID = "meeting_id";
    public static final int MEETINGID_COLUMN = 1;
    public static final String KEY_MEETINGHEADER = "meeting_header";
    public static final int MEETINGHEADER_COLUMN = 2;
    public static final String KEY_MEETINGDESCRIPTION = "meeting_description";
    public static final int MEETINGDESCRIPTION_COLUMN = 3;

    public static final String KEY_EVENT_ID = "event_id";
    public static final int EVENTID_COLUMN = 1;
    public static final String KEY_EVENTNAME = "event_name";
    public static final int EVENTNAME_COLUMN = 2;
    public static final String KEY_EVENTDATE = "event_date";
    public static final int EVENTDATE_COLUMN = 3;
    public static final String KEY_EVENT_EARLY_PRICE = "early_price";
    public static final int EVENT_EARLY_PRICE_COLUMN = 4;

    public static final String KEY_MARKET_ID = "market_id";
    public static final int MARKET_ID_COLUMN = 1;
    public static final String KEY_MARKET_TYPE_ID = "type_id";
    public static final int MARKET_TYPE_ID_COLUMN = 2;
    public static final String KEY_MARKET_EACHWAY = "market_eachway";
    public static final int MARKET_EACHWAY_COLUMN = 3;
    public static final String KEY_MARKET_PLACE_TERMS_DESCRIPTION = "place_terms_description";
    public static final int MARKET_PLACE_TERMS_DESCRIPTION_COLUMN = 4;
    public static final String KEY_MARKET_PLACE_TERMS_DEDUCTION = "place_terms_deduction";
    public static final int MARKET_PLACE_TERMS_DEDUCTION_COLUMN = 5;
    public static final String KEY_MARKET_DESCRIPTION = "description";
    public static final int MARKET_DESCRIPTION_COLUMN = 6;
    public static final String KEY_MARKET_PERIOD_ID = "period_id";
    public static final int MARKET_PERIOD_ID_COLUMN = 7;
    public static final String KEY_MARKET_PERIOD_DESCRIPTION = "period_description";
    public static final int MARKET_PERIOD_DESCRIPTION_COLUMN = 8;

    public static final String KEY_OUTCOME_ID = "outcome_id";
    public static final int OUTCOME_ID_COLUMN = 1;
    public static final String KEY_OUTCOME_DESCRIPTION = "outcome_name";
    public static final int OUTCOME_DESCRIPTION_COLUMN = 2;
    public static final String KEY_OUTCOME_PRICE_ID = "outcome_priceid";
    public static final int OUTCOME_PRICE_ID_COLUMN = 3;
    public static final String KEY_OUTCOME_DECIMAL_PRICE = "outcome_decimalprice";
    public static final int OUTCOME_DECIMAL_PRICE_COLUMN = 4;
    public static final String KEY_OUTCOME_STARTING_PRICE = "outcome_startingprice";
    public static final int OUTCOME_STARTING_PRICE_COLUMN = 5;
    public static final String KEY_OUTCOME_FORMATTED_PRICE = "outcome_formattedprice";
    public static final int OUTCOME_FORMATTED_PRICE_COLUMN = 6;

    public static final String KEY_PREVIOUS_PRICE_ID = "previous_price_id";
    public static final int PREVIOUS_PRICE_ID_COLUMN = 1;
    public static final String KEY_PREVIOUS_DECIMAL = "previous_decimal";
    public static final int PREVIOUS_DECIMAL_COLUMN = 2;
    public static final String KEY_PREVIOUS_FORMATTED = "previous_formatted";
    public static final int PREVIOUS_FORMATTED_COLUMN = 3;

    public static final String KEY_OPPONENTSID = "opponent_id";
    public static final int OPPONENTID_COLUMN = 1;
    public static final String KEY_OPPONENTSDESCRIPTION = "opponent_description";
    public static final int OPPONENTDESCRIPTION_COLUMN = 2;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "sportbooks.db";
    private static final String SPORTS_TABLE = "sports";
    private static final String MEETINGS_TABLE = "meetings";
    private static final String EVENTS_TABLE = "events";
    private static final String MARKET_TABLE = "market";
    private static final String OUTCOMES_TABLE = "outcomes";
    private static final String PREVIOUS_PRICES_TABLE = "previous_prices";
    private static final String OPPONENTS_TABLE = "opponents";

    private SQLiteDatabase db;
    private DBOpenHelper dbHelper;
    private SportsBookResponse sportsBookResponse;

    public SportsBook(Context context) {
        dbHelper = new DBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

  /**
   * Getting All Sports in the database.
   * @return ArrayList<SportsBookResponse>
   */
    public ArrayList<SportsBookResponse> getSports() {
        Cursor cursor = db.query(SPORTS_TABLE, new String[] {
                KEY_ID, KEY_SPORT_ID, KEY_SPORTNAME, KEY_HAS_EVENTS
        }, null, null, null, null, null);

        ArrayList<SportsBookResponse> sportsdata = new ArrayList<SportsBookResponse>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            sportsBookResponse = new SportsBookResponse();
            sportsBookResponse.setSportId(cursor.getString(SPORTID_COLUMN));
            sportsBookResponse.setSportName(cursor.getString(SPORTNAME_COLUMN));
            sportsBookResponse
                    .setHasEvents(Boolean.getBoolean(cursor.getString(HAS_EVENTS_COLUMN)));
            sportsdata.add(sportsBookResponse);
            cursor.moveToNext();
        }
        cursor.close();
        return sportsdata;
    }
/**
 * Inserts sports into the database.
 * @param sportsdata
 */
    public void insertSports(ArrayList<SportsBookResponse> sportsdata) {

        for (SportsBookResponse sportsBookResponse : sportsdata) {
            ContentValues values = new ContentValues();
            values.put(KEY_SPORT_ID, sportsBookResponse.getSportId());
            values.put(KEY_SPORTNAME, sportsBookResponse.getSportName());
            values.put(KEY_HAS_EVENTS, sportsBookResponse.isHasEvents());
            db.insert(SPORTS_TABLE, null, values);
        }

    }
/**
 * Delete Sports,with dependencies.
 */
    public void deleteSports() {
        // no need to do it recursively because everything must be deleted
        db.delete(SPORTS_TABLE, null, null);
        db.delete(MEETINGS_TABLE, null, null);
        db.delete(EVENTS_TABLE, null, null);
        db.delete(OPPONENTS_TABLE, null, null);
        db.delete(MARKET_TABLE, null, null);
        db.delete(OUTCOMES_TABLE, null, null);
        db.delete(PREVIOUS_PRICES_TABLE, null, null);
    }

    /**
     * Gets the meetings from the meetings table.
     * @param sportId
     * @return ArrayList<MeetingsResponse>
     */
    public ArrayList<MeetingsResponse> getMeetings(String sportId) {
        Cursor cursor = db.query(MEETINGS_TABLE, new String[] {
                KEY_ID, KEY_SPORT_ID, KEY_MEETING_ID, KEY_MEETINGHEADER, KEY_MEETINGDESCRIPTION
        }, KEY_SPORT_ID + "=?", new String[] {
                sportId
        }, null, null, null);

        MeetingsResponse mr;
        ArrayList<MeetingsResponse> meetingsdata = new ArrayList<MeetingsResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mr = new MeetingsResponse();
            mr.setMeetingId(cursor.getString(MEETINGID_COLUMN));
            mr.setMeetingHeader(cursor.getString(MEETINGHEADER_COLUMN));
            mr.setMeetingDescription(cursor.getString(MEETINGDESCRIPTION_COLUMN));

            meetingsdata.add(mr);

            cursor.moveToNext();
        }
        cursor.close();
        return meetingsdata;
    }
/**
 * Inserts Meetings
 * @param meetingsdata
 * @param sportid
 */
    public void insertMeetings(ArrayList<MeetingsResponse> meetingsdata, String sportid) {

        for (MeetingsResponse mr : meetingsdata) {

            ContentValues values = new ContentValues();
            values.put(KEY_MEETING_ID, mr.getMeetingId());
            values.put(KEY_SPORT_ID, sportid);
            values.put(KEY_MEETINGHEADER, mr.getMeetingHeader());
            values.put(KEY_MEETINGDESCRIPTION, mr.getMeetingDescription());

            db.insert(MEETINGS_TABLE, null, values);
        }

    }
/**
 * Deletes Meetings
 * @param sportid
 */
    public void deleteMeetings(String sportid) {

        ArrayList<MeetingsResponse> meetingsdata = getMeetings(sportid);
        for (MeetingsResponse meetingsResponse : meetingsdata) {
            String meetingid = meetingsResponse.getMeetingId();
            deleteEvents(meetingid, sportid);
        }

        db.delete(MEETINGS_TABLE, KEY_SPORT_ID + "=?", new String[] {
                sportid
        });
    }

   /**
    * Gets the Events from the events table
    * @param meetingid
    * @param sportid
    * @return ArrayList<EventsResponse>
    */
    public ArrayList<EventsResponse> getEvents(String meetingid, String sportid) {
        Cursor cursor = db.query(EVENTS_TABLE, new String[] {
                KEY_ID, KEY_SPORT_ID, KEY_MEETING_ID, KEY_EVENT_ID, KEY_EVENTNAME, KEY_EVENTDATE,
                KEY_EVENT_EARLY_PRICE
        }, KEY_MEETING_ID + "=? AND " + KEY_SPORT_ID + "=?", new String[] {
                meetingid, sportid
        }, null,
                null, null);
        EventsResponse eventsResponse;
        ArrayList<OpponentResponse> opponentsdata;
        ArrayList<EventsResponse> eventsdata = new ArrayList<EventsResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            eventsResponse = new EventsResponse();

            eventsResponse.setEventId(cursor.getString(EVENTID_COLUMN));
            eventsResponse.setEventName(cursor.getString(EVENTNAME_COLUMN));
            eventsResponse.setEventDate(cursor.getString(EVENTDATE_COLUMN));
            eventsResponse.setEarlyPrice(cursor.getString(EVENT_EARLY_PRICE_COLUMN));

            opponentsdata = getOpponents(cursor.getString(EVENTID_COLUMN));
            eventsResponse.setOpponentsList(opponentsdata);

            eventsdata.add(eventsResponse);

            cursor.moveToNext();
        }
        cursor.close();
        return eventsdata;
    }
/**
 * Get Events by id
 * @param eventId
 * @return EventsResponse
 */
    public EventsResponse getEventById(String eventId) {
        Cursor cursor = db.query(EVENTS_TABLE, null, KEY_EVENT_ID + "=?", new String[] {
                eventId
        }, null,
                null, null);
        EventsResponse eventsResponse = null;
        ArrayList<OpponentResponse> opponentsdata;
       
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            eventsResponse = new EventsResponse();

            eventsResponse.setEventId(cursor.getString(EVENTID_COLUMN));
            eventsResponse.setEventName(cursor.getString(EVENTNAME_COLUMN));
            eventsResponse.setEventDate(cursor.getString(EVENTDATE_COLUMN));
            eventsResponse.setEarlyPrice(cursor.getString(EVENT_EARLY_PRICE_COLUMN));

            opponentsdata = getOpponents(cursor.getString(EVENTID_COLUMN));
            eventsResponse.setOpponentsList(opponentsdata);

         

            cursor.moveToNext();
        }
        cursor.close();
        return eventsResponse;
    }
/**
 * Insert Events
 * @param eventsdata
 * @param meetingId
 * @param sportId
 */
    public void insertEvents(ArrayList<EventsResponse> eventsdata, String meetingId,
            String sportId) {

        for (EventsResponse er : eventsdata) {
            ContentValues values = new ContentValues();
            values.put(KEY_EVENT_ID, er.getEventId());
            values.put(KEY_MEETING_ID, meetingId);
            values.put(KEY_SPORT_ID, sportId);
            values.put(KEY_EVENTNAME, er.getEventName());
            values.put(KEY_EVENTDATE, er.getEventDate());
            values.put(KEY_EVENT_EARLY_PRICE, er.getEarlyPrice());

            db.insert(EVENTS_TABLE, null, values);
        }
    }
/**
 * Delete Events
 * @param meetingId
 * @param sportId
 */
    public void deleteEvents(String meetingId, String sportId) {
        ArrayList<EventsResponse> eventsdata = getEvents(meetingId, sportId);
        for (EventsResponse er : eventsdata) {
            String eventId = er.getEventId();
            deleteOpponents(eventId);
            deleteMarkets(meetingId, eventId, sportId);
        }
        db.delete(EVENTS_TABLE, KEY_MEETING_ID + "=? AND " + KEY_SPORT_ID + "=?", new String[] {
                meetingId, sportId
        });
    }

   /**
    * Insert Opponents
    * @param opponentData
    */
    public void insertOpponents(ArrayList<OpponentResponse> opponentData) {
        for (OpponentResponse or : opponentData) {
            ContentValues values = new ContentValues();
            values.put(KEY_OPPONENTSID, or.getOpponentsId());
            values.put(KEY_OPPONENTSDESCRIPTION, or.getOpponentsDescription());
            values.put(KEY_EVENT_ID, or.getEventId());
            db.insert(OPPONENTS_TABLE, null, values);
        }
    }
/**
 * Gets Opponents from the Opponents Table
 * @param eventId
 * @return ArrayList<OpponentsResponse>
 */
    public ArrayList<OpponentResponse> getOpponents(String eventId) {
        Cursor cursor = db.query(OPPONENTS_TABLE, new String[] {
                KEY_ID, KEY_OPPONENTSID, KEY_OPPONENTSDESCRIPTION
        }, KEY_EVENT_ID + "=?", new String[] {
                eventId
        }, null,
                null, null);
        OpponentResponse opponentResponse;
        ArrayList<OpponentResponse> opponentsData = new ArrayList<OpponentResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            opponentResponse = new OpponentResponse();

            opponentResponse.setEventId(cursor.getString(EVENTID_COLUMN));
            opponentResponse.setOpponentsId(cursor.getString(OPPONENTID_COLUMN));
            opponentResponse.setOpponentsDescription(cursor.getString(OPPONENTDESCRIPTION_COLUMN));

            opponentsData.add(opponentResponse);

            cursor.moveToNext();
        }
        cursor.close();
        return opponentsData;
    }
/**
 * Delete Opponents
 * @param eventId
 */
    public void deleteOpponents(String eventId) {

        db.delete(OPPONENTS_TABLE, KEY_EVENT_ID + "=?", new String[] {
                eventId
        });
    }

    /**
     * Gets Markets from the Market Database
     * @param eventId
     * @param meetingId
     * @param sportId
     * @return ArrayList<MarketsResponse>
     */
    public ArrayList<MarketsResponse> getMarket(String eventId, String meetingId,
            String sportId) {

        Cursor cursor = db.query(MARKET_TABLE, new String[] {
                KEY_ID, KEY_MARKET_ID, KEY_MARKET_TYPE_ID, KEY_MARKET_PLACE_TERMS_DESCRIPTION,
                KEY_MARKET_DESCRIPTION, KEY_MARKET_PERIOD_ID, KEY_MARKET_PERIOD_DESCRIPTION,
                KEY_SPORT_ID,
                KEY_MEETING_ID, KEY_EVENT_ID
        },
                KEY_EVENT_ID + "=? AND "
                        + KEY_MEETING_ID + "=? AND " + KEY_SPORT_ID + "=?", new String[] {
                        eventId, meetingId, sportId
                }, null, null, null);
        MarketsResponse mor;
        ArrayList<MarketsResponse> marketsdata = new ArrayList<MarketsResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            mor = new MarketsResponse();

            mor.setId(cursor.getString(MARKET_ID_COLUMN));
            mor.setTypeId(cursor.getString(MARKET_TYPE_ID_COLUMN));
            mor.setEachWay(cursor.getString(MARKET_EACHWAY_COLUMN));
            mor.setPlaceTermsDescription(cursor.getString(MARKET_PLACE_TERMS_DESCRIPTION_COLUMN));
            mor.setPlaceTermsDeduction(cursor.getString(MARKET_PLACE_TERMS_DEDUCTION_COLUMN));
            mor.setDescription(cursor.getString(MARKET_DESCRIPTION_COLUMN));
            mor.setPeriodId(cursor.getString(MARKET_PERIOD_ID_COLUMN));
            mor.setPeriodDescription(cursor.getString(MARKET_PERIOD_DESCRIPTION_COLUMN));
            mor.setEventId(cursor.getString(EVENTID_COLUMN));
            mor.setMeetingId(cursor.getString(MEETINGID_COLUMN));
            mor.setSportId(cursor.getString(SPORTID_COLUMN));

            marketsdata.add(mor);
            cursor.moveToNext();
        }
        cursor.close();
        return marketsdata;
    }
/**
 * Inserts Markets into the database.
 * @param marketData
 * @param eventId
 * @param meetingId
 * @param sportId
 */
    public void insertMarket(ArrayList<MarketsResponse> marketData,
            String eventId, String meetingId, String sportId) {

        for (MarketsResponse mr : marketData) {

            ContentValues values = new ContentValues();

            values.put(KEY_MARKET_ID, mr.getId());
            values.put(KEY_MARKET_TYPE_ID, mr.getTypeId());
            values.put(KEY_MARKET_EACHWAY, mr.getEachWay());
            values.put(KEY_MARKET_PLACE_TERMS_DESCRIPTION, mr.getPlaceTermsDescription());
            values.put(KEY_MARKET_PLACE_TERMS_DEDUCTION, mr.getPlaceTermsDeduction());
            values.put(KEY_MARKET_DESCRIPTION, mr.getDescription());
            values.put(KEY_MARKET_PERIOD_ID, mr.getPeriodId());
            values.put(KEY_MARKET_PERIOD_DESCRIPTION, mr.getPeriodDescription());
            values.put(KEY_SPORT_ID, sportId);
            values.put(KEY_MEETING_ID, meetingId);
            values.put(KEY_EVENT_ID, eventId);

            db.insert(MARKET_TABLE, null, values);

        }
    }
/**
 * Deletes Markets
 * @param meetingId
 * @param eventId
 * @param sportId
 */
    public void deleteMarkets(String meetingId, String eventId, String sportId) {
        ArrayList<MarketsResponse> marketList = getMarket(eventId, meetingId, sportId);
        for (MarketsResponse mr : marketList) {
            String marketId = mr.getId();
            deleteOutcomes(marketId);
        }
        db.delete(MARKET_TABLE, KEY_MARKET_ID + "=? AND " + KEY_SPORT_ID + "=? AND " + KEY_EVENT_ID
                + "=?", new String[] {
                meetingId, sportId, eventId
        });
    }

    /**
     * Get Outcomes from the Outcomes Database
     * @param marketId
     * @return ArrayList<OutcomesResponse>
     */
    public ArrayList<OutcomesResponse> getOutcomes(String marketId) {

        Cursor cursor = db.query(OUTCOMES_TABLE, new String[] {
                KEY_ID, KEY_OUTCOME_ID, KEY_OUTCOME_DESCRIPTION, KEY_OUTCOME_PRICE_ID,
                KEY_OUTCOME_DECIMAL_PRICE, KEY_OUTCOME_STARTING_PRICE, KEY_OUTCOME_FORMATTED_PRICE,
                KEY_PREVIOUS_PRICE_ID, KEY_MARKET_ID
        }, null, null, null, null, null);

        OutcomesResponse or;
        ArrayList<OutcomesResponse> marketsdata = new ArrayList<OutcomesResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            or = new OutcomesResponse();

            or.setId(cursor.getString(OUTCOME_ID_COLUMN));
            or.setDescription(cursor.getString(OUTCOME_DESCRIPTION_COLUMN));
            or.setPriceId(cursor.getString(OUTCOME_PRICE_ID_COLUMN));
            or.setPriceDecimal(cursor.getString(OUTCOME_DECIMAL_PRICE_COLUMN));
            or.setPriceStarting(cursor.getString(OUTCOME_STARTING_PRICE_COLUMN));
            or.setPriceFormatted(cursor.getString(OUTCOME_FORMATTED_PRICE_COLUMN));
            or.setPreviousPriceId(cursor.getString(PREVIOUS_PRICE_ID_COLUMN));
            or.setMarketId(cursor.getString(MARKET_ID_COLUMN));
            marketsdata.add(or);
            cursor.moveToNext();
        }
        cursor.close();
        return marketsdata;
    }
/**
 * Inserts Outcomes
 * @param outcomesData
 */
    public void insertOutcomes(ArrayList<OutcomesResponse> outcomesData) {

        for (OutcomesResponse or : outcomesData) {

            ContentValues values = new ContentValues();
            values.put(KEY_OUTCOME_ID, or.getId());
            values.put(KEY_OUTCOME_DESCRIPTION, or.getDescription());
            values.put(KEY_OUTCOME_PRICE_ID, or.getPriceId());
            values.put(KEY_OUTCOME_DECIMAL_PRICE, or.getPriceDecimal());
            values.put(KEY_OUTCOME_STARTING_PRICE, or.getPriceStarting());
            values.put(KEY_OUTCOME_FORMATTED_PRICE, or.getPriceFormatted());
            values.put(KEY_MARKET_ID, or.getMarketId());
            values.put(KEY_EVENT_ID, or.getEventId());
            values.put(KEY_MEETING_ID, or.getMeetingId());
            values.put(KEY_SPORT_ID, or.getSportId());
            db.insert(OUTCOMES_TABLE, null, values);
        }
    }
/**
 * Deletes Outcomes
 * @param marketId
 */
    public void deleteOutcomes(String marketId) {
        ArrayList<OutcomesResponse> orList = getOutcomes(marketId);
        for (OutcomesResponse or : orList) {
            String outcomeId = or.getId();
            deletePreviousPrice(outcomeId);
        }
        db.delete(OUTCOMES_TABLE, KEY_MARKET_ID + "=?", new String[] {
            marketId
        });
    }

    /**
     * Gets Previous Prices
     * @param outcomeId
     * @return ArrayList<PreviousPriceResponse>
     */
    public ArrayList<PreviousPriceResponse> getPreviousPrice(String outcomeId) {

        Cursor cursor = db.query(PREVIOUS_PRICES_TABLE, new String[] {
                KEY_ID, KEY_PREVIOUS_PRICE_ID, KEY_PREVIOUS_DECIMAL, KEY_PREVIOUS_FORMATTED,
                KEY_OUTCOME_ID
        }, null, null, null, null, null);

        PreviousPriceResponse ppr;
        ArrayList<PreviousPriceResponse> prevPriceData = new ArrayList<PreviousPriceResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            ppr = new PreviousPriceResponse();

            ppr.setId(System.currentTimeMillis() + outcomeId);

            ppr.setDecimal(cursor.getString(PREVIOUS_DECIMAL_COLUMN));

            ppr.setFormatted(cursor.getString(PREVIOUS_FORMATTED_COLUMN));
            ppr.setOutcomeId(cursor.getString(OUTCOME_ID_COLUMN));

            prevPriceData.add(ppr);
            cursor.moveToNext();
        }
        cursor.close();
        return prevPriceData;
    }
/**
 * Insert Previous Prices
 * @param prevPriceData
 */
    public void insertPreviousPrice(ArrayList<PreviousPriceResponse> prevPriceData) {

        for (PreviousPriceResponse or : prevPriceData) {

            ContentValues values = new ContentValues();
            values.put(KEY_PREVIOUS_PRICE_ID, or.getId());
            values.put(KEY_PREVIOUS_DECIMAL, or.getDecimal());
            values.put(KEY_PREVIOUS_FORMATTED, or.getFormatted());
            values.put(KEY_OUTCOME_ID, or.getOutcomeId());

            db.insert(PREVIOUS_PRICES_TABLE, null, values);
        }
    }
/**
 * Deletes Previous Price
 * @param outcomeId
 */
    public void deletePreviousPrice(String outcomeId) {
        db.delete(PREVIOUS_PRICES_TABLE, KEY_OUTCOME_ID + "=?", new String[] {
            outcomeId
        });
    }

    private static class DBOpenHelper extends SQLiteOpenHelper {

        private static final String SPORTSTABLE_CREATE = "create table " + SPORTS_TABLE + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_SPORT_ID + " text not null, "
                + KEY_SPORTNAME + " text not null, "
                + KEY_HAS_EVENTS + " text not null );";

        private static final String MEETINGSTABLE_CREATE = "create table " + MEETINGS_TABLE + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_MEETING_ID + " text not null, "
                + KEY_MEETINGHEADER + " text not null, "
                + KEY_MEETINGDESCRIPTION + " text not null, "
                + KEY_SPORT_ID + " text not null );";

        private static final String EVENTSTABLE_CREATE = "create table " + EVENTS_TABLE + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_EVENT_ID + " text not null, "
                + KEY_EVENTNAME + " text not null, "
                + KEY_EVENTDATE + " text not null, "
                + KEY_EVENT_EARLY_PRICE + " text not null, "
                + KEY_MEETING_ID + " text not null, "
                + KEY_SPORT_ID + " text not null );";

        private static final String OPPONENTSTABLE_CREATE = "create table " + OPPONENTS_TABLE
                + " (" + KEY_ID + " integer primary key autoincrement, "
                + KEY_OPPONENTSID + " text not null, "
                + KEY_OPPONENTSDESCRIPTION + " text not null, "
                + KEY_EVENT_ID + " text not null );";

        private static final String MARKETSTABLE_CREATE = "create table " + MARKET_TABLE
                + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_MARKET_ID + " text not null, "
                + KEY_MARKET_TYPE_ID + " text not null, "
                + KEY_MARKET_EACHWAY + " text not null, "
                + KEY_MARKET_PLACE_TERMS_DESCRIPTION + " text not null, "
                + KEY_MARKET_PLACE_TERMS_DEDUCTION + " text not null, "
                + KEY_MARKET_DESCRIPTION + " text not null, "
                + KEY_MARKET_PERIOD_ID + " text not null, "
                + KEY_MARKET_PERIOD_DESCRIPTION + " text not null, "
                + KEY_EVENT_ID + " text not null, "
                + KEY_MEETING_ID + " text not null, "
                + KEY_SPORT_ID + " text not null );";

        private static final String OUTCOMESTABLE_CREATE = "create table " + OUTCOMES_TABLE
                + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_OUTCOME_ID + " text not null, "
                + KEY_OUTCOME_DESCRIPTION + " text not null, "
                + KEY_OUTCOME_PRICE_ID + " text not null, "
                + KEY_OUTCOME_DECIMAL_PRICE + " text not null, "
                + KEY_OUTCOME_STARTING_PRICE + " text not null, "
                + KEY_OUTCOME_FORMATTED_PRICE + " text not null, "
                + KEY_MARKET_ID + " text not null, "
                + KEY_EVENT_ID + " text not null, "
                + KEY_MEETING_ID + " text not null, "
                + KEY_SPORT_ID + " text not null );";

        private static final String PREVIOUS_PRICE_TABLE_CREATE = "create table "
                + PREVIOUS_PRICES_TABLE
                + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_PREVIOUS_PRICE_ID + " text, "
                + KEY_PREVIOUS_DECIMAL + " text not null, "
                + KEY_PREVIOUS_FORMATTED + " text not null, "
                + KEY_OUTCOME_ID + " text not null);";

        public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SPORTSTABLE_CREATE);
            db.execSQL(MEETINGSTABLE_CREATE);
            db.execSQL(EVENTSTABLE_CREATE);
            db.execSQL(OPPONENTSTABLE_CREATE);
            db.execSQL(MARKETSTABLE_CREATE);
            db.execSQL(OUTCOMESTABLE_CREATE);
            db.execSQL(PREVIOUS_PRICE_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SPORTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + MEETINGSTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + EVENTSTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + OPPONENTSTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + MARKETSTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + OUTCOMESTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + PREVIOUS_PRICES_TABLE);
            onCreate(db);
        }

    }

}
