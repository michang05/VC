
package mobi.victorchandler.task;

import mobi.victorchandler.R;
import mobi.victorchandler.webservice.CountryListService;

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
 * Task for CountryList service
 * @author riveram
 *
 */
public class GetCountryListTask extends AsyncTask<Void, Void, HashMap<Integer, String>> {

    private CountryListService service;
    private Activity act;
    private Spinner spnCountry;

    public GetCountryListTask(Activity act, Spinner spnCountry) {
        this.act = act;
        this.spnCountry = spnCountry;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        service = new CountryListService();
    }

    @Override
    protected HashMap<Integer, String> doInBackground(Void... params) {
        if (!service.executeRequest()) {
            return null;
        }
        return service.getCountryList();
    }

    @Override
    protected void onPostExecute(HashMap<Integer, String> result) {

        super.onPostExecute(result);

        if (result == null) {
            Toast.makeText(act, act.getString(R.string.service_unavailable),
                    Toast.LENGTH_LONG).show();

            return;
        } else {

            ArrayList<String> countryNames = new ArrayList<String>();

            Set<Entry<Integer, String>> countries = result.entrySet();
            Iterator<Entry<Integer, String>> it = countries.iterator();
            while (it.hasNext()) {
                Entry<Integer, String> country = it.next();
                countryNames.add(country.getValue());
            }

            Collections.sort(countryNames);

            ArrayAdapter<String> aa = new ArrayAdapter<String>(act,
                    android.R.layout.simple_spinner_item, countryNames);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnCountry.setAdapter(aa);
        }

    }

}
