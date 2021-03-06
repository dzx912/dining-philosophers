package org.example.concurrency.dining.philosophers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.concurrency.dining.philosophers.simple.ForkSimple;
import org.example.concurrency.dining.philosophers.simple.PhilosopherSimple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Anton Lenok
 * @since 20.11.16.
 */
public class MainApp {
    private final static int TIME_EXPERIMENT = 5 * 1000;
    private final static int COUNT_PHILOSOPHERS = 5;

    private static final Logger LOGGER = LogManager.getLogger(MainApp.class);


    public static void main(String[] args) {
        LOGGER.info("Start Dining philosophers");

        List<Philosopher> philosophers = createSimplePhilosophers();

        List<Thread> philosopherThreads =
                philosophers.stream()
                        .map(Thread::new)
                        .collect(Collectors.toList());

        philosopherThreads.forEach(Thread::start);

        try {
            Thread.sleep(TIME_EXPERIMENT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("Shutdown all threads");

        //TODO: use Callable interface for statistics
        philosopherThreads.forEach(Thread::interrupt);
    }

    private static List<Philosopher> createSimplePhilosophers() {
        List<Fork> forks = new ArrayList<>(COUNT_PHILOSOPHERS);
        for (int indexFork = 0; indexFork < COUNT_PHILOSOPHERS; indexFork++) {
            forks.add(factoryFork());
        }

        List<Philosopher> philosophers = new ArrayList<>();
        for (int indexPhilosophers = 0; indexPhilosophers < COUNT_PHILOSOPHERS; indexPhilosophers++) {
            int indexLeftFork = indexPhilosophers;
            int indexRightFork = indexPhilosophers + 1;

            if (COUNT_PHILOSOPHERS <= indexRightFork) {
                indexRightFork = 0;
            }

            Fork leftFork = forks.get(indexLeftFork);
            Fork rightFork = forks.get(indexRightFork);

            Philosopher philosopherSimple = factoryPhilosopher(leftFork, rightFork);
            philosophers.add(philosopherSimple);
        }

        return philosophers;
    }

    private static Philosopher factoryPhilosopher(Fork leftFork, Fork rightFork) {
        return new PhilosopherSimple(leftFork, rightFork);
    }

    private static Fork factoryFork() {
        return new ForkSimple();
    }
}
