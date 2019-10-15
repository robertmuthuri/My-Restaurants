package com.example.myrestaurants.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myrestaurants.models.Business;
import com.example.myrestaurants.ui.RestaurantDetailFragment;

import java.util.List;

public class RestaurantPageAdapter extends FragmentPagerAdapter {
    private List<Business> mRestaurants;

    public RestaurantPageAdapter(FragmentManager fm, int behaviour, List<Business> restaurants) {
        super(fm, behaviour);
        mRestaurants = restaurants;
    }
    @Override public Fragment getItem(int position) { return RestaurantDetailFragment.newInstance(mRestaurants.get(position)); }

    @Override public int getCount() { return mRestaurants.size(); }



}
