package org.example.concurrency.dining.philosophers.simple;

import net.jcip.annotations.GuardedBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.concurrency.dining.philosophers.AbstractPhilosopher;
import org.example.concurrency.dining.philosophers.Fork;
import org.example.concurrency.dining.philosophers.PhilosopherException;

import java.util.Random;

/**
 * @author Anton Lenok
 * @since 20.11.16.
 */
public class PhilosopherSimple extends AbstractPhilosopher {

    private static final Logger LOGGER = LogManager.getLogger(PhilosopherSimple.class);

    private final static int MAX_DELAY = 100;

    @GuardedBy("this")
    private static final Object COMMON_TOTEM = new Object();
    private final Random random = new Random();

    private int countStarved;
    private int countEat;
    private boolean correctThread;

    public PhilosopherSimple(Fork leftFork, Fork rightFork) {
        super(leftFork, rightFork);
        countStarved = 0;
        countEat = 0;
        correctThread = true;
    }

    @Override
    public void eat() throws PhilosopherException, InterruptedException {
        String textForks = "[" + getLeftFork().getNumber() + ", " + getRightFork().getNumber() + "]";
        synchronized (COMMON_TOTEM) {
            boolean forksBusy = getLeftFork().isBusy() || getRightFork().isBusy();
            if (forksBusy) {
                countStarved += 1;
                LOGGER.info("Forks " + textForks + " are busy. Philosopher " + getNumber() + ", starved = " + countStarved);
                return;
            } else {
                countEat += 1;
                LOGGER.info("Forks " + textForks + " are free. Philosopher " + getNumber() + ", start eat = " + countEat);
                getLeftFork().get();
                getRightFork().get();
            }
        }

        try {
            sleep();
        } finally {
            LOGGER.info("Forks " + textForks + " are free. Philosopher " + getNumber() + ", finish eat = " + countEat);
            getLeftFork().put();
            getRightFork().put();
        }
    }

    @Override
    public void think() throws InterruptedException {
        sleep();
    }

    private void print() {
        if (correctThread) {
            LOGGER.info("Philosopher " + getNumber() + ", countEat = " + countEat + ", countStarved = " + countStarved);
        } else {
            LOGGER.info("Philosopher " + getNumber() + ", doesn't correct");
        }
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(random.nextInt(MAX_DELAY));
    }

    @Override
    public void run() {
        try {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    eat();
                    think();
                }
            } catch (PhilosopherException e) {
                e.printStackTrace();
                correctThread = false;
            }
        } catch (InterruptedException ignore) {
        } finally {
            print();
        }
    }
}
