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

class MyLongAdderTask implements Runnable {

    private LongAdder counter;
    private int factor;

    public MyLongAdderTask(LongAdder counter, int factor) {
        this.counter = counter;
        this.factor = factor;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < factor; i++) {
                Thread.sleep((long) Math.random() * 100);
                counter.increment();
            }
        } catch (InterruptedException ex) {
            System.err.printf("[%s] interrupted!\n", Thread.currentThread().getName());
        }
    }

}

class MyAtomimcIntegerTask implements Runnable {

    private AtomicInteger counter;
    private int factor;

    public MyAtomimcIntegerTask(AtomicInteger counter, int factor) {
        this.counter = counter;
        this.factor = factor;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < factor; i++) {
                Thread.sleep((long) Math.random() * 100);
                counter.incrementAndGet();
            }
        } catch (InterruptedException ex) {
            System.err.printf("[%s] interrupted!\n", Thread.currentThread().getName());
        }
    }

}

class MySynchronizedAtomicOperations implements Runnable {

    private AtomicInteger q;
    private AtomicInteger p;
    private int factor;

    public MySynchronizedAtomicOperations(AtomicInteger q, AtomicInteger p, int factor) {
        this.q = q;
        this.p = p;
        this.factor = factor;
    }

    @Override
    public void run() {
       // try {
            for (int i = 0; i < factor; i++) {
               // Thread.sleep((long) Math.random() * 100);
                p.incrementAndGet();
                q.incrementAndGet();
                q.addAndGet(p.get());
            }
      //  } catch (InterruptedException ex) {
     //       System.err.printf("[%s] interrupted!\n", Thread.currentThread().getName());
     //   }
    }

}

