package rw.notification.ratelimiter.api.model;
import jakarta.validation.constraints.Size;

public class NotificationRequest {

    @Size(min = 1)
    private String subId;

    public NotificationRequest(String subId) {
        this.subId = subId;
    }

    public NotificationRequest() {

    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }
}
