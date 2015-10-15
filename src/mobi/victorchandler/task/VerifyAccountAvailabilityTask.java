
package mobi.victorchandler.task;

import mobi.victorchandler.webservice.VerifyAccountAvailabilityService;

import android.os.AsyncTask;
import android.widget.EditText;
/**
 * Task for Verify Account Availability service
 * @author riveram
 *
 */
public class VerifyAccountAvailabilityTask extends AsyncTask<Void, Void, Boolean> {

    private VerifyAccountAvailabilityService accountService;
    private EditText txtUsername;

    public VerifyAccountAvailabilityTask(EditText txtUsername) {
        this.txtUsername = txtUsername;
    }

    protected void onPreExecute() {

        accountService = new VerifyAccountAvailabilityService(txtUsername);

    }

    @Override
    protected Boolean doInBackground(Void... params) {

        accountService.executeRequest();

        return accountService.isAvailable();
    }

    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

    }

}
