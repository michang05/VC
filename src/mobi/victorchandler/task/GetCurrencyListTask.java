
package mobi.victorchandler.task;

import mobi.victorchandler.R;
import mobi.victorchandler.webservice.CurrencyListService;

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
 * Task for Currency List service
 * @author riveram
 *
 */
public class GetCurrencyListTask extends AsyncTask<Void, Void, HashMap<Integer, String>> {

    private CurrencyListService service;
    private Activity act;
    private Spinner spnCurrency;

    public GetCurrencyListTask(Activity act, Spinner spnCurrency) {
        this.act = act;
        this.spnCurrency = spnCurrency;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        service = new CurrencyListService();
    }

    @Override
    protected HashMap<Integer, String> doInBackground(Void... params) {
        if (!service.executeRequest()) {
            return null;
        }
        return service.getCurrencyList();
    }

    @Override
    protected void onPostExecute(HashMap<Integer, String> result) {

        super.onPostExecute(result);

        if (result == null) {
            Toast.makeText(act, act.getString(R.string.service_unavailable),
                    Toast.LENGTH_LONG).show();
            return;
        } else {

            ArrayList<String> currencyNames = new ArrayList<String>();

            Set<Entry<Integer, String>> countries = result.entrySet();
            Iterator<Entry<Integer, String>> it = countries.iterator();
            while (it.hasNext()) {
                Entry<Integer, String> country = it.next();
                currencyNames.add(country.getValue());
            }

            Collections.sort(currencyNames);
            ArrayAdapter<String> aa = new ArrayAdapter<String>(act,
                    android.R.layout.simple_spinner_item, currencyNames);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnCurrency.setAdapter(aa);
        }
    }

}
