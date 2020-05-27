/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author cesarino
 */
public class LockedCounter {
    private int counter;
    private ReentrantLock lock;

    public LockedCounter() {
        counter = 0;
        lock = new ReentrantLock();
    }

    public void increment() {
        try{
        lock.lock();
        counter++;
        }finally{
            lock.unlock();
        }
    }

    public int get() {
        return counter;
    }
}

class MyLockedTask implements Runnable {

    private LockedCounter counter;
    private int factor;

    public MyLockedTask(LockedCounter counter, int factor) {
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

