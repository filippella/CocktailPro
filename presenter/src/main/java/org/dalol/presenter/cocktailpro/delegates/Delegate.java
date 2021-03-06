package org.dalol.presenter.cocktailpro.delegates;

import rx.Observable;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/11/2016
 */
public interface Delegate<O> {

    Observable<O> getObservable();
}
