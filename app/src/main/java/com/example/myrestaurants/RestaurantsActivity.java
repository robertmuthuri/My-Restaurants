package com.example.myrestaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantsActivity extends AppCompatActivity {
//    private TextView mLocationTextView; // declare member variable
    @BindView(R.id.locationTextView) TextView mLocationTextView;

//    private ListView mListView; // declare an mListView member variable
    @BindView(R.id.listView) ListView mListView;

//    private String[] restaurants = new String[] {"Mi Mero Mole", "Mother's Bistro", "life of Pie", "Screen door", "Luc Lac", "Sweet Basil", "Slappy Cakes", "Equinox", "Miss Delta's", "Andina", "Lardo", "Portland City Grill", "Fat Head's Brewery", "Chipotle", "Subway"};
//    private String[] cuisines = new String[] {"Vegan Food", "Breakfast", "Fish Dishs", "Scandinavian", "Coffee", "English Food", "Burgers", "Fast Food", "Noodle Soups", "Mexican", "BBQ", "Cuban", "Bar Food", "Sports Bar", "Breakfast", "Mexican"};

    public static final String TAG = RestaurantsActivity.class.getSimpleName(); // Defines a tag constant to use in an activity's log methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

//        mListView = (ListView) findViewById(R.id.listView); // defines the mListView variable by locating it's specific id using the findViewById() method
//        mLocationTextView = (TextView) findViewById(R.id.locationTextView); // define the member variable

//        MyRestaurantsArrayAdapter adapter = new MyRestaurantsArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants, cuisines); // creates a new array adapter with three arguments: current context, list layout, array list.
//        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String restraurant = ((TextView)view).getText().toString();

                Toast.makeText(RestaurantsActivity.this, restraurant, Toast.LENGTH_LONG).show();
//                Log.v("RestaurantActivity", "In the onItemClickListener!"); // when the code is triggered - a click on a restaurant - the message "In the onItemClickListener appears in the logcat.
//                Log.v(TAG, "In the onItemClickListener!"); // when the code is triggered - a click on a restaurant - the message "In the onItemClickListener appears in the logcat.
            }
        });

        // pull the data out of the intent extra
        Intent intent = getIntent(); // recreates the intent
        String location = intent.getStringExtra("location"); // pulls out the location value based on the key value we provided.
        mLocationTextView.setText("Here are all the restaurants near: " + location);

//        Log.d("RestaurantActivity", "In the onCreate method!"); // a second log of different importance level
//        Log.d(TAG, "In the onCreate method!"); // a second log of different importance level

        YelpApi client = YelpClient.getClient();
        Call<YelpBusinessesSearchResponse> call = client.getRestaurants(location, "restaurants");

        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
                if (response.isSuccessful()) {
                    List<Business> restaurantsList = response.body().getBusinesses();
                    String[] restaurants = new String[restaurantsList.size()];
                    String[] categories = new String[restaurantsList.size()];

                    for (int i = 0; i < restaurants.length; i++) {
                        restaurants[i] = restaurantsList.get(i).getName();
                    }
                    for (int i = 0; i < categories.length; i++) {
                        Category category = restaurantsList.get(i).getCategories().get(0);
                        categories[i] = category.getTitle();
                    }
                    ArrayAdapter adapter = new MyRestaurantsArrayAdapter(RestaurantsActivity.this, android.R.layout.simple_list_item_1, restaurants, categories);
                    mListView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
            }
        });
    }

}
