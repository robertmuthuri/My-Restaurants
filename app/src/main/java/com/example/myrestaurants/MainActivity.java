package com.example.myrestaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mFindRestaurantsButton; //adds a member variable to hold our button so that we can access it inside all of our methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      // causes android to run all the default behaviours for an activity
        setContentView(R.layout.activity_main); // tells the activity which layout to use on the device screen

        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton); // set the button variable

        mFindRestaurantsButton.setOnClickListener(new View.OnClickListener() { // adds a click listener which will be triggered when our button is touched
            @Override
            public void onClick(View v) {
                // adds a toast  - a simple pop up message that automatically fades in and out of the screen when triggered.
                // a toast takes three parameters - a context - a message - a duration
                // context - (MainActivity.this);
                Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_LONG).show();
            }
        });
    }
}
