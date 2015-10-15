
package mobi.victorchandler.task;

import mobi.victorchandler.R;
import mobi.victorchandler.response.NextEventsResponse;
import mobi.victorchandler.util.DataHelper;
import mobi.victorchandler.view.NextEventsArrayAdapter;
import mobi.victorchandler.webservice.NextEventsService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Task for NextEvents service
 * @author riveram
 *
 */
public class GetNextEventsTask extends AsyncTask<Void, Void, Void> {

    private Context ctx;
    private ListView mList;
    private ProgressDialog progressDialog;
    private NextEventsService mNextEventsService;
    private Activity nextEvents;

    public GetNextEventsTask(ListView mList, Context ctx, Activity nextEvents) {
        this.mList = mList;
        this.ctx = ctx;
        this.nextEvents = nextEvents;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Fetching Next Events ...");
        progressDialog.show();

        mNextEventsService = new NextEventsService(ctx);

    }

    @Override
    protected Void doInBackground(Void... params) {

        mNextEventsService.executeRequest();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);

        ArrayList<NextEventsResponse> alNextEvents = new ArrayList<NextEventsResponse>();

        if (mNextEventsService.getNextEventsData() == null) {

            Toast.makeText(ctx, ctx.getString(R.string.service_unavailable), Toast.LENGTH_LONG)
                    .show();
            progressDialog.dismiss();
            return;
        }

        if (mNextEventsService.getNextEventsData().size() == 0) {
            new GetNextEventsTask(mList, ctx, nextEvents).execute();

        } else {
            for (NextEventsResponse nextEvents : mNextEventsService.getNextEventsData()) {
                alNextEvents.add(nextEvents);
            }
        }
        mList.invalidate();
        mList.setAdapter(new NextEventsArrayAdapter(ctx,
                R.layout.next_events_row, alNextEvents));

        final TextView tvLastUpdate = (TextView) nextEvents.findViewById(R.id.tvLastUpdate);
        tvLastUpdate.setText("Updated: "
                + DataHelper.getFormattedTimeNow(System.currentTimeMillis()));

        progressDialog.dismiss();
    }
}
