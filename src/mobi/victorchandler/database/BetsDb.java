
package mobi.victorchandler.database;

import mobi.victorchandler.response.EventsResponse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
/**
 * Database for Bets
 * @author riveram
 *
 */
public class BetsDb {

    public static final String KEY_ID = "_id";
    public static final int ID_COLUMN = 0;
    public static final String KEY_EVENTNAME = "eventname";
    public static final int EVENTNAME_COLUMN = 1;
    public static final String KEY_MARKETNAME = "marketname";
    public static final int MARKETNAME_COLUMN = 2;
    public static final String KEY_MARKETPERIOD = "marketperiod";
    public static final int MARKETPERIOD_COLUMN = 3;
    public static final String KEY_OUTCOMENAME = "outcomename";
    public static final int OUTCOMENAME_COLUMN = 4;
    public static final String KEY_OUTCOMEPRICE = "outcomeprice";
    public static final int OUTCOMEPRICE_COLUMN = 5;
    public static final String KEY_OUTCOMEPRICEID = "outcomepriceid";
    public static final int OUTCOMEPRICEID_COLUMN = 6;
    public static final String KEY_OUTCOMEID = "outcomeid";
    public static final int OUTCOMEID_COLUMN = 7;
    public static final String KEY_OUTCOMEFORMATTEDPRICE = "formattedprice";
    public static final int OUTCOMEFORMATTEDPRICE_COLUMN = 8;
    public static final String KEY_CANEW = "canew";
    public static final int CANEW_COLUMN = 9;
    public static final String KEY_PT_DEDUCTION = "placeterms_deduction";
    public static final int PT_DEDUCTION_COLUMN = 10;
    public static final String KEY_PT_DESCRIPTION = "placeterms_description";
    public static final int PT_DESCRIPTION_COLUMN = 11;
    public static final String KEY_EVENT_ID = "event_id";
    public static final int EVENT_ID_COLUMN = 12;
    public static final String KEY_EVENT_DATE = "event_date";
    public static final int EVENT_DATE_COLUMN = 13;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bets.db";
    private static final String BETS_TABLE = "bets";

    private SQLiteDatabase db;
    private DBOpenHelper dbHelper;
    private Context context;

    public BetsDb(Context context) {
        dbHelper = new DBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void close() {
        db.close();
    }
/**
 * Opens the Database
 * @throws SQLiteException
 */
    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }
/**
 * Gets the cursor from the Database.
 * @return Cursor
 */
    public Cursor getCursor() {
        return db.query(BETS_TABLE, new String[] {
                KEY_ID,
                KEY_EVENTNAME
                , KEY_MARKETNAME
                , KEY_MARKETPERIOD
                , KEY_OUTCOMENAME
                , KEY_OUTCOMEPRICE
                , KEY_OUTCOMEPRICEID
                , KEY_OUTCOMEID
                , KEY_OUTCOMEFORMATTEDPRICE
                , KEY_CANEW
                , KEY_PT_DEDUCTION
                , KEY_PT_DESCRIPTION
                , KEY_EVENT_ID
                , KEY_EVENT_DATE
        }, null, null, null, null, null);
    }
/**
 * Method that updates a bet.
 * 
 * @param outcomeid
 * @param outcomepriceid
 * @param outcomeprice
 * @param outcomeformattedprice
 * @return boolean
 */
    public boolean updateBet(String outcomeid, String outcomepriceid, String outcomeprice,
            String outcomeformattedprice) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUTCOMEPRICE, outcomeprice);
        cv.put(KEY_OUTCOMEFORMATTEDPRICE, outcomeformattedprice);
        cv.put(KEY_OUTCOMEPRICEID, outcomepriceid);
        return db.update(BETS_TABLE, cv, KEY_OUTCOMEID + "=?", new String[] {
                outcomeid
        }) == 1;
    }
