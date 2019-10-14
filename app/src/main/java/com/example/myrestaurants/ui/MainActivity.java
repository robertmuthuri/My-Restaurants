package com.example.myrestaurants.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myrestaurants.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//  private Button mFindRestaurantsButton; //adds a member variable to hold our button so that we can access it inside all of our methods
    @BindView(R.id.findRestaurantsButton) Button mFindRestaurantsButton;
    public static final String TAG = MainActivity.class.getSimpleName();
//  private EditText mLocationEditText; // initialize a new member variable for our EditText
    @BindView(R.id.locationEditText) EditText mLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      // causes android to run all the default behaviours for an activity
        setContentView(R.layout.activity_main); // tells the activity which layout to use on the device screen
        ButterKnife.bind(this);

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

            // add a toast  - a simple pop up message that automatically fades in and out of the screen when triggered.
            // a toast takes three parameters - a context - a message - a duration
            // context - (MainActivity.this);
    //                Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_LONG).show();

            // Add an intent - something our app "intends to do: by describing a simple action you'd like the app to perform; mostly used to start another activity
            Intent myIntent = new Intent(MainActivity.this, RestaurantsActivity.class); // initiate an instance of the Intent class with the current and next contexts
            myIntent.putExtra("location", location);
            startActivity(myIntent); // executes the intent
    //                Toast.makeText(MainActivity.this, location, Toast.LENGTH_LONG).show(); // makes a toast to confirm that zip location keyed in.

        }
    }
}
