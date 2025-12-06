package core.datasource;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class EntiteTropAnciennePersistenceException extends PersistenceException {

    public EntiteTropAnciennePersistenceException() {
    }

    public EntiteTropAnciennePersistenceException(final String message) {
        super(message);
    }

    public EntiteTropAnciennePersistenceException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    public EntiteTropAnciennePersistenceException(final Throwable cause) {
        super(cause);
    }

    public EntiteTropAnciennePersistenceException(final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
