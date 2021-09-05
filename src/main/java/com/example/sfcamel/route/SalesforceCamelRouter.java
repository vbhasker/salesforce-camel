package com.example.sfcamel.route;

import com.example.sfcamel.dto.Contact;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class SalesforceCamelRouter extends RouteBuilder {

    @Override
    public void configure(){

        from("salesforce:event/Log__e")
                .to("log:my-platform-event");

        from("direct:sfPlatformEventLog")
                .setHeader(Exchange.HTTP_RESPONSE_CODE,simple("201"))
                .to("log:incoming-platform-event-data");

        from("direct:sfGetVersions")
                .to("salesforce:limits")
                .unmarshal().json(JsonLibrary.Jackson);

        from("direct:sfGetContacts")
                .setHeader("sObjectQuery", simple("SELECT Id, Name FROM Contact"))
                .to("salesforce:query?sObjectQuery=&sObjectClass=" + Contact.class.getName())
                .process(exchange -> {
                    Object body = exchange.getIn().getBody();
                    System.out.println(body.getClass());
                    System.out.println(body);
                })
                .unmarshal().json(JsonLibrary.Jackson);

//        from("direct:querySalesforce")
//                .to("salesforce:limits")
//                .choice()
//                .when(spel("#{1.0 * body.dailyApiRequests.remaining / body.dailyApiRequests.max < 0.1}"))
//                .setHeader("sObjectQuery", simple("SELECT Id, Name FROM Contact'"))
//                .to("salesforce:query?sObjectQuery=&sObjectClass=" + Contact.class.getName())
//                .process(exchange -> {
//                    Object body = exchange.getIn().getBody();
//                    System.out.println(body.getClass());
//                    System.out.println(body);
//                })
//                .otherwise()
//                .setBody(constant("Used up Salesforce API limits, leaving 10% for critical routes"))
//                .log("Got this body ${body}")
//                .endChoice();
    }
}
