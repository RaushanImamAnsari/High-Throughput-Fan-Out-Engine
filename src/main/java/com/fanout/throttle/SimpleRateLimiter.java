package com.fanout.throttle;

public class SimpleRateLimiter {

    private final long intervalMillis;
    private long lastRequestTime = 0;

    public SimpleRateLimiter(int permitsPerSecond) {
        this.intervalMillis = 1000L / permitsPerSecond;
    }

    public synchronized void acquire() {
        long now = System.currentTimeMillis();
        long waitTime = lastRequestTime + intervalMillis - now;

        if (waitTime > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        lastRequestTime = System.currentTimeMillis();
    }
}
