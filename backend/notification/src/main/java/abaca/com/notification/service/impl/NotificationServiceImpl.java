package abaca.com.notification.service.impl;

import abaca.com.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Override
    public boolean sendSMS(String msg) {
        log.info("Send msg success");
        return true;
    }
}
