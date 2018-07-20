package sample.camel.wordpress;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.wordpress.WordpressComponent;
import org.apache.camel.component.wordpress.WordpressComponentConfiguration;
import org.apache.camel.component.wordpress.api.model.Post;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sample.camel.wordpress.model.Statistics;
import sample.camel.wordpress.model.StatisticsSummary;
import sample.camel.wordpress.nlg.ContentFactory;

@Component
public class ExampleCamelWordpressRoute extends RouteBuilder {
    
    @Autowired
    private ExampleCamelWordpressRouteConfig config;
  
    @Override
    public void configure() throws Exception {       
        final WordpressComponentConfiguration configuration = new WordpressComponentConfiguration();
        final WordpressComponent component = new WordpressComponent();
        configuration.setUrl(config.getWordpressUrl()); 
        configuration.setApiVersion("2");
        component.setConfiguration(configuration);
        getContext().addComponent("wordpress", component);
        
        onException(Exception.class)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
            .setHeader(Exchange.CONTENT_TYPE, constant(ContentType.APPLICATION_JSON))
            .setBody(simple("{ \"error\": \"${exception.message}\" }"))
            .handled(true);
        
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
                .get("/{fixtureId}/summary").description("Get game summary based on statistics").outType(StatisticsSummary.class).to("direct:get-match-summary")
                .get("/{fixtureId}/send").description("Send game summary to the Wordpress blog").outType(Post.class).to("direct:send-to-wordpress");
        
        //~~~~~~ Rest Routes
        from("direct:get-match-summary")
            .routeId("get-match-summary")
            .to("direct:get-fixture-detail")
            .to("direct:convert-nlg");
        
        from("direct:send-to-wordpress")
            .routeId("send-to-wordpress")
            .to("direct:get-fixture-detail")
            .to("direct:convert-nlg")
            .to("direct:post-new-summary");
        
        //~~~~~~ Routes specialization
        from("direct:get-fixture-detail")
            .routeId("get-fixture-details")
            .setHeader("X-Auth-Token", constant(config.getFootballApiToken()))
            .to(String.format("rest:get:%s?host=%s&synchronous=true", config.getFootballApiFixturePath(), config.getFootballApiHost()))
            .unmarshal().json(JsonLibrary.Jackson, Statistics.class);
        
        from("direct:convert-nlg")
            .routeId("convert-nlg")
            .bean(ContentFactory.class, "generate");
        
        from("direct:post-new-summary")
            .routeId("post-new-summary")
            .convertBodyTo(Post.class)
            .to("wordpress:post?user=" + config.getWordpressUser() + "&password=" + config.getWordpressPassword());
    }

}
