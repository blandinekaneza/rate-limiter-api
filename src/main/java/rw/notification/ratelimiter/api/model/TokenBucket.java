package rw.notification.ratelimiter.api.model;

// Token Bucket Algorithm

public class TokenBucket {
    private final long maxBucketSize;
    private final long refillRate;

    private double currentBucketSize;
    private long lastRefillTimestamp;

    // Constructor
    public TokenBucket(long maxBucketSize, long refillRate) {
        this.maxBucketSize = maxBucketSize;
        this.refillRate = refillRate;

        // Number of tokens initially is equal to the maximum capacity
        currentBucketSize = maxBucketSize;
        // Current time in nanoseconds
        lastRefillTimestamp = System.nanoTime();
    }

    // Allow request method
    // Synchronized as several threads may be calling the method concurrently
    public synchronized boolean allowRequest(int tokens) {
        // First, refill bucket with tokens accumulated since the last call
        refill();

        // If bucket has enough tokens, call is allowed
        if (currentBucketSize > tokens) {
            currentBucketSize -= tokens;

            return true;
        }

        // Request is throttled as bucket does not have enough tokens
        return false;
    }

    // Refill method
    public void refill() {
        long now = System.nanoTime();
        // These many tokens accumulated since the last refill
        double tokensToAdd = (now - lastRefillTimestamp) * refillRate / 1e9;
        // Number of tokens should never exceed maximum capacity
        currentBucketSize = Math.min(currentBucketSize + tokensToAdd, maxBucketSize);
        lastRefillTimestamp = now;
    }
}
