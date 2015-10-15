
package mobi.victorchandler;

import mobi.victorchandler.model.CreateAccountPojo;
import mobi.victorchandler.task.GetSecurityQuestionListTask;
import mobi.victorchandler.task.VerifyAccountAvailabilityTask;
import mobi.victorchandler.util.NetworkHelper;
import mobi.victorchandler.validation.EmailValidator;
import mobi.victorchandler.validation.PasswordValidator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
/**
 * Screen for Creating a new Account.
 * 
 * @author riveram
 *
 */
public class CreateAccount extends Activity {

    private CreateAccountPojo createAccountObj;

    private Button btnContinue;
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtEmail;
    private Spinner spnSecQuestions;
    private EditText txtSecAnswer;

    private Spinner spnTitle;
    private EditText txtFirstname;
    private EditText txtLastname;

    private GetSecurityQuestionListTask sqt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        createAccountObj = new CreateAccountPojo();

        txtUsername = (EditText) findViewById(R.id.txtCreateUsername);
        txtPassword = (EditText) findViewById(R.id.txtCreatePassword);
        txtEmail = (EditText) findViewById(R.id.txtCreateEmail);
        spnSecQuestions = (Spinner) findViewById(R.id.spnCreateSecurityQuestion);
        sqt = new GetSecurityQuestionListTask(this, spnSecQuestions);
        sqt.execute();

        txtSecAnswer = (EditText) findViewById(R.id.txtCreateSecurityAnswer);
        spnTitle = (Spinner) findViewById(R.id.spnTitle);
        txtFirstname = (EditText) findViewById(R.id.txtCreateFirstName);
        txtLastname = (EditText) findViewById(R.id.txtCreateLastName);

        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                NetworkHelper nh = new NetworkHelper(CreateAccount.this);
                if (!nh.isNetworkAvailable()) {
                    Toast.makeText(CreateAccount.this, getString(R.string.newtwork_unavailable),
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                } else {
                    if (areBlankFieldsExist()) {
                        return;
                    } else {
                        if (verifyAccount()) {
                            Intent i = new Intent(CreateAccount.this,
                                    CreateAccount2.class);
                            i.putExtra(getPackageName(), createAccountObj);

                            startActivity(i);
                        } else {
                            return;
                        }
                    }
                }

            }
        });
    }
    /**
     * Method for checking if the TextFields required are left blank.
     * 
     * @return boolean
     */
    protected boolean areBlankFieldsExist() {
        if (txtUsername.length() == 0) {
            Toast.makeText(this, getString(R.string.username_field_validation), Toast.LENGTH_LONG)
                    .show();
            return true;
        }
        if (txtPassword.length() == 0) {
            Toast.makeText(this, getString(R.string.password_field_validation), Toast.LENGTH_LONG)
                    .show();
            return true;
        }

        if (txtEmail.length() == 0) {
            Toast.makeText(this, getString(R.string.email_char_validation), Toast.LENGTH_LONG)
                    .show();
            return true;
        }
        if (txtSecAnswer.length() == 0) {
            Toast.makeText(this, getString(R.string.answer_field_validation), Toast.LENGTH_LONG)
                    .show();
            return true;
        }
        if (txtFirstname.length() == 0) {
            Toast.makeText(this, getString(R.string.firstname_field_validation), Toast.LENGTH_LONG)
                    .show();
            return true;
        }
        if (txtLastname.length() == 0) {
            Toast.makeText(this, getString(R.string.lastname_field_validation), Toast.LENGTH_LONG)
                    .show();
            return true;
        }

        return false;
    }

    /**
     * Method to verify Account if exists in the system.
     * 
     * @return boolean
     */
    private boolean verifyAccount() {

        // Username
        VerifyAccountAvailabilityTask vat = new VerifyAccountAvailabilityTask(txtUsername);
        vat.execute();
        try {
            if (vat.get().booleanValue()) {
                createAccountObj.setUsername(txtUsername.getText().toString());
            } else {
                Toast.makeText(CreateAccount.this, getString(R.string.username_taken),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Password 
        PasswordValidator pv = new PasswordValidator(txtPassword,
                getString(R.string.password_char_validation), CreateAccount.this);
        if (pv.validate().isValid()) {
            createAccountObj.setPassword(txtPassword.getText().toString());
        } else {
            Toast.makeText(CreateAccount.this,
                    pv.validate().getMessage(),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        // Email
        EmailValidator ev = new EmailValidator(txtEmail,
                getString(R.string.email_char_validation));
        if (ev.validate().isValid()) {
            createAccountObj.setEmail(txtEmail.getText().toString());
        } else {
            Toast.makeText(CreateAccount.this,
                    ev.validate().getMessage(),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        // Security Questions
        createAccountObj.setSecQuestion(spnSecQuestions.getSelectedItem().toString());
        Set<Entry<Integer, String>> questions;
        try {
            questions = sqt.get().entrySet();
            Iterator<Entry<Integer, String>> it = questions.iterator();
            while (it.hasNext()) {
                Entry<Integer, String> country = it.next();
                if (spnSecQuestions.getSelectedItem().toString().equals(country.getValue())) {
                    createAccountObj.setSecQuestionId(country.getKey());
                }
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Security Answer
        if (txtSecAnswer.getText().toString().length() != 0) {
            createAccountObj.setSecAnswer(txtSecAnswer.getText().toString());
        } else {
            Toast.makeText(CreateAccount.this,
                    getString(R.string.answer_field_validation),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        createAccountObj.setTitle(spnTitle.getSelectedItem().toString());

        // Firstname
        if (txtFirstname.getText().toString().length() != 0) {
            createAccountObj.setFirstname(txtFirstname.getText().toString());
        } else {
            Toast.makeText(CreateAccount.this,
                    getString(R.string.firstname_field_validation),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        // Lastname
        if (txtLastname.getText().toString().length() != 0) {
            createAccountObj.setLastname(txtLastname.getText().toString());
        } else {
            Toast.makeText(CreateAccount.this,
                    getString(R.string.lastname_field_validation),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
