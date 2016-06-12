package org.dalol.presenter.cocktailpro.delegates;

import android.content.Context;

import com.google.gson.Gson;

import org.dalol.model.cocktailpro.cocktail.Cocktail;
import org.dalol.model.cocktailpro.cocktail.CocktailItem;
import org.dalol.presenter.cocktailpro.utilities.FileUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/11/2016
 */
public class CocktailDelegate implements Delegate<List<CocktailItem>>, Observable.OnSubscribe<List<CocktailItem>> {

    @Inject Gson mGson;
    @Inject Context mContext;

    @Inject
    public CocktailDelegate() {
    }

    public Observable<List<CocktailItem>> getObservable() {
        return Observable.create(CocktailDelegate.this);
    }

    @Override
    public void call(Subscriber<? super List<CocktailItem>> subscriber) {
        String json = FileUtils.loadJSONFromAsset(mContext, "cocktails.json");
        Cocktail[] cocktails = mGson.fromJson(json, Cocktail[].class);

        List<CocktailItem> items = new ArrayList<>();

        for (int i = 0; i < cocktails.length; i++) {
            Cocktail cocktail = cocktails[i];
            String resName = cocktail.getImageId();
            int resID = mContext.getResources().getIdentifier(resName , "raw", mContext.getPackageName());
            items.add(new CocktailItem(cocktail, resID));
        }
        subscriber.onNext(items);
        subscriber.onCompleted();
//        for (Cocktail cocktail : cocktails) {
//            String resName = "bramble";
//            int resID = mContext.getResources().getIdentifier(resName , "raw", mContext.getPackageName());
//            subscriber.onNext(new CocktailItem(cocktail, resID));
//        }
//            Thread.sleep(3000L);
        //subscriber.onNext(Arrays.asList(cocktails));

    }
}
