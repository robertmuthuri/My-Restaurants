package com.example.myrestaurants;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyRestaurantsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mRestuarants;
    private String[] mCuisines;

    public MyRestaurantsArrayAdapter(Context mContext, int resource, String[] mRestuarants, String[] mCuisines) {
        super(mContext, resource);          //  Information about layout utilized
        this.mContext = mContext;           // passes the current context
        this.mRestuarants = mRestuarants;   // data storage
        this.mCuisines = mCuisines;         // data storage
    }

    @Override
    public Object getItem(int position) {
        String restaurant = mRestuarants[position];
        String cuisine = mCuisines[position];
        return String.format("%s \nServes great: %s", restaurant, cuisine);
    }

    @Override
    public int getCount() {
        return mRestuarants.length;
    }
}
