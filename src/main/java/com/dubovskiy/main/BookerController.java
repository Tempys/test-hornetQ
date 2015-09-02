package com.dubovskiy.main;

import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/booker")
public class BookerController {

   private final Logger log = Logger.getLogger("terminal");
   @Inject
   BookerService bookerService;

   @GET
   public Response saveBooker(){
      log.debug("saveBooker request received");
      bookerService.bookSeat();

    return Response.ok().build();
   }


}
