package com.fanout.resilience;

public class RetryExecutor {

    public static void executeWithRetry(Runnable task, int maxRetries) {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                task.run();
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt == maxRetries) {
                    throw e;
                }
            }
        }
    }
}
