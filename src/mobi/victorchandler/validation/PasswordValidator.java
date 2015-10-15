
package mobi.victorchandler.validation;

import mobi.victorchandler.R;

import android.app.Activity;
import android.widget.EditText;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Class for validating passwords
 * @author riveram
 *
 */
public class PasswordValidator extends AbstractValidator {

    private EditText txtPassword;
    private Activity act;

    public PasswordValidator(EditText txtPassword) {
        super(true);
        this.txtPassword = txtPassword;
    }

    public PasswordValidator(EditText txtPassword, String requiredMessage,Activity act) {
        super(true);
        this.txtPassword = txtPassword;
        _requiredMessage = requiredMessage;
        this.act = act;
    }

    @Override
    public ValidationResult validate() {
        ValidationResult _v = super.validate();
        String password = txtPassword.getText().toString();

        Pattern p = Pattern
                .compile("^(?=.*[A-Za-z])(?=.*[0-9])(?!.*[^A-Za-z0-9])(?!.*\\s).{6,12}$");

        // Match the given string with the pattern
        Matcher m = p.matcher(password);

        // check whether match is found
        boolean matchFound = m.matches();

        StringTokenizer st = new StringTokenizer(password, ".");
        String lastToken = null;
        while (st.hasMoreTokens()) {
            lastToken = st.nextToken();
        }

        if (matchFound && lastToken.length() >= 2
                && password.length() - 1 != lastToken.length()) {
            _v = new ValidationResult(true, "");
        }
        else {
            if (password.length() < 6 || password.length() > 12) {
                _requiredMessage = act.getString(R.string.password_length_validation);
            }
            _v = new ValidationResult(false, _requiredMessage);
        }
        return _v;
    }

    @Override
    public Object getSource() {

        return txtPassword;
    }

}
