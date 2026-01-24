package com.fanout.ingestion;

import com.fanout.model.Record;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileStreamer {

    public interface RecordConsumer {
        void accept(Record record);
    }

    public static void streamCsv(String resourcePath, RecordConsumer consumer) {
        try {
            InputStream is = FileStreamer.class
                    .getClassLoader()
                    .getResourceAsStream(resourcePath);

            if (is == null) {
                throw new RuntimeException("File not found in resources: " + resourcePath);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                Record record = new Record(parts[0], parts[1], parts[2]);

                consumer.accept(record);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading file", e);
        }
    }
}
