
package mobi.victorchandler.webservice;

import mobi.victorchandler.parser.CurrencyListParser;
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
 * Class for connecting to CurrencyList Service
 * @author riveram
 *
 */
public class CurrencyListService implements WebServiceExecutor {

    private CurrencyListParser listParser;
    private HashMap<Integer, String> currencyList;

    @Override
    public void loadFromDb() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean executeRequest() {
        try {
            HttpGet get = new HttpGet(LIST_CURRENCIES);
            HttpResponse response = new DefaultHttpClient().execute(get);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = DataHelper.convertStreamToString(instream);
                instream.close();

                if (result.trim().equals(""))
                    return false;
                
                // Parse JSON Result
                listParser = new CurrencyListParser(result);
                currencyList = listParser.getCurrencyList();
                if (currencyList == null) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public HashMap<Integer, String> getCurrencyList() {
        return currencyList;
    }
}
