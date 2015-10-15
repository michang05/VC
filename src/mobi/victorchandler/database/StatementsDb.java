
package mobi.victorchandler.database;

import mobi.victorchandler.response.BetStatementsResponse;
import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.MeetingsResponse;
import mobi.victorchandler.response.OpponentResponse;
import mobi.victorchandler.response.OutcomesResponse;
import mobi.victorchandler.response.PreviousPriceResponse;
import mobi.victorchandler.response.TransactionStatementResponse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
/**
 * Statements Database
 * @author riveram
 *
 */
public class StatementsDb {

    private static final int DATABASE_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_TRANSACTION_ID = "transaction_id";
    public static final String KEY_STATEMENT_BET_ID = "bet_id";
    public static final String KEY_TYPE_ID = "type_id";
    public static final String KEY_TYPE_DESCRIPTION = "type_description";
    public static final String KEY_SUBTYPE_ID = "subtype_id";
    public static final String KEY_SUBTYPE_DESCRIPTION = "subtype_description";
    public static final String KEY_SETTLED = "settled";
    public static final String KEY_DATE = "date";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DEBIT_DECIMAL = "debit_decimal";
    public static final String KEY_DEBIT_FORMATTED = "debit_formatted";
    public static final String KEY_CREDIT_DECIMAL = "credit_decimal";
    public static final String KEY_CREDIT_FORMATTED = "credit_formatted";

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

    private static final String DATABASE_NAME = "statements.db";
    private static final String TRANSACTIONS_TABLE = "transactions";
    private static final String BETS_TABLE = "bets";
    private static final String MEETINGS_TABLE = "meetings";
    private static final String EVENTS_TABLE = "events";
    private static final String MARKET_TABLE = "market";
    private static final String OUTCOMES_TABLE = "outcomes";
    private static final String PREVIOUS_PRICES_TABLE = "previous_prices";
    private static final String OPPONENTS_TABLE = "opponents";

    private SQLiteDatabase db;
    private DatabaseOpenHelper dbHelper;
    private BetStatementsResponse mStatementsResponse;

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        private static final String TRANSACTIONS_TABLE_CREATE = "create table "
                + TRANSACTIONS_TABLE + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_TRANSACTION_ID + " text, "
                + KEY_TYPE_ID + " text, "
                + KEY_TYPE_DESCRIPTION + " text, "
                + KEY_SUBTYPE_ID + " text, "
                + KEY_SUBTYPE_DESCRIPTION + " text, "
                + KEY_SETTLED + " text, "
                + KEY_DATE + " text, "
                + KEY_DESCRIPTION + " text, "
                + KEY_DEBIT_DECIMAL + " text, "
                + KEY_DEBIT_FORMATTED + " text, "
                + KEY_CREDIT_DECIMAL + " text, "
                + KEY_CREDIT_FORMATTED + " text );";

        private static final String BETS_TABLE_CREATE = "create table "
                + BETS_TABLE + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_STATEMENT_BET_ID + " text, "
                + KEY_TYPE_ID + " text, "
                + KEY_TYPE_DESCRIPTION + " text, "
                + KEY_SUBTYPE_ID + " text, "
                + KEY_SUBTYPE_DESCRIPTION + " text, "
                + KEY_SETTLED + " text, "
                + KEY_DATE + " text, "
                + KEY_DESCRIPTION + " text, "
                + KEY_DEBIT_DECIMAL + " text, "
                + KEY_DEBIT_FORMATTED + " text, "
                + KEY_CREDIT_DECIMAL + " text, "
                + KEY_CREDIT_FORMATTED + " text, "
                + KEY_OUTCOME_ID + " text, "
                + KEY_MARKET_ID + " text,"
                + KEY_MEETING_ID + " text, "
                + KEY_EVENT_ID + " text );";

        private static final String MEETINGSTABLE_CREATE = "create table " + MEETINGS_TABLE + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_MEETING_ID + " text, "
                + KEY_MEETINGHEADER + " text, "
                + KEY_MEETINGDESCRIPTION + " text, "
                + KEY_STATEMENT_BET_ID + " text not null );";

        private static final String EVENTSTABLE_CREATE = "create table " + EVENTS_TABLE + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_EVENT_ID + " text, "
                + KEY_EVENTNAME + " text, "
                + KEY_EVENTDATE + " text, "
                + KEY_EVENT_EARLY_PRICE + " text, "
                + KEY_MEETING_ID + " text,"
                + KEY_STATEMENT_BET_ID + " text);";

        private static final String OPPONENTSTABLE_CREATE = "create table " + OPPONENTS_TABLE
                + " (" + KEY_ID + " integer primary key autoincrement, "
                + KEY_OPPONENTSID + " text, "
                + KEY_OPPONENTSDESCRIPTION + " text, "
                + KEY_EVENT_ID + " text );";

