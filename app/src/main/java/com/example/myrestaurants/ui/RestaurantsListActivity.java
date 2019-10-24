package com.example.myrestaurants.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myrestaurants.R;
import com.example.myrestaurants.adapters.RestaurantListAdapter;
import com.example.myrestaurants.models.Business;
import com.example.myrestaurants.models.Constants;
import com.example.myrestaurants.models.Location;
import com.example.myrestaurants.models.YelpBusinessesSearchResponse;
import com.example.myrestaurants.network.YelpApi;
import com.example.myrestaurants.network.YelpClient;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantsListActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private String mRecentAddress;
    private SharedPreferences.Editor mEditor;

//    private TextView mLocationTextView; // declare member variable
//    @BindView(R.id.locationTextView) TextView mLocationTextView;
//    private ListView mListView; // declare an mListView member variable
//    @BindView(R.id.listView) ListView mListView;
//    replace the above binds with the one below
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.errorTextView) TextView mErrorTextView;

    private RestaurantListAdapter mAdapter;
    public List<Business> restaurants;

//    private String[] restaurants = new String[] {"Mi Mero Mole", "Mother's Bistro", "life of Pie", "Screen door", "Luc Lac", "Sweet Basil", "Slappy Cakes", "Equinox", "Miss Delta's", "Andina", "Lardo", "Portland City Grill", "Fat Head's Brewery", "Chipotle", "Subway"};
//    private String[] cuisines = new String[] {"Vegan Food", "Breakfast", "Fish Dishs", "Scandinavian", "Coffee", "English Food", "Burgers", "Fast Food", "Noodle Soups", "Mexican", "BBQ", "Cuban", "Bar Food", "Sports Bar", "Breakfast", "Mexican"};

    public static final String TAG = RestaurantsListActivity.class.getSimpleName(); // Defines a tag constant to use in an activity's log methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

//        mListView = (ListView) findViewById(R.id.listView); // defines the mListView variable by locating it's specific id using the findViewById() method
//        mLocationTextView = (TextView) findViewById(R.id.locationTextView); // define the member variable

//        MyRestaurantsArrayAdapter adapter = new MyRestaurantsArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants, cuisines); // creates a new array adapter with three arguments: current context, list layout, array list.
//        mListView.setAdapter(adapter);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String restaurant = ((TextView)view).getText().toString();
//
//                Toast.makeText(RestaurantsListActivity.this, restaurant, Toast.LENGTH_LONG).show();
////                Log.v("RestaurantActivity", "In the onItemClickListener!"); // when the code is triggered - a click on a restaurant - the message "In the onItemClickListener appears in the logcat.
////                Log.v(TAG, "In the onItemClickListener!"); // when the code is triggered - a click on a restaurant - the message "In the onItemClickListener appears in the logcat.
//            }
//        });

        // pull the data out of the intent extra
//        Intent intent = Parcels.unwrap(getIntent().getParcelableExtra("location")); // recreates the intent
        Intent intent = getIntent();
//        String myLocation = intent.getParcelable("location"); // pulls out the location value based on the key value we provided.
        String myLocation = Parcels.unwrap(intent.getParcelableExtra("location")); // pulls out the location value based on the key value we provided.

        getRestaurants(myLocation);
//        mLocationTextView.setText("Here are all the restaurants near: " + location);

//        Log.d("RestaurantActivity", "In the onCreate method!"); // a second log of different importance level
//        Log.d(TAG, "In the onCreate method!"); // a second log of different importance level

        // Call the dedicated preference manager to access shared preferences
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
//        Log.d("Shared Pref Location", "onCreate: " + mRecentAddress);
        if (mRecentAddress != null) {
//            call = client.getRestaurants(String.valueOf(mRecentAddress), "restaurants");
            getRestaurants(mRecentAddress);
        }
    }
    private void getRestaurants(String myLocation) {
        YelpApi client = YelpClient.getClient();
        Call<YelpBusinessesSearchResponse> call = client.getRestaurants(String.valueOf(myLocation), "restaurants");
        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
//                    List<Business> restaurantsList = response.body().getBusinesses();
//                    String[] restaurants = new String[restaurantsList.size()];
//                    String[] categories = new String[restaurantsList.size()];
//
//                    for (int i = 0; i < restaurants.length; i++) {
//                        restaurants[i] = restaurantsList.get(i).getName();
//                    }
//                    for (int i = 0; i < categories.length; i++) {
//                        Category category = restaurantsList.get(i).getCategories().get(0);
//                        categories[i] = category.getTitle();
//                    }
//                    ArrayAdapter adapter = new MyRestaurantsArrayAdapter(RestaurantsListActivity.this, android.R.layout.simple_list_item_1, restaurants, categories);
//                    mListView.setAdapter(adapter);
                    restaurants = response.body().getBusinesses();
                    //instantiate adapter
                    mAdapter = new RestaurantListAdapter(RestaurantsListActivity.this, restaurants);
                    //associate adapter with recycle view
                    mRecyclerView.setAdapter(mAdapter);
                    // Instantiate layout manager
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RestaurantsListActivity.this);
                    // Assign layout manager to overridden response method
                    mRecyclerView.setLayoutManager(layoutManager);
                    // inform mRecyclerView that its width and height should always remain the same so it doesn't reset its own size to best fit the content as individual list item views are continually recycled.
                    mRecyclerView.setHasFixedSize(true);
                    showRestaurants();
                } else {
                    showUnsuccessfulMessage();
                }
            }
            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                hideProgressBar();
                showFailureMessage();
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getRestaurants(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }
    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }
    private void showRestaurants() {
//        mListView.setVisibility(View.VISIBLE);
//        mLocationTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void addToSharedPreferences(String location){
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}
