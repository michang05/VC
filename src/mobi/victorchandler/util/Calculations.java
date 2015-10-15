
package mobi.victorchandler.util;

import mobi.victorchandler.database.BetsDb;

import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 *
 * for calculations of numbers required
 */
public class Calculations {

    /**
     * @param roundedValue
     * @param roundedPlace
     * @return float
     */
    public static String roundOff(String roundedValue, int roundedPlace) {
        BigDecimal value = new BigDecimal(String.valueOf(roundedValue));
        value = value.setScale(roundedPlace, RoundingMode.CEILING);
        return value.toEngineeringString();
    }

    /**
     * Each way calculation
     *  
     * @param context
     * @param stakeAmount
     * @param price
     * @return
     */
    public static double getEacWayWinnings(Context context, double stakeAmount, double price) {

        double winnings;
        String placeTermsDed = null;
        BetsDb bets = new BetsDb(context);
        bets.open();
        Cursor c = bets.getCursor();
        if (c.moveToFirst()) {
            do {
                placeTermsDed = c.getString(c.getColumnIndex(c
                        .getColumnName(BetsDb.PT_DEDUCTION_COLUMN)));
              
            } while (c.moveToNext());
        }
        bets.close();
        winnings =
                stakeAmount * (((price - 1) / Double.parseDouble(placeTermsDed)) + 1);
        return winnings;
    }
}
