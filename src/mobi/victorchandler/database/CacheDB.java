package mobi.victorchandler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Database for Cache data
 * 
 * @author riveram
 *
 */
public class CacheDB {

	public static long TIMEOUT = 1000 * 30;
	// public static long TIMEOUT = 1000 * 60;

	public static final String KEY_ID = "_id";
	public static final int ID_COLUMN = 0;
	public static final String KEY_URL = "url";
	public static final int URL_COLUMN = 1;
	public static final String KEY_ETAG = "etag";
	public static final int ETAG_COLUMN = 2;
	public static final String KEY_TIME = "time";
	public static final int TIME_COLUMN = 3;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "cache.db";
	private static final String CACHE_TABLE = "cache";

	private SQLiteDatabase db;
	private DBOpenHelper dbHelper;

	public CacheDB(Context context) {
		dbHelper = new DBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
/**
 * Closes Cache Database
 */
	public void close() {
		db.close();
	}
/**
 * Opens the Cache database
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
	 * checks if a url needs to be downloaded again or can be retreived from
	 * database.
	 * 
	 * @param url
	 *            the url to check
	 * @return true if the url has been downloaded too long ago or never
	 */
	public boolean isUrlExpired(String url) {
		open();   
		Cursor cursor = db.query(CACHE_TABLE, new String[] { KEY_ID, KEY_TIME }, KEY_URL + "= ?", new String[] { url }, null, null, null);
		if (!cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		long time = cursor.getLong(1);
		cursor.close();
		close();
	
		return (System.currentTimeMillis() > (time + TIMEOUT));
	}
/**
 * Updates the url if expiration is met.
 * @param url
 */
	public void updateUrlExpiration(String url) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(KEY_TIME, System.currentTimeMillis());
		db.update(CACHE_TABLE, cv, KEY_URL + "=?", new String[] { url });
		close();
	}
/**
 * Gets the Url Etag 
 * @param url
 * @return String
 */
	public String getUrlEtag(String url) {
		open();
		Cursor cursor = db.query(CACHE_TABLE, new String[] { KEY_ID, KEY_ETAG }, KEY_URL + "= ?", new String[] { url }, null, null, null);
		if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		String etag = cursor.getString(1);
		cursor.close();
		close();
		return etag;
	}
/**
 * Inserts Url
 * @param url
 */
	public void insertUrl(String url) {
		open();
		ContentValues values = new ContentValues();
		values.put(KEY_URL, url);
		values.put(KEY_TIME, 0);
		db.insert(CACHE_TABLE, null, values);
		close();
	}

	/**
	 * sets a net etag for a url and also updates the expiration time
	 * 
	 * @param url
	 * @param etag
	 */
	public void updateUrlEtag(String url, String etag) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(KEY_ETAG, etag);
		cv.put(KEY_TIME, System.currentTimeMillis());
		db.update(CACHE_TABLE, cv, KEY_URL + "= ?", new String[] { url });
		close();
	}

	private static class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		private static final String CACHETABLE_CREATE = "create table " + CACHE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_URL + " text not null, " + KEY_ETAG + " text, " + KEY_TIME + " integer );";

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CACHETABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + CACHE_TABLE);
			onCreate(db);
		}

	}

}
