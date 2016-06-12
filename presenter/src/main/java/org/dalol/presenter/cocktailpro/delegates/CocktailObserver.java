package org.dalol.presenter.cocktailpro.delegates;

import org.dalol.contract.cocktailpro.base.OnMainCallback;
import org.dalol.model.cocktailpro.DalolException;

import rx.Observer;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/11/2016
 */
public class CocktailObserver<O> implements Observer<O> {

    private OnMainCallback<O> mView;

    public CocktailObserver(OnMainCallback<O> view) {
        mView = view;
    }

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {
        mView.onError(new DalolException(e));
    }

    @Override
    public void onNext(O o) {
        mView.onSuccess(o);
    }
}
