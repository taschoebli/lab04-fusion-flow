package io.flowing.retail.booking.rest;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.flowing.retail.booking.domain.Customer;
import io.flowing.retail.booking.domain.Booking;
import io.flowing.retail.booking.messages.Message;
import io.flowing.retail.booking.messages.MessageSender;

@RestController
public class ShopRestController {
  
  @Autowired
  private MessageSender messageSender;


  @RequestMapping(path = "/api/cart/book", method = PUT)
  public String placeOrder(@RequestParam(value = "customerId") String customerId) {
    Booking booking = new Booking();
    booking.addItem("article1", 5);
    booking.addItem("article2", 10);
    
    booking.setCustomer(new Customer("Camunda", "Zossener Strasse 55\n10961 Berlin\nGermany"));
    
    Message<Booking> message = new Message<Booking>("EventBookedEvent", booking);
    messageSender.send(message);
        
    // note that we cannot easily return an order id here - as everything is asynchronous
    // and blocking the client is not what we want.
    // but we return an own correlationId which can be used in the UI to show status maybe later
    return "{\"traceId\": \"" + message.getTraceid() + "\"}";
  }

}
