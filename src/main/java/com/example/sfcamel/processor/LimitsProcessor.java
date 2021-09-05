package com.example.sfcamel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class LimitsProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        String body = in.getBody(String.class);
        System.out.println("This is returned from the exchange");
        System.out.println(body);
    }
}
