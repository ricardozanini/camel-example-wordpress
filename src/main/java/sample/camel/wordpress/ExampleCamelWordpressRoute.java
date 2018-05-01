package sample.camel.wordpress;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sample.camel.wordpress.model.Statistics;
import sample.camel.wordpress.nlg.ContentFactory;

@Component
public class ExampleCamelWordpressRoute extends RouteBuilder {
        
    @Value("${football.api.fixture.path}")
    private String footballApiFixturePath;
    
    @Value("${football.api.host}")
    private String footballApiHost;
    
    @Value("${football.api.token}")
    private String footballApiToken;
    
    @Value("${wordpress.url}")
    private String wordpressUrl;
    
    @Value("${wordpress.user}")
    private String wordpressUser;
    
    @Value("${wordpress.password}")
    private String wordpressPassword;

    @Override
    public void configure() throws Exception {        
        restConfiguration() 
        .component("servlet") 
        .producerComponent("restlet")
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "Camel Example Wordpress API")
            .apiProperty("api.version", "1.0")
            .apiProperty("cors", "true")
            .apiContextRouteId("doc-api")
            .bindingMode(RestBindingMode.json);
        
        rest("/match").description("Soccer Match endpoint")
            .consumes("application/json")
            .produces("application/json")
                .get("/{fixtureId}/summary").description("Get game summary based on statistics").outType(String.class).to("direct:get-match-summary")
                .get("/{fixtureId}/send").description("Send game summary to the Wordpress blog").outType(String.class).to("direct:send-to-wordpress");
        
        //~~~~~~ Rest Routes
        from("direct:get-match-summary")
            .to("direct:get-fixture-detail")
            .to("direct:convert-nlg");
        
        from("direct:send-to-wordpress")
            .to("direct:get-fixture-detail")
            .to("direct:convert-nlg")
            .to("direct:post-new-summary");
        
        //~~~~~~ Routes specialization
        from("direct:get-fixture-detail")
            .routeId("get-fixture-details")
            .setHeader("X-Auth-Token", constant(footballApiToken))
            .to(String.format("rest:get:%s?host=%s&synchronous=true", footballApiFixturePath, footballApiHost))
            .unmarshal().json(JsonLibrary.Jackson, Statistics.class);
        
        from("direct:convert-nlg")
            .routeId("convert-nlg")
            .bean(ContentFactory.class, "generate"); 
        
        from("direct:post-new-summary")
            // TODO: create a bean conversor from String to Post :)
            .to(String.format("wordpress:post?url=%s&user=%s&password=%s", wordpressUrl, wordpressUser, wordpressPassword));
    }

}
