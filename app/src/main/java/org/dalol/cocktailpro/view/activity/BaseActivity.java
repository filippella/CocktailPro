package org.dalol.cocktailpro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Filippo-TheAppExpert on 1/13/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());
        configViews(getIntent(), savedInstanceState);
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        onViewSetupCompleted();
    }

    protected abstract void onViewSetupCompleted();

    protected abstract void configViews(Intent intent, Bundle savedInstanceState);

    protected abstract Toolbar getToolbar();

    protected abstract int getResourceId();
}
