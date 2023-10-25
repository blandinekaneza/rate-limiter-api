package rw.notification.ratelimiter.api.service;

import org.springframework.stereotype.Service;
import rw.notification.ratelimiter.api.model.RateLimiterProperties;
import rw.notification.ratelimiter.api.model.TokenBucket;

@Service
public class NotificationService {

    private final TokenBucket tokenBucket;

    public NotificationService(RateLimiterProperties rateLimiterProperties) {
        this.tokenBucket = new TokenBucket(rateLimiterProperties.getMaxBucketSize(), rateLimiterProperties.getRefillRate()); // Adjust capacity as needed
    }

    public boolean allowRequest() {
        // add a value > au maxBucketSize will return "Rate limit exceeded"
        int tokens = 10000;
        // add a value < au maxBucketSize will return "Request successful"
        // int tokens = 1;
        return tokenBucket.allowRequest(tokens);
    }

}
