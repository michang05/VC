
package mobi.victorchandler.webservice;

import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.parser.EventsParser;
import mobi.victorchandler.response.EventsResponse;
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
public class EventsService implements WebServiceExecutor {

    private Activity ctx;
    // private CacheDB cachedb;
    private String eventUrl;
    private ArrayList<EventsResponse> eventsdata;
    private String meetingId;
    private String sportId;
    private EventsParser eventsParser;
    private HttpURLConnection con;

    public EventsService(Activity act, String sportId, String meetingId) {

        this.ctx = act;
        this.sportId = sportId;
        this.meetingId = meetingId;

        eventUrl = BASE_URL + "/sport/" + sportId + "/meeting/" + meetingId + "/events";

    }

    @Override
    public void loadFromDb() {
        SportsBook db = new SportsBook(ctx);
        db.open();
        eventsdata = db.getEvents(meetingId, sportId);
        db.close();
    }

    @Override
    public boolean executeRequest() {

        // cachedb = new CacheDB(ctx);
        //
        // if (!cachedb.isUrlExpired(url)) {
        // loadFromDb();
        // return false;
        // }
        // String etag = cachedb.getUrlEtag(url);
        // if (etag == null)
        // cachedb.insertUrl(url);

        try {
            System.setProperty("http.keepAlive", "false");
            URL url = new URL(eventUrl);
            con = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(con.getInputStream());
            String result = DataHelper.convertStreamToString(in);
            in.close();

            eventsParser = new EventsParser(result, ctx, sportId, meetingId);
            eventsdata = eventsParser.parseJsonResponse();

        } catch (MalformedURLException e) {

            e.printStackTrace();

            return false;

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        } finally {
            con.disconnect();
        }

        // get.addHeader(new BasicHeader("If-None-Match", etag));
        // int code = response.getStatusLine().getStatusCode();
        // if (code == 304) {
        // cachedb.updateUrlExpiration(url);
        // loadFromDb();
        // return false;
        // }
        // Header[] headers = response.getHeaders("Etag");
        // cachedb.updateUrlEtag(url, headers[0].getValue());

        return true;
    }

    public ArrayList<EventsResponse> getEventsdata() {
        return eventsdata;
    }

    public void setEventsdata(ArrayList<EventsResponse> eventsdata) {
        this.eventsdata = eventsdata;
    }

}
