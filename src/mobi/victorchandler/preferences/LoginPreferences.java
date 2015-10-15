
package mobi.victorchandler.preferences;

import android.util.Base64;
/**
 * Class for Login Preference
 * @author riveram
 *
 */
public class LoginPreferences extends BasePreferences {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";

    private static final String DEFAULT_USERNAME = "";
    private static final String DEFAULT_PASSWORD = "";
    private static final String DEFAULT_TOKEN = "";
//Username
    public static String getUsername() {
        String username = settings.getString(USERNAME, DEFAULT_USERNAME);
        if (username == null || username.equals("")) {
            return "";
        }
        return username;
    }

    public static void setUsername(String username) {

        if (username == null || username.equals("")) {
            username = "";
        }
        editor.putString(USERNAME, username);
    }
//Password
    public static String getPassword() {
        String password = settings.getString(PASSWORD, DEFAULT_PASSWORD);
        if (password == null || password.equals("")) {
            return "";
        }
        return password;
    }

    public static void setPassword(String password) {

        if (password == null || password.equals("")) {
            password = "";
        }
        editor.putString(PASSWORD, Base64.encode(password.getBytes(), Base64.DEFAULT).toString());
    }

    public static String getToken() {
        String token = settings.getString(TOKEN, DEFAULT_TOKEN);
        if (token == null || token.equals("")) {
            return "";
        }
        return token;
    }

    public static void setToken(String token) {

        if (token == null || token.equals("")) {
            token = "";
        }
        editor.putString(TOKEN, Base64.encodeToString(token.getBytes(), Base64.DEFAULT)
                .trim());
    }

}
