package org.dalol.cocktailpro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.model.adapter.DetailPagerAdapter;
import org.dalol.cocktailpro.view.fragment.TabFragment;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/13/2016
 */
public class DetailActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DetailPagerAdapter mAdapter;
    private int[] tabIcons = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    @Override
    protected void onViewSetupCompleted() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        configPager();
        //configTabLayout();
    }

    private void configTabLayout() {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void configPager() {
        mAdapter = new DetailPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new TabFragment(), "RECIPE");
        mAdapter.addFragment(new TabFragment(), "PREPARATION");
        mAdapter.addFragment(new TabFragment(), "HISTORY");
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void configViews(Intent intent, Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    @Override
    protected Toolbar getToolbar() {
        mToolbar.setTitle("Name");
        return mToolbar;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
