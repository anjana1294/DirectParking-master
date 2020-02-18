package com.directparking.app.ui.history.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.directparking.app.ui.history.model.HistoryRideResponse;
import com.directparking.app.ui.history.rides.MyRidesFragment;
import com.directparking.app.ui.history.trips.MyTripsFragment;

/**
 * Created by root on 10/8/18.
 */

public class HistoryPagerAdapter extends FragmentPagerAdapter {
    HistoryRideResponse historyRideData;
    String tablayoutLabel[] = {"My Trips", "My Rides"};

    public HistoryPagerAdapter(FragmentManager fm, HistoryRideResponse historyRideData) {
        super(fm);
        this.historyRideData = historyRideData;
    }

    @Override
    public int getCount() {
        return tablayoutLabel.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MyTripsFragment.newInstance(historyRideData.getTripData());
            case 1:
                return MyRidesFragment.newInstance(historyRideData.getRideData());
        }
        return MyTripsFragment.newInstance(historyRideData.getTripData());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tablayoutLabel[position];
    }
}
