package io.github.sng78.webapp;

public class DeadLock {
    public static final Object A = new Object();
    public static final Object B = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (A) {
                System.out.println("Thread 1 blocked A");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread 1 waiting for lock B");
                synchronized (B) {
                    System.out.println("Thread 1 blocked A and B");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (B) {
                System.out.println("Thread 2 blocked B");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread 2 waiting for lock A");
                synchronized (A) {
                    System.out.println("Thread 2 blocked A and B");
                }
            }
        }).start();
    }
}
