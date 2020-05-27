/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;


/**
 *
 * @author cesarino
 */
public class SynchronizedCounter {

    private int counter;

    public SynchronizedCounter() {
        counter = 0;
    }

    synchronized public void increment() {
        counter++;
    }

    public int get() {
        return counter;
    }
}

class MySynchronizedTask implements Runnable {

    private SynchronizedCounter counter;
    private int factor;

    public MySynchronizedTask(SynchronizedCounter counter, int factor) {
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
