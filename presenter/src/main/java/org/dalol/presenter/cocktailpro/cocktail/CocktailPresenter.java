package org.dalol.presenter.cocktailpro.cocktail;

import org.dalol.contract.cocktailpro.base.OnMainCallback;
import org.dalol.contract.cocktailpro.cocktail.CocktailView;
import org.dalol.model.cocktailpro.cocktail.AppPreferences;
import org.dalol.model.cocktailpro.cocktail.CocktailItem;
import org.dalol.model.cocktailpro.constants.Constants;
import org.dalol.presenter.cocktailpro.base.BasePresenter;
import org.dalol.presenter.cocktailpro.delegates.CocktailObserver;
import org.dalol.presenter.cocktailpro.delegates.Delegate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
public class CocktailPresenter extends BasePresenter {

    @Inject protected CocktailView mView;
    @Inject protected AppPreferences mPreferences;
    @Inject @Named(Constants.COCKTAIL_TAG) protected Delegate<List<CocktailItem>> mCocktailDelegate;
//    @Inject @Named(Constants.PREFERENCE_TAG) protected Delegate<AppPreferences> mPreferenceDelegate;

    @Inject
    public CocktailPresenter() {
    }

    public <O> void getCocktails(OnMainCallback<O> callback) {
        subscribe(mCocktailDelegate.getObservable(), new CocktailObserver(callback));
    }

    public void getPreferences() {
//        subscribe(mPreferenceDelegate.getObservable(),new CocktailObserver<CocktailView, List<Cocktail>>(mView));
//        mPreferences.getPreferences();
    }
}
