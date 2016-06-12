package org.dalol.cocktailpro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.base.BaseActivity;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/13/2016
 */
public class SettingActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Preferences");
        onViewSetupCompleted();

    }

    protected void onViewSetupCompleted() {

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SettingsPreference())
                .commit();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class SettingsPreference extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }
    }
}
