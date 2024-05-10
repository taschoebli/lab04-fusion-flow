package io.flowing.retail.booking.flow;

import io.flowing.retail.booking.messages.MessageSender;
import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.context.Context;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.camunda.bpm.engine.impl.jobexecutor.JobExecutorContext;

import java.util.Random;


@Component
@Configuration
@Named
public class CreditCardAdapter implements JavaDelegate {

    @Autowired
    MessageSender messageSender;

    public static String COULD_NOT_PROCESS_PAYMENT_ERROR = "Error_CouldNotProcessPayment";

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //simulate an error in the credit card processing
        JobExecutorContext jobExecuterContext = Context.getJobExecutorContext();
        if (jobExecuterContext != null && jobExecuterContext.getCurrentJob() != null){
            if (jobExecuterContext.getCurrentJob().getRetries() <= 1){
                throw new BpmnError(COULD_NOT_PROCESS_PAYMENT_ERROR);
            }
            Random r = new Random();
            float error = r.nextFloat();
            if (error < 0.2){
                throw new Exception("could not process payment, try again later");
            } else {
                execution.setVariable("paymentMade", true);
            }
        }
    }
}
