package io.flowing.retail.booking.flow;

import io.flowing.retail.booking.messages.MessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;

@Component
public class QrInvoiceAdapter implements JavaDelegate {

    @Autowired
    MessageSender messageSender;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Object totalCost = execution.getVariable("booking");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8092/api/qr/createQrInvoice?totalCost=125"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
