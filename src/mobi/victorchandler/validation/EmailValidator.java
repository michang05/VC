
package mobi.victorchandler.validation;

import android.widget.EditText;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Class for validating Email addresses
 */
public class EmailValidator extends AbstractValidator {

    private EditText txtEmail;

    public EmailValidator(EditText txtEmail) {
        super(true);
        this.txtEmail = txtEmail;
    }

    public EmailValidator(EditText txtEmail, String requiredMessage) {
        super(true);
        this.txtEmail = txtEmail;
        _requiredMessage = requiredMessage;
    }

    @Override
    public ValidationResult validate() {
        ValidationResult _v = super.validate();
        String email = txtEmail.getText().toString();

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
            _v = new ValidationResult(true, "");
        }
        else
            _v = new ValidationResult(false, _requiredMessage);
        return _v;
    }

    @Override
    public Object getSource() {

        return txtEmail;
    }

}
