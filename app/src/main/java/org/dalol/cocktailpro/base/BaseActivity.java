package org.dalol.cocktailpro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.model.ActivityTransitionType;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/13/2016
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable @Bind(R.id.toolbar) protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            overridePendingTransition(ActivityTransitionType.ALPHA_IN_ALPHA_OUT);
        }

        int contentView = getContentView();
        if(contentView > 0) {
            setContentView(contentView);
        }
        ButterKnife.bind(this);
        if (mToolbar != null) {
            //mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    protected abstract int getContentView();

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = Math.round(getResources().getDimensionPixelSize(resourceId));
        }
        return result;
    }


    @Override
    public void finish() {
        overridePendingTransitionOut();
        super.finish();
    }

    protected void overridePendingTransition(ActivityTransitionType type) {
        switch (type) {
            case ALPHA_IN_ALPHA_OUT:
            default:
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

    }

    protected void overridePendingTransitionOut() {
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
