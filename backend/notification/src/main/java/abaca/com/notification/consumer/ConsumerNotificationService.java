package abaca.com.notification.consumer;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import abaca.com.jms.dto.PhoneVoucherJmsDto;
import abaca.com.jms.queue.JmsQueueName;
import abaca.com.notification.service.NotificationService;

@Service
public class ConsumerNotificationService {
  
  private final ObjectMapper objectMapper;
  
  public ConsumerNotificationService() {
    objectMapper = new ObjectMapper();
  }
  
  @Autowired
  private NotificationService notificationService;
  
  @JmsListener(destination = JmsQueueName.PHONE_VOUCHER_NOTIFICATION)
  public void receiveMessage(final Message jsonMessage)
      throws JMSException, JsonMappingException, JsonProcessingException {
    System.out.println("Received message " + jsonMessage);
    if (jsonMessage instanceof TextMessage) {
      TextMessage textMessage = (TextMessage) jsonMessage;
      final String messageData = textMessage.getText();
      final PhoneVoucherJmsDto phoneVoucherJmsDto =
          objectMapper.readValue(messageData, PhoneVoucherJmsDto.class);
      notificationService.sendSMS(phoneVoucherJmsDto);
    }
  }
}
