package mobi.victorchandler.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Class that Detects if there is an available network connection
 * @author riveram
 *
 */
public class NetworkHelper {
    
    private Context ctx;
    
    public NetworkHelper(Context ctx) {
        this.ctx = ctx;
    }

   public boolean isServiceAvailable(){
        
       return true;
    }
    
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    
        return activeNetworkInfo != null;
    }
}
