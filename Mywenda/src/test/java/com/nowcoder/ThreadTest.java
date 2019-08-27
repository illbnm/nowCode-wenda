package com.nowcoder;


import java.util.concurrent.ArrayBlockingQueue;

import java.util.concurrent.BlockingQueue;

public class ThreadTest {

    static class Consumer implements Runnable {
        private BlockingQueue<String> q;

        public Consumer(BlockingQueue<String> q) {
            this.q = q;
        }

        @Override
        public void run() {
            try {

                while (true) {
                    System.out.println(Thread.currentThread().getName() + ":" + q.take());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    static class Produce implements Runnable {
        private BlockingQueue<String> q;

        public Produce(BlockingQueue<String> q) {
            this.q = q;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(1000);
                    q.add(String.valueOf(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void testBlockingQueue() {
        BlockingQueue<String> q = new ArrayBlockingQueue<String>(10);
        new Thread(new Produce(q)).start();
        new Thread(new Consumer(q),"Consumer1").start();
        new Thread(new Consumer(q),"Consumer2").start();
    }

    public static void main(String[] args) {
        testBlockingQueue();
    }
}
