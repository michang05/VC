
package mobi.victorchandler.webservice;

import mobi.victorchandler.database.CacheDB;
import mobi.victorchandler.database.NextEventsDb;
import mobi.victorchandler.parser.NextEventsParser;
import mobi.victorchandler.response.NextEventsResponse;
import mobi.victorchandler.util.DataHelper;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/**
 * Class that connects to NextEvents Service
 * @author riveram
 *
 */
public class NextEventsService implements WebServiceExecutor {

    private Context ctx;
    private CacheDB cacheDb;
    private String mEtag;
    private NextEventsParser mNextEventsParser;
    private ArrayList<NextEventsResponse> upcomingdata;

    public NextEventsService(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean executeRequest() {

        cacheDb = new CacheDB(ctx);

        if (!cacheDb.isUrlExpired(NEXT_EVENTS_SERVICE)) {
            loadFromDb();
            return false;
        }

        mEtag = cacheDb.getUrlEtag(NEXT_EVENTS_SERVICE);
        if (mEtag == null) {
            cacheDb.insertUrl(NEXT_EVENTS_SERVICE);
        }

        try {
            HttpGet get = new HttpGet(NEXT_EVENTS_SERVICE);
            get.addHeader(new BasicHeader("If-None-Match", mEtag));
            HttpResponse response = new DefaultHttpClient().execute(get);
            int code = response.getStatusLine().getStatusCode();
            if (code == 304) {
                cacheDb.updateUrlExpiration(NEXT_EVENTS_SERVICE);
                loadFromDb();
                return false;
            }
            Header[] headers = response.getHeaders("Etag");
            cacheDb.updateUrlEtag(NEXT_EVENTS_SERVICE, headers[0].getValue());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = DataHelper.convertStreamToString(instream);
                instream.close();

                if (result.trim().equals(""))
                    return false;

                // Parse JSON Result
                mNextEventsParser = new NextEventsParser(result, ctx);
                upcomingdata = mNextEventsParser.parseJsonResponse();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

    @Override
    public void loadFromDb() {
        NextEventsDb  db = new NextEventsDb(ctx);
        upcomingdata = db.getEvents();
        
    }

    public ArrayList<NextEventsResponse> getNextEventsData() {
        return upcomingdata;
    }

}
