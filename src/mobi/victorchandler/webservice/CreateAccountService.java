
package mobi.victorchandler.webservice;

import mobi.victorchandler.parser.CreateAccountParser;
import mobi.victorchandler.util.DataHelper;

import android.app.Activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
/**
 * Class for connecting to CreateAccount Service
 * @author riveram
 *
 */
public class CreateAccountService implements WebServiceExecutor {

    private HashMap<String, String> map;
    private Activity act;
    private CreateAccountParser cap;
    private HttpURLConnection con;

    public CreateAccountService(HashMap<String, String> map, Activity act) {
        this.map = map;
        this.act = act;
    }

    @Override
    public void loadFromDb() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean executeRequest() {

        try {
            StringBuilder sb = new StringBuilder();
            String charset = "UTF-8";

            if (!map.isEmpty()) {
                int ctr = 0;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    ctr++;
                    sb.append("account[" + entry.getKey() + "]=");
                    sb.append(URLEncoder.encode(entry.getValue(), charset));

                    if (ctr != map.size()) {
                        sb.append("&");
                    }
                }
            }

            System.setProperty("http.keepAlive", "false");

            URL url = new URL(CREATE_ACCOUNT_SERVICE);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true); // uses POST

            OutputStream wr = new BufferedOutputStream(con
                    .getOutputStream());
            // this is were we're adding post data to the request
            wr.write(sb.toString().getBytes());
            wr.flush();

            InputStream is = new BufferedInputStream(con.getInputStream());
            String result = DataHelper.convertStreamToString(is);
            is.close();

            cap = new CreateAccountParser(result, act);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            con.disconnect();
        }

        return cap.parseCreateAccountResponse();
    }
}
