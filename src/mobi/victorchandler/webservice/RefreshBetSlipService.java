
package mobi.victorchandler.webservice;

import mobi.victorchandler.BetSlip;
import mobi.victorchandler.database.BetsDb;
import mobi.victorchandler.parser.RefreshBetSlipParser;
import mobi.victorchandler.response.RefreshBetSlipResponse;
import mobi.victorchandler.util.DataHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpProtocolParams;

import android.database.Cursor;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Class that connects to RefreshBetSlip Service
 * @author riveram
 *
 */
public class RefreshBetSlipService implements WebServiceExecutor {

    private BetSlip ctx;
    private RefreshBetSlipParser refreshBetSlipParser;
    private ArrayList<RefreshBetSlipResponse> resultbets;
    private ArrayList<String> priceids;
    private ArrayList<String> outcomeids;
    private BetsDb db;
    private boolean serviceError;

    public RefreshBetSlipService(BetSlip ctx) {
        this.ctx = ctx;

    }

    @Override
    public void loadFromDb() {

        db = new BetsDb(ctx);
        db.open();
        Cursor cursor = db.getCursor();

        priceids = new ArrayList<String>(cursor.getCount());
        outcomeids = new ArrayList<String>(cursor.getCount());

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            priceids.add(cursor.getString(BetsDb.OUTCOMEPRICEID_COLUMN));
            outcomeids.add(cursor.getString(BetsDb.OUTCOMEID_COLUMN));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
    }

    @Override
    public boolean executeRequest() {

        loadFromDb();

        resultbets = new ArrayList<RefreshBetSlipResponse>();

        if (outcomeids.size() == 0) {
            return false;
        }

        try {
            HttpPost post = new HttpPost(REFRESH_BETSLIP);
            HttpProtocolParams.setUseExpectContinue(post.getParams(), false);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            for (int i = 0; i < priceids.size(); i++) {
                nameValuePairs.add(new BasicNameValuePair("singles[" + i +
                        "][outcomeId]", outcomeids.get(i)));
                nameValuePairs.add(new BasicNameValuePair("singles[" + i +
                        "][priceId]", priceids.get(i)));
            }
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = new DefaultHttpClient().execute(post);
            
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                
                InputStream instream = entity.getContent();
                
                String result = DataHelper.convertStreamToString(instream);
                Log.d("RefreshBetslip====", result);
                instream.close();

                refreshBetSlipParser = new RefreshBetSlipParser(result);
                resultbets = refreshBetSlipParser.parseJsonResponse();
                if(resultbets.size()==0) {
                    return false;
                }
            }

        } catch (ClientProtocolException e) {
           serviceError = true;
            return false;
        } catch (IOException e) {
           serviceError = true;
            return false;
        }
        return true;

    }

    public ArrayList<RefreshBetSlipResponse> getBetSlipResponses() {
        return resultbets;
    }

    public ArrayList<String> getPriceids() {
        return priceids;
    }

    public void setPriceids(ArrayList<String> priceids) {
        this.priceids = priceids;
    }

    public boolean isServiceError() {
        return serviceError;
    }

    public void setServiceError(boolean serviceError) {
        this.serviceError = serviceError;
    }

    
    
}
