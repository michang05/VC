
package mobi.victorchandler;

import mobi.victorchandler.preferences.BasePreferences;
import mobi.victorchandler.preferences.TimezonePreferences;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * A Preference Activity for Settings
 * this can be improved and design in a different way
 * with localizations.
 * 
 * @author riveram
 *
 */
public class Settings extends PreferenceActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        BasePreferences.load(this);
        setPreferenceScreen(createPreferenceHierarchy());

    }
/**
 * Method to create hierarchy of preferences to be listed.
 * 
 * @return PreferenceScreen
 */
    private PreferenceScreen createPreferenceHierarchy() {
        // Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        PreferenceCategory fromLoginPreferences = new PreferenceCategory(this);
        fromLoginPreferences.setTitle("Timezone/Locale Preferences");
        root.addPreference(fromLoginPreferences);

        EditTextPreference timeZone = new EditTextPreference(this);
        timeZone.setKey("timezone");
        timeZone
                .setTitle(preferences.getString("timezone", TimezonePreferences.getTimeZone()));
        timeZone.setSummary("Your current time zone");
        timeZone.setEnabled(false);
        fromLoginPreferences.addPreference(timeZone);

        EditTextPreference locale = new EditTextPreference(this);
        locale.setKey("locale");
        locale.setTitle(preferences.getString("locale", TimezonePreferences.getLocale()));
        locale.setSummary("Your Locale");
        locale.setEnabled(false);
        fromLoginPreferences.addPreference(locale);

        // List Preferences =========
        PreferenceCategory dialogBasedPrefCat = new PreferenceCategory(this);
        dialogBasedPrefCat.setTitle("Betting Preferences");
        root.addPreference(dialogBasedPrefCat);

        // List preference
        final ListPreference listLanguage = new ListPreference(this);
        CharSequence[] languages = new CharSequence[] {
                "English", "Spanish", "Greek", "Hebrew", "German", "Romanian"
        };
        listLanguage.setEntries(languages);
        listLanguage.setEntryValues(languages);
        listLanguage.setDialogTitle("Choose Language");
        listLanguage.setKey("language");
        listLanguage.setDefaultValue("English");

        listLanguage.setTitle(preferences.getString("language", "English"));
        listLanguage.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                listLanguage.setTitle(newValue.toString());
                Resources res = getResources();
                // Change locale settings in the app.
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                if (newValue.toString().equals("English")) {
                    conf.locale = new Locale("en");

                } else if (newValue.toString().equals("Spanish")) {
                    conf.locale = new Locale("es");
                }
                res.updateConfiguration(conf, dm);
                
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setMessage("Language/Locale Changed, exiting the app to make the changes.")
                       .setCancelable(false)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               Intent intent = new Intent(getBaseContext(),Home.class);
                               intent.addCategory(Intent.CATEGORY_HOME);
                               intent.setAction(Intent.ACTION_MAIN);
                               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(intent);
                               finish();
                           }
                       });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });

        listLanguage.setSummary("Select language");
        dialogBasedPrefCat.addPreference(listLanguage);

        // Odds Style
        final ListPreference oddsList = new ListPreference(this);
        CharSequence[] odds = new CharSequence[] {
                "US style odds", "UK style odds", "Euro style odds", "Indonesian style odds",
                "Chinese style odds"
        };
        oddsList.setEntries(odds);
        oddsList.setEntryValues(odds);
        oddsList.setDialogTitle("Choose Odds Display");
        oddsList.setKey("style_odds");
        oddsList.setDefaultValue("US style odds");
        oddsList.setTitle(preferences.getString("style_odds", "US style odds"));
        oddsList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                oddsList.setTitle(newValue.toString());
                return true;
            }
        });

        oddsList.setSummary("Select Odds Display");
        dialogBasedPrefCat.addPreference(oddsList);

        return root;
    }
}
