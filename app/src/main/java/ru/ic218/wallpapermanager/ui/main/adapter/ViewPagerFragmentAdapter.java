package ru.ic218.wallpapermanager.ui.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Nikolay Vlaskin on 12.03.2018.
 */

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private List<String> title;
    private List<Fragment> fragments;

    public ViewPagerFragmentAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm);
        this.title = title;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
