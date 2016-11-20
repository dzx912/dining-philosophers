package org.example.concurrency.dining.philosophers;

/**
 * @author Anton Lenok
 * @since 20.11.16.
 */
public interface Fork {
    void get();

    void put();

    boolean isBusy();
}
