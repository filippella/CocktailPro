package org.dalol.cocktailpro.model.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import org.dalol.cocktailpro.model.callback.PagerInterface;

/**
 * Created by Filippo-TheAppExpert on 1/14/2016.
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private PagerInterface mPagerListener;
    private Fragment primary;

    public ImagePagerAdapter(PagerInterface parent, FragmentManager fragmentManager) {
        super(fragmentManager);
        mPagerListener = parent;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        primary = (Fragment) object;
    }

    public void setUserVisibleHint(boolean isVisible) {
        if (primary != null) {
            primary.setUserVisibleHint(isVisible);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mPagerListener.getItem(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPagerListener.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return mPagerListener.getCount();
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO: don't recreate fragments that exist
        return POSITION_NONE;
    }
}
