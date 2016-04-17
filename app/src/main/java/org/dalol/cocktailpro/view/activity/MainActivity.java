package org.dalol.cocktailpro.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
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
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.model.adapter.RecyclerGridAdapter;
import org.dalol.cocktailpro.model.adapter.RecyclerListAdapter;
import org.dalol.cocktailpro.model.constants.Constant;

public class MainActivity extends BaseActivity implements ViewSwitcher.ViewFactory {

    private static final long INTERVAL = 10000;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageSwitcher mHeaderImage;
    private int mImageIndex = 0;

    private final int[] images = {R.drawable.bg, R.drawable.cocktail_bg};
    private boolean isRunning = true;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton mActionButton;
    private AppBarLayout mBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private State mCurrentState = State.IDLE;
    private Menu mViewTypeOptions;
    private boolean mFlag;
    private int mAdapterType;


    @Override
    protected void onViewSetupCompleted() {
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

                if(mAdapterType == 2) {
                    mAdapterType = 0;
                } else {
                    mAdapterType = 2;
                }
                configAdapter(mAdapterType);
            }
        });

//        mHeaderImage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b) {
//                    Log.d("FAB STATE", "Invisible");
//                } else {
//                    Log.d("FAB STATE", "Visible");
//                }
//            }
//        });

//        mBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (appBarLayout.getHeight() / 2 < -verticalOffset) {
//                    Log.d("FAB STATE", "Invisible");
//                } else {
//                    Log.d("FAB STATE", "Visible");
//                }
//            }
//        });

//        FloatingActionButton.OnVisibilityChangedListener fabListener;
//
//        fabListener = new FloatingActionButton.OnVisibilityChangedListener(){
//            @Override
//            public void onShown(FloatingActionButton fab) {
//
//                Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//                super.onShown(fab);
//            }
//
//            @Override
//            public void onHidden(FloatingActionButton fab) {
//                Toast.makeText(getApplicationContext(), "FloatingActionButton Hidden", Toast.LENGTH_SHORT).show();
//                super.onHidden(fab);
//            }
//        };
//
//        mActionButton.show(fabListener);
//
        mBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//                if (verticalOffset == 0) {
//                    if (mCurrentState != State.EXPANDED) {
//                        onStateChanged(appBarLayout, State.EXPANDED);
//                    }
//                    mCurrentState = State.EXPANDED;
//                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
//                    if (mCurrentState != State.COLLAPSED) {
//                        onStateChanged(appBarLayout, State.COLLAPSED);
//                    }
//                    mCurrentState = State.COLLAPSED;
//                } else {
//                    if (mCurrentState != State.IDLE) {
//                        onStateChanged(appBarLayout, State.IDLE);
//                    }
//                    mCurrentState = State.IDLE;
//                }
//
//
                if (mCollapsingToolbarLayout.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(mCollapsingToolbarLayout)) {
                    if (mCurrentState != State.EXPANDED) {
                        Log.d("BUTTONSTATUS", State.EXPANDED.name() + " [HIDDEN]");
                        if (mViewTypeOptions != null) {
                            mFlag = true;
                            Toast.makeText(getApplicationContext(), "Show Menu!", Toast.LENGTH_SHORT).show();

                            invalidateOptionsMenu();
                            supportInvalidateOptionsMenu();
                        }
                    }
                    mCurrentState = State.EXPANDED;
                } else {
                    if (mCurrentState != State.COLLAPSED) {
                        Log.d("BUTTONSTATUS", State.COLLAPSED.name() + " [VISIBLE]");
                        if (mViewTypeOptions != null) {
                            mFlag = false;

                            Toast.makeText(getApplicationContext(), "Hide Menu!", Toast.LENGTH_SHORT).show();

                            invalidateOptionsMenu();
                            supportInvalidateOptionsMenu();
                        }
                    }
                    mCurrentState = State.COLLAPSED;
                }
            }
        });


