package io.github.sng78.tasks;

public class MainDeadLock {
    private static final String A = "A";
    private static final String B = "B";

    public static void main(String[] args) {
        new Thread(new MyRunnable(A, B)).start();
        new Thread(new MyRunnable(B, A)).start();
    }
}
