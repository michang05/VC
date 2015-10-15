
package mobi.victorchandler.listeners;

import mobi.victorchandler.BetSlip;

import android.view.View.OnClickListener;
/**
 * Class that manages Errors received and provides
 * the right listener to process the error.
 * @author riveram
 *
 */
public class ErrorListenerManager {

    private BetSlip activity;
    private int errorType;
    private String outcomeId;

    public ErrorListenerManager(BetSlip activity, int errorType, String outcomeId) {
        this.activity = activity;
        this.errorType = errorType;
        this.outcomeId = outcomeId;
    }
/**
 * Gets the corresponding errorlistener from the error type
 * @return
 */
    public OnClickListener getListener() {

        OnClickListener listener = null;

        switch (errorType) {
            //Add Error Listeners(create an implementation of Listener) here base on type
            case 45:
                listener = new BetErrorListener(activity, outcomeId);
                break;
            case 46:
                break;
            case 47:
                break;
            default: 
                listener = new BetErrorListener(activity, outcomeId);
                break;
        }
        return listener;
    }

}
