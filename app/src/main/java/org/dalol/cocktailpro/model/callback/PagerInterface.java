package org.dalol.cocktailpro.model.callback;

import android.support.v4.app.Fragment;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/13/2016
 */
public interface PagerInterface {

    Fragment getItem(int position);

    CharSequence getPageTitle(int position);

    int getCount();
}
