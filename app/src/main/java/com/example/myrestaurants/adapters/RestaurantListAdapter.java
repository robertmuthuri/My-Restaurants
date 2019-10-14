package com.example.myrestaurants.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurants.models.Business;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    private List<Business> mRestaurants;
    private Context mContext;
    public RestaurantListAdapter(Context context, List<Business> restaurants) {
        mContext = context;
        mRestaurants = restaurants;
    }
}
