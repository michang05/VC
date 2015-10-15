package mobi.victorchandler.task;

import mobi.victorchandler.webservice.RefreshBalanceService;

import android.app.Activity;
import android.os.AsyncTask;
/**
 * Task for Refresh Balance service
 * @author riveram
 *
 */
public class RefreshBalanceTask extends AsyncTask<Void, Void, Boolean> {

    private RefreshBalanceService service;
    private Activity act;

    public RefreshBalanceTask(Activity act) {
       this.act = act;
    }
    
    @Override
    protected void onPreExecute() {
       
        super.onPreExecute();
       service = new RefreshBalanceService(act);
    }
    
    @Override
    protected Boolean doInBackground(Void... params) {
        return service.executeRequest();
    }

}
