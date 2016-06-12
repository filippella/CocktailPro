package org.dalol.model.cocktailpro;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
public class DalolException extends RuntimeException {

    public DalolException(String message) {
        super(message);
    }

    public DalolException(String message, Throwable cause) {
        super(message, cause);
    }

    public DalolException(Throwable cause) {
        super(cause);
    }
}
