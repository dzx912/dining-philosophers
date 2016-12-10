package org.example.concurrency.dining.philosophers;

/**
 * @author Anton Lenok
 * @since 20.11.16.
 */
public interface Philosopher extends Runnable {
    void eat() throws PhilosopherException, InterruptedException;

    void think() throws InterruptedException;

    int getNumber();

    void finish();
}
