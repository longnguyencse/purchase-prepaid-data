package abaca.com.jms.service;

import javax.jms.Message;

import org.springframework.jms.JmsException;

public interface JmsRequestResponseTemplate {
  Message sendAndReceive(String destinationName, String message) throws JmsException;

  void send(String destinationName, String message) throws JmsException;
}
