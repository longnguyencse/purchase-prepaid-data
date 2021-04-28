package abaca.com.notification.service;

import abaca.com.jms.dto.PhoneVoucherJmsDto;

public interface NotificationService {
    boolean sendSMS(final PhoneVoucherJmsDto phoneVoucherJmsDto);
}
