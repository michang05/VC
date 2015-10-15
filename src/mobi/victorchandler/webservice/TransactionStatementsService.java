
package mobi.victorchandler.webservice;

import mobi.victorchandler.database.StatementsDb;
import mobi.victorchandler.parser.TransactionStatementsParser;
import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.response.TransactionStatementResponse;
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
 * Class that connects to TransactionStatements Service
 * @author riveram
 *
 */
public class TransactionStatementsService implements WebServiceExecutor {

    private TransactionStatementsParser parser;
    private Activity statements;
    private ArrayList<TransactionStatementResponse> alTransactions;
    private HttpURLConnection con;

    public TransactionStatementsService(Activity statements) {
        LoginPreferences.load(statements);
        this.statements = statements;
    }

    @Override
    public void loadFromDb() {
        StatementsDb db = new StatementsDb(statements);
        db.open();
        alTransactions = db.getTransactions();
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
            URL url = new URL(STATEMENTS_TRANSACTIONS);
            con = (HttpURLConnection) url.openConnection();
            con.addRequestProperty("Authorization", "Basic " + LoginPreferences.getToken());

            InputStream in = new BufferedInputStream(con.getInputStream());
            String result = DataHelper.convertStreamToString(in);
            in.close();

            parser = new TransactionStatementsParser(statements, result);
            if (parser.parseJsonResponse() != null) {
                alTransactions = parser.parseJsonResponse();
            } else {
                return false;
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();

            return false;

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        } finally {
            con.disconnect();
        }

        return true;
    }

    public ArrayList<TransactionStatementResponse> getAlTransactions() {
        return alTransactions;
    }
}
