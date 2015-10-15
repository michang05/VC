
package mobi.victorchandler.response;

import mobi.victorchandler.model.Multiple;
import mobi.victorchandler.model.Single;

import java.util.ArrayList;
/**
 * Class for storing RefreshBetSlip response attributes
 */
public class RefreshBetSlipResponse {

    private ArrayList<Single> singles;
    private ArrayList<Multiple> multiples;
    
    public ArrayList<Single> getSingles() {
        return singles;
    }
    public void setSingles(ArrayList<Single> singles) {
        this.singles = singles;
    }
    public ArrayList<Multiple> getMultiples() {
        return multiples;
    }
    public void setMultiples(ArrayList<Multiple> multiples) {
        this.multiples = multiples;
    }
    
    

}
