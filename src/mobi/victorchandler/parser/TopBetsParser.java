
package mobi.victorchandler.parser;

import mobi.victorchandler.database.TopBetsDb;
import mobi.victorchandler.response.TopBetsResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.util.ArrayList;
/**
 * Class that parses Top Bets service
 * @author riveram
 *
 */
public class TopBetsParser extends BaseVcParser {

    private String mResult;
    private ArrayList<TopBetsResponse> betsdata;
    private Context ctx;
    private TopBetsResponse mTopeBets;

    public TopBetsParser(String result, Context ctx) {
        
        this.mResult = result;
        this.ctx = ctx;
        
    }

    @Override
    public ArrayList<TopBetsResponse> parseJsonResponse() {
        
        betsdata = new ArrayList<TopBetsResponse>();
        
        try {

            JSONObject json = new JSONObject(mResult);
            JSONArray singles = json.getJSONArray(SINGLES);

            for (int i = 0; i < singles.length(); i++) {

                JSONObject single = singles.getJSONObject(i);
                mTopeBets = new TopBetsResponse();

                JSONObject market = single.getJSONObject(MARKET);
                JSONObject outcome = single.getJSONObject(OUTCOME);
                mTopeBets.setSportName(single.getJSONObject(SPORT).getString(DESCRIPTION));
                mTopeBets.setMarketName(market.getString(DESCRIPTION));
                mTopeBets.setMarketPeriod(market.getJSONObject(PERIOD).getString(DESCRIPTION));
                mTopeBets.setMeetingName(single.getJSONObject(MEETING).getString(DESCRIPTION));
                mTopeBets.setEventName(single.getJSONObject(EVENT).getString(DESCRIPTION));
                mTopeBets.setOutcomeName(outcome.getString(DESCRIPTION));
                mTopeBets.setOutcomeFormattedPrice(outcome.getJSONObject(PRICE)
                        .getString(FORMATTED));
                mTopeBets.setOutcomePrice(outcome.getJSONObject(PRICE).getString(DECIMAL));
                mTopeBets.setOutcomePriceId(outcome.getJSONObject(PRICE)
                        .getString(ID));
                mTopeBets.setOutcomeId(outcome.getString(ID));
                // TODO get proper value from nodejs layer
                mTopeBets.setMarketEw("false");

                betsdata.add(mTopeBets);

            }

            TopBetsDb db = new TopBetsDb(ctx);
            db.deleteBets();
            db.insertBets(betsdata);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return betsdata;

    }

}
