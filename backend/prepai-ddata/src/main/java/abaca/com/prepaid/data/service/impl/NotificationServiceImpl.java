package abaca.com.prepaid.data.service.impl;

import abaca.com.jms.queue.JmsQueueName;
import abaca.com.jms.service.JmsRequestResponseTemplate;
import abaca.com.jms.service.JmsRequestResponseTemplateImpl;
import abaca.com.prepaid.data.service.NotificationService;
import lombok.extern.slf4j.Slf4j;

import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
  
  @Autowired
  private JmsRequestResponseTemplate jmsRequestResponseTemplate;
  
    @Override
    public boolean sendSMS(String data) {
      // TODO: Call notification service to send sms
      boolean result = false;
      try {
        final TextMessage textMessage = (TextMessage) jmsRequestResponseTemplate
            .sendAndReceive(JmsQueueName.PHONE_VOUCHER_NOTIFICATION, data);
        if (null != textMessage) {
          log.info("Sent data successfully");
          result = true;
        }
      } catch (Exception e) {
        log.error("Error occurs when sending data via a message broker {}", e.getMessage());
      }
      return result;
    }
}
