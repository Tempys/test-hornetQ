package com.dubovskiy.main;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ApplicationScoped
public class BookerService implements Serializable {

    @Inject
    private BookingQueueProducer bookingQueueProducer;

    public void bookSeat() {
        bookingQueueProducer.sendMessage("[JMS Message] User registered seat" + 1);
    }
}
