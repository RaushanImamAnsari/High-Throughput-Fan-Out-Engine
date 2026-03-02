package com.fanout.metrics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Metrics {

    public static AtomicLong totalProcessed = new AtomicLong(0);
    public static AtomicLong startTime = new AtomicLong(System.currentTimeMillis());

    public static Map<String, AtomicLong> success = new ConcurrentHashMap<>();
    public static Map<String, AtomicLong> failure = new ConcurrentHashMap<>();

    public static void markSuccess(String sink) {
        success.computeIfAbsent(sink, k -> new AtomicLong()).incrementAndGet();
        totalProcessed.incrementAndGet();
    }




    public static void markFailure(String sink) {
        failure.computeIfAbsent(sink, k -> new AtomicLong()).incrementAndGet();
        totalProcessed.incrementAndGet();
    }
}
