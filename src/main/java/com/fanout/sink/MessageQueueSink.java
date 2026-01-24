package com.fanout.sink;

import com.fanout.model.Record;

public class MessageQueueSink implements Sink {

    @Override
    public void send(Record record) {
        System.out.println("[MQ] Published: " + record);
    }

    @Override
    public String name() {
        return "MESSAGE_QUEUE";
    }
}
