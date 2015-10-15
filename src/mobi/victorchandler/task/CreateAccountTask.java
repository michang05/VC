
package mobi.victorchandler.task;

import mobi.victorchandler.Home;
import mobi.victorchandler.model.CreateAccountPojo;
import mobi.victorchandler.webservice.CreateAccountService;
import mobi.victorchandler.webservice.LoginService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
/**
 * Task for Create Account service
 * @author riveram
 *
 */
public class CreateAccountTask extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
    private CreateAccountPojo cap;
    private CreateAccountService service;
    private ProgressDialog progressDialog;

    public CreateAccountTask(Activity activity, CreateAccountPojo cap) {

        this.activity = activity;
        this.cap = cap;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Creating account ...");
        progressDialog.show();

    }

    @Override
    protected Boolean doInBackground(Void... arg0) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("locale", cap.getLocale());
        map.put("title", cap.getTitle());
        map.put("firstName", cap.getFirstname());
        map.put("lastName", cap.getLastname());
        map.put("mobile", cap.getMobile());
        map.put("dateOfBirth", cap.getDateOfBirth());
        map.put("address1", cap.getAddress1());
        map.put("address2", cap.getAddress2());
        map.put("city", cap.getCity());
        map.put("region", cap.getRegion());
        map.put("postCode", cap.getPostCode());
        map.put("countryId", String.valueOf(cap.getCountryId()));
        map.put("username", cap.getUsername());
        map.put("password", cap.getPassword());
        map.put("email", cap.getEmail());
        map.put("currencyId", String.valueOf(cap.getCurrencyId()));
        map.put("securityQuestionId", String.valueOf(cap.getSecQuestionId()));
        map.put("securityAnswer", cap.getSecAnswer());
        map.put("sendEmail", cap.getSendEmail());
        map.put("sendPhone", cap.getSendPhone());
        map.put("sendSms", cap.getSendSms());

        service = new CreateAccountService(map, activity);

        return service.executeRequest();
    }

    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        if (result) {
            LoginService loginService = new LoginService(activity, new String[] {
                    cap.getUsername(), cap.getPassword()
            });
            if (loginService.executeRequest()) {

                Toast.makeText(
                        activity,
                        result ? "Account Created, you are now logged in."
                                : "Account Creation Failed!",
                        Toast.LENGTH_LONG).show();
                Intent home = new Intent(activity, Home.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(home);
            }else {
                Log.d("CreateAccountTask", "Login Failed!");
            }
        }else {
            Log.d("CreateAccountTask", "Error Creating Account");
        }

        progressDialog.dismiss();
    }

}
