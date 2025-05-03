package com.example.sachinlld.rate_limiting;

import java.time.Instant;

public class FixedWindowCounterAlgorithm {
    private final long windowSizeInSeconds; // size of each window in seconds
    private final long maxRequestsPerWindow; // max number of request allowed per window
    private long currentWindowStart; // start time of the current window
    private long requestCount; // number of requests in the current window

    /**
     * Initialize the values
     * @param windowSizeInSeconds
     * @param maxRequestsPerWindow
     */
    public FixedWindowCounterAlgorithm(long windowSizeInSeconds, long maxRequestsPerWindow) {
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.currentWindowStart = Instant.now().getEpochSecond();
        this.requestCount = 0;
    }

    /**
     * Method to check if we need to allow this request or not
     * @return
     */
    public synchronized boolean allowRequests() {
        long now = Instant.now().getEpochSecond();
        //check if we have moved to a new window
        if (now - currentWindowStart>=windowSizeInSeconds) {
            currentWindowStart = now;
            requestCount=0;
        }
        // We've exceeded the limit for this window, deny the request
        if (requestCount>maxRequestsPerWindow) {
            return false;
        }
        requestCount++; // Increment the count for this window
        return true; // Allow the request
    }
}
