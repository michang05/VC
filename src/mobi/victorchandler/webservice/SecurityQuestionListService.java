
package mobi.victorchandler.webservice;

import mobi.victorchandler.parser.SecurityQuestionListParser;
import mobi.victorchandler.util.DataHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
/**
 * Class that connects to SecurityQuestionList Service
 * @author riveram
 *
 */
public class SecurityQuestionListService implements WebServiceExecutor {

    private SecurityQuestionListParser listParser;
    private HashMap<Integer, String> secQuestionList;

    @Override
    public void loadFromDb() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean executeRequest() {
        try {
            HttpGet get = new HttpGet(LIST_SECURITY_QUESTIONS);
            HttpResponse response = new DefaultHttpClient().execute(get);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = DataHelper.convertStreamToString(instream);
                instream.close();

                if (result.trim().equals(""))
                    return false;

                // Parse JSON Result
                listParser = new SecurityQuestionListParser(result);

                secQuestionList = listParser.getQuestionsList();
                if (secQuestionList == null) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public HashMap<Integer, String> getSecQuestionList() {
        return secQuestionList;
    }
}
