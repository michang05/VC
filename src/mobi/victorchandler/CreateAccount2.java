
package mobi.victorchandler;

import mobi.victorchandler.model.CreateAccountPojo;
import mobi.victorchandler.task.CreateAccountTask;
import mobi.victorchandler.task.GetCountryListTask;
import mobi.victorchandler.task.GetCurrencyListTask;
import mobi.victorchandler.util.NetworkHelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
/**
 * Continuation Screen for creating account.
 * 
 * @author riveram
 *
 */
public class CreateAccount2 extends Activity {

    private EditText txtMobile;
    private EditText txtAddress1;
    private EditText txtRegion;
    private EditText txtCity;
    private EditText txtPostCode;
    private Spinner spnCountry;
    private Spinner spnCurrency;
    private TextView tvDateOfBirth;
    private RadioGroup rbgGender;
    private Button btnCreateAccount;

    private int mYear;
    private int mMonth;
    private int mDay;

    private TextView mDateDisplay;
    private Button mPickDate;

    static final int DATE_DIALOG_ID = 0;
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }

            };

    private CreateAccountPojo cap;
    private GetCountryListTask countryListTask;
    private GetCurrencyListTask currencyListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account2);

        Bundle b = getIntent().getExtras();
        cap = b.getParcelable(getPackageName());

        txtMobile = (EditText) findViewById(R.id.txtCreateMobile);
        txtAddress1 = (EditText) findViewById(R.id.txtCreateAdd1);
        txtRegion = (EditText) findViewById(R.id.txtCreateRegion);
        txtCity = (EditText) findViewById(R.id.txtCreateCity);
        txtPostCode = (EditText) findViewById(R.id.txtCreatePostCode);

        spnCountry = (Spinner) findViewById(R.id.spnCountry);
        countryListTask = new GetCountryListTask(this, spnCountry);
        countryListTask.execute();

        spnCurrency = (Spinner) findViewById(R.id.spnCurrency);
        currencyListTask = new GetCurrencyListTask(this, spnCurrency);
        currencyListTask.execute();

        tvDateOfBirth = (TextView) findViewById(R.id.tvDoB);
        rbgGender = (RadioGroup) findViewById(R.id.rbgGender);

        rbgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                for (int i = 0; i < rg.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                    if (btn.getId() == checkedId) {
                        String value = btn.getText().toString();
                        cap.setGender(value);
                        return;
                    }
                }
            }

        });

        mDateDisplay = (TextView) findViewById(R.id.tvDoB);
        mPickDate = (Button) findViewById(R.id.btnSetDate);

        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance(Locale.ENGLISH);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date
        updateDisplay();

        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Validation first
                validateFields();
            }
        });
    }

    private void validateFields() {

        if (txtMobile.length() != 0) {
            cap.setMobile(txtMobile.getText().toString());
        } else {
            Toast.makeText(CreateAccount2.this,
                    getString(R.string.mobile_validation),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (txtAddress1.length() != 0) {
            cap.setAddress1(txtAddress1.getText().toString());
        } else {
            Toast.makeText(CreateAccount2.this,
                    getString(R.string.address1_field_validation),
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (txtCity.length() != 0) {
            cap.setCity(txtCity.getText().toString());
        } else {
            Toast.makeText(CreateAccount2.this,
                    getString(R.string.city_validation),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (txtPostCode.length() != 0) {
            cap.setPostCode(txtPostCode.getText().toString());
        } else {
            Toast.makeText(CreateAccount2.this,
                    getString(R.string.postcode_field_validation),
                    Toast.LENGTH_LONG).show();
            return;
        }
        Set<Entry<Integer, String>> countries;
        Set<Entry<Integer, String>> currencies;
        try {
            countries = countryListTask.get().entrySet();
            Iterator<Entry<Integer, String>> it = countries.iterator();
            while (it.hasNext()) {
                Entry<Integer, String> country = it.next();
                if (spnCountry.getSelectedItem().toString().equals(country.getValue())) {
                    cap.setCountryId(country.getKey());
                }
            }

            currencies = currencyListTask.get().entrySet();
            Iterator<Entry<Integer, String>> itC = currencies.iterator();
            while (itC.hasNext()) {
                Entry<Integer, String> currency = itC.next();
                if (spnCurrency.getSelectedItem().toString().equals(currency.getValue())) {
                    cap.setCurrencyId(currency.getKey());
                }
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cap.setCountry(spnCountry.getSelectedItem().toString());
        cap.setCurrency(spnCurrency.getSelectedItem().toString());
        if (mYear == Calendar.getInstance().get(Calendar.YEAR)) {
            Toast.makeText(this, getString(R.string.birthdate_validation), Toast.LENGTH_LONG)
                    .show();
            return;
        } else {
            cap.setDateOfBirth(tvDateOfBirth.getText().toString());
        }
        if (rbgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(CreateAccount2.this,
                    getString(R.string.gender_validation),
                    Toast.LENGTH_LONG).show();
            return;
        }
        
        cap.setRegion(txtRegion.getText().toString());
        cap.setSendEmail("false");
        cap.setSendPhone("false");
        cap.setSendSms("false");
        cap.setAddress2("");
        cap.setLocale(Locale.getDefault().toString());

        NetworkHelper nh = new NetworkHelper(this);
        if (!nh.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.newtwork_unavailable),
                    Toast.LENGTH_LONG)
                    .show();
            return;
        } else {
            // finally send it to the service
            new CreateAccountTask(this, cap).execute();
        }

        // Log.d("CreateAccount", cap.toString());

    }
/**
 * Method to update the DatePicker Display
 */
    private void updateDisplay() {

        this.mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1

                        .append(mDay).append("/")
                        .append(mMonth + 1).append("/")
                        .append(mYear).append(" "));
    }

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

}
