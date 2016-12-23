package org.example.concurrency.dining.philosophers.simple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.concurrency.dining.philosophers.Fork;
import org.example.concurrency.dining.philosophers.PhilosopherException;

/**
 * @author Anton Lenok
 * @since 20.11.16.
 */
public class ForkSimple implements Fork {

    private static final Logger LOGGER = LogManager.getLogger(ForkSimple.class);

    private static int currentNumber = 0;

    private final int number;

    private volatile boolean busy = false;

    public ForkSimple() {
        currentNumber += 1;
        number = currentNumber;
    }

    @Override
    public void get() throws PhilosopherException {
        LOGGER.info("Getting fork " + number);
        if (busy) {
            throw new PhilosopherException("Fork " + number + " is already get");
        }
        busy = true;
    }

    @Override
    public void put() throws PhilosopherException {
        LOGGER.info("Putting fork " + number);
        if (!busy) {
            throw new PhilosopherException("Fork " + number + " is already put");
        }
        busy = false;
    }

    @Override
    public boolean isBusy() {
        return busy;
    }

    @Override
    public int getNumber() {
        return number;
    }
}
