package org.dalol.cocktailpro.application;

import android.app.Application;
import android.content.Context;

import org.dalol.cocktailpro.di.components.BaseComponent;
import org.dalol.cocktailpro.di.components.DaggerBaseComponent;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/13/2016
 */
public class CocktailProApplication extends Application {

    @Override
    public void onCreate() {
        getBaseDependency();
        super.onCreate();
        //FontReplacementUtil.replaceDefaultFont(this, "helvetica-neue-light.ttf", "DEFAULT");
    }

    public static BaseComponent getBaseDependency() {
        return DaggerBaseComponent.builder().build();
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
