package rw.notification.ratelimiter.api.model;

public class Subscriber {
    private String subId;
    private Integer maximumAllowed;
    private long lastSentNotificationEpochTime;
    private Integer usedTokens;

    public Subscriber(String subId, Integer maximumAllowed, Integer lastSentNotificationEpochTime, Integer usedTokens) {
        this.subId = subId;
        this.maximumAllowed = maximumAllowed;
        this.lastSentNotificationEpochTime = lastSentNotificationEpochTime;
        this.usedTokens = usedTokens;
    }

    public Subscriber() {
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public Integer getMaximumAllowed() {
        return maximumAllowed;
    }

    public void setMaximumAllowed(Integer maximumAllowed) {
        this.maximumAllowed = maximumAllowed;
    }

    public long getLastSentNotificationEpochTime() {
        return lastSentNotificationEpochTime;
    }

    public void setLastSentNotificationEpochTime(long lastSentNotificationEpochTime) {
        this.lastSentNotificationEpochTime = lastSentNotificationEpochTime;
    }

    public Integer getUsedTokens() {
        return usedTokens;
    }

    public void setUsedTokens(Integer usedTokens) {
        this.usedTokens = usedTokens;
    }

    public void incrementUsedTokens() {
        this.usedTokens ++;
    }
}
