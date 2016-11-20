package org.example.concurrency.dining.philosophers;

/**
 * @author Anton Lenok
 * @since 20.11.16.
 */
public abstract class AbstractPhilosopher implements Philosopher {

    private final Fork leftFork;
    private final Fork rightFork;

    private static int currentNumber = 0;

    private final int number;

    public AbstractPhilosopher(Fork leftFork, Fork rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        currentNumber += 1;
        this.number = currentNumber;
    }

    public Fork getLeftFork() {
        return leftFork;
    }

    public Fork getRightFork() {
        return rightFork;
    }

    @Override
    public int getNumber() {
        return number;
    }
}
