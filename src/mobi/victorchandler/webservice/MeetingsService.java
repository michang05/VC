
package mobi.victorchandler.webservice;

import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.parser.MeetingsParser;
import mobi.victorchandler.response.MeetingsResponse;
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
 * Class that connects to Meetings Service
 * @author riveram
 *
 */
public class MeetingsService implements WebServiceExecutor {

    private String meetingsUrl;
    private Activity act;
    private ArrayList<MeetingsResponse> meetingsdata;
    private String sportId;
    private MeetingsParser meetingsParser;
    private HttpURLConnection con;

    public MeetingsService(Activity act, String sportId) {

        this.act = act;
        this.sportId = sportId;

        meetingsUrl = BASE_URL + "/sport/" + sportId + "/meetings";

    }

    @Override
    public void loadFromDb() {
        SportsBook db = new SportsBook(act);
        db.open();
        meetingsdata = db.getMeetings(sportId);
        db.close();
    }

    @Override
    public boolean executeRequest() {

        try {
            System.setProperty("http.keepAlive", "false");
            URL url = new URL(meetingsUrl);
            con = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(con.getInputStream());
            String result = DataHelper.convertStreamToString(in);
            in.close();

            meetingsParser = new MeetingsParser(result, act, sportId);
            meetingsdata = meetingsParser.parseJsonResponse();

            if (meetingsdata == null) {
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

    public ArrayList<MeetingsResponse> getMeetingsdata() {
        return meetingsdata;
    }

}
