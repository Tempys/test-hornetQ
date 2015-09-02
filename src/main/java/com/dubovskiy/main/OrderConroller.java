package com.dubovskiy.main;


import org.apache.log4j.Logger;
import javax.annotation.Resource;
import javax.jms.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/postOrder")
public class OrderConroller {

    private final Logger log = Logger.getLogger("terminal");

    /*
     * Map a JNDI connection factory name. As this service is going to be in same
     * JAVA VM as JMS use "java:/ConnectionFactory". You can find this entry in JBOSS server
     * standalone-full.xml configuration file.
     */
    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

 /*
  * Map the queue. Note how the queue name is defined here. This should match the context
  * of JNDI defined which is "java" namespace followed by the entry name as given in
  * standalone-full.xml configuration file.
  */
 @Resource(mappedName = "java:/queue/ticketOrderQueue")
 private Queue orderQueue;

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response postOrder(String xmlStr) throws JMSException {

        log.debug("Started processing the order...."+ xmlStr);

        Connection con = null;
        try{
            //In real world you may want to do this only once
            log.debug("java:/ConnectionFactory: "+ connectionFactory );
            con = connectionFactory.createConnection();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(orderQueue);
            log.debug("starting HornetQ-JMS connection");
            con.start();
            log.debug("started HornetQ-JMS connection");
            TextMessage txtMsg = session.createTextMessage();
            txtMsg.setText(xmlStr);
            producer.send(txtMsg);
            log.debug("Sent message HornetQ-JMS to QUEUE");
        }catch(JMSException e){
            log.error("Failed to push order to the queue ", e);

        }finally{
            if(con != null){
                try{
                    con.close();
                }catch(JMSException e){
                    log.error("Unexpected error while trying to close the connection", e);
                }
            }
        }
        return Response.status(200).entity("Received XML").build();
    }

    @GET
    public Response postOrder(){
        log.debug("PostOrder receive");
        return Response.ok().build();
    }

}
