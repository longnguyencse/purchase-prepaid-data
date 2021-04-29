package abaca.com.jms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class JmsRequestResponseTemplateImpl implements JmsRequestResponseTemplate {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public Message sendAndReceive(String destinationName, String message) throws JmsException {
        return jmsTemplate.sendAndReceive(destinationName, this.getMessageCreator(message));
    }

    @Override
    public void send(String destinationName, String message) throws JmsException {
        jmsTemplate.send(destinationName, this.getMessageCreator(message));
    }

    private MessageCreator getMessageCreator(final String jmsMessageText) {
        return session -> {
            TextMessage message = session.createTextMessage();
            message.setText(jmsMessageText);
            return message;
        };
    }

}
