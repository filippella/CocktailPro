package org.dalol.presenter.cocktailpro.delegates;

import org.dalol.model.cocktailpro.cocktail.AppPreferences;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/11/2016
 */
public class PreferenceDelegate implements Delegate<AppPreferences> {


    @Inject
    public PreferenceDelegate() {
    }

    @Override
    public Observable<AppPreferences> getObservable() {
        return null;
    }
}
