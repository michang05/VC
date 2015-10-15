
package mobi.victorchandler.webservice;

import mobi.victorchandler.database.StatementsDb;
import mobi.victorchandler.parser.BetStatementsParser;
import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.response.BetStatementsResponse;
import mobi.victorchandler.util.DataHelper;

import android.app.Activity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Class for connecting to Bet Statement Service
 * @author riveram
 *
 */
public class BetStatementsService implements WebServiceExecutor {

    private BetStatementsParser sParser;
    private ArrayList<BetStatementsResponse> alStatements;
    private Activity statements;
    private HttpURLConnection con;

    // private CacheDB cacheDb;
    // private String mEtag;

    public BetStatementsService(Activity statements) {
        LoginPreferences.load(statements);
        this.statements = statements;
    }

    @Override
    public void loadFromDb() {
        StatementsDb db = new StatementsDb(statements);
        db.open();
        alStatements = db.getBets();
        db.close();
    }

    @Override
    public boolean executeRequest() {

        // cacheDb = new CacheDB(statements);
        //
        // if (!cacheDb.isUrlExpired(STATEMENTS_SERVICE)) {
        // loadFromDb();
        // return false;
        // }
        //
        // mEtag = cacheDb.getUrlEtag(STATEMENTS_SERVICE);
        // if (mEtag == null) {
        // cacheDb.insertUrl(STATEMENTS_SERVICE);
        // }

        try {
            System.setProperty("http.keepAlive", "false");
            URL url = new URL(STATEMENTS_ALL_BETS);
            con = (HttpURLConnection) url.openConnection();
            con.addRequestProperty("Authorization", "Basic " + LoginPreferences.getToken());

            InputStream in = new BufferedInputStream(con.getInputStream());
            String result = DataHelper.convertStreamToString(in);
            in.close();

            sParser = new BetStatementsParser(statements, result);
            if (sParser.parseJson()) {
                alStatements = sParser.getAlStatements();
            } else {
                return false;
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

            return false;

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }finally {
            con.disconnect();
        }

        // int code = response.getStatusLine().getStatusCode();
        // if (code == 304) {
        // cacheDb.updateUrlExpiration(STATEMENTS_SERVICE);
        // loadFromDb();
        // return false;
        // }
        //
        // Header[] headers = response.getHeaders("Etag");
        // cacheDb.updateUrlEtag(STATEMENTS_SERVICE, headers[0].getValue());

        return true;
    }

    public ArrayList<BetStatementsResponse> getAlStatements() {
        return alStatements;
    }

    public void setAlStatements(ArrayList<BetStatementsResponse> alStatements) {
        this.alStatements = alStatements;
    }

    public ArrayList<BetStatementsResponse> getOpenBets() {
        StatementsDb db = new StatementsDb(statements);
        db.open();
        alStatements = db.getOpenBets();
        db.close();
        return alStatements;
    }

    public ArrayList<BetStatementsResponse> getClosedBets() {
        StatementsDb db = new StatementsDb(statements);
        db.open();
        alStatements = db.getCloseBets();
        db.close();
        return alStatements;
    }

}
