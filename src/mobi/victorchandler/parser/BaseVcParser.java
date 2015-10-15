
package mobi.victorchandler.parser;

/**
 * Base Class for Parsing JSON request
 * @author riveram
 *
 */
public abstract class BaseVcParser implements JsonParser {

    // Top Bets
    protected static final String MARKET = "market";
    protected static final String OUTCOME = "outcome";
    protected static final String SPORT = "sport";
    protected static final String DESCRIPTION = "description";
    protected static final String PERIOD = "period";
    protected static final String MEETING = "meeting";
    protected static final String EVENT = "event";
    protected static final String PRICE = "price";
    protected static final String FORMATTED = "formatted";
    protected static final String DECIMAL = "decimal";
    protected static final String ID = "id";
    protected static final String SINGLES = "singles";
    protected static final String MULTIPLES = "multiples";

    // NextEvents
    protected static final String UPCOMING = "upcoming";
    protected static final String DATE = "date";

    // SportsBook
    protected static final String SPORTS = "sports";
    protected static final String HAS_EVENTS = "hasEvents";

    // PlaceBet
    protected static final String SUCCESS = "success";
    protected static final String ERROR = "error";
    protected static final String STATUS = "status";
    protected static final String MULTIPLE_OUTCOMES = "multipleOutcomes";

    // Meeting
    protected static final String MEETINGS = "meetings";
    protected static final String HEADER = "header";

    // Events
    protected static final String EVENTS = "events";
    protected static final String EARLY_PRICE = "earlyPrice";
    protected static final String OPPONENTS = "opponents";

    // Market Outcomes
    protected static final String MARKETS = "markets";
    protected static final String TYPE_ID = "typeId";
    protected static final String EACH_WAY_BET_TYPE = "eachWayBetType";
    protected static final String WIN_BET_TYPE = "winBetType";

    protected static final String PLACE_TERMS = "placeTerms";
    protected static final String DEDUCTION = "deduction";
    protected static final String OUTCOMES = "outcomes";
    protected static final String STARTING_PRICE = "startingPrice";
    protected static final String PREVIOUS_PRICES = "previousPrices";

    protected static final String STATEMENTS = "statements";
    protected static final String TYPE = "type";
    protected static final String SUB_TYPE = "subType";
    protected static final String SETTLED = "settled";
    protected static final String DEBIT = "debit";
    protected static final String CREDIT = "credit";

    protected static final String MESSAGE = "message";
    protected static final String CURRENCIES = "currencies";
    protected static final String COUNTRIES = "countries";
    protected static final String SECURITY_QUESTIONS = "securityQuestions";
    protected static final String AVAILABLE = "available";
    protected static final String BET = "Bet";
    protected static final String BETSLIP = "betslip";
    protected static final String MULTIPLICITY = "multiplicity";
    protected static final String ENABLED = "enabled";
    protected static final String MULTIPLICATION_FACTOR = "multiplicationFactor";
    protected static final String BET_TYPE_ID = "betTypeId";
    protected static final String WIN = "win";
    protected static final String EACH_WAY = "eachWay";

    public BaseVcParser() {

    }

}
