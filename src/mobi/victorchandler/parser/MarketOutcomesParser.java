
package mobi.victorchandler.parser;

import mobi.victorchandler.database.SportsBook;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.OutcomesResponse;
import mobi.victorchandler.response.PreviousPriceResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.util.ArrayList;
/**
 * Class that Parses Market Outcomes
 * @author riveram
 *
 */
public class MarketOutcomesParser extends BaseVcParser {

    private String sportId;
    private String meetingId;
    private String eventId;
//    private String sportName;
//    private String meetingName;
//    private String eventName;

    private String mResult;
    private ArrayList<MarketsResponse> marketList;
    private ArrayList<OutcomesResponse> outcomeList;
    private ArrayList<PreviousPriceResponse> prevPriceList;
    private Context ctx;
    private MarketsResponse marketResponse;
    private OutcomesResponse outcomesResponse;
    private PreviousPriceResponse previousPriceResponse;

    public MarketOutcomesParser(Context ctx, String[] market, String result) {

        this.ctx = ctx;
        mResult = result;

        sportId = market[0];
        meetingId = market[1];
        eventId = market[2];
//        sportName = market[3];
//        meetingName = market[4];
//        eventName = market[5];
    }

    @Override
    public ArrayList<MarketsResponse> parseJsonResponse() {

        marketList = new ArrayList<MarketsResponse>();
        outcomeList = new ArrayList<OutcomesResponse>();
        prevPriceList = new ArrayList<PreviousPriceResponse>();

        try {

            JSONObject json = new JSONObject(mResult);

            JSONArray markets = json.getJSONArray(MARKETS);
            for (int i = 0; i < markets.length(); i++) {
                JSONObject market = markets.getJSONObject(i);

                marketResponse = new MarketsResponse();

                marketResponse.setSportId(sportId);
                marketResponse.setMeetingId(meetingId);
                marketResponse.setEventId(eventId);

                String marketid = market.getString(ID);
                marketResponse.setId(marketid);
                String typeId = market.getString(TYPE_ID);
                marketResponse.setTypeId(typeId);
                String ew = market.getString(EACH_WAY);
                marketResponse.setEachWay(ew);

                JSONObject placeTerms = market.getJSONObject(PLACE_TERMS);
                String ptDeduction = placeTerms.getString(DEDUCTION);
                marketResponse.setPlaceTermsDeduction(ptDeduction);
                String ptDescription = placeTerms.getString(DESCRIPTION);
                marketResponse.setPlaceTermsDescription(ptDescription);
                String description = market.getString(DESCRIPTION);
                marketResponse.setDescription(description);

                JSONObject period = market.getJSONObject(PERIOD);
                String periodId = period.getString(ID);
                String periodDescription = period.getString(DESCRIPTION);
                marketResponse.setPeriodId(periodId);
                marketResponse.setPeriodDescription(periodDescription);
            
                JSONArray outcomes = market.getJSONArray(OUTCOMES);
                for (int j = 0; j < outcomes.length(); j++) {
                    JSONObject outcome = outcomes.getJSONObject(j);
                    outcomesResponse = new OutcomesResponse();
                    outcomesResponse.setId(outcome.getString(ID));
                    outcomesResponse.setDescription(outcome.getString(DESCRIPTION));

                    JSONObject price = outcome.getJSONObject(PRICE);
                    outcomesResponse.setPriceId(price.getString(ID));
                    outcomesResponse.setPriceDecimal(price.getString(DECIMAL));
                    outcomesResponse.setPriceStarting(price.getString(STARTING_PRICE));
                    outcomesResponse.setPriceFormatted(price.getString(FORMATTED));
                    outcomesResponse.setMarketId(market.getString(ID));
                    outcomesResponse.setEventId(eventId);
                    outcomesResponse.setMeetingId(meetingId);
                    outcomesResponse.setSportId(sportId);

                    JSONArray prevPrices = price.getJSONArray(PREVIOUS_PRICES);
                    for (int x = 0; x < prevPrices.length(); x++) {
                        JSONObject p = prevPrices.getJSONObject(x);
                        previousPriceResponse = new PreviousPriceResponse();

                        previousPriceResponse.setId(p.getString(ID));
                        previousPriceResponse.setDecimal(p.getString(DECIMAL));
                        previousPriceResponse.setFormatted(p.getString(FORMATTED));
                        previousPriceResponse.setOutcomeId(outcome.getString(ID));
                        prevPriceList.add(previousPriceResponse);
                    }
                    outcomesResponse.setPrevPriceList(prevPriceList);
                    outcomeList.add(outcomesResponse);

                }
                marketResponse.setOutcomeList(outcomeList);
                marketList.add(marketResponse);
            }
            SportsBook db = new SportsBook(ctx);
            db.open();

            db.deleteMarkets(eventId, meetingId, sportId);
            db.insertPreviousPrice(prevPriceList);
            db.insertOutcomes(outcomeList);
            db.insertMarket(marketList, eventId, meetingId, sportId);

            db.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return marketList;
    }

}
