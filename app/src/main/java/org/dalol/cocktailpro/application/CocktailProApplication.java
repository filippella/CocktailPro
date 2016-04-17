package org.dalol.cocktailpro.application;

import android.app.Application;
import android.content.Context;
//import android.support.multidex.MultiDex;

import org.dalol.cocktailpro.model.utilities.FontReplacementUtil;

/**
 * Created by Filippo-TheAppExpert on 1/13/2016.
 */
public class CocktailProApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //FontReplacementUtil.replaceDefaultFont(this, "helvetica-neue-light.ttf", "DEFAULT");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(base);
    }
}
