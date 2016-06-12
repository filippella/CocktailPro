package org.dalol.cocktailpro.di.components;

import com.google.gson.Gson;

import org.dalol.cocktailpro.di.modules.BaseModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
@Singleton
@Component(modules = BaseModule.class)
public interface BaseComponent {

    Gson gson();
}
