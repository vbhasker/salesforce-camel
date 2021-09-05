package com.example.sfcamel.route;

import com.example.sfcamel.dto.Log;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RestRouter extends RouteBuilder {
    @Override
    public void configure() {

        restConfiguration()
                .component("netty-http")
                .port(8081)
                .bindingMode(RestBindingMode.json);

        rest("/say")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .get("/hello").route()
                .to("direct:hello")
                .endRest()
                .get("/contacts").route()
                .to("direct:sfGetContacts")
                .endRest();

        rest("/platformevent")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .post().type(Log.class)
                .route()
                .to("direct:sfPlatformEventLog")
                .endRest();

        from("direct:hello")
                .to("direct:sfGetVersions");
    }
}
