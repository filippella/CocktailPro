/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dalol.model.cocktailpro.cocktail;

import java.io.Serializable;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 5/22/2016
 */
public class Ingredients implements Serializable {

    private String amount;
    private String unit;
    private String ingredient;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "Ingredients [amount = " + amount + ", unit = " + unit + ", ingredient = " + ingredient + "]";
    }
}
