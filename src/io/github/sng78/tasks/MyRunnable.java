package io.github.sng78.tasks;

public class MyRunnable implements Runnable {
    private final String A;
    private final String B;

    public MyRunnable(String A, String B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public void run() {
        synchronized (A) {
            System.out.printf("%s blocked %s%n", Thread.currentThread().getName(), A);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("%s waiting for lock %s%n", Thread.currentThread().getName(), B);
            synchronized (B) {
                System.out.printf("%s blocked A and B%n", Thread.currentThread().getName());
            }
        }
    }
}
