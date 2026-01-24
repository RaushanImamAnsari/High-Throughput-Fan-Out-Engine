package com.fanout.model;

public class Record {
    public String id;
    public String name;
    public String email;

    public Record(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Record{id='" + id + "', name='" + name + "', email='" + email + "'}";
    }
}
