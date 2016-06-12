package org.dalol.cocktailpro.cocktail;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.application.CocktailProApplication;
import org.dalol.cocktailpro.base.BaseActivity;
import org.dalol.cocktailpro.di.components.DaggerCocktailComponent;
import org.dalol.cocktailpro.di.modules.CocktailModule;
import org.dalol.cocktailpro.model.adapter.RecyclerGridAdapter;
import org.dalol.cocktailpro.model.adapter.RecyclerListAdapter;
import org.dalol.cocktailpro.model.constants.Constant;
import org.dalol.cocktailpro.view.activity.SettingActivity;
import org.dalol.contract.cocktailpro.base.OnMainCallback;
import org.dalol.contract.cocktailpro.cocktail.CocktailView;
import org.dalol.model.cocktailpro.DalolException;
import org.dalol.model.cocktailpro.cocktail.AppPreferences;
import org.dalol.model.cocktailpro.cocktail.Cocktail;
import org.dalol.model.cocktailpro.cocktail.CocktailItem;
import org.dalol.presenter.cocktailpro.cocktail.CocktailPresenter;
import org.dalol.presenter.cocktailpro.delegates.CocktailObserver;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
public class CocktailActivity extends BaseActivity implements CocktailView, ViewSwitcher.ViewFactory {

    private static final String CLASS_TAG = CocktailActivity.class.getSimpleName();

    private static final long INTERVAL = 3000;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mAdapterType;

    @Bind(R.id.collapsingToolbarLayout) protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.drawerLayout) protected DrawerLayout mDrawerLayout;
    @Bind(R.id.recycler_view) protected RecyclerView mRecyclerView;
    @Bind(R.id.nav_view) protected NavigationView mNavigationView;
    @Bind(R.id.imageSwitcher) protected ImageSwitcher mHeaderImage;
    @Bind(R.id.fab) protected FloatingActionButton mActionButton;
    @Bind(R.id.appbar) protected AppBarLayout mBarLayout;

    @Inject CocktailPresenter mPresenter;
    private Menu mViewTypeOptions;
    private boolean mFlag;
    private boolean isRunning = true;
    private RecyclerGridAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private WindowInsetsCompat mSystemInsets;
    private List<CocktailItem> mItems;
    private int mImageIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configViews();
        getSupportActionBar().setTitle("Navigation!");
        resolveDependency();
        mAdapter = new RecyclerGridAdapter();
        mLayoutManager = new GridLayoutManager(getApplicationContext(), Constant.NUM_OF_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(mCollapsingToolbarLayout, new android.support.v4.view.OnApplyWindowInsetsListener() {

            @Override
            public
            WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                mSystemInsets = insets;
                mCollapsingToolbarLayout.requestLayout();
                return insets.consumeSystemWindowInsets();
            }
        });

        mBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                final int scrollRange = appBarLayout.getTotalScrollRange();
                final int insetTop = (mSystemInsets != null) ? mSystemInsets.getSystemWindowInsetTop() : 0;

                if (mCollapsingToolbarLayout.getHeight() - insetTop + verticalOffset < 2 * ViewCompat.getMinimumHeight(mCollapsingToolbarLayout)) {
                    if (mViewTypeOptions != null) {
                        mFlag = true;
                        invalidateOptionsMenu();
                        supportInvalidateOptionsMenu();
                    }
                } else {
                    if (mViewTypeOptions != null) {
                        mFlag = false;
                        invalidateOptionsMenu();
                        supportInvalidateOptionsMenu();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.getCocktails(new OnMainCallback<List<CocktailItem>>() {

                @Override
                public void onError(DalolException exception) {
                    Log.d(CLASS_TAG, "OnMainCallbackError -> " + exception.getMessage());
                    Toast.makeText(CocktailActivity.this, "OnMainCallbackError -> " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(List<CocktailItem> value) {
                    //anmateItems(value);
                    for (CocktailItem cocktailItem : value) {
                        mAdapter.addCocktailItem(cocktailItem);
                        Log.d(CLASS_TAG, "OnMainCallbackOkay -> " + cocktailItem.getImageId());
                    }
                }
            });
    }

    private void anmateItems(List<CocktailItem> value) {
        mItems = value;

        final Animation aniIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        aniIn.setDuration(3000);
        Animation aniOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        aniOut.setDuration(3000);

        mHeaderImage.setInAnimation(aniIn);
        mHeaderImage.setOutAnimation(aniOut);
        mHeaderImage.setFactory(this);
        int imageId = mItems.get(mImageIndex).getImageId();
        mHeaderImage.setImageResource(imageId);


        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {

            @Override
            public void run() {

                if (isRunning) {
//                    if(mImageIndex >= mItems.size()) {
//                        mImageIndex = 0;
//                    }
                    mImageIndex++;
                    mImageIndex = mImageIndex % mItems.size();

                    mHeaderImage.setImageResource( mItems.get(mImageIndex).getImageId());
                    handler.postDelayed(this, INTERVAL);
                }
            }
        };
        handler.postDelayed(runnable, INTERVAL);
    }

    private void resolveDependency() {
        DaggerCocktailComponent.builder()
                .baseComponent(CocktailProApplication.getBaseDependency())
                .cocktailModule(new CocktailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    protected void configViews() {
        mCollapsingToolbarLayout.setTitle("Filippo");
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        mCollapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#FFFFFF"));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle("Title");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "FloatingActionButton Selected", Toast.LENGTH_SHORT).show();

                if (mAdapterType == 2) {
                    mAdapterType = 0;
                } else {
                    mAdapterType = 2;
                }
                configAdapter(mAdapterType);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_share:
                        Toast.makeText(getApplicationContext(), "About Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_about:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showAbout();
                            }
                        }, 250);

                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

//        Animation aniIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
//        aniIn.setDuration(3000);
//        Animation aniOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
//        aniOut.setDuration(3000);
//
//        mHeaderImage.setInAnimation(aniIn);
//        mHeaderImage.setOutAnimation(aniOut);
//        mHeaderImage.setFactory(this);
//        //mHeaderImage.setImageResource(images[mImageIndex]);
//
//
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//
//            @Override
//            public void run() {
//
//                if (isRunning) {
////                    mImageIndex++;
////                    mImageIndex = mImageIndex % images.length;
////
////                    mHeaderImage.setImageResource(images[mImageIndex]);
////                    handler.postDelayed(this, INTERVAL);
//                }
//            }
//        };
//        handler.postDelayed(runnable, INTERVAL);
    }

    private void configAdapter(int type) {

        mAdapter = new RecyclerGridAdapter();

        switch (type) {
            default:
            case Constant.TYPE_VERTICAL_LIST:
                mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                //mAdapter = new RecyclerListAdapter();
                break;
            case Constant.TYPE_HORIZONTAL_LIST:
                mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                break;
            case Constant.TYPE_GRID_VIEW:
                mLayoutManager = new GridLayoutManager(getApplicationContext(), Constant.NUM_OF_COLUMNS);
                break;
            case Constant.TYPE_HORIZONTAL_GRID_VIEW_STAGGERED:
                mLayoutManager = new StaggeredGridLayoutManager(Constant.HR_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL);
                break;
            case Constant.TYPE_VERTICAL_GRID_VIEW_STAGGERED:
                mLayoutManager = new StaggeredGridLayoutManager(Constant.VR_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }


        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onProvidePreferences(AppPreferences preferences) {
        getSupportActionBar().setTitle("BETS OF THE BEST!");
        Toast.makeText(CocktailActivity.this, "Received Preferences!", Toast.LENGTH_SHORT).show();
    }
//
//    @Override
//    public void onCompleted() {
//        Toast.makeText(CocktailActivity.this, "Completed!", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onError(DalolException exception) {
//        Toast.makeText(CocktailActivity.this, "Error -> " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onSuccess(Object o) {
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int selectedMenuId = item.getItemId();

        if (selectedMenuId == android.R.id.home) {
            Toast.makeText(getApplicationContext(), "Home Clicked!", Toast.LENGTH_SHORT).show();
        } else if (selectedMenuId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            Toast.makeText(getApplicationContext(), "Settings Clicked!", Toast.LENGTH_SHORT).show();
        } else if (selectedMenuId == R.id.nav_viewType) {
            if (mAdapterType == 2) {
                mAdapterType = 0;
            } else {
                mAdapterType = 2;
            }
            configAdapter(mAdapterType);
            Toast.makeText(getApplicationContext(), "View Clicked!", Toast.LENGTH_SHORT).show();
        } else if (selectedMenuId == R.id.nav_search) {
            Toast.makeText(getApplicationContext(), "Search Clicked!", Toast.LENGTH_SHORT).show();

            View dialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_dialog_layout, null);

            final AlertDialog alertDialog = new AlertDialog.Builder(CocktailActivity.this)
                    .setCancelable(true)
                    .setView(dialog)
                    .create();
            ImageButton close = (ImageButton) dialog.findViewById(R.id.close_button);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });


            final AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) dialog.findViewById(R.id.autoCompleteTextView);

            String[] array =
                    {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
                            "Ten"};

            ArrayAdapter adapter = new ArrayAdapter(CocktailActivity.this, android.R.layout.select_dialog_item, array);
            autocompletetextview.setThreshold(1);
            autocompletetextview.setAdapter(adapter);

            autocompletetextview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String val = parent.getAdapter().getItem(position).toString();

                    Toast.makeText(getApplicationContext(), "Clicked item " + val, Toast.LENGTH_SHORT).show();
                }
            });

            autocompletetextview.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    Filterable filterable = mAdapter;
                    Filter filter = filterable.getFilter();
                    filter.filter(s.toString());