        private static final String MARKETSTABLE_CREATE = "create table " + MARKET_TABLE
                + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_MARKET_ID + " text, "
                + KEY_MARKET_TYPE_ID + " text, "
                + KEY_MARKET_EACHWAY + " text, "
                + KEY_MARKET_PLACE_TERMS_DESCRIPTION + " text, "
                + KEY_MARKET_PLACE_TERMS_DEDUCTION + " text, "
                + KEY_MARKET_DESCRIPTION + " text, "
                + KEY_MARKET_PERIOD_ID + " text, "
                + KEY_MARKET_PERIOD_DESCRIPTION + " text, "
                + KEY_EVENT_ID + " text, "
                + KEY_MEETING_ID + " text, "
                + KEY_STATEMENT_BET_ID + " text);";

        private static final String OUTCOMESTABLE_CREATE = "create table " + OUTCOMES_TABLE
                + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_OUTCOME_ID + " text, "
                + KEY_OUTCOME_DESCRIPTION + " text, "
                + KEY_OUTCOME_PRICE_ID + " text, "
                + KEY_OUTCOME_DECIMAL_PRICE + " text, "
                + KEY_OUTCOME_STARTING_PRICE + " text, "
                + KEY_OUTCOME_FORMATTED_PRICE + " text, "
                + KEY_MARKET_ID + " text, "
                + KEY_EVENT_ID + " text, "
                + KEY_MEETING_ID + " text, "

                + KEY_PREVIOUS_PRICE_ID + " text, "
                + KEY_STATEMENT_BET_ID + " text);";

        private static final String PREVIOUS_PRICE_TABLE_CREATE = "create table "
                + PREVIOUS_PRICES_TABLE
                + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_PREVIOUS_PRICE_ID + " text, "
                + KEY_PREVIOUS_DECIMAL + " text, "
                + KEY_PREVIOUS_FORMATTED + " text, "
                + KEY_OUTCOME_ID + " text);";

        public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TRANSACTIONS_TABLE_CREATE);
            db.execSQL(BETS_TABLE_CREATE);
            db.execSQL(MEETINGSTABLE_CREATE);
            db.execSQL(EVENTSTABLE_CREATE);
            db.execSQL(OPPONENTSTABLE_CREATE);
            db.execSQL(MARKETSTABLE_CREATE);
            db.execSQL(OUTCOMESTABLE_CREATE);
            db.execSQL(PREVIOUS_PRICE_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + BETS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + MEETINGSTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + EVENTSTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + OPPONENTSTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + MARKETSTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + OUTCOMESTABLE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS " + PREVIOUS_PRICES_TABLE);
            onCreate(db);
        }

    }

    public StatementsDb(Context context) {
        dbHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
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
    * Inserts Transactions in the database
    * @param alTransactions
    */
    public void insertTransactions(ArrayList<TransactionStatementResponse> alTransactions) {
        open();
        for (TransactionStatementResponse tsr : alTransactions) {

            ContentValues values = new ContentValues();

            values.put(KEY_TRANSACTION_ID, tsr.getId());
            values.put(KEY_TYPE_ID, tsr.getTypeId());
            values.put(KEY_TYPE_DESCRIPTION, tsr.getTypeDescription());
            values.put(KEY_SUBTYPE_ID, tsr.getSubTypeId());
            values.put(KEY_SUBTYPE_DESCRIPTION, tsr.getSubTypeDescription());
            values.put(KEY_SETTLED, tsr.getSettled());
            values.put(KEY_DATE, tsr.getDate());
            values.put(KEY_DESCRIPTION, tsr.getDescription());
            values.put(KEY_DEBIT_DECIMAL, tsr.getDebitDecimal());
            values.put(KEY_DEBIT_FORMATTED, tsr.getDebitFormatted());
            values.put(KEY_CREDIT_DECIMAL, tsr.getCreditDecimal());
            values.put(KEY_CREDIT_FORMATTED, tsr.getCreditFormatted());

            db.insert(TRANSACTIONS_TABLE, null, values);
        }
        close();
    }
/**
 * Gets Transactions in the database
 * @return ArrayList<TransationsStatementResponse>
 */
    public ArrayList<TransactionStatementResponse> getTransactions() {
        open();

        Cursor cursor = db.query(TRANSACTIONS_TABLE, null, null, null, null, null, null);

        ArrayList<TransactionStatementResponse> alTransactions = new ArrayList<TransactionStatementResponse>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            TransactionStatementResponse tsr = new TransactionStatementResponse();

            tsr.setId(cursor.getString(cursor.getColumnIndex(KEY_TRANSACTION_ID)));
            tsr.setTypeId(cursor.getString(cursor.getColumnIndex(KEY_TYPE_ID)));
            tsr.setTypeDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_TYPE_DESCRIPTION)));
            tsr.setSubTypeId(cursor.getString(cursor.getColumnIndex(KEY_SUBTYPE_ID)));
            tsr.setSubTypeDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_SUBTYPE_DESCRIPTION)));
            tsr.setSettled(cursor.getString(cursor.getColumnIndex(KEY_SETTLED)));
            tsr.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            tsr.setDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_DESCRIPTION)));
            tsr.setDebitDecimal(cursor.getString(cursor
                    .getColumnIndex(KEY_DEBIT_DECIMAL)));
            tsr.setDebitFormatted(cursor.getString(cursor
                    .getColumnIndex(KEY_DEBIT_FORMATTED)));
            tsr.setCreditDecimal(cursor.getString(cursor
                    .getColumnIndex(KEY_CREDIT_DECIMAL)));
            tsr.setCreditFormatted(cursor.getString(cursor
                    .getColumnIndex(KEY_CREDIT_FORMATTED)));
            alTransactions.add(tsr);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return alTransactions;
    }
