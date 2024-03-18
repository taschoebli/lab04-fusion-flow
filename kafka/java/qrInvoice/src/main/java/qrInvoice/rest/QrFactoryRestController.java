package qrInvoice.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class QrFactoryRestController {

  @RequestMapping(path = "/api/qr/createQrInvoice", method = GET)
  public String placeOrder(@RequestParam(value = "totalCost") float totalCost) {

    String qrInvoice = "Pretend this is a QR invoice for " + totalCost + " CHF";
    qrInvoice = Base64.getEncoder().encodeToString(qrInvoice.getBytes());
    return qrInvoice;
  }

}
