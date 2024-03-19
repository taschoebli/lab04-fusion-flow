package io.flowing.retail.booking.rest;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.flowing.retail.booking.domain.Customer;
import io.flowing.retail.booking.domain.Booking;

import java.util.UUID;

@RestController
public class ShopRestController {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private ObjectMapper objectMapper;

  @RequestMapping(path = "/api/cart/book", method = PUT)
  public String placeOrder(@RequestParam(value = "date") String date) throws JsonProcessingException {
    Booking booking = new Booking();
    booking.addItem("article1", 5);
    booking.addItem("article2", 10);
    booking.setDate(date);
    
    booking.setCustomer(new Customer("Camunda", "Zossener Strasse 55\n10961 Berlin\nGermany"));

    String payLoad = objectMapper.writeValueAsString(booking);

    runtimeService.createMessageCorrelation("BookingCreated")
            .processInstanceBusinessKey(UUID.randomUUID().toString())
            .setVariable("paymentTypeIsInvoice", true)
            .setVariable("booking", payLoad)
            .correlateWithResult();

    return "Order received";
  }

}
