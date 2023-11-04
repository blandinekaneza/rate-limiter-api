package rw.notification.ratelimiter.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.notification.ratelimiter.api.model.NotificationRequest;
import rw.notification.ratelimiter.api.service.NotificationService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Handles request for notification
     * Taken the subscriber id as subId in reqbody
     * return a success/failure message
     * @param reqBody
     * @return
     */
    @PostMapping(value = "/send-notification")
    public ResponseEntity<String> sendNotification(@RequestBody @Valid NotificationRequest reqBody) {
        Integer res = this.notificationService.handleNotificationRequest(reqBody);
        if (res == 1) {
            return ResponseEntity.ok("Rate limit exceeded");
        } else if (res == 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request successful");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server error happened, please try again later");
    }
}
