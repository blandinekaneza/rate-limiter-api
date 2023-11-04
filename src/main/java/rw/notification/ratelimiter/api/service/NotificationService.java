package rw.notification.ratelimiter.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import rw.notification.ratelimiter.api.model.NotificationRequest;
import rw.notification.ratelimiter.api.model.Subscriber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class NotificationService {

    /**
     * Gets existing list of configs
     * @return
     */
    public List<Subscriber> getSubscribersConfigs() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Subscriber[]> subscribersRef = new TypeReference<>() {};
            File subscribersStorage = ResourceUtils.getFile("classpath:subscribers.json");
            Subscriber[] subscribers = mapper.readValue(subscribersStorage, subscribersRef);
            return List.of(subscribers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Validating send notification request
     * @param subId
     * @return
     */
    public boolean validateRequest(String subId) {
        List<Subscriber> configs = this.getSubscribersConfigs();
        List<Subscriber> subsOfInterest = configs.stream().filter(s -> s.getSubId().equals(subId)).toList();
        Subscriber subOfInterest = subsOfInterest.get(0);

        Subscriber modifiedSub = this.allowRequest(subOfInterest);

        if (!ObjectUtils.isEmpty(modifiedSub)) {
            int lastConfigIndex = configs.indexOf(subOfInterest);

            List<Subscriber> ModifiableList = new ArrayList<>(configs);

            ModifiableList.set(lastConfigIndex, modifiedSub);
            saveNewConfigs(ModifiableList);
            return true;
        }

        return false;
    }

    /**
     * Saves updates to subscribers configs in a file
     * @param subscribers
     */
    public void saveNewConfigs(List<Subscriber> subscribers) {
        String jsonToBeWritten = convertListToJson(subscribers);
        try {
            System.out.println("Writing to a file");
            File subscribersStorage = ResourceUtils.getFile("classpath:subscribers.json");
            FileWriter writer = new FileWriter(subscribersStorage);
            writer.write(jsonToBeWritten);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //throw new RuntimeException(e);
        }
    }

    /**
     * Converts list of subscribers/configs to json string ready to be saved
     * @param list
     * @return
     */
    public static String convertListToJson(List<?> list) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * Sends a notification.
     * For now it just prints a notification message
     */
    public void sendNotification() {
        System.out.println("Send a new notification");
    }

    /**
     * Primary request handler for the send notification request
     * @param request
     * @return an integer repressing the results of the notifications processing
     */
    public Integer handleNotificationRequest(NotificationRequest request) {
        try {
            if (!this.validateRequest(request.getSubId())) {
                return 1;
            }

            this.sendNotification();
            return 2;

        } catch (Exception exception) {
            return 3;
        }
    }

    /**
     * Checks whether subscriber is allowed to send the request
     * The count is reset every 60 seconds
     * @param sub
     * @return
     */
    public Subscriber allowRequest(Subscriber sub) {
        long currentTime =  TimeUnit.MILLISECONDS.toSeconds(Instant.now().toEpochMilli());
        boolean shouldReset = (currentTime - sub.getLastSentNotificationEpochTime()) > 60;

        if (shouldReset) {
            System.out.println(String.format("%s %s %s", LocalDateTime.now().toString(), "Should reset the bucket", sub.getSubId()));

            sub.setUsedTokens(1);
            sub.setLastSentNotificationEpochTime(currentTime);
            return sub;
        }

        if (sub.getUsedTokens() < sub.getMaximumAllowed()) {
            System.out.println(String.format("%s %s %s", LocalDateTime.now().toString(), "Incrementing the count", sub.getSubId()));
            sub.incrementUsedTokens();
            sub.setLastSentNotificationEpochTime(currentTime);
            return sub;
        }

        return null;
    }
}
