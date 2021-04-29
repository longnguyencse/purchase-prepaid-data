package abaca.com.prepaid.data.service.impl;

import abaca.com.prepaid.data.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private RabbitTemplate rabbitTemplate;
  
    @Override
    public boolean sendSMS(String data) {
      try {
        rabbitTemplate.convertAndSend(data);
      } catch (Exception e) {
        log.error("Error occurs when sending data via a message broker {}", e.getMessage());
        return false;
      }
      return true;
    }
}
