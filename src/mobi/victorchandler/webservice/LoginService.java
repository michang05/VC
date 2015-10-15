
package mobi.victorchandler.webservice;

import mobi.victorchandler.parser.LoginParser;
import mobi.victorchandler.util.DataHelper;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Class that connects to Login Service
 * @author riveram
 *
 */
public class LoginService implements WebServiceExecutor {

    private Activity login;
    private boolean loginResult;
    private String username;
    private String password;
    private HttpURLConnection con;

    public LoginService(Activity login, String[] credentials) {
        this.login = login;
        this.username = credentials[0];
        this.password = credentials[1];
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
            // adding some data to send along with the request to the server
            sb.append("username=").append(URLEncoder.encode(username, charset));
            sb.append("&");
            sb.append("password=").append(URLEncoder.encode(password, charset));

            System.setProperty("http.keepAlive", "false");

            URL url = new URL(LOGIN_SERVICE);
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

            Log.d("LoginService", result);

            LoginParser loginParser = new LoginParser(result, login);
            loginResult = loginParser.parseLogin();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

        return loginResult;
    }

    public boolean isLoginResult() {
        return loginResult;
    }

    public void setLoginResult(boolean loginResult) {
        this.loginResult = loginResult;
    }

}
