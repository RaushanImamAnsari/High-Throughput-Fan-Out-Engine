package com.fanout.sink;

import com.fanout.model.Record;

public interface Sink {
    void send(Record record);
    String name();
}
