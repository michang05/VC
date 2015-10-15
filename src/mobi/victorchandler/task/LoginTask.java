
package mobi.victorchandler.task;

import mobi.victorchandler.Login;
import mobi.victorchandler.database.FavsDB;
import mobi.victorchandler.preferences.AccountPreferences;
import mobi.victorchandler.preferences.BasePreferences;
import mobi.victorchandler.webservice.LoginService;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;
/**
 * Task for Login service
 * @author riveram
 *
 */
public class LoginTask extends AsyncTask<Void, Void, Void> {

    private Activity act;
    private LoginService loginService;
    private String[] credentials;

    public LoginTask(Activity act, String[] credentials) {
        this.act = act;
        this.credentials = credentials;
    }

    protected void onPreExecute() {

        act.showDialog(Login.DIALOG_LOGGINGIN);

        loginService = new LoginService(act,credentials);
    }

    @Override
    protected Void doInBackground(Void... params) {

        loginService.executeRequest();

        return null;
    }

    protected void onPostExecute(Void result) {

        act.dismissDialog(Login.DIALOG_LOGGINGIN);

        if (loginService.isLoginResult()) {
            
            BasePreferences.load(act);
            FavsDB favs = new FavsDB(act);
            favs.open();
            favs.addBackUserFavorites(AccountPreferences.getAccountNumber());
            favs.close();

            Toast.makeText(act, "You are now logged in", Toast.LENGTH_SHORT).show();

            act.setResult(Activity.RESULT_OK);
            act.finish();

        } else {
            act.showDialog(Login.DIALOG_BADPW);
        }

        super.onPostExecute(result);
    }

}
