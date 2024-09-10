# fireProcessingTimers Debugging Repository

## Introduction
This repository is designed to investigate and debug the performance issue related to timer processing in Flink-benchmark. Specifically, it aims to identify why a "immediately" scheduled timer fails to cancel properly on powerful CPU architectures.

## Problem Statement
The current implementation of fireProcessingTimers in Flink-benchmark exhibits lower-than-expected performance on high-end CPUs. This is caused by the invocation of timer callbacks even when they are cancelled immediately. As a result, invalid timers accumulate and slow down the benchmark's timer test.

## Simplified Test Case
To isolate this issue, a test code has been implemented in this repository. The code schedules an "immediately" timer and then cancels it. In an ideal scenario, no timer callback should be executed. However, our observation is that sometimes the callback function still gets invoked, leading to performance degradation.

## Running the Test
To reproduce and debug this issue, execute the following command:
```shell
./run.sh
```
This script will run the test code, allowing you to observe the behavior of fireProcessingTimers on your system. Your feedback and contributions are welcome in helping us resolve this performance issue and improve Flink-benchmark's timer functionality.