//        mActionButton.hide(new FloatingActionButton.OnVisibilityChangedListener() {
//            @Override
//            public void onHidden(FloatingActionButton fab) {
//                Toast.makeText(getApplicationContext(), "FloatingActionButton Hidden", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mActionButton.show(new FloatingActionButton.OnVisibilityChangedListener() {
//            @Override
//            public void onShown(FloatingActionButton fab) {
//                Toast.makeText(getApplicationContext(), "FloatingActionButton onShown", Toast.LENGTH_SHORT).show();
//            }
//        });


//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
//        mRecyclerView.setAdapter(new RecyclerListAdapter());

        mAdapterType = 2;
        configAdapter(mAdapterType);


        //mNavigationView.setItemIconTintList(R.color.black);
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

        Animation aniIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        aniIn.setDuration(3000);
        Animation aniOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        aniOut.setDuration(3000);

        mHeaderImage.setInAnimation(aniIn);
        mHeaderImage.setOutAnimation(aniOut);
        mHeaderImage.setFactory(this);
        mHeaderImage.setImageResource(images[mImageIndex]);


        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {

            @Override
            public void run() {

                if (isRunning) {
                    mImageIndex++;
                    mImageIndex = mImageIndex % images.length;

                    mHeaderImage.setImageResource(images[mImageIndex]);
                    handler.postDelayed(this, INTERVAL);
                }
            }
        };
        handler.postDelayed(runnable, INTERVAL);
    }

    private void configAdapter(int type) {

        switch (type) {
            default:
            case Constant.TYPE_VERTICAL_LIST:
                mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                mAdapter = new RecyclerListAdapter();
                break;
            case Constant.TYPE_HORIZONTAL_LIST:
                mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                mAdapter = new RecyclerGridAdapter();
                break;
            case Constant.TYPE_GRID_VIEW:
                mLayoutManager = new GridLayoutManager(getApplicationContext(), Constant.NUM_OF_COLUMNS);
                mAdapter = new RecyclerGridAdapter();
                break;
            case Constant.TYPE_HORIZONTAL_GRID_VIEW_STAGGERED:
                mLayoutManager = new StaggeredGridLayoutManager(Constant.HR_SPAN_COUNT, StaggeredGridLayoutManager.HORIZONTAL);
                mAdapter = new RecyclerGridAdapter();
                break;
            case Constant.TYPE_VERTICAL_GRID_VIEW_STAGGERED:
                mLayoutManager = new StaggeredGridLayoutManager(Constant.VR_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                mAdapter = new RecyclerGridAdapter();
                break;
        }


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void configViews(Intent intent, Bundle savedInstanceState) {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        mCollapsingToolbarLayout.setTitle("Filippo");
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mHeaderImage = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mBarLayout = (AppBarLayout) findViewById(R.id.appbar);
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
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

        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int selectedMenuId = item.getItemId();

        if (selectedMenuId == android.R.id.home) {
            Toast.makeText(getApplicationContext(), "Home Clicked!", Toast.LENGTH_SHORT).show();
        } else if (selectedMenuId == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
            Toast.makeText(getApplicationContext(), "Settings Clicked!", Toast.LENGTH_SHORT).show();
        } else if (selectedMenuId == R.id.nav_viewType) {
            if(mAdapterType == 2) {
                mAdapterType = 0;
            } else {
                mAdapterType = 2;
            }
            configAdapter(mAdapterType);
            Toast.makeText(getApplicationContext(), "View Clicked!", Toast.LENGTH_SHORT).show();
        } else if (selectedMenuId == R.id.nav_search) {
            Toast.makeText(getApplicationContext(), "Search Clicked!", Toast.LENGTH_SHORT).show();

            View dialog = LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_dialog_layout, null);

            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
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

            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.select_dialog_item, array);
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
//                    if(!autocompletetextview.isPopupShowing()) {
//                        Toast.makeText(getApplicationContext(), "Hide arrow", Toast.LENGTH_SHORT).show();
//                    }

                    if(!autocompletetextview.isPerformingCompletion()) {
                        Toast.makeText(getApplicationContext(), "Hide arrow", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

            Window window = alertDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.TOP  | Gravity.CENTER_HORIZONTAL;
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


    enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }
}
