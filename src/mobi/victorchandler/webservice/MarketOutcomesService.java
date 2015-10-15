
package mobi.victorchandler.webservice;

import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.parser.MarketOutcomesParser;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.util.DataHelper;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Class that connects to MarketOutcomes Service
 * @author riveram
 *
 */
public class MarketOutcomesService implements WebServiceExecutor {

    private String sportId;
    private String meetingId;
    private String eventId;
    // private String sportName;
    // private String meetingName;
    // private String eventName;
    private Context ctx;

    private ArrayList<MarketsResponse> marketoutcomesdata;

    private String marketOutcomesUrl;

    private MarketOutcomesParser marketOutcomesParser;
    private String[] market;
    private HttpURLConnection con;

    public MarketOutcomesService(Context ctx, String[] market) {

        this.ctx = ctx;

        this.market = market;

        sportId = market[0];
        meetingId = market[1];
        eventId = market[2];
        // sportName = market[3];
        // meetingName = market[4];
        // eventName = market[5];

        marketOutcomesUrl = BASE_URL + "/sport/" + sportId + "/meeting/" + meetingId + "/event/"
                + eventId
                + "/markets";
    }

    @Override
    public boolean executeRequest() {

        try {
            System.setProperty("http.keepAlive", "false");
            URL url = new URL(marketOutcomesUrl);
            con = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(con.getInputStream());
            String result = DataHelper.convertStreamToString(in);
            in.close();

            marketOutcomesParser = new MarketOutcomesParser(ctx, market, result);
            marketoutcomesdata = marketOutcomesParser.parseJsonResponse();

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

    @Override
    public void loadFromDb() {
        SportsBook db = new SportsBook(ctx);
        db.open();
        marketoutcomesdata = db.getMarket(eventId, meetingId, sportId);
        db.close();

    }

    public ArrayList<MarketsResponse> getMarketoutcomesdata() {
        return marketoutcomesdata;
    }

    public void setMarketoutcomesdata(ArrayList<MarketsResponse> marketoutcomesdata) {
        this.marketoutcomesdata = marketoutcomesdata;
    }

}
