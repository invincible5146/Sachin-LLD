package com.example.sachinlld.rate_limiting;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;

public class LeakyBucketAlgorithm {
    private final long capacity; // Maximum number of requests the bucket can hold
    private final double leadRate; // Rate at which requests leak out of the bucket (requests per second)
    private final Queue<Instant>bucket; // Queue to hold timestamps of requests
    private Instant lastLeakTimestamp;  // Last time we leaked from the bucket

    /**
     * constructor to initialize the values
     * @param capacity
     * @param leadRate
     */
    public LeakyBucketAlgorithm(long capacity, double leadRate) {
        this.capacity = capacity;
        this.leadRate = leadRate;
        this.bucket = new LinkedList<>();
        this.lastLeakTimestamp = Instant.now();
    }

    /**
     * Method to check if the request should be allowed or not
     * @return
     */
    public synchronized boolean allowRequest() {
        leak(); //first leak out any request based on elapsed time
        if (bucket.size()<capacity) {
            bucket.offer(Instant.now());  // Add the new request to the bucket
            return true;  // Allow the request
        }
        return false; // bucket is full, dont allow request
    }

    private void leak() {
        Instant now = Instant.now();
        long elapsedTimeMillis = now.toEpochMilli() - lastLeakTimestamp.toEpochMilli();
        int leakedItems = (int) (elapsedTimeMillis*leadRate/1000.0); //calculate how many items have leaked
        //remove the leaked items from the bucket
        for(int i=0; i<leakedItems; i++) {
            bucket.poll();
        }
        this.lastLeakTimestamp = now;
    }
}
