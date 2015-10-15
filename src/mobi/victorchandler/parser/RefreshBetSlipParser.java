
package mobi.victorchandler.parser;

import mobi.victorchandler.model.Multiple;
import mobi.victorchandler.model.Single;
import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.MeetingsResponse;
import mobi.victorchandler.response.OpponentResponse;
import mobi.victorchandler.response.OutcomesResponse;
import mobi.victorchandler.response.PreviousPriceResponse;
import mobi.victorchandler.response.RefreshBetSlipResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * Class that parses Refresh Betslip service
 * @author riveram
 *
 */
public class RefreshBetSlipParser extends BaseVcParser {

    private String mResult;
    private RefreshBetSlipResponse refreshBetSlipResponse;
    private ArrayList<RefreshBetSlipResponse> refBetSlipData;

    private OutcomesResponse outcomeResp;
    private PreviousPriceResponse prevPriceResp;
    private MarketsResponse marketResp;
    private EventsResponse eventsResp;
    private MeetingsResponse meetingResp;

    private ArrayList<PreviousPriceResponse> listPrevPrice;
    private ArrayList<OpponentResponse> listOpponents;

    public RefreshBetSlipParser(String result) {
        this.mResult = result;
    }

    @Override
    public ArrayList<RefreshBetSlipResponse> parseJsonResponse() {

        refreshBetSlipResponse = new RefreshBetSlipResponse();
        refBetSlipData = new ArrayList<RefreshBetSlipResponse>();

        ArrayList<Single> singlesList = null;
        listPrevPrice = new ArrayList<PreviousPriceResponse>();

        ArrayList<Multiple> resultMultiples = null;

        try {
            JSONObject json = new JSONObject(mResult);
            JSONObject betslip = json.getJSONObject(BETSLIP);

            if (betslip.length() != 0) {

                JSONArray multiples = betslip.getJSONArray(SINGLES);

                singlesList = new ArrayList<Single>(multiples.length());

                Single single = null;

                for (int i = 0; i < multiples.length(); i++) {

                    single = new Single();

                    // Outcome
                    JSONObject outcome = multiples.getJSONObject(i).getJSONObject(OUTCOME);
                    outcomeResp = new OutcomesResponse();
                    outcomeResp.setId(outcome.getString(ID));
                    outcomeResp.setDescription(outcome.getString(DESCRIPTION));

                    JSONObject price = outcome.getJSONObject(PRICE);
                    outcomeResp.setPriceId(price.getString(ID));
                    outcomeResp.setPriceDecimal(price.getString(DECIMAL));
                    outcomeResp.setPriceFormatted(price.getString(FORMATTED));
                    outcomeResp.setPriceStarting(price.getString(STARTING_PRICE));
                    prevPriceResp = new PreviousPriceResponse();
                    listPrevPrice.add(prevPriceResp);
                    outcomeResp.setPrevPriceList(listPrevPrice);

                    single.setOutcome(outcomeResp);

                    // Event
                    JSONObject event = multiples.getJSONObject(i).getJSONObject(EVENT);
                    eventsResp = new EventsResponse();
                    eventsResp.setEventId(event.getString(ID));
                    eventsResp.setEventName(event.getString(DESCRIPTION));
                    eventsResp.setEventDate(event.getString(DATE));
                    listOpponents = new ArrayList<OpponentResponse>();
                    eventsResp.setOpponentsList(listOpponents);
                    single.setEvent(eventsResp);

                    // Meeting
                    JSONObject meeting = multiples.getJSONObject(i).getJSONObject(MEETING);
                    meetingResp = new MeetingsResponse();
                    meetingResp.setMeetingId(meeting.getString(ID));
                    meetingResp.setMeetingHeader(meeting.getString(HEADER));
                    meetingResp.setMeetingDescription(meeting.getString(DESCRIPTION));
                    single.setMeeting(meetingResp);
                    // Market
                    JSONObject market = multiples.getJSONObject(i).getJSONObject(MARKET);
                    marketResp = new MarketsResponse();
                    marketResp.setId(ID);
                    marketResp.setTypeId(market.getString(TYPE_ID));
                    marketResp.setDescription(market.getString(DESCRIPTION));
                    marketResp.setEachWay(market.getString(EACH_WAY));

                    JSONObject period = market.getJSONObject(PERIOD);
                    marketResp.setPeriodId(period.getString(ID));
                    marketResp.setPeriodDescription(period.getString(DESCRIPTION));
                    single.setMarket(marketResp);

                    singlesList.add(single);
                }

                multiples = betslip.getJSONArray(MULTIPLES);

                Multiple multiple = null;

                resultMultiples = new ArrayList<Multiple>(multiples.length());
                for (int i = 0; i < multiples.length(); i++) {

                    multiple = new Multiple();

                    JSONObject object = multiples.getJSONObject(i);
                    multiple.setDescription(object.getString(DESCRIPTION));
                    multiple.setMultiplicity(object.getInt(MULTIPLICITY));

                    JSONObject ewBetType = object.getJSONObject(EACH_WAY_BET_TYPE);
                    multiple.setEachWayEnabled(ewBetType.getBoolean(ENABLED));
                    multiple.setEwfactor(ewBetType.getDouble(MULTIPLICATION_FACTOR));
                    multiple.setEachWayBetTypeId(ewBetType.getInt(BET_TYPE_ID));

                    JSONObject winBetType = object.getJSONObject(WIN_BET_TYPE);
                    multiple.setWinEnabled(ewBetType.getBoolean(ENABLED));
                    multiple.setWinfactor(winBetType.getDouble(MULTIPLICATION_FACTOR));
                    multiple.setWinid(winBetType.getInt(BET_TYPE_ID));

                    resultMultiples.add(multiple);
                }

                refreshBetSlipResponse.setSingles(singlesList);
                refreshBetSlipResponse.setMultiples(resultMultiples);
                refBetSlipData.add(refreshBetSlipResponse);
            } else {
                refBetSlipData = null;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return refBetSlipData;

    }

}
