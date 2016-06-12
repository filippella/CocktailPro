package org.dalol.cocktailpro.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface BackgroundTask {
}
