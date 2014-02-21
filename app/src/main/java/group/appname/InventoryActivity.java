package group.appname;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class InventoryActivity extends ListActivity {
    CharSequence mTitle = "Inventory";
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_inventory);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
        //listView = (ListView)findViewById(R.id.inventory_list_items);

        //Lists of items recommended to user
        //String[] items = getInventoryItems();
        String[] items = new String[] {"Inventory 1", "Inventory 2", "Inventory 3",
                "Inventory 4", "Inventory 5", "Inventory 6",
                "Inventory 7", "Inventory 8", "Inventory 9",};

        counter = items.length;
        setTitle(mTitle + " (" + counter + ")");

        ArrayAdapter<String> listViewAdapter =
                new ArrayAdapter<String>(this, R.layout.inventory_list_item, R.id.inventory_item_title, items);
        setListAdapter(listViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    public void toProductActivity(View v){
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("fromUserActivity", 1); // 1 = InventoryActivity
        startActivity(intent);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);
//            return rootView;
//        }
//    }

}