//                    if(!autocompletetextview.isPopupShowing()) {
//                        Toast.makeText(getApplicationContext(), "Hide arrow", Toast.LENGTH_SHORT).show();
//                    }

                    if (!autocompletetextview.isPerformingCompletion()) {
                        Toast.makeText(getApplicationContext(), "Hide arrow", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

            Window window = alertDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

            alertDialog.show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mViewTypeOptions = menu;
        getMenuInflater().inflate(R.menu.menu, menu);

        if (mFlag) {
//            MenuItem edit_item = menu.add(0, (Menu.FIRST+1), 0, "Search");
//            edit_item.setIcon(R.mipmap.ic_search);
//            edit_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItem menuItem = menu.findItem(R.id.nav_viewType);
            //menuItem.setIcon(R.mipmap.ic_list);
            menuItem.setVisible(true);
        } else {
            MenuItem menuItem = menu.findItem(R.id.nav_viewType);
            //menuItem.setIcon(R.mipmap.ic_grid);
            menuItem.setVisible(false);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void finish() {
        isRunning = false;
        super.finish();
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                ImageSwitcher.LayoutParams.MATCH_PARENT, ImageSwitcher.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    private void showAbout() {
        View dialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.about_dialog_layout, null);

        Button sendEmail = (Button) dialog.findViewById(R.id.sendEmail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Send Email", Toast.LENGTH_SHORT).show();
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(CocktailActivity.this)
                .setCancelable(true)
                .setView(dialog)
                .create();
        Button close = (Button) dialog.findViewById(R.id.close_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
    }
}
