package mobi.victorchandler.preferences;
/**
 * Class for Account Preference
 * @author riveram
 *
 */
public class AccountPreferences extends BasePreferences {

    private static final String ACCOUNT_NUMBER = "account_number";
    private static final String FIRST_NAME= "first_name";
    private static final String LAST_NAME ="last_name";
    private static final String BALANCE = "balance";
    private static final String AVAILABLE_BALANCE="available_balance";
    private static final String PROMOTIONAL_BALANCE="promotional_balance";

    private static final String DEFAULT_ACCOUNT_NUMBER = "";
    private static final String DEFAULT_FIRST_NAME = "";
    private static final String DEFAULT_LAST_NAME = "";
    private static final String DEFAULT_BALANCE = "";
    private static final String DEFAULT_AVAILABLE_BALANCE = "";
    private static final String DEFAULT_PROMOTIONAL_BALANCE = "";
 

    public static String getAccountNumber() {
        String accountNumber = settings.getString(ACCOUNT_NUMBER, DEFAULT_ACCOUNT_NUMBER);
        if (accountNumber == null || accountNumber.equals("")) {
            return "";
        }
        return accountNumber;
    }

    public static void setAccountNumber(String accountNumber) {

        if (accountNumber == null || accountNumber.equals("")) {
            accountNumber = "";
        }
        editor.putString(ACCOUNT_NUMBER, accountNumber);
    }
    
    
    //FirstName
    public static String getFirstName() {
        String firstName = settings.getString(FIRST_NAME, DEFAULT_FIRST_NAME);
        if (firstName == null || firstName.equals("")) {
            return "";
        }
        return firstName;
    }

    public static void setFirstName(String firstName) {

        if (firstName == null || firstName.equals("")) {
            firstName = "";
        }
        editor.putString(FIRST_NAME, firstName);
    }
    //LastName
    public static String getLastName() {
        String lastName = settings.getString(LAST_NAME, DEFAULT_LAST_NAME);
        if (lastName == null || lastName.equals("")) {
            return "";
        }
        return lastName;
    }

    public static void setLastName(String lastName) {

        if (lastName == null || lastName.equals("")) {
            lastName = "";
        }
        editor.putString(LAST_NAME, lastName);
    }
    //Balance
    public static String getBalance() {
        String balance = settings.getString(BALANCE, DEFAULT_BALANCE);
        if (balance == null || balance.equals("")) {
            return "";
        }
        return balance;
    }

    public static void setBalance(String balance) {

        if (balance == null || balance.equals("")) {
            balance = "";
        }
        editor.putString(BALANCE, balance);
    }
    //Available Balance
    public static String getAvailableBalance() {
        String availableBalance = settings.getString(AVAILABLE_BALANCE, DEFAULT_AVAILABLE_BALANCE);
        if (availableBalance == null || availableBalance.equals("")) {
            return "";
        }
        return availableBalance;
    }

    public static void setAvailableBalance(String availableBalance) {

        if (availableBalance == null || availableBalance.equals("")) {
            availableBalance = "";
        }
        editor.putString(AVAILABLE_BALANCE, availableBalance);
    }
    //Promotional Balance
    public static String getPromotionalBalance() {
        String promotionalBalance = settings.getString(PROMOTIONAL_BALANCE, DEFAULT_PROMOTIONAL_BALANCE);
        if (promotionalBalance == null || promotionalBalance.equals("")) {
            return "";
        }
        return promotionalBalance;
    }

    public static void setPromotionalBalance(String promotionalBalance) {

        if (promotionalBalance == null || promotionalBalance.equals("")) {
            promotionalBalance = "";
        }
        editor.putString(PROMOTIONAL_BALANCE, promotionalBalance);
    }
    
    
}