/**
 * Method that insert a bet into the database
 * 
 * @param eventname
 * @param marketname
 * @param marketperiod
 * @param outcomename
 * @param outcomeprice
 * @param outcomepriceid
 * @param outcomeid
 * @param outcomeformattedprice
 * @param canew
 * @param ptDeduction
 * @param ptDescription
 * @param eventId
 * @return long
 */
    public long insertBet(String eventname, String marketname, String marketperiod,
            String outcomename, String outcomeprice, String outcomepriceid, String outcomeid,
            String outcomeformattedprice, String canew, String ptDeduction, String ptDescription,
            String eventId) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_EVENTNAME, eventname);
        cv.put(KEY_MARKETNAME, marketname);
        cv.put(KEY_MARKETPERIOD, marketperiod);
        cv.put(KEY_OUTCOMENAME, outcomename);
        cv.put(KEY_OUTCOMEPRICE, outcomeprice);
        cv.put(KEY_OUTCOMEFORMATTEDPRICE, outcomeformattedprice);
        cv.put(KEY_OUTCOMEPRICEID, outcomepriceid);
        cv.put(KEY_OUTCOMEID, outcomeid);
        cv.put(KEY_CANEW, canew);
        cv.put(KEY_PT_DEDUCTION, ptDeduction);
        cv.put(KEY_PT_DESCRIPTION, ptDescription);
        cv.put(KEY_EVENT_ID, eventId);

        SportsBook sb = new SportsBook(context);
        sb.open();
        EventsResponse er = sb.getEventById(eventId);
        sb.close();
      
        cv.put(KEY_EVENT_DATE, er.getEventDate());

        return db.insert(BETS_TABLE, null, cv);
    }
/**
 * Method for checking if the bet already exist
 * 
 * @param outcomeid
 * @return boolean
 */
    public boolean hasBet(String outcomeid) {
        SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM " + BETS_TABLE
                + " WHERE " + KEY_OUTCOMEID + "=" + outcomeid + ";");
        long result = statement.simpleQueryForLong();
        statement.close();
        return result > 0;
    }

    /**
     * Method for counting of bets in the database
     * 
     * @return long
     */
    public long countBets() {
        SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM " + BETS_TABLE);
        long result = statement.simpleQueryForLong();
        statement.close();
        return result;
    }
/**
 *Method for deleting a bet by outcomeId
 * 
 * @param outcomeid
 * @return boolean
 */
    public boolean deleteBet(String outcomeid) {
        return db.delete(BETS_TABLE, KEY_OUTCOMEID + "=?", new String[] {
                outcomeid
        }) > 0;
    }
    /**
     * Method for deleting a bet by Event Name
     * 
     * @param eventName
     * @return boolean
     */
    public boolean deleteBetByEventName(String eventName) {
        return db.delete(BETS_TABLE, KEY_EVENTNAME + "=?", new String[] {
                eventName
        }) > 0;
    }

    /**
     * Method for deleting a bet by Database ID
     * 
     * @param id
     * @return boolean
     */
    public boolean delete(int id) {
        return db.delete(BETS_TABLE, KEY_ID + "=?", new String[] {
                String.valueOf(id)
        }) > 0;
    }

    /**
     * Removes the bets Table
     */
    public void clear() {
        db.delete(BETS_TABLE, null, null);
    }
/**
 * Static Class that extends SQLiteOpenHelper
 * creates the table and upgrades it.
 * @author riveram
 *
 */
    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String BETSTABLE_CREATE = "create table " + BETS_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_EVENTNAME + " text not null, "
                + KEY_MARKETNAME + " text not null, " + KEY_MARKETPERIOD + " text not null, "
                + KEY_OUTCOMENAME + " text not null, " + KEY_OUTCOMEPRICE + " text not null, "
                + KEY_OUTCOMEPRICEID + " text not null, " + KEY_OUTCOMEID + " text not null, "
                + KEY_OUTCOMEFORMATTEDPRICE + " text not null, " + KEY_CANEW + " text not null, "
                + KEY_PT_DEDUCTION + " text not null, "
                + KEY_PT_DESCRIPTION + " text not null,"
                + KEY_EVENT_ID + " text not null, "
                + KEY_EVENT_DATE + " text not null );";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(BETSTABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + BETSTABLE_CREATE);
            onCreate(db);
        }

    }

}
