package com.example.myrestaurants.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.myrestaurants.R;
import com.example.myrestaurants.adapters.FirebaseRestaurantViewHolder;
import com.example.myrestaurants.models.Business;
import com.example.myrestaurants.models.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRestaurantListActivity extends AppCompatActivity {
    // Initialize db reference, recycler adapter, and recycler view.
    private DatabaseReference mRestaurantsReference;
    private FirebaseRecyclerAdapter<Business, FirebaseRestaurantViewHolder> mFirebaseAdapter;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pass activity_restaurants layout to display the correct layout
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        // Set the db reference using the restaurants child node key in Constants class
        mRestaurantsReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESTAURANTS);
        setUpFirebaseAdapter();
        hideProgressBar();
    }

    // Create method to set up Firebase Adapter;
    private void setUpFirebaseAdapter() {

        // Create a Firebase Recycler options object, cast into the model class
        FirebaseRecyclerOptions<Business> options = new FirebaseRecyclerOptions.Builder<Business>()
                // Build the object by setting the query (or db ref); pass the db-ref and the model class the object will be parsed into;
                .setQuery(mRestaurantsReference, Business.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Business, FirebaseRestaurantViewHolder>(options) {
            // Call the bindRestaurant() method on the viewholder to set the appropriate text and image with the given restaurant
            @Override
            protected void onBindViewHolder(@NonNull FirebaseRestaurantViewHolder firebaseRestaurantViewHolder, int position, @NonNull Business restaurant) {
                firebaseRestaurantViewHolder.bindRestaurant(restaurant);
            }

            @NonNull
            @Override
            public FirebaseRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
                return new FirebaseRestaurantViewHolder(view);
            }
        };
        // Set the adapter on the recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }
    // Clean up db on stop so adapter stops listening for changes.
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mFirebaseAdapter != null) {
            mFirebaseAdapter.stopListening();
        }
    }
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

}
