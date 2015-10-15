
package mobi.victorchandler.task;

import mobi.victorchandler.R;
import mobi.victorchandler.response.TopBetsResponse;
import mobi.victorchandler.util.DataHelper;
import mobi.victorchandler.view.TopBetsArrayAdapter;
import mobi.victorchandler.webservice.TopBetsService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Task for TopBets service
 * @author riveram
 *
 */
public class GetTopBetsTask extends AsyncTask<Void, Void, Void> {

    private Context ctx;
    private ListView mList;
    private ProgressDialog progressDialog;
    private TopBetsService mTopBetsService;
    private Activity topBets;

    public GetTopBetsTask(ListView mList, Context ctx,Activity topBets) {
        this.mList = mList;
        this.ctx = ctx;
        this.topBets = topBets;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Fetching Top Bets ...");
        progressDialog.show();

        mTopBetsService = new TopBetsService(ctx);

    }

    @Override
    protected Void doInBackground(Void... params) {

        mTopBetsService.executeRequest();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);

        ArrayList<TopBetsResponse> alTopBets = new ArrayList<TopBetsResponse>();

        if(mTopBetsService.getBetsdata()==null) {
            
            Toast.makeText(ctx, ctx.getString(R.string.service_unavailable), Toast.LENGTH_LONG).show();
              progressDialog.dismiss();
              return;
        }
        
        if (mTopBetsService.getBetsdata().size() == 0) {
            new GetTopBetsTask(mList, ctx,topBets).execute();
            
        } else {
            for (TopBetsResponse topBets : mTopBetsService.getBetsdata()) {
 
                alTopBets.add(topBets);
            }
        }
        mList.invalidate();
        mList.setAdapter(new TopBetsArrayAdapter(ctx, R.layout.top_bets_row, alTopBets));

  
        final TextView tvLastUpdate = (TextView)topBets.findViewById(R.id.tvLastUpdate);
        tvLastUpdate.setText("Updated: "
                + DataHelper.getFormattedTimeNow(System.currentTimeMillis()));
        
        progressDialog.dismiss();
    }
}
