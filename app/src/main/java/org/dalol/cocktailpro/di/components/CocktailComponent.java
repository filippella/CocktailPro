package org.dalol.cocktailpro.di.components;

import org.dalol.cocktailpro.cocktail.CocktailActivity;
import org.dalol.cocktailpro.di.modules.CocktailModule;
import org.dalol.cocktailpro.di.scopes.CustomScope;

import dagger.Component;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
@CustomScope
@Component(modules = CocktailModule.class, dependencies = BaseComponent.class)
public interface CocktailComponent {

    void inject(CocktailActivity activity);
}
