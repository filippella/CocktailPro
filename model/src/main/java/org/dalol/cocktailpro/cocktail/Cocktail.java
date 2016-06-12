/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dalol.cocktailpro.cocktail;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 5/22/2016
 */
public class Cocktail {

    private Ingredients[] ingredients;
    private String category;
    private String preparation;
    private String name;
    private String garnish;
    private String glass;

    public Ingredients[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGarnish() {
        return garnish;
    }

    public void setGarnish(String garnish) {
        this.garnish = garnish;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    @Override
    public String toString() {
        return "Cocktail [ingredients = " + ingredients + ", category = " + category + ", preparation = " + preparation + ", name = " + name + ", garnish = " + garnish + ", glass = " + glass + "]";
    }
}
