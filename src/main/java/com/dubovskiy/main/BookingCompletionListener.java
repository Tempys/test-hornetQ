package com.dubovskiy.main;

import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.CompletionListener;
import javax.jms.Message;

/**
 * Created by Misha on 01.09.2015.
 */
@ApplicationScoped
public class BookingCompletionListener implements CompletionListener {

    private final Logger log = Logger.getLogger("terminal");

    @Override
    public void onCompletion(Message message) {
        try {
            final String text = message.getBody(String.class);
            log.info("Send was successful: " + text );
        } catch (Exception e) {
            log.error("Problem with message format");
        }
    }

    @Override
    public void onException(Message message, Exception exception) {
        try {
            final String text = message.getBody(String.class);
            log.debug("Send failed… " + text);
        } catch (Exception e) {
            log.debug("Problem with message format");
        }

    }
}
