package com.dubovskiy.main;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

/**
 * Created by Misha on 25.08.2015.
 */
public class JMSConsumerExample {
    /*
 * for a remote client use RemoteConnectionFactory JNDI which will be defined in standalone-full.xml
 * configuration file
 */
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    //Queue name as mentioned in standalone-full.xml under <entry-name>
    private static final String DEFAULT_DESTINATION = "jms/queue/ticketOrderQueue";
    private static final String DEFAULT_USERNAME = "jmsadmin";
    private static final String DEFAULT_PASSWORD = "jmsadmin";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "remote://localhost:4447";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        Destination destination = null;
        TextMessage message = null;
        Context context = null;
        try {
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL,
                    System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            env.put(Context.SECURITY_PRINCIPAL,
                    System.getProperty("username", DEFAULT_USERNAME));
            env.put(Context.SECURITY_CREDENTIALS,
                    System.getProperty("password", DEFAULT_PASSWORD));
            context = new InitialContext(env);
            String connectionFactoryString = System.getProperty(
                    "connection.factory", DEFAULT_CONNECTION_FACTORY);
            connectionFactory = (ConnectionFactory) context
                    .lookup(connectionFactoryString);
            String destinationString = System.getProperty("destination",
                    DEFAULT_DESTINATION);
            destination = (Destination) context.lookup(destinationString);
            connection = connectionFactory.createConnection(
                    System.getProperty("username", DEFAULT_USERNAME),
                    System.getProperty("password", DEFAULT_PASSWORD));
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(destination);
            connection.start();
            message = (TextMessage) consumer.receive(10000);
            System.out.println("Received message " + message.getText());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (context != null) {
                context.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}