/**
 * delete Transactions
 */
    public void deleteTransactions() {
        open();
        db.delete(TRANSACTIONS_TABLE, null, null);

        close();
    }

   /**
    * Inserts Bets into the database
    * @param alBets
    */
    public void insertBets(ArrayList<BetStatementsResponse> alBets) {
        open();
        for (BetStatementsResponse sr : alBets) {

            ContentValues values = new ContentValues();

            values.put(KEY_STATEMENT_BET_ID, sr.getId());
            values.put(KEY_TYPE_ID, sr.getTypeId());
            values.put(KEY_TYPE_DESCRIPTION, sr.getTypeDescription());
            values.put(KEY_SUBTYPE_ID, sr.getSubTypeId());
            values.put(KEY_SUBTYPE_DESCRIPTION, sr.getSubTypeDescription());
            values.put(KEY_SETTLED, sr.getSettled());
            values.put(KEY_DATE, sr.getDate());
            values.put(KEY_DESCRIPTION, sr.getDescription());
            values.put(KEY_DEBIT_DECIMAL, sr.getDebit_decimal());
            values.put(KEY_DEBIT_FORMATTED, sr.getDebit_formatted());
            values.put(KEY_CREDIT_DECIMAL, sr.getCredit_decimal());
            values.put(KEY_CREDIT_FORMATTED, sr.getCredit_formatted());
            values.put(KEY_OUTCOME_ID, sr.getOutcomeId());
            values.put(KEY_MARKET_ID, sr.getMarketId());
            values.put(KEY_EVENT_ID, sr.getEventId());
            values.put(KEY_MEETING_ID, sr.getMeetingId());

            db.insert(BETS_TABLE, null, values);
        }
        close();
    }
