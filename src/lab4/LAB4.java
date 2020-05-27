/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 *
 * @author cesarino
 */
public class LAB4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Expected result: " + testAtomicOperations(1, 10000000));
        System.out.println("Real result: " + testAtomicOperations(100000, 100));
    }

    private static int testAtomicOperations(int nThreads, int factor) {
        AtomicInteger q = new AtomicInteger(1);
        AtomicInteger p = new AtomicInteger(1);
        try {

            Thread[] threads = new Thread[nThreads];
            for (int i = 0; i < nThreads; i++) {
                threads[i] = new Thread(new MySynchronizedAtomicOperations(q, p, factor));
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].start();
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].join();
            }

        } catch (InterruptedException ex) {
            System.err.printf("[%s] interrupted!\n", Thread.currentThread().getName());
        }
        return q.get();
    }

    private static void measureTimes() {
        double[] startTimes = new double[4];
        double[] endTimes = new double[4];

        for (int i = 0; i < 100000; i += 1000) {

            //Measure synchronized
            startTimes[0] = System.nanoTime() / 100000000.0;
            testSynchronized(100, i);
            endTimes[0] = System.nanoTime() / 100000000.0;

            //Measure Locks
            startTimes[1] = System.nanoTime() / 100000000.0;
            testLock(100, i);
            endTimes[1] = System.nanoTime() / 100000000.0;

            //Measure LongAdder
            startTimes[2] = System.nanoTime() / 100000000.0;
            testLongAdder(100, i);
            endTimes[2] = System.nanoTime() / 100000000.0;

            //Measure AtomicInteger
            startTimes[3] = System.nanoTime() / 100000000.0;
            testLongAdder(100, i);
            endTimes[3] = System.nanoTime() / 100000000.0;

            System.out.printf("%d\t%f\t%f\t%f\t%f\n", i * 100, (endTimes[0] - startTimes[0]),
                    (endTimes[1] - startTimes[1]),
                    (endTimes[2] - startTimes[2]),
                    (endTimes[3] - startTimes[3]));

        }
    }

    private static void testSynchronized(int nThreads, int factor) {
        try {
            SynchronizedCounter counter = new SynchronizedCounter();
            Thread[] threads = new Thread[nThreads];
            for (int i = 0; i < nThreads; i++) {
                threads[i] = new Thread(new MySynchronizedTask(counter, factor));
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].start();
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].join();
            }
            //System.out.println("Expected counter: " + nThreads * factor);
            //System.out.println("Real counter: " + counter.get());
        } catch (InterruptedException ex) {
            System.err.printf("[%s] interrupted!\n", Thread.currentThread().getName());
        }
    }

    private static void testLongAdder(int nThreads, int factor) {
        try {
            LongAdder counter = new LongAdder();
            Thread[] threads = new Thread[nThreads];
            for (int i = 0; i < nThreads; i++) {
                threads[i] = new Thread(new MyLongAdderTask(counter, factor));
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].start();
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].join();
            }
            //System.out.println("Expected counter: " + nThreads * factor);
            //System.out.println("Real counter: " + counter.longValue());
        } catch (InterruptedException ex) {
            System.err.printf("[%s] interrupted!\n", Thread.currentThread().getName());
        }
    }

    private static void testAtomicInteger(int nThreads, int factor) {
        try {
            AtomicInteger counter = new AtomicInteger(0);
            Thread[] threads = new Thread[nThreads];
            for (int i = 0; i < nThreads; i++) {
                threads[i] = new Thread(new MyAtomimcIntegerTask(counter, factor));
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].start();
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].join();
            }
            //System.out.println("Expected counter: " + nThreads * factor);
            //System.out.println("Real counter: " + counter.longValue());
        } catch (InterruptedException ex) {
            System.err.printf("[%s] interrupted!\n", Thread.currentThread().getName());
        }
    }

    private static void testLock(int nThreads, int factor) {
        try {
            LockedCounter counter = new LockedCounter();
            Thread[] threads = new Thread[nThreads];
            for (int i = 0; i < nThreads; i++) {
                threads[i] = new Thread(new MyLockedTask(counter, factor));
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].start();
            }
            for (int i = 0; i < nThreads; i++) {
                threads[i].join();
            }
            //System.out.println("Expected counter: " + nThreads * factor);
            //System.out.println("Real counter: " + counter.get());
        } catch (InterruptedException ex) {
            System.err.printf("[%s] interrupted!\n", Thread.currentThread().getName());
        }
    }

}
