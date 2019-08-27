package com.nowcoder;

public class ThreadLocal {

    private static java.lang.ThreadLocal<Integer> threadLocalUserID = new java.lang.ThreadLocal<>();
    private static int userid;

    public static void testThreadlocal() {
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            threadLocalUserID.set(finalI);
                            try {
                                Thread.sleep(1000);
                                System.out.println("ThreadLocal" + threadLocalUserID.get());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
        }

    }

    public static void main(String[] args) {
        testThreadlocal();
    }

}
