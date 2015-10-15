
package mobi.victorchandler.model;
/**
 * Class using attributes for Multiple
 * @author riveram
 *
 */
public class Multiple {

    private String description;
    private int multiplicity;
    private double winfactor;
    private double ewfactor;
    private boolean eachWayEnabled;
    private int winid;
    private boolean winEnabled;
    private int ewid;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(int multiplicity) {
        this.multiplicity = multiplicity;
    }

    public double getWinfactor() {
        return winfactor;
    }

    public void setWinfactor(double winfactor) {
        this.winfactor = winfactor;
    }

    public double getEwfactor() {
        return ewfactor;
    }

    public void setEwfactor(double ewfactor) {
        this.ewfactor = ewfactor;
    }

    public boolean isEachWayEnabled() {
        return eachWayEnabled;
    }

    public void setEachWayEnabled(boolean eachWayEnabled) {
        this.eachWayEnabled = eachWayEnabled;
    }

    public int getWinid() {
        return winid;
    }

    public void setWinid(int winid) {
        this.winid = winid;
    }

    public boolean isWinEnabled() {
        return winEnabled;
    }

    public void setWinEnabled(boolean winEnabled) {
        this.winEnabled = winEnabled;
    }

    public int getEachWayBetTypeId() {
        return ewid;
    }

    public void setEachWayBetTypeId(int ewid) {
        this.ewid = ewid;
    }

}