/**
 * Gets Open Bets
 * @return ArrayList<BetStatementsResponse>
 */
    public ArrayList<BetStatementsResponse> getOpenBets() {
        open();

        Cursor cOutcomes = db.query(OUTCOMES_TABLE, null, null, null, null, null, null);

        ArrayList<OutcomesResponse> alOutcomes = new ArrayList<OutcomesResponse>();
        cOutcomes.moveToFirst();

        while (!cOutcomes.isAfterLast()) {
            OutcomesResponse er = new OutcomesResponse();
            er.setId(cOutcomes.getString(cOutcomes.getColumnIndex(KEY_OUTCOME_ID)));
            er.setPriceFormatted(cOutcomes.getString(cOutcomes
                    .getColumnIndex(KEY_OUTCOME_FORMATTED_PRICE)));
            er.setDescription(cOutcomes.getString(cOutcomes.getColumnIndex(KEY_OUTCOME_DESCRIPTION)));

            er.setBetStatementId(cOutcomes.getString(cOutcomes.getColumnIndex(KEY_STATEMENT_BET_ID)));

            alOutcomes.add(er);
            cOutcomes.moveToNext();
        }
        cOutcomes.close();

        Cursor cMarkets = db.query(MARKET_TABLE, null, null, null, null, null, null);

        ArrayList<MarketsResponse> alMarkets = new ArrayList<MarketsResponse>();
        cMarkets.moveToFirst();

        while (!cMarkets.isAfterLast()) {
            MarketsResponse er = new MarketsResponse();
            er.setId(cMarkets.getString(cMarkets.getColumnIndex(KEY_MARKET_ID)));
            er.setDescription(cMarkets.getString(cMarkets.getColumnIndex(KEY_MARKET_DESCRIPTION)));
            er.setOutcomeList(alOutcomes);

            er.setBetStatementId(cMarkets.getString(cMarkets.getColumnIndex(KEY_STATEMENT_BET_ID)));

            alMarkets.add(er);
            cMarkets.moveToNext();
        }
        cMarkets.close();

        Cursor cEvents = db.query(EVENTS_TABLE, null, null, null, null, null, null);

        ArrayList<EventsResponse> alEvents = new ArrayList<EventsResponse>();
        cEvents.moveToFirst();

        while (!cEvents.isAfterLast()) {
            EventsResponse er = new EventsResponse();
            er.setEventId(cEvents.getString(cEvents.getColumnIndex(KEY_EVENT_ID)));
            er.setEventName(cEvents.getString(cEvents.getColumnIndex(KEY_EVENTNAME)));
            er.setEarlyPrice(cEvents.getString(cEvents.getColumnIndex(KEY_EVENT_EARLY_PRICE)));

            er.setBetStatementId(cEvents.getString(cEvents.getColumnIndex(KEY_STATEMENT_BET_ID)));
            er.setMarketsList(alMarkets);
            alEvents.add(er);
            cEvents.moveToNext();
        }
        cEvents.close();

        Cursor cMeetings = db.query(MEETINGS_TABLE, null, null, null, null, null, null);

        ArrayList<MeetingsResponse> alMeetings = new ArrayList<MeetingsResponse>();
        cMeetings.moveToFirst();

        while (!cMeetings.isAfterLast()) {
            MeetingsResponse mr = new MeetingsResponse();
            mr.setMeetingId(cMeetings.getString(cMeetings.getColumnIndex(KEY_MEETING_ID)));
            mr.setMeetingDescription(cMeetings.getString(cMeetings
                    .getColumnIndex(KEY_MEETINGDESCRIPTION)));
            mr.setMeetingHeader(cMeetings.getString(cMeetings.getColumnIndex(KEY_MEETINGHEADER)));
            mr.setBetStatementId(cMeetings.getString(cMeetings.getColumnIndex(KEY_STATEMENT_BET_ID)));
            mr.setEventsList(alEvents);
            alMeetings.add(mr);
            cMeetings.moveToNext();
        }
        cMeetings.close();

        Cursor cursor =
                db.rawQuery("SELECT * FROM " + BETS_TABLE + " where "
                        + KEY_SETTLED + "= ? AND " + KEY_TYPE_DESCRIPTION + "= ?", new String[] {
                        "false", "Bet"
                });
        ArrayList<BetStatementsResponse> alSr = new ArrayList<BetStatementsResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            mStatementsResponse = new BetStatementsResponse();

            mStatementsResponse
                    .setId(cursor.getString(cursor.getColumnIndex(KEY_STATEMENT_BET_ID)));
            mStatementsResponse.setTypeId(cursor.getString(cursor.getColumnIndex(KEY_TYPE_ID)));
            mStatementsResponse.setTypeDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_TYPE_DESCRIPTION)));
            mStatementsResponse
                    .setSubTypeId(cursor.getString(cursor.getColumnIndex(KEY_SUBTYPE_ID)));
            mStatementsResponse.setSubTypeDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_SUBTYPE_DESCRIPTION)));

            mStatementsResponse.setSettled(cursor.getString(cursor.getColumnIndex(KEY_SETTLED)));
            mStatementsResponse.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            mStatementsResponse.setDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_DESCRIPTION)));
            mStatementsResponse.setDebit_decimal(cursor.getString(cursor
                    .getColumnIndex(KEY_DEBIT_DECIMAL)));
            mStatementsResponse.setDebit_formatted(cursor.getString(cursor
                    .getColumnIndex(KEY_DEBIT_FORMATTED)));
            mStatementsResponse.setCredit_decimal(cursor.getString(cursor
                    .getColumnIndex(KEY_CREDIT_DECIMAL)));
            mStatementsResponse.setCredit_formatted(cursor.getString(cursor
                    .getColumnIndex(KEY_CREDIT_FORMATTED)));
            mStatementsResponse.setOutcomeId(cursor.getString(cursor
                    .getColumnIndex(KEY_OUTCOME_ID)));
            mStatementsResponse.setMarketId(cursor.getString(cursor
                    .getColumnIndex(KEY_MARKET_ID)));
            mStatementsResponse.setEventId(cursor.getString(cursor
                    .getColumnIndex(KEY_EVENT_ID)));
            mStatementsResponse.setMeetingId(cursor.getString(cursor
                    .getColumnIndex(KEY_MEETING_ID)));

            mStatementsResponse.setMeetingsList(alMeetings);

            alSr.add(mStatementsResponse);

            cursor.moveToNext();
        }

        cursor.close();
        close();
        return alSr;
    }
