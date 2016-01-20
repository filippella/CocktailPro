package org.dalol.cocktailpro.model.callback;

import android.support.v4.app.Fragment;

/**
 * Created by Filippo-TheAppExpert on 1/14/2016.
 */
public interface PagerInterface {

    Fragment getItem(int position);

    CharSequence getPageTitle(int position);

    int getCount();
}
