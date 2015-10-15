
package mobi.victrorchandler.loader;

import mobi.victorchandler.R;
import mobi.victorchandler.response.SportsBookResponse;
import mobi.victorchandler.task.GetSportsBookTask;
import mobi.victorchandler.view.SportsBookArrayAdapter;
import mobi.victorchandler.webservice.SportsBookService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * Class for extending AsyncTaskLoader,
 * this subclass can be improved.
 * @author riveram
 *
 */
public class SportsBookLoader extends AsyncTaskLoader<Boolean> {

    private ListView lv;
    private Context ctx;
    private Activity sports;
    private ProgressDialog progressDialog;
    private SportsBookService sportsService;
    private ArrayList<SportsBookResponse> alSportsbook;

    public SportsBookLoader(Context context) {
        super(context);
    }

    public SportsBookLoader(ListView lv, Context ctx, Activity sports) {
        super(ctx);
        this.lv = lv;
        this.ctx = ctx;
        this.sports = sports;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Fetching All Sports ...");
        progressDialog.show();
        sportsService = new SportsBookService(ctx);
    }
    

    @Override
    public Boolean loadInBackground() {

        return sportsService.executeRequest();

    }

    @Override
    protected void onStartLoading() {
       
        super.onStartLoading();
        if (alSportsbook != null) {
            deliverResult(true);
        }
        if (takeContentChanged() || alSportsbook == null) {
            forceLoad();
        }
    }
    
    @Override
    public void deliverResult(Boolean data) {

        super.deliverResult(data);

        if(!data) {
            return;
        }
        
     alSportsbook = new ArrayList<SportsBookResponse>();

        if (sportsService.getSportsdata() == null) {

            Toast.makeText(ctx, ctx.getString(R.string.service_unavailable), Toast.LENGTH_LONG)
                    .show();
            progressDialog.dismiss();
            return;
        }

        if (sportsService.getSportsdata().size() == 0) {
            new GetSportsBookTask(lv,ctx, sports).execute();

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
