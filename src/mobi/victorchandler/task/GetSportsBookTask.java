
package mobi.victorchandler.task;

import mobi.victorchandler.R;
import mobi.victorchandler.response.SportsBookResponse;
import mobi.victorchandler.view.SportsBookArrayAdapter;
import mobi.victorchandler.webservice.SportsBookService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * Task for SportsBook service
 * @author riveram
 *
 */
public class GetSportsBookTask extends AsyncTask<Void, Void, Void> {

    private ListView lv;
    private Context ctx;
    private Activity sports;
    private ProgressDialog progressDialog;
    private SportsBookService sportsService;

    public GetSportsBookTask(ListView lv, Context ctx, Activity sports) {

        this.lv = lv;
        this.ctx = ctx;
        this.sports = sports;
    }

    @Override
    protected void onCancelled(Void result) {
       
        super.onCancelled(result);
        Toast.makeText(ctx, "Fetching was cancelled", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Fetching All Sports ...");
        progressDialog.show();
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new OnCancelListener() {
            
            @Override
            public void onCancel(DialogInterface dialog) {
             cancel(true); 
            }
        });
        
        sportsService = new SportsBookService(ctx);
    }

    @Override
    protected Void doInBackground(Void... params) {
       
        if(!sportsService.executeRequest()) {
            
        }
        
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);

        ArrayList<SportsBookResponse> alSportsbook = new ArrayList<SportsBookResponse>();

        if (sportsService.getSportsdata() == null) {

            Toast.makeText(ctx, ctx.getString(R.string.service_unavailable), Toast.LENGTH_LONG)
                    .show();
            progressDialog.dismiss();
            return;
        }

        if (sportsService.getSportsdata().size() == 0) {
            new GetSportsBookTask(lv, ctx, sports).execute();

        } else {
            for (SportsBookResponse sr : sportsService.getSportsdata()) {
                alSportsbook.add(sr);
            }
        }

        Comparator<SportsBookResponse> comparator = new Comparator<SportsBookResponse>() {

            @Override
            public int compare(SportsBookResponse object1, SportsBookResponse object2) {
                return object1.getSportName().compareTo(object2.getSportName());
            }
        };

        SportsBookArrayAdapter aa = new SportsBookArrayAdapter(ctx, R.layout.all_sports_row,
                alSportsbook);
        aa.sort(comparator);
        aa.notifyDataSetChanged();
        lv.setAdapter(aa);

        progressDialog.dismiss();

    }

}
