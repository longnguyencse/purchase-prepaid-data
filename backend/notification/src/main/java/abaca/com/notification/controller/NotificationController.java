package abaca.com.notification.controller;

import abaca.com.notification.dto.ResultDTO;
import abaca.com.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping(value = "")
    public ResultDTO<Boolean> sendSMS(@RequestParam("message") String message) {
        return ResultDTO.<Boolean>builder()
                .data(notificationService.sendSMS(message))
                .build();
    }
}
