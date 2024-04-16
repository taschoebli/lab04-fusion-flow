package io.flowing.retail.accounting.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowing.retail.accounting.flow.PaymentReceivedAdapter;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class AccountingRestController {

    @Autowired
    private RuntimeService runtimeService;

    @RequestMapping(path = "/api/accounting/confirmPayment", method = PUT)
    public String placeOrder(@RequestParam(value = "processInstance") String processInstance) throws JsonProcessingException {

        System.out.println("payment received for business process: " + processInstance);

        runtimeService.createMessageCorrelation("PaymentReceived")
                .processInstanceBusinessKey(processInstance)
                .correlateWithResult();

        return "Payment confirmed";
    }
}
