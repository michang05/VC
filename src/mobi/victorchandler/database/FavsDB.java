
package mobi.victorchandler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
/**
 * Favorites database
 * @author riveram
 *
 */
public class FavsDB {

    public static final String KEY_ID = "_id";
    public static final int ID_COLUMN = 0;
    public static final String KEY_TITLE = "title";
    public static final int TITLE_COLUMN = 1;
    public static final String KEY_SPORTID = "sportid";
    public static final int SPORTID_COLUMN = 2;
    public static final String KEY_MEETINGID = "meetingid";
    public static final int MEETINGID_COLUMN = 3;
    public static final String KEY_ACCOUNT_NUMBER = "account_number";
    public static final int ACCOUNT_NUMBER = 4;
    public static final String KEY_REMOVED = "removed";
    public static final int REMOVED = 5;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favs.db";
    private static final String FAVS_TABLE = "favs";

    private SQLiteDatabase db;
    private DBOpenHelper dbHelper;

    public FavsDB(Context context) {
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
 * Method that gets the Favorite from the database.
 * @param title
 * @return Cursor
 */
    public Cursor getFavorite(String title) {
        Cursor c = db.query(FAVS_TABLE, new String[] {
                KEY_ID, KEY_TITLE, KEY_SPORTID, KEY_MEETINGID
        }, KEY_TITLE + "=?", new String[] {
                title
        }, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
/**
 * adds the previous user's favorites using a flag
 * @param accountNumber
 */
    public void addBackUserFavorites(String accountNumber) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_REMOVED, "false");
        db.update(FAVS_TABLE, cv, KEY_ACCOUNT_NUMBER + "=?",
                new String[] {
                    accountNumber
                });
    }
    /**
     * Removes favorite from the database
     * @param title
     */
    public void removeFavoriteFromDb(String title) {
      
        db.delete(FAVS_TABLE, KEY_TITLE + "=?",
                new String[] {
                   title
                });
    }
    /**
     * Method that flag a removed user for 
     * showing favorites
     * @param accountNumber
     */
    public void flagRemovedUserFavorites(String accountNumber) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_REMOVED, "true");
        db.update(FAVS_TABLE, cv, KEY_ACCOUNT_NUMBER + "=?",
                new String[] {
                    accountNumber
                });
    }

    public Cursor getCursor() {
        return db.query(FAVS_TABLE, new String[] {
                KEY_ID, KEY_TITLE, KEY_SPORTID, KEY_MEETINGID
        }, "NOT "+KEY_REMOVED+"=?",new String[] {"true"}, null, null, null);
    }
/**
 * Inserts Favorites
 * @param title
 * @param sportid
 * @param meetingid
 * @param accountNumber
 * @return long
 */
    public long insertFav(String title, String sportid, String meetingid, String accountNumber) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_SPORTID, sportid);
        cv.put(KEY_MEETINGID, meetingid);
        cv.put(KEY_ACCOUNT_NUMBER, accountNumber);
        cv.put(KEY_REMOVED, "false");
        return db.insert(FAVS_TABLE, null, cv);
    }
/**
 * Removes Favorites
 * @param title
 * @param sportid
 * @param meetingid
 * @return boolean
 */
    public boolean removeFav(String title, String sportid, String meetingid) {
        return 0 != db.delete(FAVS_TABLE, KEY_TITLE + "=? AND " + KEY_SPORTID + "=? AND "
                + KEY_MEETINGID + "=?", new String[] {
                title, sportid, meetingid
        });
    }

    /**
     * Checks if the event/sport is a favorite that's in the database.
     * @param title
     * @param sportid
     * @param meetingid
     * @return
     */
    public boolean isFav(String title, String sportid, String meetingid) {
        SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM " + FAVS_TABLE
                + " WHERE " + KEY_TITLE + "=\"" + title + "\" AND " + KEY_SPORTID + "=" + sportid
                + " AND " + KEY_MEETINGID + "=" + meetingid + ";");
        long result = statement.simpleQueryForLong();
        statement.close();
        return result > 0;
    }

    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String FAVSTABLE_CREATE = "create table " + FAVS_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TITLE + " text not null, "
                + KEY_SPORTID + " text not null, " + KEY_MEETINGID + " text not null, "
                + KEY_ACCOUNT_NUMBER + " text not null, " 
                + KEY_REMOVED + " text not null "+ ");";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(FAVSTABLE_CREATE);
         
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + FAVSTABLE_CREATE);
            onCreate(db);
        }

    }
/**
 * method that removes all favorites.
 */
    public void removeAllFavorites() {
        db.delete(FAVS_TABLE, null,null);
    }

}
