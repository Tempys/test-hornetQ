package com.dubovskiy.main;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@ApplicationScoped
public class BookingQueueProducer {

    @Inject
    private JMSContext context;
    @Inject
    private BookingCompletionListener bookingCompletionListener;

    @Resource(mappedName = BookingQueueDefinition.BOOKING_QUEUE)
    private Queue syncQueue;

    public void sendMessage(String txt) {
          context.createProducer()
                 .setAsync(bookingCompletionListener)
                 .send(syncQueue, txt);
    }

}
