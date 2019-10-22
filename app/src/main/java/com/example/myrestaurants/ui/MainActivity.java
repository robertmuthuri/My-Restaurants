package com.example.myrestaurants.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myrestaurants.R;
import com.example.myrestaurants.models.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    // Adds instance of the searchedLocations DatabaseReference
    private DatabaseReference mSearchedLocationReferences;
    // Create member variables to store refs to shared preferences tool itself
//    private SharedPreferences mSharedPreferences;
    // Create the dedicated tool to edit the foregoing variables.
//    private SharedPreferences.Editor mEditor;

//  private Button mFindRestaurantsButton; //adds a member variable to hold our button so that we can access it inside all of our methods
    @BindView(R.id.findRestaurantsButton) Button mFindRestaurantsButton;
    public static final String TAG = MainActivity.class.getSimpleName();
//  private EditText mLocationEditText; // initialize a new member variable for our EditText
    @BindView(R.id.locationEditText) EditText mLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Instantiates database reference passing the constant as argument
        databaseReference.child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

        super.onCreate(savedInstanceState);      // causes android to run all the default behaviours for an activity
        setContentView(R.layout.activity_main); // tells the activity which layout to use on the device screen
        ButterKnife.bind(this);

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();

//        mLocationEditText = (EditText) findViewById(R.id.locationEditText); //set the EditText variable
//        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton); // set the button variable

//        mFindRestaurantsButton.setOnClickListener(new View.OnClickListener() { // adds a click listener which will be triggered when our button is touched
        mFindRestaurantsButton.setOnClickListener(this); // current context now includes the listener interface, so just pass the current context as argument
//        });
    }
    @Override
    public void onClick(View v) {
        if (v == mFindRestaurantsButton) {
            String location = mLocationEditText.getText().toString(); // use the getText() method to grab the input value of our EditText view and save to location
            Log.d(TAG, location); // log the input text
            saveLocationToFirebase(location);
//            if (!(location).equals("")) {
//                addToSharedPreferences(location);
//            }

            // add a toast  - a simple pop up message that automatically fades in and out of the screen when triggered.
            // a toast takes three parameters - a context - a message - a duration
            // context - (MainActivity.this);
    //                Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_LONG).show();

            // Add an intent - something our app "intends to do: by describing a simple action you'd like the app to perform; mostly used to start another activity
            Intent myIntent = new Intent(MainActivity.this, RestaurantsListActivity.class); // initiate an instance of the Intent class with the current and next contexts
            myIntent.putExtra("location", Parcels.wrap(location));
            startActivity(myIntent); // executes the intent
    //                Toast.makeText(MainActivity.this, location, Toast.LENGTH_LONG).show(); // makes a toast to confirm that zip location keyed in.

        }
    }
    // Create method which takes user input zip-code as argument
//    private void addToSharedPreferences(String location) {
//        // Provide editor with the key stored in Constants file and the zip code location passed as argument
//        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
//    }

    // Create method to save location to database
    public void saveLocationToFirebase(String location) {
        databaseReference.push().setValue(location);
    }
}