/**
 * Gets Close Bets
 * @return ArrayList<BetStatmentsResponse>
 */
    public ArrayList<BetStatementsResponse> getCloseBets() {
        open();

        Cursor cOutcomes = db.query(OUTCOMES_TABLE, null, null, null, null, null, null);

        ArrayList<OutcomesResponse> alOutcomes = new ArrayList<OutcomesResponse>();
        cOutcomes.moveToFirst();

        while (!cOutcomes.isAfterLast()) {
            OutcomesResponse er = new OutcomesResponse();
            er.setId(cOutcomes.getString(cOutcomes.getColumnIndex(KEY_OUTCOME_ID)));
            er.setPriceFormatted(cOutcomes.getString(cOutcomes
                    .getColumnIndex(KEY_OUTCOME_FORMATTED_PRICE)));
            er.setDescription(cOutcomes.getString(cOutcomes.getColumnIndex(KEY_OUTCOME_DESCRIPTION)));

            er.setBetStatementId(cOutcomes.getString(cOutcomes.getColumnIndex(KEY_STATEMENT_BET_ID)));

            alOutcomes.add(er);
            cOutcomes.moveToNext();
        }
        cOutcomes.close();

        Cursor cMarkets = db.query(MARKET_TABLE, null, null, null, null, null, null);

        ArrayList<MarketsResponse> alMarkets = new ArrayList<MarketsResponse>();
        cMarkets.moveToFirst();

        while (!cMarkets.isAfterLast()) {
            MarketsResponse er = new MarketsResponse();
            er.setId(cMarkets.getString(cMarkets.getColumnIndex(KEY_MARKET_ID)));
            er.setDescription(cMarkets.getString(cMarkets.getColumnIndex(KEY_MARKET_DESCRIPTION)));
            er.setOutcomeList(alOutcomes);

            er.setBetStatementId(cMarkets.getString(cMarkets.getColumnIndex(KEY_STATEMENT_BET_ID)));

            alMarkets.add(er);
            cMarkets.moveToNext();
        }
        cMarkets.close();

        Cursor cEvents = db.query(EVENTS_TABLE, null, null, null, null, null, null);

        ArrayList<EventsResponse> alEvents = new ArrayList<EventsResponse>();
        cEvents.moveToFirst();

        while (!cEvents.isAfterLast()) {
            EventsResponse er = new EventsResponse();
            er.setEventId(cEvents.getString(cEvents.getColumnIndex(KEY_EVENT_ID)));
            er.setEventName(cEvents.getString(cEvents.getColumnIndex(KEY_EVENTNAME)));
            er.setEarlyPrice(cEvents.getString(cEvents.getColumnIndex(KEY_EVENT_EARLY_PRICE)));

            er.setBetStatementId(cEvents.getString(cEvents.getColumnIndex(KEY_STATEMENT_BET_ID)));
            er.setMarketsList(alMarkets);
            alEvents.add(er);
            cEvents.moveToNext();
        }
        cEvents.close();

        Cursor cMeetings = db.query(MEETINGS_TABLE, null, null, null, null, null, null);

        ArrayList<MeetingsResponse> alMeetings = new ArrayList<MeetingsResponse>();
        cMeetings.moveToFirst();

        while (!cMeetings.isAfterLast()) {
            MeetingsResponse mr = new MeetingsResponse();
            mr.setMeetingId(cMeetings.getString(cMeetings.getColumnIndex(KEY_MEETING_ID)));
            mr.setMeetingDescription(cMeetings.getString(cMeetings
                    .getColumnIndex(KEY_MEETINGDESCRIPTION)));
            mr.setMeetingHeader(cMeetings.getString(cMeetings.getColumnIndex(KEY_MEETINGHEADER)));
            mr.setBetStatementId(cMeetings.getString(cMeetings.getColumnIndex(KEY_STATEMENT_BET_ID)));
            mr.setEventsList(alEvents);
            alMeetings.add(mr);
            cMeetings.moveToNext();
        }
        cMeetings.close();

        Cursor cursor =
                db.rawQuery("SELECT * FROM " + BETS_TABLE + " where "
                        + KEY_SETTLED + "= ? AND " + KEY_TYPE_DESCRIPTION + "= ?", new String[] {
                        "true", "Bet"
                });
        ArrayList<BetStatementsResponse> alSr = new ArrayList<BetStatementsResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            mStatementsResponse = new BetStatementsResponse();

            mStatementsResponse
                    .setId(cursor.getString(cursor.getColumnIndex(KEY_STATEMENT_BET_ID)));
            mStatementsResponse.setTypeId(cursor.getString(cursor.getColumnIndex(KEY_TYPE_ID)));
            mStatementsResponse.setTypeDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_TYPE_DESCRIPTION)));
            mStatementsResponse
                    .setSubTypeId(cursor.getString(cursor.getColumnIndex(KEY_SUBTYPE_ID)));
            mStatementsResponse.setSubTypeDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_SUBTYPE_DESCRIPTION)));

            mStatementsResponse.setSettled(cursor.getString(cursor.getColumnIndex(KEY_SETTLED)));
            mStatementsResponse.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            mStatementsResponse.setDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_DESCRIPTION)));
            mStatementsResponse.setDebit_decimal(cursor.getString(cursor
                    .getColumnIndex(KEY_DEBIT_DECIMAL)));
            mStatementsResponse.setDebit_formatted(cursor.getString(cursor
                    .getColumnIndex(KEY_DEBIT_FORMATTED)));
            mStatementsResponse.setCredit_decimal(cursor.getString(cursor
                    .getColumnIndex(KEY_CREDIT_DECIMAL)));
            mStatementsResponse.setCredit_formatted(cursor.getString(cursor
                    .getColumnIndex(KEY_CREDIT_FORMATTED)));
            mStatementsResponse.setOutcomeId(cursor.getString(cursor
                    .getColumnIndex(KEY_OUTCOME_ID)));
            mStatementsResponse.setMarketId(cursor.getString(cursor
                    .getColumnIndex(KEY_MARKET_ID)));
            mStatementsResponse.setEventId(cursor.getString(cursor
                    .getColumnIndex(KEY_EVENT_ID)));
            mStatementsResponse.setMeetingId(cursor.getString(cursor
                    .getColumnIndex(KEY_MEETING_ID)));

            mStatementsResponse.setMeetingsList(alMeetings);

            alSr.add(mStatementsResponse);

            cursor.moveToNext();
        }

        cursor.close();
        close();
        return alSr;
    }
