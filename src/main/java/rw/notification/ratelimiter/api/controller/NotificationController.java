package rw.notification.ratelimiter.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.notification.ratelimiter.api.service.NotificationService;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-notification")
    public ResponseEntity<String> sendNotification() {
        if (notificationService.allowRequest()) {
            return ResponseEntity.ok("Request successful");
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
        }
    }
}
