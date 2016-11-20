package org.example.concurrency.dining.philosophers;

/**
 * @author Anton Lenok
 * @since 20.11.16.
 */
public class PhilosopherException extends RuntimeException {
    public PhilosopherException() {
    }

    public PhilosopherException(String message) {
        super(message);
    }

    public PhilosopherException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhilosopherException(Throwable cause) {
        super(cause);
    }

    public PhilosopherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
