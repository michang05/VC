
package mobi.victorchandler.database;

import mobi.victorchandler.response.NextEventsResponse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
/**
 * Next Events database
 * @author riveram
 *
 */
public class NextEventsDb {

    public static final String KEY_ID = "_id";
    public static final int ID_COLUMN = 0;
    public static final String KEY_EVENT_NAME = "eventname";
    public static final int DESCRIPTION_COLUMN = 1;
    public static final String KEY_DATE = "date";
    public static final int DATE_COLUMN = 2;
    public static final String KEY_EVENTID = "eventid";
    public static final int EVENTID_COLUMN = 3;
    public static final String KEY_SPORTID = "sportid";
    public static final int SPORTID_COLUMN = 4;
    public static final String KEY_MEETINGID = "meetingid";
    public static final int MEETINGID_COLUMN = 5;
    public static final String KEY_SPORTNAME = "sportname";
    public static final int SPORTNAME_COLUMN = 6;
    public static final String KEY_MEETINGNAME = "meetingname";
    public static final int MEETINGNAME_COLUMN = 7;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "upcoming.db";
    private static final String UPCOMING_TABLE = "upcoming";

    private SQLiteDatabase db;
    private DBOpenHelper dbHelper;
    private NextEventsResponse mNextEventsResponse;

    public NextEventsDb(Context context) {
        dbHelper = new DBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close() {
        db.close();
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }
/**
 * Method for getting Events
 * @return ArrayList<NextEventsResponse>
 */
    public ArrayList<NextEventsResponse> getEvents() {
        open();
        Cursor cursor = db.query(UPCOMING_TABLE, new String[] {
                KEY_ID,
                KEY_EVENT_NAME, KEY_DATE, KEY_EVENTID, KEY_SPORTID, KEY_MEETINGID,
                KEY_SPORTNAME, KEY_MEETINGNAME
        }, null, null, null, null, null);
        ArrayList<NextEventsResponse> alNextEvents = new ArrayList<NextEventsResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mNextEventsResponse = new NextEventsResponse();

            mNextEventsResponse.setEventDate(cursor.getString(DATE_COLUMN));
            mNextEventsResponse.setEventName(cursor.getString(DESCRIPTION_COLUMN));
            mNextEventsResponse.setEventId(cursor.getString(EVENTID_COLUMN));
            mNextEventsResponse.setSportId(cursor.getString(SPORTID_COLUMN));
            mNextEventsResponse.setMeetingId(cursor.getString(MEETINGID_COLUMN));
            mNextEventsResponse.setSportName(cursor.getString(SPORTNAME_COLUMN));
            mNextEventsResponse.setMeetingName(cursor.getString(MEETINGNAME_COLUMN));

            alNextEvents.add(mNextEventsResponse);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return alNextEvents;
    }
/**
 * Insert next events to the database
 * @param eventsdata
 */
    public void insertEvents(ArrayList<NextEventsResponse> eventsdata) {
        open();
        
        for (NextEventsResponse nextEventsResponse : eventsdata) {
            ContentValues values = new ContentValues();
            values.put(KEY_EVENT_NAME, nextEventsResponse.getEventName());
            values.put(KEY_DATE, nextEventsResponse.getEventDate());
            values.put(KEY_EVENTID, nextEventsResponse.getEventId());
            values.put(KEY_MEETINGID, nextEventsResponse.getMeetingId());
            values.put(KEY_SPORTID, nextEventsResponse.getSportId());
            values.put(KEY_MEETINGNAME, nextEventsResponse.getMeetingName());
            values.put(KEY_SPORTNAME, nextEventsResponse.getSportName());
            db.insert(UPCOMING_TABLE, null, values);
        }

        close();
    }
/**
 * delete next events from the database
 */
    public void deleteEvents() {
        open();
        db.delete(UPCOMING_TABLE, "1", null);
        close();
    }

    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String UPCOMINGTABLE_CREATE = "create table " + UPCOMING_TABLE
                + " (" + KEY_ID + " integer primary key autoincrement, "
                + KEY_EVENT_NAME + " text not null, "
                + KEY_DATE + " text not null, "
                + KEY_EVENTID + " text not null, "
                + KEY_MEETINGID + " text not null, "
                + KEY_SPORTID + " text not null, "
                + KEY_SPORTNAME + " text not null, "
                + KEY_MEETINGNAME + " text not null );";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(UPCOMINGTABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + UPCOMING_TABLE);
            onCreate(db);
        }

    }

}
