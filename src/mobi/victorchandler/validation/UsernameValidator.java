
package mobi.victorchandler.validation;

import android.widget.EditText;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Class for validating Usernames
 * @author riveram
 *
 */
public class UsernameValidator extends AbstractValidator {
   
    private EditText txtUsername;

    public UsernameValidator(EditText txtUsername) {
        super(true);
        this.txtUsername = txtUsername;
    }

    public UsernameValidator(EditText txtUsername, String requiredMessage) {
        super(true);
        this.txtUsername = txtUsername;
        _requiredMessage = requiredMessage;
    }

    @Override
    public ValidationResult validate() {
        ValidationResult _v = super.validate();
        String username = txtUsername.getText().toString();

        
        Pattern p = Pattern.compile("^.*(?=.{6,12})(?=.*\\d)(?=.*[a-zA-Z]).*$");

        // Match the given string with the pattern
        Matcher m = p.matcher(username);

        // check whether match is found
        boolean matchFound = m.matches();

        StringTokenizer st = new StringTokenizer(username, ".");
        String lastToken = null;
        while (st.hasMoreTokens()) {
            lastToken = st.nextToken();
        }

        if (matchFound && lastToken.length() >= 2
                && username.length() - 1 != lastToken.length()) {

            // validate the country code
            _v = new ValidationResult(true, "");
        }
        else
            _v = new ValidationResult(false, _requiredMessage);
        return _v;
    }

    @Override
    public Object getSource() {

        return txtUsername;
    }

}
