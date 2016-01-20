package org.dalol.cocktailpro.model.utilities;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by Filippo-TheAppExpert on 1/13/2016.
 */
public class FontReplacementUtil {

    public static void replaceDefaultFont(Context context, String fontName, String replaceFontType) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
        replaceFont(typeface, replaceFontType);
    }

    private static void replaceFont(Typeface typeface, String replaceFontType) {
        try {
            Field typefaceField = Typeface.class.getDeclaredField(replaceFontType);
            typefaceField.setAccessible(true);
            typefaceField.set(null, typeface);
        } catch(NoSuchFieldException nsfe) {
            nsfe.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
    }
}
