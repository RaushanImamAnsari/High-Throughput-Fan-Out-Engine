package com.fanout.sink;

import com.fanout.model.Record;
import com.fanout.resilience.RetryExecutor;
import com.fanout.throttle.SimpleRateLimiter;
import com.fanout.metrics.Metrics;


public class RestApiSink implements Sink {

    private final SimpleRateLimiter limiter = new SimpleRateLimiter(5); // 5 req/sec

    @Override
    public void send(Record record) {
        RetryExecutor.executeWithRetry(() -> {
            limiter.acquire();
            System.out.println("[REST] Sent: " + record);
            Metrics.markSuccess(name());
        }, 3);

    }

    @Override
    public String name() {
        return "REST_API";
    }
}