/**
 * Gets Bets
 * @retun ArrayList<BetsStatementsResponse>
 */
    public ArrayList<BetStatementsResponse> getBets() {

        open();

        Cursor cursor = db.query(BETS_TABLE, new String[] {
                KEY_ID, KEY_STATEMENT_BET_ID, KEY_TYPE_ID,
                KEY_TYPE_DESCRIPTION,
                KEY_SUBTYPE_ID,
                KEY_SUBTYPE_DESCRIPTION,
                KEY_SETTLED,
                KEY_DATE,
                KEY_DESCRIPTION,
                KEY_DEBIT_DECIMAL,
                KEY_DEBIT_FORMATTED,
                KEY_CREDIT_DECIMAL,
                KEY_CREDIT_FORMATTED,
                KEY_OUTCOME_ID,
                KEY_MARKET_ID,
                KEY_EVENT_ID,
                KEY_MEETING_ID
        }, null, null, null, null, null);

        ArrayList<BetStatementsResponse> alSr = new ArrayList<BetStatementsResponse>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            mStatementsResponse = new BetStatementsResponse();

            mStatementsResponse
                    .setId(cursor.getString(cursor.getColumnIndex(KEY_STATEMENT_BET_ID)));
            mStatementsResponse.setTypeId(cursor.getString(cursor.getColumnIndex(KEY_TYPE_ID)));
            mStatementsResponse.setTypeDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_TYPE_DESCRIPTION)));
            mStatementsResponse
                    .setSubTypeId(cursor.getString(cursor.getColumnIndex(KEY_SUBTYPE_ID)));
            mStatementsResponse.setSubTypeDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_SUBTYPE_DESCRIPTION)));
            mStatementsResponse.setSettled(cursor.getString(cursor.getColumnIndex(KEY_SETTLED)));
            mStatementsResponse.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            mStatementsResponse.setDescription(cursor.getString(cursor
                    .getColumnIndex(KEY_DESCRIPTION)));
            mStatementsResponse.setDebit_decimal(cursor.getString(cursor
                    .getColumnIndex(KEY_DEBIT_DECIMAL)));
            mStatementsResponse.setDebit_formatted(cursor.getString(cursor
                    .getColumnIndex(KEY_DEBIT_FORMATTED)));
            mStatementsResponse.setCredit_decimal(cursor.getString(cursor
                    .getColumnIndex(KEY_CREDIT_DECIMAL)));
            mStatementsResponse.setCredit_formatted(cursor.getString(cursor
                    .getColumnIndex(KEY_CREDIT_FORMATTED)));
            mStatementsResponse.setOutcomeId(cursor.getString(cursor
                    .getColumnIndex(KEY_OUTCOME_ID)));
            mStatementsResponse.setMarketId(cursor.getString(cursor
                    .getColumnIndex(KEY_MARKET_ID)));
            mStatementsResponse.setEventId(cursor.getString(cursor
                    .getColumnIndex(KEY_EVENT_ID)));
            mStatementsResponse.setMeetingId(cursor.getString(cursor
                    .getColumnIndex(KEY_MEETING_ID)));

            mStatementsResponse.setMeetingsList(getMeetings(cursor.getString(cursor
                    .getColumnIndex(KEY_STATEMENT_BET_ID))));

            cursor.moveToNext();
        }

        cursor.close();
        close();

        return alSr;
    }
