
package mobi.victorchandler.webservice;

import mobi.victorchandler.database.TopBetsDb;
import mobi.victorchandler.parser.TopBetsParser;
import mobi.victorchandler.response.TopBetsResponse;
import mobi.victorchandler.util.DataHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/**
 * Class that connects to TopBets Service
 * @author riveram
 *
 */
public class TopBetsService implements WebServiceExecutor {

    private Context ctx;
//    private CacheDB cacheDb;
//    private String mEtag;
    private TopBetsParser mTopBetsParser;
    private ArrayList<TopBetsResponse> betsdata;
  

    public TopBetsService(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean executeRequest() {
        
//        cacheDb = new CacheDB(ctx);
//
//        if (!cacheDb.isUrlExpired(TOP_BETS_SERVICE)) {
//            loadFromDb();
//            return false;
//        }
//
//        mEtag = cacheDb.getUrlEtag(TOP_BETS_SERVICE);
//        if (mEtag == null) {
//            cacheDb.insertUrl(TOP_BETS_SERVICE);
//        }

        try {
            HttpGet get = new HttpGet(TOP_BETS_SERVICE);
//            get.addHeader(new BasicHeader("If-None-Match", mEtag));
            HttpResponse response = new DefaultHttpClient().execute(get);
         
//            int code = response.getStatusLine().getStatusCode();
//            if (code == 304) {
//                cacheDb.updateUrlExpiration(TOP_BETS_SERVICE);
//                loadFromDb();
//                return false;
//            }
//            
//            Header[] headers = response.getHeaders("Etag");
//            cacheDb.updateUrlEtag(TOP_BETS_SERVICE, headers[0].getValue());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = DataHelper.convertStreamToString(instream);
                instream.close();

                if (result.trim().equals(""))
                    return false;

                // Parse JSON Result
                mTopBetsParser = new TopBetsParser(result,ctx);
                betsdata = mTopBetsParser.parseJsonResponse();
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
        TopBetsDb db = new TopBetsDb(ctx);
        db.open();
        betsdata = db.getBets();
        db.close();
       
    }

    public ArrayList<TopBetsResponse> getBetsdata() {
        return betsdata;
    }

}
