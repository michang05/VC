
package mobi.victorchandler.parser;

import mobi.victorchandler.database.StatementsDb;
import mobi.victorchandler.response.TransactionStatementResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Class that parses TransactionStatements service
 * @author riveram
 *
 */
public class TransactionStatementsParser extends BaseVcParser {

    private String result;
    private Activity context;
    private TransactionStatementResponse tsr;
    private ArrayList<TransactionStatementResponse> alTransactions;

    public TransactionStatementsParser(Activity context, String result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public ArrayList<TransactionStatementResponse> parseJsonResponse() {

        alTransactions = new ArrayList<TransactionStatementResponse>();

        try {
            JSONObject json = new JSONObject(result);

            JSONArray statements = json.getJSONArray(STATEMENTS);
            if(statements.length()!=0) {
            for (int i = 0; i < statements.length(); i++) {

                tsr = new TransactionStatementResponse();
                JSONObject statement = statements.getJSONObject(i);

                tsr.setId(statement.getString(ID));

                tsr.setTypeId(statement.getJSONObject(TYPE).getString(ID));
                tsr.setTypeDescription(statement.getJSONObject(TYPE).getString(DESCRIPTION));
                tsr.setSubTypeId(statement.getJSONObject(SUB_TYPE).getString(ID));
                tsr.setSubTypeDescription(statement.getJSONObject(SUB_TYPE)
                        .getString(DESCRIPTION));

                tsr.setSettled(String.valueOf(statement.getBoolean(SETTLED)));
                tsr.setDate(DateFormat.getDateTimeInstance()
                        .format(new Date(statement.getLong(DATE))));
                tsr.setDescription(statement.getString(DESCRIPTION));

                tsr.setDebitDecimal(statement.getJSONObject(DEBIT).getString(DECIMAL));
                tsr.setDebitFormatted(statement.getJSONObject(DEBIT).getString(FORMATTED));

                tsr.setCreditDecimal(statement.getJSONObject(CREDIT).getString(DECIMAL));
                tsr.setCreditFormatted(statement.getJSONObject(CREDIT).getString(FORMATTED));

                alTransactions.add(tsr);

            }
            StatementsDb db = new StatementsDb(context);
            db.deleteTransactions();
            db.insertTransactions(alTransactions);
            }else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return alTransactions;
    }

}
