package com.dubovskiy.main;

import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Test MDB
 */
@MessageDriven(name = "BookingQueueReceiver",
        activationConfig = {
                 @ActivationConfigProperty(propertyName = "destinationLookup",
                                           propertyValue = BookingQueueDefinition.BOOKING_QUEUE),
                 @ActivationConfigProperty(propertyName = "destinationType",
                                           propertyValue = "javax.jms.Queue"),})
public class BookingQueueReceiver implements MessageListener {

    private final Logger log = Logger.getLogger("terminal");

    @Override
    public void onMessage(Message message) {

        try {
            final String text = message.getBody(String.class);
            log.debug("Received message " + text);
        } catch (JMSException ex) {
            log.error(ex.toString());
        }

    }
}
