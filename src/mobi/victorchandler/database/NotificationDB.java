package mobi.victorchandler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Class that stores the notification.
 * @author riveram
 *
 */
public class NotificationDB {

	public static final String KEY_ID = "_id";
	public static final int ID_COLUMN = 0;
	public static final String KEY_DATE = "date";
	public static final int DATE_COLUMN = 1;
	public static final String KEY_EVENT = "event";
	public static final int EVENT_COLUMN = 2;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "notification.db";
	private static final String NOTIFICATION_TABLE = "notification";

	private SQLiteDatabase db;
	private DBOpenHelper dbHelper;

	public NotificationDB(Context context) {
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
 * Checks if a notification already exist.
 * @param date
 * @return
 */
	public boolean hasNotification(String date) {
		return db.compileStatement("SELECT COUNT(*) FROM " + NOTIFICATION_TABLE + " WHERE " + KEY_DATE + "=" + date + ";").simpleQueryForLong() > 0;
	}
/**
 * gets the cursor of the notification database
 * @return
 */
	public Cursor getCursor() {
		return db.query(NOTIFICATION_TABLE, new String[] { KEY_ID, KEY_DATE, KEY_EVENT }, null, null, null, null, null);
	}
/**
 * Deletes the notification using the id.
 * @param id
 * @return
 */
	public boolean delete(long id) {
		return db.delete(NOTIFICATION_TABLE, KEY_ID + "=?", new String[] { String.valueOf(id) }) == 1;
	}
/**
 * Inserts Notification into the database.
 * @param date
 * @param event
 * @return long
 */
	public long insertNotification(String date, String event) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_EVENT, event);
		cv.put(KEY_DATE, date);
		return db.insert(NOTIFICATION_TABLE, null, cv);
	}

	private static class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		private static final String NOTIFICATIONTABLE_CREATE = "create table " + NOTIFICATION_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_DATE + " text not null, " + KEY_EVENT + " text not null );";

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(NOTIFICATIONTABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + NOTIFICATION_TABLE);
			onCreate(db);
		}

	}

}
