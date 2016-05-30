package org.dalol.cocktailpro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.model.adapter.RecyclerListAdapter;
import org.dalol.cocktailpro.view.custom.StickyHeaderView;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/13/2016
 */
public class CocktailDetailActivity extends BaseActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onViewSetupCompleted() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new RecyclerListAdapter());
        mRecyclerView.addItemDecoration(new StickyHeaderView());

    }

    @Override
    protected void configViews(Intent intent, Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected Toolbar getToolbar() {
        mToolbar.setTitle("Name");
        return mToolbar;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_cocktail_detail;
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
