package mobi.victorchandler.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
/**
 * Class for watching currency change
 * @author riveram
 *
 */
public class CurrencyWatcher implements TextWatcher {
	EditText et;

	public CurrencyWatcher(EditText et) {
		this.et = et;
	}

	private String current = "";

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!s.toString().equals(current)) {
			String cleanString = s.toString().replaceAll("[$,.]", "");

			double parsed = Double.parseDouble(cleanString);
			String formated = NumberFormat.getCurrencyInstance().format((parsed / 100));
			formated = formated.replaceAll("[$,]", "");
			current = formated;
			et.setText(formated);
			et.setSelection(formated.length());

		}
	}

	@Override
	public void afterTextChanged(Editable et) {
	    
	    
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int before, int count) {
	    if (!s.toString().equals(current)) {
            String cleanString = s.toString().replaceAll("[$,.]", "");

            double parsed = Double.parseDouble(cleanString);
            String formated = NumberFormat.getCurrencyInstance().format((parsed / 100));
            formated = formated.replaceAll("[$,]", "");
            current = formated;
            et.setText(formated);
            et.setSelection(formated.length());

        }
	}
}