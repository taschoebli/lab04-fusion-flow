package io.flowing.retail.kafka.order.flow;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.flowing.retail.kafka.order.flow.payload.OrderCompletedEventPayload;
import io.flowing.retail.kafka.order.messages.Message;
import io.flowing.retail.kafka.order.messages.MessageSender;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;

@Component
public class OrderCompletedAdapter {
  
  @Autowired
  private MessageSender messageSender;  

  @JobWorker(type = "order-completed")
  public void handle(JobClient client, ActivatedJob job) {
    OrderFlowContext context = OrderFlowContext.fromMap(job.getVariablesAsMap());
       
    messageSender.send( //
        new Message<OrderCompletedEventPayload>( //
            "OrderCompletedEvent", //
            context.getTraceId(), //
            new OrderCompletedEventPayload() //
              .setOrderId(context.getOrderId())));
    
    //TODO: Reintorduce traceId?     .setCorrelationId(event.get)));
    
    client.newCompleteCommand(job.getKey()).send().join();
  }

  

}
