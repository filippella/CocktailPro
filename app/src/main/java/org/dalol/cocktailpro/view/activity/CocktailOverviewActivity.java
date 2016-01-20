package org.dalol.cocktailpro.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.model.adapter.ImagePagerAdapter;
import org.dalol.cocktailpro.model.callback.PagerInterface;
import org.dalol.cocktailpro.view.fragment.ImageFragment;

/**
 * Created by Filippo-TheAppExpert on 1/15/2016.
 */
public class CocktailOverviewActivity extends BaseActivity implements PagerInterface {

    private Toolbar mToolbar;
    private ViewPager mPager;
    private final static Integer[] mImageIds = {R.drawable.bg, R.drawable.cocktail_bg, R.drawable.bg, R.drawable.cocktail_bg};
    private final static int[] COLORS = {R.color.light_purple, R.color.light_orange, R.color.purple, R.color.light_blue1};
    private TextView mCounter;
    private int mTotalCount;
    private Button mHowTo;

    @Override
    protected void onViewSetupCompleted() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mTotalCount = mImageIds.length;
        mPager.setAdapter(new ImagePagerAdapter(this, getSupportFragmentManager()));
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCounter.setText((position + 1) + " of " + mTotalCount);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                int color = getResources().getColor(COLORS[position]);


                if (positionOffset > 0f && position < mTotalCount) {
                    int nextColor = getResources().getColor(COLORS[(position + 1)]);
                    if (color != nextColor) {
                        color = blendColors(nextColor, color, positionOffset);
                    }
                    //mCounter.setBackgroundColor(color);
                }
            }
        });

        mCounter.setText("1 of " + mTotalCount);

        mPager.setCurrentItem(mPager.getCurrentItem());

        mHowTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Show How", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CocktailOverviewActivity.this, CocktailDetailActivity.class));
            }
        });

    }

    @Override
    protected void configViews(Intent intent, Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCounter = (TextView) findViewById(R.id.counter);
        mPager = (ViewPager) findViewById(R.id.cocktailPager);
        mHowTo = (Button) findViewById(R.id.howTo);
    }

    @Override
    protected Toolbar getToolbar() {
        mToolbar.setTitle("Name");
        return mToolbar;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_cocktail_overview;
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
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return ImageFragment.newInstance(mImageIds[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Test";
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }
}
