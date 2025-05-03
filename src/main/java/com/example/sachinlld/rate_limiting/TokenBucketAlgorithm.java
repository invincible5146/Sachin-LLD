package com.example.sachinlld.rate_limiting;

import java.time.Instant;

public class TokenBucketAlgorithm {
    private final long capacity; // max number of tokens the bucket can hold
    private final double refillRate; //rate at which tokens are filled into the bucket
    private double tokens; //current number of tokens in the bucket
    private Instant lastRefillTimestamp; //last time we refilled the bucket;

    /**
     * Constructer to intialize the values
     * @param capacity
     * @param refillRate
     */
    public TokenBucketAlgorithm(long capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity; // start with full capacity bucket
        this.lastRefillTimestamp = Instant.now();
    }

    /**
     * Method to check if we allow the request by consuming the number of tokens or not
     * @param tokens
     * @return
     */
    public synchronized boolean allowRequests(int tokens) {
        // First, add any new tokens based on elapsed time
        //can use cronjob or redis for this
        refill();
        if (this.tokens<tokens) {
            return false;
        }
        this.tokens-=tokens;
        return true;
    }

    /**
     * Method to refill the tokens in the bucket
     */
    private void refill() {
        Instant now = Instant.now();
        //calculate how many tokens should be added based upon elapsed time
        double tokensToAdd = (now.toEpochMilli()-lastRefillTimestamp.toEpochMilli())*refillRate/1000.0;
        this.tokens+= Math.min(tokensToAdd+this.tokens, capacity); // add tokens but dont exceed capacity
        this.lastRefillTimestamp = now;
    }
}
