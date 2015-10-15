
package mobi.victorchandler.task;

import mobi.victorchandler.R;
import mobi.victorchandler.TransactionsTab;
import mobi.victorchandler.database.StatementsDb;
import mobi.victorchandler.response.TransactionStatementResponse;
import mobi.victorchandler.webservice.TransactionStatementsService;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
/**
 * Task for Transactions service
 * @author riveram
 *
 */
public class GetTransactionsTask extends
        AsyncTask<Void, Void, ArrayList<TransactionStatementResponse>> {

    private TransactionsTab transTab;
    private TransactionStatementsService service;
    private ProgressDialog progressDialog;
    private ArrayList<HashMap<String, String>> groupData;
    private ArrayList<ArrayList<HashMap<String, String>>> childData;
    private ArrayList<TransactionStatementResponse> sortList;

    public GetTransactionsTask(TransactionsTab transTab) {
        this.transTab = transTab;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        progressDialog = new ProgressDialog(transTab);
        progressDialog.setMessage("Fetching Transactions ...");
        progressDialog.show();

        service = new TransactionStatementsService(transTab);
    }

    @Override
    protected ArrayList<TransactionStatementResponse> doInBackground(Void... params) {
        ArrayList<TransactionStatementResponse> alTrans;
        if(service.executeRequest()) {
           alTrans  = service.getAlTransactions();
        }else {
           alTrans = null;
        }
        return alTrans;
    }

    private void reOrderData(ArrayList<TransactionStatementResponse> result) {
        groupData = new ArrayList<HashMap<String, String>>();

        childData = new ArrayList<ArrayList<HashMap<String, String>>>();

        // Sort the Region
        sortList = new ArrayList<TransactionStatementResponse>();
        for (TransactionStatementResponse mr : result) {
            sortList.add(mr);
        }

        final Comparator<TransactionStatementResponse> comparatorRegion = new Comparator<TransactionStatementResponse>() {
            @Override
            public int compare(TransactionStatementResponse object1,
                    TransactionStatementResponse object2) {
                return object1.getTypeDescription().compareTo(object2.getTypeDescription());
            }
        };

        Collections.sort(sortList, comparatorRegion);

        for (TransactionStatementResponse tsr : sortList) {
            HashMap<String, String> mapGroupUnfound = new HashMap<String, String>();
            mapGroupUnfound.put(StatementsDb.KEY_DATE, tsr.getDate());
            mapGroupUnfound.put(StatementsDb.KEY_TYPE_DESCRIPTION,
                    tsr.getTypeDescription());
            mapGroupUnfound.put(StatementsDb.KEY_DEBIT_FORMATTED,
                    tsr.getDebitFormatted());
            mapGroupUnfound.put(StatementsDb.KEY_CREDIT_FORMATTED,
                    tsr.getCreditFormatted());
            groupData.add(mapGroupUnfound);

            ArrayList<HashMap<String, String>> listChild = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> mapChild = new HashMap<String, String>();
            mapChild.put(StatementsDb.KEY_SUBTYPE_DESCRIPTION,
                    tsr.getSubTypeDescription());
            mapChild.put(StatementsDb.KEY_DESCRIPTION, tsr.getDescription());

            listChild.add(mapChild);
            childData.add(listChild);

        }
    }

    @Override
    protected void onPostExecute(ArrayList<TransactionStatementResponse> result) {

        super.onPostExecute(result);
        
        
        
        if (result == null) {
            Toast.makeText(transTab, transTab.getString(R.string.service_unavailable),
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        if (service.getAlTransactions().size() == 0) {
            new GetTransactionsTask(transTab).execute();

        } else {
            reOrderData(result);

            SimpleExpandableListAdapter settleAdapter = new
                    SimpleExpandableListAdapter(transTab, groupData,
                            R.layout.transaction_statement_group, new String[] {
                                    StatementsDb.KEY_DATE,
                                    StatementsDb.KEY_TYPE_DESCRIPTION,
                                    StatementsDb.KEY_DEBIT_FORMATTED,
                                    StatementsDb.KEY_CREDIT_FORMATTED
                            }, new int[] {
                                    R.id.tvStatementDate,
                                    R.id.tvStatementTypeDescription,
                                    R.id.tvBsgDebit,
                                    R.id.tvBsgCredit

                            }, childData, R.layout.transaction_statements_child, new String[] {
                                    StatementsDb.KEY_SUBTYPE_DESCRIPTION,
                                    StatementsDb.KEY_DESCRIPTION
                            }, new int[] {
                                    R.id.tvTransStatementSubTypeDescription,
                                    R.id.tvTransactionStatementDescription

                            });
            settleAdapter.notifyDataSetInvalidated();
            transTab.setListAdapter(settleAdapter);
        }
        progressDialog.dismiss();
    }
}
