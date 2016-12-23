package org.example.concurrency.dining.philosophers;

/**
 * @author Anton Lenok
 * @since 20.11.16.
 */
public interface Fork {
    void get() throws PhilosopherException;

    void put() throws PhilosopherException;

    boolean isBusy();

    int getNumber();
}
