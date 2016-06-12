package org.dalol.cocktailpro.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
@Module
public class BaseModule {

    @Singleton
    @Provides
    ExecutorService provideExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Provides
    @Singleton
    ThreadPoolExecutor provideThreadPoolExecutor() {
        return new ThreadPoolExecutor(3, 6, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }
}
