package abaca.com.prepaid.data.service.impl;

import abaca.com.prepaid.data.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public boolean sendSMS(String data) {
        // TODO: Call notifice service to send sms
        return false;
    }
}
