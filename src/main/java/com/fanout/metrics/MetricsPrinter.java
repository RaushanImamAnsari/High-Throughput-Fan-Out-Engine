package com.fanout.metrics;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MetricsPrinter {

    public static void start() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> {
                    long elapsed = (System.currentTimeMillis() - Metrics.startTime.get()) / 1000;
                    long throughput = elapsed == 0 ? 0 : Metrics.totalProcessed.get() / elapsed;

                    System.out.println("\n📊 STATUS UPDATE");
                    System.out.println("Processed: " + Metrics.totalProcessed.get());
                    System.out.println("Throughput: " + throughput + " records/sec");
                    System.out.println("Success: " + Metrics.success);
                    System.out.println("Failure: " + Metrics.failure);
                }, 5, 5, TimeUnit.SECONDS);
    }
}
