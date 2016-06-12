package org.dalol.contract.cocktailpro.cocktail;

import org.dalol.contract.cocktailpro.base.OnMainCallback;
import org.dalol.model.cocktailpro.cocktail.AppPreferences;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
public interface CocktailView {

    void onProvidePreferences(AppPreferences preferences);
}
