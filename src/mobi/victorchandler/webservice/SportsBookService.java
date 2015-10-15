
package mobi.victorchandler.webservice;

import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.parser.SportsBookParser;
import mobi.victorchandler.response.SportsBookResponse;
import mobi.victorchandler.util.DataHelper;

import org.apache.http.client.ClientProtocolException;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Class that connects to SportsBook Service
 * @author riveram
 *
 */
public class SportsBookService implements WebServiceExecutor {

    private Context ctx;
    // private CacheDB cachedb;
    private ArrayList<SportsBookResponse> sportsdata;
    private HttpURLConnection con;

    public SportsBookService(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void loadFromDb() {
        SportsBook db = new SportsBook(ctx);
        db.open();
        sportsdata = db.getSports();
        db.close();
    }

    @Override
    public boolean executeRequest() {

        // cachedb = new CacheDB(ctx);
        //
        // if (!cachedb.isUrlExpired(ALL_SPORTS_SERVICE)) {
        // loadFromDb();
        // return false;
        // }
        // String etag = cachedb.getUrlEtag(ALL_SPORTS_SERVICE);
        // if (etag == null)
        // cachedb.insertUrl(ALL_SPORTS_SERVICE);

        try {
            System.setProperty("http.keepAlive", "false");
            URL url = new URL(ALL_SPORTS_SERVICE);
            con = (HttpURLConnection) url.openConnection();
           
            InputStream in = new BufferedInputStream(con.getInputStream());
            String result = DataHelper.convertStreamToString(in);
            in.close();
            
            SportsBookParser sportsBookParser = new SportsBookParser(result, ctx);
            sportsdata = sportsBookParser.parseJsonResponse();

            // HttpGet get = new HttpGet(ALL_SPORTS_SERVICE);
            // get.addHeader(new BasicHeader("If-None-Match", etag));
            // HttpResponse response = new DefaultHttpClient().execute(get);
            // int code = response.getStatusLine().getStatusCode();
            // if (code == 304) {
            // cachedb.updateUrlExpiration(ALL_SPORTS_SERVICE);
            // loadFromDb();
            // return false;
            // }

            // HttpEntity entity = response.getEntity();
            // if (entity != null) {
            // InputStream instream = entity.getContent();
            // String result = DataHelper.convertStreamToString(instream);
            // instream.close();
            //
            // if (result.trim().equals(""))
            // return false;
            //
            // sportsBookParser = new SportsBookParser(result, ctx);
            // sportsdata = sportsBookParser.parseJsonResponse();
            // }

        } catch (ClientProtocolException e) {
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

    public ArrayList<SportsBookResponse> getSportsdata() {
        return sportsdata;
    }

    public void setSportsdata(ArrayList<SportsBookResponse> sportsdata) {
        this.sportsdata = sportsdata;
    }

}
