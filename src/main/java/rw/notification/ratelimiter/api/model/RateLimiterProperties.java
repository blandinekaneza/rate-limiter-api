package rw.notification.ratelimiter.api.model;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("rate-limit")
public class RateLimiterProperties {
    private int maxBucketSize;
    private int refillRate;

    // Getters and setters

    public int getMaxBucketSize() {
        return maxBucketSize;
    }

    public void setMaxBucketSize(int maxBucketSize) {
        this.maxBucketSize = maxBucketSize;
    }

    public int getRefillRate() {
        return refillRate;
    }

    public void setRefillRate(int refillRate) {
        this.refillRate = refillRate;
    }
}
