package org.dalol.cocktailpro.di.modules;

import android.app.Activity;
import android.content.Context;

import org.dalol.cocktailpro.di.scopes.CustomScope;
import org.dalol.contract.cocktailpro.cocktail.CocktailView;
import org.dalol.model.cocktailpro.cocktail.AppPreferences;
import org.dalol.model.cocktailpro.cocktail.AppPreferencesImpl;
import org.dalol.model.cocktailpro.cocktail.CocktailItem;
import org.dalol.model.cocktailpro.constants.Constants;
import org.dalol.presenter.cocktailpro.delegates.CocktailDelegate;
import org.dalol.presenter.cocktailpro.delegates.Delegate;
import org.dalol.presenter.cocktailpro.delegates.PreferenceDelegate;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
@Module
public class CocktailModule {

    private CocktailView mView;

    public CocktailModule(CocktailView view) {
        mView = view;
    }

    @Provides
    @CustomScope
    CocktailView provideView() {
        return mView;
    }

    @Provides
    @CustomScope
    @Named(Constants.COCKTAIL_TAG)
    Delegate<List<CocktailItem>> provideCocktailDelegate(CocktailDelegate delegate) {
        return delegate;
    }

    @Provides
    @CustomScope
    @Named(Constants.PREFERENCE_TAG)
    Delegate<AppPreferences> providePreferenceDelegate(PreferenceDelegate delegate) {
        return delegate;
    }

    @Provides
    @CustomScope
    AppPreferences providePreferences(AppPreferencesImpl appPreferences) {
        return appPreferences;
    }

    @Provides
    @CustomScope
    Context provideContext() {
        return ((Activity) mView).getApplicationContext();
    }
}
