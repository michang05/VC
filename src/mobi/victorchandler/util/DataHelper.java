package mobi.victorchandler.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * contains methods for:<br>
 *  <br>* converting stream to string.
 *  <br>* formatting primitive type long to formatted string (time now).
 *  <br>* formatting primitive type long to formatted string (time from now).
 *  <br>* validating String e-mails.
 * @author riveram
 *
 */
public class DataHelper {

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * Formats long type to formatted string, getting the time now
	 * @param time
	 * @return
	 */
	public static String getFormattedTimeNow(long time) {
	    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
	  
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(time);
	    return formatter.format(cal.getTime());
	}

	/**
	 * Formats long type to formatted string, getting time from now
	 * @param time
	 * @return
	 */
	public static String timeFromNow(long time) {
		long diff = time - System.currentTimeMillis();
		diff /= 60000;
		return diff + " minutes";
	}
	
	/**
     * validate String e-mails
     * 
     * @param email
     * @return boolean
     */
    public boolean validateEmail(String email) {
        // Input the string for validation
        // String email = "xyz@.com";
        // Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        // Match the given string with the pattern
        Matcher m = p.matcher(email);

        // check whether match is found
        boolean matchFound = m.matches();

        StringTokenizer st = new StringTokenizer(email, ".");
        String lastToken = null;
        while (st.hasMoreTokens()) {
            lastToken = st.nextToken();
        }

        if (matchFound && lastToken.length() >= 2
                && email.length() - 1 != lastToken.length()) {

            // validate the country code
            return true;
        } else
            return false;
    }
}
