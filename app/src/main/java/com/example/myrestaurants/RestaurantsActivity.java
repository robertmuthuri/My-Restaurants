package com.example.myrestaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RestaurantsActivity extends AppCompatActivity {
    private TextView mLocationTextView; // declare member variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        mLocationTextView = (TextView) findViewById(R.id.locationTextView); // define the member variable

        // pull the data out of the intent extra
        Intent intent = getIntent(); // recreates the intent
        String location = intent.getStringExtra("location"); // pulls out the location value based on the key value we provided.
        mLocationTextView.setText("Here are all the restaurants near: " + location);
    }

}
