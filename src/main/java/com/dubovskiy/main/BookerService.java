package com.dubovskiy.main;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ApplicationScoped
public class BookerService implements Serializable {

    @Inject
    private BookingQueueProducer bookingQueueProducer;
    @Inject
    private BookingQueueReceiver bookingQueueReceiver;

    public void bookSeat() {
        bookingQueueProducer.sendMessage("[JMS Message] User registered seat" + 1);
    }

    public String getBook(){
       return "";
    }
}
