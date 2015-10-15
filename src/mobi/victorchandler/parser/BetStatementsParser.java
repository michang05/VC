
package mobi.victorchandler.parser;

import mobi.victorchandler.database.StatementsDb;
import mobi.victorchandler.response.BetStatementsResponse;
import mobi.victorchandler.response.EventsResponse;
import mobi.victorchandler.response.MarketsResponse;
import mobi.victorchandler.response.MeetingsResponse;
import mobi.victorchandler.response.OutcomesResponse;
import mobi.victorchandler.response.PreviousPriceResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Class for Parsing BetStatements
 * @author riveram
 *
 */
public class BetStatementsParser extends BaseVcParser {

    private String result;
    private ArrayList<BetStatementsResponse> alStatements;
    private ArrayList<MeetingsResponse> meetingsList;
    private ArrayList<EventsResponse> eventsList;
    private ArrayList<MarketsResponse> marketList;
    private ArrayList<OutcomesResponse> outcomeList;
    private ArrayList<PreviousPriceResponse> prevPriceList;
    private MeetingsResponse meetingsResponse;
    private EventsResponse eventsResponse;
    private MarketsResponse marketResponse;
    private OutcomesResponse outcomesResponse;
    private PreviousPriceResponse previousPriceResponse;
    private Activity context;
    private BetStatementsResponse bsr;

    public BetStatementsParser(Activity context, String result) {
        this.result = result;
        this.context = context;
    }
/**
 * Method that parses Betstatements responses.
 * @return
 */
    public boolean parseJson() {

        meetingsList = new ArrayList<MeetingsResponse>();
        eventsList = new ArrayList<EventsResponse>();
        marketList = new ArrayList<MarketsResponse>();
        outcomeList = new ArrayList<OutcomesResponse>();
        prevPriceList = new ArrayList<PreviousPriceResponse>();

        alStatements = new ArrayList<BetStatementsResponse>();

        try {
            JSONObject json = new JSONObject(result);
            JSONObject status = json.getJSONObject(STATUS);
            if (!status.getBoolean(SUCCESS)) {
                return false;
            }

            JSONArray statements = json.getJSONArray(STATEMENTS);
            if (statements.length() != 0) {
                for (int i = 0; i < statements.length(); i++) {

                    bsr = new BetStatementsResponse();
                    JSONObject statement = statements.getJSONObject(i);

                    if (!statement.getJSONObject(TYPE).getString(DESCRIPTION).equals(BET)) {
                        continue;
                    }
                    bsr.setId(statement.getString(ID));

                    bsr.setTypeId(statement.getJSONObject(TYPE).getString(ID));
                    bsr.setTypeDescription(statement.getJSONObject(TYPE).getString(DESCRIPTION));
                    bsr.setSubTypeId(statement.getJSONObject(SUB_TYPE).getString(ID));
                    bsr.setSubTypeDescription(statement.getJSONObject(SUB_TYPE)
                            .getString(DESCRIPTION));

                    bsr.setSettled(String.valueOf(statement.getBoolean(SETTLED)));
                    bsr.setDate(DateFormat.getDateTimeInstance()
                            .format(new Date(statement.getLong(DATE))));
                    bsr.setDescription(statement.getString(DESCRIPTION));

                    bsr.setDebit_decimal(statement.getJSONObject(DEBIT).getString(DECIMAL));
                    bsr.setDebit_formatted(statement.getJSONObject(DEBIT).getString(FORMATTED));

                    bsr.setCredit_decimal(statement.getJSONObject(CREDIT).getString(DECIMAL));
                    bsr.setCredit_formatted(statement.getJSONObject(CREDIT).getString(FORMATTED));

                    // Meetings
                    JSONArray meetings = statement.getJSONArray(MEETINGS);
                    for (int m = 0; m < meetings.length(); m++) {
                        JSONObject meeting = meetings.getJSONObject(m);
                        meetingsResponse = new MeetingsResponse();
                        meetingsResponse.setMeetingId(meeting.getString(ID));
                        meetingsResponse.setMeetingHeader(meeting.getString(HEADER));
                        meetingsResponse.setMeetingDescription(meeting.getString(DESCRIPTION));
                        meetingsResponse.setBetStatementId(statement.getString(ID));
                        bsr.setMeetingId(meeting.getString(ID));

                        // Events
                        JSONArray events = meeting.getJSONArray(EVENTS);
                        for (int n = 0; n < events.length(); n++) {
                            JSONObject event = events.getJSONObject(n);
                            eventsResponse = new EventsResponse();
                            eventsResponse.setEventId(event.getString(ID));
                            eventsResponse.setEventName(event.getString(DESCRIPTION));
                            eventsResponse.setEventDate(event.getString(DATE));
                            eventsResponse.setEarlyPrice(event.getString(EARLY_PRICE));
                            eventsResponse.setBetStatementId(statement.getString(ID));
                            eventsResponse.setMeetingId(bsr.getMeetingId());
                            eventsResponse.setOutcomeId(bsr.getOutcomeId());
                            bsr.setEventId(event.getString(ID));

                            // Markets
                            JSONArray markets = event.getJSONArray(MARKETS);
                            marketResponse = new MarketsResponse();
                            for (int z = 0; z < markets.length(); z++) {

                                // Outcomes
                                JSONObject outcomes = markets.getJSONObject(z);
                                JSONArray outcomeA = outcomes.getJSONArray(OUTCOMES);
                                for (int j = 0; j < outcomeA.length(); j++) {
                                    JSONObject outcome = outcomeA.getJSONObject(j);

                                    outcomesResponse = new OutcomesResponse();
                                    outcomesResponse.setId(outcome.getString(ID));
                                    bsr.setOutcomeId(outcome.getString(ID));
                                    outcomesResponse.setDescription(outcome.getString(DESCRIPTION));

                                    JSONObject price = outcome.getJSONObject(PRICE);
                                    outcomesResponse.setPriceId(price.getString(ID));
                                    outcomesResponse.setPriceDecimal(price.getString(DECIMAL));
                                    outcomesResponse.setPriceStarting(price
                                            .getString(STARTING_PRICE));
                                    outcomesResponse.setPriceFormatted(price.getString(FORMATTED)
                                            .length() > 0 ? price.getString(FORMATTED) : "SP");
                                    outcomesResponse.setMarketId(String.valueOf(j));
                                    outcomesResponse.setEventId(String.valueOf(j));
                                    outcomesResponse.setMeetingId(String.valueOf(j));
                                    outcomesResponse.setBetStatementId(statement.getString(ID));

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
                            eventsResponse.setMarketsList(marketList);
                            eventsList.add(eventsResponse);
                        }
                        meetingsResponse.setEventsList(eventsList);
                        meetingsList.add(meetingsResponse);
                    }

                    bsr.setMeetingsList(meetingsList);

                    alStatements.add(bsr);
                }
                StatementsDb db = new StatementsDb(context);
                db.deleteBets();
                db.open();
                db.insertMeetings(meetingsList);
                db.insertEvents(eventsList);
                db.insertMarket(marketList);
                db.insertOutcomes(outcomeList);
                db.insertPreviousPrice(prevPriceList);
                db.close();
                db.insertBets(alStatements);
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<BetStatementsResponse> getAlStatements() {
        return alStatements;
    }

    public void setAlStatements(ArrayList<BetStatementsResponse> alStatements) {
        this.alStatements = alStatements;
    }

    @Override
    public List<?> parseJsonResponse() {
        // TODO Auto-generated method stub
        return null;
    }

}
