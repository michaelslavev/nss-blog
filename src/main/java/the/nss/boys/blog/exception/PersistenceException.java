package the.nss.boys.blog.exception;

/**
 * Indicates problem with persistence
 */
public class PersistenceException extends EarException {

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }
}