/**
 * Delete bets
 */
    public void deleteBets() {
        open();
        db.delete(OUTCOMES_TABLE, null, null);
        db.delete(MARKET_TABLE, null, null);
        db.delete(EVENTS_TABLE, null, null);
        db.delete(MEETINGS_TABLE, null, null);
        db.delete(BETS_TABLE, null, null);
        close();
    }

   /**
    * Get Meetings
    * @param betStatementId
    * @return ArrayList<MeetingsResponse>
    */
    public ArrayList<MeetingsResponse> getMeetings(String betStatementId) {
        Cursor cursor = db.query(MEETINGS_TABLE, new String[] {
                KEY_ID, KEY_STATEMENT_BET_ID, KEY_MEETING_ID, KEY_MEETINGHEADER,
                KEY_MEETINGDESCRIPTION
        }, KEY_STATEMENT_BET_ID + "=?", new String[] {betStatementId}, null, null, null);

        MeetingsResponse mr;
        ArrayList<MeetingsResponse> meetingsdata = new ArrayList<MeetingsResponse>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mr = new MeetingsResponse();

            mr.setMeetingId(cursor.getString(MEETINGID_COLUMN));
            mr.setMeetingHeader(cursor.getString(MEETINGHEADER_COLUMN));
            mr.setMeetingDescription(cursor.getString(MEETINGDESCRIPTION_COLUMN));
            mr.setEventsList(getEvents(cursor.getString(MEETINGID_COLUMN), betStatementId));

            meetingsdata.add(mr);

            cursor.moveToNext();
        }
        cursor.close();
        return meetingsdata;
    }
/**
 * Inserts Meetings
 * @param meetingsdata
 */
    public void insertMeetings(ArrayList<MeetingsResponse> meetingsdata) {

        for (MeetingsResponse mr : meetingsdata) {

            ContentValues values = new ContentValues();
            values.put(KEY_MEETING_ID, mr.getMeetingId());
            values.put(KEY_STATEMENT_BET_ID, mr.getBetStatementId());
            values.put(KEY_MEETINGHEADER, mr.getMeetingHeader());
            values.put(KEY_MEETINGDESCRIPTION, mr.getMeetingDescription());

            db.insert(MEETINGS_TABLE, null, values);
        }

    }
