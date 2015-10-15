package mobi.victorchandler;

import mobi.victorchandler.view.ActionBar.AbstractAction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Just a Dummy class 
 * @author riveram
 *
 */
public class OtherActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.other);
        Log.d("=====OtherAcvitiy", "onCreate Baby");
//        ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
//        // You can also assign the title programmatically by passing a
//        // CharSequence or resource id.
//        //actionBar.setTitle(R.string.some_title);
//        actionBar.setHomeAction(new IntentAction(this, Home.createIntent(this), R.drawable.home));
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.addAction(new IntentAction(this, createShareIntent(), R.drawable.icon));
//        actionBar.addAction(new ExampleAction());
    }
    
    private Intent createShareIntent() {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Shared from the ActionBar widget.");
        return Intent.createChooser(intent, "Share");
    }

    private class ExampleAction extends AbstractAction {

        public ExampleAction() {
            super(R.drawable.icon);
        }

        @Override
        public void performAction(View view) {
            Toast.makeText(OtherActivity.this,
                    "Example action", Toast.LENGTH_SHORT).show();
        }

    }
    
   

   
}