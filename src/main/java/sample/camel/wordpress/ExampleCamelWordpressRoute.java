package sample.camel.wordpress;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sample.camel.wordpress.model.Statistics;

@Component
public class ExampleCamelWordpressRoute extends RouteBuilder {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleCamelWordpressRoute.class);
    
    @Value("${football.api.fixture.path}")
    private String footballApiFixturePath;
    
    @Value("${football.api.host}")
    private String footballApiHost;
    
    @Value("${football.api.token}")
    private String footballApiToken;

    @Override
    public void configure() throws Exception {
        LOGGER.info("Setting up route");
        
        restConfiguration() 
        .component("servlet") 
        .producerComponent("restlet")
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "Camel Example Wordpress API")
            .apiProperty("api.version", "1.0")
            .apiProperty("cors", "true")
            .apiContextRouteId("doc-api")
            .bindingMode(RestBindingMode.json);
        
        rest("/run/statistics").description("Start point to run the sample application")
            .consumes("application/json")
            .produces("application/json")
                .get("/{fixtureId}")
                    .description("Run NLG based on the game statistics")
                    .outType(String.class)
                .to("direct:get-fixture-details");
        
        from("direct:get-fixture-details")
            .routeId("get-fixture-details")
            .setHeader("X-Auth-Token", constant(footballApiToken))
            .to(String.format("rest:get:%s?host=%s&synchronous=true", footballApiFixturePath, footballApiHost))
            .unmarshal().json(JsonLibrary.Jackson, Statistics.class)
            .log("${body}");
    }

}
