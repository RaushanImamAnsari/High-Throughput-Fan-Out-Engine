# High-Throughput Fan-Out Engine (Java)
## Overview

This project implements a High-Throughput Distributed Fan-Out & Transformation Engine in Java.

The system reads records from a large flat-file source (CSV), processes them in a streaming and memory-safe manner, and fans out each record to multiple downstream sinks in parallel.
Each sink simulates a different type of external system and applies its own rate-limiting and retry logic.

The design focuses on scalability, resilience, and extensibility, as required by the assignment.

## Key Features

### 🚀 Streaming File Ingestion

* Reads CSV files line by line
* Designed to handle very large files (100GB+)
* No full file loading into memory, ensuring low heap usage

### 🔁 Fan-Out Architecture

* Each input record is distributed to multiple sinks
* Sinks operate independently from each other
* Failure or slowness in one sink does not block others

### ⚙️ Concurrency

* Parallel sink execution using ExecutorService
* Improves throughput by utilizing multiple CPU cores
* Designed to scale linearly with available system resources

### 🛡️ Rate Limiting & Resilience

* Per-sink rate limiting to protect downstream systems
* Retry mechanism with a maximum of 3 retries per record
* Graceful handling of malformed or invalid input data
* System continues running without crashes or data loss

### 📊 Observability

* Periodic status reporting every 5 seconds

#### Displays:
* Total records processed
* Throughput (records per second)
* Success count per sink
* Failure count per sink

### 🧩 Extensible Design

* Implements the Strategy Pattern for sink handling
* New sinks can be added without modifying core orchestration logic
* Promotes clean separation of concerns and future scalability

# Architecture Overview

```
CSV File (Streaming)
         |
         v
FileStreamer (Producer)
         |
         v
Record (Domain Model)
         |
         v
+----------------------+
|   Fan-Out Orchestrator |
+----------------------+
    |            |
    v            v
REST Sink     MQ Sink
(Rate Limit)  (Mock)
```
# Project Structure

```
fanout-engine
├── src
│   └── main
│       ├── java
│       │   └── com.fanout
│       │       ├── ingestion     # File streaming logic
│       │       ├── model         # Record model
│       │       ├── sink          # Sink strategies
│       │       ├── throttle      # Rate limiting
│       │       ├── resilience    # Retry logic
│       │       ├── metrics       # Observability
│       │       └── Main.java     # Application entry point
│       └── resources
│           └── input
│               └── sample.csv    # Sample input file
├── pom.xml
└── README.md
``` 
# How It Works

## Ingestion

* CSV file is read using BufferedReader via classpath resources.
* Each line is parsed into a Record.

## Fan-Out

* For each record, tasks are submitted to an ExecutorService.
* Each sink processes the record independently.

## Rate Limiting

* Each sink has its own SimpleRateLimiter.
* Ensures downstream systems are not overwhelmed.

## Retry Handling

* Each sink retries up to 3 times on failure.
* Failures are recorded without crashing the system.

## Metrics Reporting

* A scheduled task prints system metrics every 5 seconds.

# Running the Application
## Prerequisites

* Java 21+
* Maven

## Steps

1. Clone the repository
2. Build the project:
```
mvn clean package
```
3. Run the application:
```
mvn exec:java -Dexec.mainClass="com.fanout.Main"
```

Or simply run Main.java directly from IntelliJ.

# Sample Output
```
Fan-Out Engine started
[REST] Sent: Record{id='1', name='Alice', email='alice@test.com'}
[MQ] Published: Record{id='1', name='Alice', email='alice@test.com'}

📊 STATUS UPDATE
Processed: 3
Throughput: 1 records/sec
Success: {REST_API=3}
Failure: {}
```
# Design Decisions

### Streaming over Batch
* Ensures low memory usage and supports very large files.

### ExecutorService instead of Frameworks
* Keeps the solution simple, transparent, and easy to reason about.

### Strategy Pattern for Sinks
* New sinks can be added without modifying the core orchestration logic.

### Independent Metrics Thread
* Observability is decoupled from data processing.

# Assumptions

* CSV input format is consistent (id, name, email).
* Sinks are mocked and simulate external systems.
* Application runs as a long-lived service (metrics continue printing).

# Extensibility

#### To add a new sink (e.g., Elasticsearch):

* Implement the Sink interface
* Add rate limiting if required
* Register the sink in Main
* No core logic changes required.

# Conclusion

### This project demonstrates:

* High-throughput streaming ingestion
* Parallel fan-out processing
* Resilience through throttling and retries
* Clean, extensible Java design