
package mobi.victorchandler.webservice;

import mobi.victorchandler.parser.VerifyAccountAvailabilityParser;
import mobi.victorchandler.util.DataHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Class that connects to VerifyAccountAvailability Service
 * @author riveram
 *
 */
public class VerifyAccountAvailabilityService implements WebServiceExecutor {

    private boolean available;
    private VerifyAccountAvailabilityParser accountParser;
    private EditText txtUsername;

    public VerifyAccountAvailabilityService(EditText txtUsername) {
        this.txtUsername = txtUsername;
    }

    @Override
    public void loadFromDb() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean executeRequest() {

        try {

            HttpPost post = new HttpPost(CHECK_ACCOUNT_AVAILABILITY);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs
                    .add(new BasicNameValuePair("username", txtUsername.getText().toString()));
            // nameValuePairs.add(new BasicNameValuePair("password", "abc123"));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = new DefaultHttpClient().execute(post);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = DataHelper.convertStreamToString(instream);
                instream.close();
                Log.d("-----", result);
                accountParser = new VerifyAccountAvailabilityParser(result);
                available = accountParser.isUsernameAvailable();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean isAvailable() {
        return available;
    }

}
