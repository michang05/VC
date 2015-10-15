
package mobi.victorchandler;

import mobi.victorchandler.database.FavsDB;
import mobi.victorchandler.preferences.BasePreferences;
import mobi.victorchandler.view.ActionBar;
import mobi.victorchandler.view.ActionBar.Action;
import mobi.victorchandler.view.ActionBar.IntentAction;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Screen for Listing the Favorites added from the events or sports.
 * 
 * @author riveram
 *
 */
public class Favorites extends ListActivity {

    // context menu
    private static final int REMOVE_ITEM = Menu.FIRST;
    // options menu
    private static final int REMOVE_ALL = Menu.FIRST;

    private ArrayAdapter<String> aa;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.favorites);
        setActionBar();

        BasePreferences.load(this);
      
        aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        getFavorites();
        setListAdapter(aa);

        registerForContextMenu(getListView());
    }

    public void setActionBar() {

        actionBar = (ActionBar) findViewById(R.id.actionbar);

        actionBar.setHomeAction(new IntentAction(this, Home.createIntent(this), R.drawable.home));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Favorites");

        Action accountAction = new IntentAction(this, new Intent(this, MyAccountHome.class),
                R.drawable.account);
        actionBar.addAction(accountAction);

        Intent betslip = new Intent(this, BetSlip.class);
        Action betSlipAction = new IntentAction(this, betslip,
                R.drawable.betslip);

        actionBar.addAction(betSlipAction);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, REMOVE_ITEM, Menu.NONE, "Remove");

    }

    @Override
    protected void onStart() {
        aa.clear();
        aa.notifyDataSetChanged();
        getFavorites();
        setListAdapter(aa);
        super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
        aa.clear();
        aa.notifyDataSetChanged();
        getFavorites();
        setListAdapter(aa);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterContextMenuInfo adapterMenuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
        int pos = adapterMenuInfo.position;

        switch (item.getItemId()) {
            case REMOVE_ITEM:

                // Clear Favorites
                FavsDB favs = new FavsDB(this);
                favs.open();
                favs.removeFavoriteFromDb(getListAdapter().getItem(pos).toString());
                favs.close();
                aa.clear();
                aa.notifyDataSetChanged();
                getFavorites();
                setListAdapter(aa);

                return true;

        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        Log.d("ListItemClick", "here: " + l.getItemAtPosition(position).toString());
        FavsDB fav = new FavsDB(this);
        fav.open();
        Cursor cursor;
        try {
            cursor = fav.getFavorite(l.getItemAtPosition(position).toString());

            final String sportid = cursor.getString(FavsDB.SPORTID_COLUMN);
            final String meetingid = cursor.getString(FavsDB.MEETINGID_COLUMN);
            final String title = cursor.getString(FavsDB.TITLE_COLUMN);
            cursor.close();
            fav.close();
            Intent intent = new Intent();
            intent.putExtra("sport", sportid);

            if (meetingid.equals("-1")) {
                intent.putExtra("sportname", title);
                intent.setClass(this, Meetings.class);

            } else {
                intent.putExtra("meetingname", title);
                intent.putExtra("meeting", meetingid);
                intent.setClass(this, Events.class);

            }

            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Current Favorite is not available", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * method for getting the favorites from the database
     */
    private void getFavorites() {
        FavsDB db = new FavsDB(this);
        db.open();
        Cursor cursor = db.getCursor();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            aa.add(cursor.getString(FavsDB.TITLE_COLUMN));

            cursor.moveToNext();

        }
        cursor.close();
        db.close();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(aa.isEmpty()) {
          menu.clear();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, REMOVE_ALL, Menu.NONE, "Remove All");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case REMOVE_ALL:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to remove all?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Clear Favorites
                                FavsDB favs = new FavsDB(Favorites.this);
                                favs.open();
                                favs.removeAllFavorites();
                                favs.close();
                                aa.clear();
                                aa.notifyDataSetChanged();
                                getFavorites();
                                setListAdapter(aa);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create();
                AlertDialog alert = builder.create();
                alert.setTitle("Favorites");
                alert.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
                alert.show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
