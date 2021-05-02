package abaca.com.notification.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@RabbitListener(queues = "spring-queue")
public class ConsumerNotificationService {

    private final CountDownLatch latch = new CountDownLatch(1);

    @RabbitHandler
    public void receiveMessage(byte[] message) {
        String msg = new String(message);
        log.info("Received <" + msg + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
