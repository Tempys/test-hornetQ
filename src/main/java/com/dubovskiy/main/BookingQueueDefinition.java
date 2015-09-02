package com.dubovskiy.main;

import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(
        name = BookingQueueDefinition.BOOKING_QUEUE,
        interfaceName = "javax.jms.Queue"
)
public class BookingQueueDefinition {

    public static final String BOOKING_QUEUE = "java:global/jms/bookingQueue";

}
