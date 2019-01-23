package com.example.wdd_vip.jelaja.UserView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int tabNumber;

    public PagerAdapter(FragmentManager fm, int numOfTab)
    {
        super(fm);
        this.tabNumber = numOfTab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                TabVehicle vehicle = new TabVehicle();
                return vehicle;
            case 1:
                TabHotel hotel = new TabHotel();
                return hotel;
            case 2:
                TabResort resort = new TabResort();
                return resort;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }

}
