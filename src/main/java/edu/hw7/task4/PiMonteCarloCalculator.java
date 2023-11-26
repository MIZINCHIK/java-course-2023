package edu.hw7.task4;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class PiMonteCarloCalculator {
    private PiMonteCarloCalculator() {
        throw new IllegalStateException();
    }

    @SuppressWarnings("MagicNumber")
    public static double calculatePi(int pointsAmount, int threadsAmount) {
        if (pointsAmount < 1 || threadsAmount < 1) {
            throw new IllegalArgumentException("Number of points and threads must be no less than 1");
        }
        AtomicInteger inCircleCount = new AtomicInteger(0);
        Thread[] threads = new Thread[threadsAmount];
        int pointsForEachThread = pointsAmount / threadsAmount;
        int pointsLeft = pointsAmount % threadsAmount;
        for (int i = 0; i < threadsAmount; i++) {
            int currentThreadPoints;
            if (pointsLeft > 0) {
                pointsLeft--;
                currentThreadPoints = pointsForEachThread + 1;
            } else {
                currentThreadPoints = pointsForEachThread;
            }
            threads[i] = new Thread(() -> generatePoints(inCircleCount, currentThreadPoints));
            threads[i].start();
        }
        for (int i = 0; i < threadsAmount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignored) {
            }
        }
        return (double) 4 * inCircleCount.get() / pointsAmount;
    }

    private static void generatePoints(AtomicInteger inCircleCount, int pointsAmount) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int localCounter = 0;
        for (int i = 0; i < pointsAmount; i++) {
            double x = random.nextDouble(-1, 1);
            double y = random.nextDouble(-1, 1);
            if (x * x + y * y < 1) {
                localCounter++;
            }
        }
        inCircleCount.addAndGet(localCounter);
    }
}