/**
 * Deletes Meetings
 * @param betStatementId
 */
    public void deleteMeetings(String betStatementId) {

        ArrayList<MeetingsResponse> meetingsdata = getMeetings(betStatementId);
        for (MeetingsResponse meetingsResponse : meetingsdata) {
            String meetingid = meetingsResponse.getMeetingId();
            deleteEvents(meetingid, betStatementId);
        }

        db.delete(MEETINGS_TABLE, KEY_STATEMENT_BET_ID + "=?", new String[] {betStatementId});
    }

  /**
   * Get Events 
   * @param meetingid
   * @param betStatementId
   * @return ArrayList<EventsResponse>
   */
    public ArrayList<EventsResponse> getEvents(String meetingid, String betStatementId) {
        Cursor cursor = db.query(EVENTS_TABLE, new String[] {
                KEY_ID, KEY_STATEMENT_BET_ID, KEY_MEETING_ID, KEY_EVENT_ID, KEY_EVENTNAME,
                KEY_EVENTDATE,
                KEY_EVENT_EARLY_PRICE
        }, KEY_MEETING_ID + "=?  AND " + KEY_STATEMENT_BET_ID + "= ?", new String[] {
                meetingid, betStatementId
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
            eventsResponse.setMarketsList(getMarket(cursor.getString(EVENTID_COLUMN),
                    betStatementId));

            opponentsdata = getOpponents(cursor.getString(EVENTID_COLUMN));
            eventsResponse.setOpponentsList(opponentsdata);

            eventsdata.add(eventsResponse);

            cursor.moveToNext();
        }
        cursor.close();
        return eventsdata;
    }
/**
 * Insert Events
 * @param eventsdata
 */
    public void insertEvents(ArrayList<EventsResponse> eventsdata) {

        for (EventsResponse er : eventsdata) {
            ContentValues values = new ContentValues();
            values.put(KEY_EVENT_ID, er.getEventId());
            values.put(KEY_MEETING_ID, er.getMeetingId());
            values.put(KEY_STATEMENT_BET_ID, er.getBetStatementId());
            values.put(KEY_EVENTNAME, er.getEventName());
            values.put(KEY_EVENTDATE, er.getEventDate());
            values.put(KEY_EVENT_EARLY_PRICE, er.getEarlyPrice());
            values.put(KEY_STATEMENT_BET_ID, er.getBetStatementId());
            db.insert(EVENTS_TABLE, null, values);
        }
    }
/**
 * Delete Events
 * @param meetingId
 * @param betStatementId
 */
    public void deleteEvents(String meetingId, String betStatementId) {
        ArrayList<EventsResponse> eventsdata = getEvents(meetingId, betStatementId);
        for (EventsResponse er : eventsdata) {
            String eventId = er.getEventId();
            deleteOpponents(eventId);
            deleteMarkets(eventId, betStatementId);
        }
        db.delete(EVENTS_TABLE, KEY_MEETING_ID + "=? AND " + KEY_STATEMENT_BET_ID
                + "=?", new String[] {
                meetingId, betStatementId
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
 * Get Opponents
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
   * Get Markets
   * @param eventId
   * @param betStatementId
   * @return ArrayList<MarketsResponse>
   */
    public ArrayList<MarketsResponse> getMarket(String eventId,
            String betStatementId) {

        Cursor cursor = db.query(MARKET_TABLE, new String[] {
                KEY_ID, KEY_MARKET_ID, KEY_MARKET_TYPE_ID, KEY_MARKET_PLACE_TERMS_DESCRIPTION,
                KEY_MARKET_DESCRIPTION, KEY_MARKET_PERIOD_ID, KEY_MARKET_PERIOD_DESCRIPTION,
                KEY_STATEMENT_BET_ID,
                KEY_MEETING_ID, KEY_EVENT_ID
        },
                KEY_EVENT_ID + "=? AND " + KEY_STATEMENT_BET_ID + " =?", new String[] {
                        eventId, betStatementId
                }, null,
                null, null);
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
            mor.setBetStatementId(cursor.getString(cursor.getColumnIndex(KEY_STATEMENT_BET_ID)));
            mor.setOutcomeList(getOutcomes(betStatementId));

            marketsdata.add(mor);
            cursor.moveToNext();
        }
        cursor.close();
        return marketsdata;
    }
/**
 * Insert Markets
 * @param marketData
 */
    public void insertMarket(ArrayList<MarketsResponse> marketData) {

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
            values.put(KEY_STATEMENT_BET_ID, mr.getBetStatementId());
            values.put(KEY_MEETING_ID, mr.getMeetingId());
            values.put(KEY_EVENT_ID, mr.getEventId());

            db.insert(MARKET_TABLE, null, values);

        }
    }
/**
 * Delete Markets
 * @param eventId
 * @param betStatementId
 */
    public void deleteMarkets(String eventId, String betStatementId) {
        ArrayList<MarketsResponse> marketList = getMarket(eventId, betStatementId);
        for (MarketsResponse mr : marketList) {
            String marketId = mr.getId();
            deleteOutcomes(marketId);
        }
        db.delete(MARKET_TABLE, KEY_STATEMENT_BET_ID
                + "=? AND " + KEY_EVENT_ID + "=?", new String[] {betStatementId,eventId});
    }

    /**
     * Gets Outcomes
     * @param betStatementId
     * @return ArrayList<OutcomesResponse>
     */
    public ArrayList<OutcomesResponse> getOutcomes(String betStatementId) {

        Cursor cursor = db.query(OUTCOMES_TABLE, new String[] {
                KEY_ID, KEY_OUTCOME_ID, KEY_OUTCOME_DESCRIPTION, KEY_OUTCOME_PRICE_ID,
                KEY_OUTCOME_DECIMAL_PRICE, KEY_OUTCOME_STARTING_PRICE, KEY_OUTCOME_FORMATTED_PRICE,
                KEY_PREVIOUS_PRICE_ID, KEY_MARKET_ID, KEY_STATEMENT_BET_ID
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
            or.setBetStatementId(betStatementId);
            marketsdata.add(or);
            cursor.moveToNext();
        }
        cursor.close();
        return marketsdata;
    }
/**
 * Insert Outcomes
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
            values.put(KEY_STATEMENT_BET_ID, or.getBetStatementId());
            db.insert(OUTCOMES_TABLE, null, values);
        }
    }
/**
 * delete Outcomes
 * @param marketId
 */
    public void deleteOutcomes(String marketId) {
        ArrayList<OutcomesResponse> orList = getOutcomes(marketId);
        for (OutcomesResponse or : orList) {
            String outcomeId = or.getId();
            deletePreviousPrice(outcomeId);
        }
        db.delete(OUTCOMES_TABLE, KEY_MARKET_ID + "=?", new String[] {marketId});
    }

    /**
     * Gets Previous Price
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
 * Insert Previous Price
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
 * Deletes previous price
 * @param outcomeId
 */
    public void deletePreviousPrice(String outcomeId) {
        db.delete(PREVIOUS_PRICES_TABLE, KEY_OUTCOME_ID + "=?", new String[] {outcomeId});
    }
/**
 * Checks if there is a bet that will be shown in the statement Bet
 * @param betId
 * @return boolean
 */
    public boolean hasBetId(String betId) {
        SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM " + BETS_TABLE
                + " WHERE " + KEY_STATEMENT_BET_ID + "=" + betId + ";");
        long result = statement.simpleQueryForLong();
        statement.close();
        return result > 0;
    }

}
