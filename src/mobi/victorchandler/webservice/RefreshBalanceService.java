
package mobi.victorchandler.webservice;

import mobi.victorchandler.parser.RefreshBalanceParser;
import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.util.DataHelper;

import android.app.Activity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Class that connects to RefreshBalance Service
 * @author riveram
 *
 */
public class RefreshBalanceService implements WebServiceExecutor {

    private Activity act;
    private RefreshBalanceParser parser;
    private HttpURLConnection con;

    public RefreshBalanceService(Activity act) {
        LoginPreferences.load(act);
        this.act = act;
    }

    @Override
    public void loadFromDb() {

    }

    @Override
    public boolean executeRequest() {

        try {

            System.setProperty("http.keepAlive", "false");

            URL url = new URL(REFRESH_BALANCE_SERVICE);
            con = (HttpURLConnection) url.openConnection();
            con.addRequestProperty("Authorization", "Basic " + LoginPreferences.getToken());

            InputStream in = new BufferedInputStream(con.getInputStream());
            String result = DataHelper.convertStreamToString(in);
            in.close();

            parser = new RefreshBalanceParser(result, act);

        } catch (MalformedURLException e) {

            e.printStackTrace();

            return false;

        } catch (IOException e) {

            e.printStackTrace();

            return false;
            
        }finally {
            con.disconnect();
        }

        return parser.parseRefreshBalance();

    }

}
