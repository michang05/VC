package mobi.victorchandler.database;

import mobi.victorchandler.response.TopBetsResponse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
/**
 * Class for Top Bets Database
 * @author riveram
 *
 */
public class TopBetsDb {

	public static final String KEY_ID = "_id";
	public static final int ID_COLUMN = 0;

	public static final String KEY_SPORTNAME = "sportname";
	public static final int SPORTNAME_COLUMN = 1;
	public static final String KEY_MARKETNAME = "marketname";
	public static final int MARKETNAME_COLUMN = 2;
	public static final String KEY_MARKETPERIOD = "marketperiod";
	public static final int MARKETPERIOD_COLUMN = 3;
	public static final String KEY_MEETINGNAME = "meetingname";
	public static final int MEETINGNAME_COLUMN = 4;
	public static final String KEY_EVENTNAME = "eventname";
	public static final int EVENTNAME_COLUMN = 5;
	public static final String KEY_OUTCOMENAME = "outcomename";
	public static final int OUTCOMENAME_COLUMN = 6;
	public static final String KEY_OUTCOMEFORMATTEDPRICE = "outcomeformattedprice";
	public static final int OUTCOMEFORMATTEDPRICE_COLUMN = 7;

	public static final String KEY_OUTCOMEPRICE = "outcomeprice";
	public static final int OUTCOMEPRICE_COLUMN = 8;
	public static final String KEY_OUTCOMEPRICEID = "outcomepriceid";
	public static final int OUTCOMEPRICEID_COLUMN = 9;
	public static final String KEY_OUTCOMEID = "outcomeid";
	public static final int OUTCOMEID_COLUMN = 10;
	// TODO get proper value from nodejs layer
	public static final String KEY_MARKETEW = "marketew";
	public static final int MARKETEW_COLUMN = 11;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "topbets.db";
	private static final String TOPBETS_TABLE = "topbets";

	private SQLiteDatabase db;
	private DatabaseOpenHelper dbHelper;
	private TopBetsResponse mTopBets;
	
	
	public TopBetsDb(Context context) {
		dbHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
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
 * Gets Top Bets
 * @return ArrayList<TopBetsResponse>
 */
	public ArrayList<TopBetsResponse> getBets() {
		open();
		Cursor cursor = db.query(TOPBETS_TABLE, new String[] { KEY_ID, KEY_SPORTNAME, 
		        KEY_MARKETNAME, 
		        KEY_MARKETPERIOD,
		        KEY_MEETINGNAME, 
		        KEY_EVENTNAME, 
		        KEY_OUTCOMENAME, 
		        KEY_OUTCOMEFORMATTEDPRICE, 
		        KEY_OUTCOMEPRICE, 
		        KEY_OUTCOMEPRICEID, 
		        KEY_OUTCOMEID, KEY_MARKETEW }, null, null, null, null, null);
		
		ArrayList<TopBetsResponse> alTopBets = new ArrayList<TopBetsResponse>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
		    
			mTopBets = new TopBetsResponse();
			
			mTopBets.setSportName(cursor.getString(SPORTNAME_COLUMN));
			mTopBets.setMarketName(cursor.getString(MARKETNAME_COLUMN));
			mTopBets.setMarketPeriod(cursor.getString(MARKETPERIOD_COLUMN));
			mTopBets.setMeetingName(cursor.getString(MEETINGNAME_COLUMN));
			mTopBets.setEventName(cursor.getString(EVENTNAME_COLUMN));
			mTopBets.setOutcomeName(cursor.getString(OUTCOMENAME_COLUMN));
			mTopBets.setOutcomeFormattedPrice(cursor.getString(OUTCOMEFORMATTEDPRICE_COLUMN));
			mTopBets.setOutcomePrice(cursor.getString(OUTCOMEPRICE_COLUMN));
			mTopBets.setOutcomePriceId(cursor.getString(OUTCOMEPRICEID_COLUMN));
			mTopBets.setOutcomeId(cursor.getString(OUTCOMEID_COLUMN));
			mTopBets.setMarketEw( cursor.getString(MARKETEW_COLUMN));
			
			alTopBets.add(mTopBets);
			
			cursor.moveToNext();
		}
		
		cursor.close();
		close();
		
		return alTopBets;
	}

/**
 * Delete bets
 */
	public void deleteBets() {
		open();
		db.delete(TOPBETS_TABLE, "1", null);
		close();
	}

	private static class DatabaseOpenHelper extends SQLiteOpenHelper {

		public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		private static final String TOPBETSTABLE_CREATE = "create table " + TOPBETS_TABLE 
		+ " (" + KEY_ID + " integer primary key autoincrement, " 
		+ KEY_SPORTNAME + " text not null, " 
		+ KEY_MARKETNAME + " text not null, "
		+ KEY_MARKETPERIOD + " text not null, " 
		+ KEY_MEETINGNAME + " text not null, " 
		+ KEY_EVENTNAME + " text not null, " 
		+ KEY_OUTCOMENAME + " text not null, " 
		+ KEY_OUTCOMEFORMATTEDPRICE + " text not null, " 
		+ KEY_OUTCOMEPRICE + " text not null, " 
		+ KEY_OUTCOMEPRICEID + " text not null, " 
		+ KEY_OUTCOMEID + " text not null, " 
		+ KEY_MARKETEW + " text not null );";

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TOPBETSTABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TOPBETS_TABLE);
			onCreate(db);
		}

	}
/**
 * Inserts Top Bets 
 * @param betsdata
 */
    public void insertBets(ArrayList<TopBetsResponse> betsdata) {
        open();
        
        for (TopBetsResponse topBets : betsdata) {
            ContentValues values = new ContentValues();
            values.put(KEY_SPORTNAME, topBets.getSportName());
            values.put(KEY_MARKETNAME, topBets.getMarketName());
            values.put(KEY_MARKETPERIOD, topBets.getMarketPeriod());
            values.put(KEY_MEETINGNAME,topBets.getMeetingName());
            values.put(KEY_EVENTNAME,topBets.getEventName());
            values.put(KEY_OUTCOMENAME, topBets.getOutcomeName());
            values.put(KEY_OUTCOMEFORMATTEDPRICE, topBets.getOutcomeFormattedPrice());
            values.put(KEY_OUTCOMEPRICE, topBets.getOutcomePrice());
            values.put(KEY_OUTCOMEPRICEID, topBets.getOutcomePriceId());
            values.put(KEY_OUTCOMEID, topBets.getOutcomeId());
            values.put(KEY_MARKETEW, topBets.getMarketEw());
            db.insert(TOPBETS_TABLE, null, values);
        }
       
        close();
    }

}
