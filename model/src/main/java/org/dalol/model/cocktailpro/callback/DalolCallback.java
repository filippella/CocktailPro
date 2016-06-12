package org.dalol.model.cocktailpro.callback;

import org.dalol.model.cocktailpro.DalolException;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
public interface DalolCallback<C> {

    void onSuccess(C callback);

    void onError(DalolException exception);
}
