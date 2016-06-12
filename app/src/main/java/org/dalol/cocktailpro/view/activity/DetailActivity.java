package org.dalol.cocktailpro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.base.BaseActivity;
import org.dalol.cocktailpro.model.adapter.DetailPagerAdapter;
import org.dalol.cocktailpro.view.fragment.TabFragment;
import org.dalol.model.cocktailpro.cocktail.Cocktail;
import org.dalol.model.cocktailpro.cocktail.CocktailItem;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/13/2016
 */
public class DetailActivity extends BaseActivity {


    public static final String EXTRA_ITEM = "DetailActivity:item";

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configViews();
        onViewSetupCompleted();


        ImageView image = (ImageView) findViewById(R.id.image);

        ViewCompat.setTransitionName(image, EXTRA_ITEM);

        CocktailItem item = (CocktailItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        Cocktail cocktail = item.getCocktail();

        getSupportActionBar().setTitle(cocktail.getName());

        Picasso.with(this).load(item.getImageId()).into(image);
    }

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

    protected void configViews() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    @Override
    protected int getContentView() {
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

    public static void launch(BaseActivity activity, View transitionView, CocktailItem item) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_ITEM);
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_ITEM, item);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
