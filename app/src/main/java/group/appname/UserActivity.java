package group.appname;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import group.appname.adapter.NavDrawerListAdapter;
import group.appname.model.NavDrawerItem;

/*
Following are the major steps need take care of in the navigation drawer activity.
    > Creating a NavDrawerListAdapter instance and adding list items.
    > Assigning the navAdapter to Navigation Drawer ListView
    > Creating click event listener for list items
    > Creating and displaying fragment activities on selecting list item.

 */

public class UserActivity extends Activity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    //nav drawer title
    //private CharSequence mDrawerTitle;

    //user activity title
    private CharSequence userTitle;

    //user's item counter
    private int userCounter;

    //slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter navAdapter;

    private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_screen);

        //mTitle = getUserName();
        userTitle = "User Name";

        //userCounter=getInventoryCount();
        userCounter = 12;

        //set Activity title
        setTitle(userTitle);

        //List View in Frame Layout
        listView = (ListView) findViewById(R.id.user_list_items);

        //Lists of items recommended to user
        //String[] items = getRecommendedItems();
        String[] items = new String[] {"Item 1", "Item 2", "Item 3",
                                        "Item 4", "Item 5", "Item 6",
                                        "Item 7", "Item 8", "Item 9",
                                        "Item 10", "Item 11", "Item 12"};
        ArrayAdapter<String> listViewAdapter =
                new ArrayAdapter<String>(this, R.layout.list_item, R.id.item_title, items);

        listView.setAdapter(listViewAdapter);

        //load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        //nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.user_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        //adding nav drawer items to array
        //Account Settings
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        //Inventory - need a counter here
        //Build a method to get the user inventory counter
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1), true, userCounter));
        //Sign out
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

        //Recycle the typed array
        navMenuIcons.recycle();

        //setting the nav drawer list navAdapter
        navAdapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        drawerList.setAdapter(navAdapter);

        //enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                        R.drawable.ic_drawer, //nav menu toggle icon
                        R.string.app_name, //nav drawer open - description for accessibility
                        R.string.app_name //nav drawer close
        ){
            public void onDrawerClosed(View view){
                getActionBar().setTitle(userTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView){
                //getActionBar().setTitle(mDrawerTitle);
                getActionBar().setTitle(userTitle);

                //hide soft keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                // calling onPreparedOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);


//        if (savedInstanceState == null){
//            // on first time display view for first nav item
//            displayView(0);
//        }

        drawerList.setOnItemClickListener(new SlideMenuClickListener());
	}

    private class SlideMenuClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //displayView(position);
            switch(position){
                case 1:
                    toInventory();
                    break;
                default:
                    break;
            }
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.menu_new_search, menu);

        //Set Action Bar Icon
        //ActionBar actionBar = getActionBar();
        getActionBar().setIcon(R.drawable.app_icon);

        // Associate searchable configuration with the SearchView
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
        //        .getActionView();
        //searchView.setSearchableInfo(searchManager
        //        .getSearchableInfo(getComponentName()));

		  return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // toggle nav drawer on selecting action bar app icon/title
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        //Handle action bar actions click
        switch (item.getItemId()){
            case R.id.action_new:

            case R.id.action_search:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Called when invalidateOptionsMenu() is triggered
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        // if nav drawer is opened, hide the menu items
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        menu.findItem(R.id.action_new).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

//    @Override
//    public void setTitle(CharSequence title){
//        mTitle = title;
//        getActionBar().setTitle(mTitle + " (" + Integer.toString(userCounter) + ")");
//    }

    /*
     * When using the ActionBarDrawerToggle, must call it during onPostCreate() and
     * onConfigurationChanged() ...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void toProductDetail(View v){
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("fromUserActivity", 0); // 0 = UserActivity
        startActivity(intent);
    }

//    private void displayView(int position){
//        Fragment fragment = null;
//        switch(position){
//            case 1:
//                fragment = new InventoryFragment();
//                break;
//            default:
//                break;
//        }
//
//        if (fragment != null){
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.user_frame_container, fragment).commit();
//
//            //update selected item and title, then close the drawer
//            drawerList.setItemChecked(position, true);
//            drawerList.setSelection(position);
//            setTitle(navMenuTitles[position]);
//            drawerLayout.closeDrawer(drawerList);
//        } else{
//            //error in creating fragment
//            Log.e("UserActivity", "Error in creating fragment");
//        }
//    }

    private void toInventory(){
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.putExtra("PreActivity", this.getClass().getSimpleName());
        startActivity(intent);
    }

}
