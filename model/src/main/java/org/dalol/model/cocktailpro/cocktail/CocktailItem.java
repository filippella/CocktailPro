package org.dalol.model.cocktailpro.cocktail;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/11/2016
 */
public class CocktailItem {

    private Cocktail cocktail;
    private int imageId = -19;

    public CocktailItem(Cocktail cocktail, int imageId) {
        this.cocktail = cocktail;
        this.imageId = imageId;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public int getImageId() {
        return imageId;
    }
}
