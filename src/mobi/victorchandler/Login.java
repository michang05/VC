
package mobi.victorchandler;

import mobi.victorchandler.preferences.LoginPreferences;
import mobi.victorchandler.task.LoginTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
/**
 * Screen for Log In Credentialsyes
 * 
 * @author riveram
 *
 */
public class Login extends Activity {

    public static final int DIALOG_LOGGINGIN = 0;
    public static final int DIALOG_BADPW = 1;
    private EditText username;
    private EditText password;
    private String mUserName;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        LoginPreferences.load(this);
  
        username = (EditText) findViewById(R.id.nameEditText);
        username.setText(LoginPreferences.getUsername());

        password = (EditText) findViewById(R.id.passwordEditText);

        final CheckBox rememberme = (CheckBox) findViewById(R.id.rememberCheckBox);

        Button login = (Button) findViewById(R.id.loginbutton);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserName = username.getText().toString();
                mPassword = password.getText().toString();
                LoginPreferences.setPassword(mPassword);
                
                if (rememberme.isChecked()) {
                    LoginPreferences.setUsername(mUserName);
                } else {
                    LoginPreferences.setUsername("");
                }

                LoginPreferences.save();
                
                String[] credentials = new String[] {mUserName,mPassword};
                
                new LoginTask(Login.this,credentials).execute();
            }
        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        
        Button btnCreateAccountPage = (Button)findViewById(R.id.btnCreateAccountPage);
        btnCreateAccountPage.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
               Intent i = new Intent(Login.this,CreateAccount.class);
               startActivity(i);
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog d = new AlertDialog.Builder(this).create();
        switch (id) {
            case DIALOG_LOGGINGIN:
                d = new ProgressDialog(this);
                ((ProgressDialog) d).setMessage("Logging in...");
                break;
            case DIALOG_BADPW:
                ((AlertDialog) d).setMessage("Unknown username or bad password! try again..");
                ((AlertDialog) d).setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                        new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismissDialog(DIALOG_BADPW);
                            }
                        });
                break;
        }
        return d;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

}
