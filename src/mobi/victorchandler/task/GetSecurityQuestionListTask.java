
package mobi.victorchandler.task;

import mobi.victorchandler.R;
import mobi.victorchandler.webservice.SecurityQuestionListService;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
/**
 * Task for SecurityQuestionList service
 * @author riveram
 *
 */
public class GetSecurityQuestionListTask extends AsyncTask<Void, Void, HashMap<Integer, String>> {

    private SecurityQuestionListService service;
    private Activity act;
    private Spinner spnSecQuestion;

    public GetSecurityQuestionListTask(Activity act, Spinner spnSecQuestion) {
        this.act = act;
        this.spnSecQuestion = spnSecQuestion;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        service = new SecurityQuestionListService();
    }

    @Override
    protected  HashMap<Integer, String> doInBackground(Void... params) {
        if (!service.executeRequest()) {
            return null;
        }
        return service.getSecQuestionList();
    }

    @Override
    protected void onPostExecute( HashMap<Integer, String> result) {

        super.onPostExecute(result);
        if (result == null) {
            Toast.makeText(act, act.getString(R.string.service_unavailable),
                    Toast.LENGTH_LONG).show();
            act.finish();
        } else {
            ArrayList<String> sqList = new ArrayList<String>();

            Set<Entry<Integer, String>> questions = result.entrySet();
            Iterator<Entry<Integer, String>> it = questions.iterator();
            while (it.hasNext()) {
                Entry<Integer, String> question = it.next();
                sqList.add(question.getValue());
            }

            Collections.sort(sqList);
            
            ArrayAdapter<String> aa = new ArrayAdapter<String>(act,
                    android.R.layout.simple_spinner_item, sqList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnSecQuestion.setAdapter(aa);

        }
    }

}
