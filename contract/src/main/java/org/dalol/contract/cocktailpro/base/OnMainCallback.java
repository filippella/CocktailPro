package org.dalol.contract.cocktailpro.base;

import org.dalol.model.cocktailpro.DalolException;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/11/2016
 */
public interface OnMainCallback<O> {

    void onError(DalolException exception);

    void onSuccess(O value);
}
