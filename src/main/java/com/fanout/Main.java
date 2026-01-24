package com.fanout;

import com.fanout.ingestion.FileStreamer;
import com.fanout.metrics.MetricsPrinter;
import com.fanout.sink.MessageQueueSink;
import com.fanout.sink.RestApiSink;
import com.fanout.sink.Sink;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        // Start metrics reporting
        MetricsPrinter.start();

        System.out.println("Fan-Out Engine started");

        ExecutorService sinkExecutor = Executors.newFixedThreadPool(4);

        List<Sink> sinks = List.of(
                new RestApiSink(),
                new MessageQueueSink()
        );

        FileStreamer.streamCsv(
                "input/sample.csv",
                record -> {
                    for (Sink sink : sinks) {
                        sinkExecutor.submit(() -> sink.send(record));
                    }
                }
        );

        sinkExecutor.shutdown();
    }
}
