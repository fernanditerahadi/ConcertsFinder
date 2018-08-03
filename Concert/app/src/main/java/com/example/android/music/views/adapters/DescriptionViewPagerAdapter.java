package com.example.android.music.views.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.music.R;
import com.example.android.music.views.fragments.TracksFragment;
import com.example.android.music.views.fragments.VenueFragment;

public class DescriptionViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private final static int PAGE_COUNT = 2;

    public DescriptionViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new VenueFragment();
        }
        if (position == 1) {
            return new TracksFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.title_description_venue);
        }
        if (position == 1) {
            return mContext.getString(R.string.title_description_toptracks);
        }
        return null;
    }
}
