package sample.camel.wordpress;

import static org.assertj.core.api.Assertions.assertThat;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.wordpress.api.model.Post;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sample.camel.wordpress.model.StatisticsSummary;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = ExampleCamelWordpressApplication.class)
public class ExampleCamelWordpressRouteTest {

    private static final String MATCH_SUMMARY = "drew";
    private static final int FIXTURE_ID = 123;
    
    @Autowired
    private CamelContext context;
    @Autowired
    private ProducerTemplate template;

    @Before
    public void setup() throws Exception {
        context.getRouteDefinition("get-fixture-details").adviceWith(context, new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("rest://*").skipSendToOriginalEndpoint().process(p -> {
                    final String body = IOUtils.toString(this.getClass().getResourceAsStream("/mock-data/fixture-158193.json"), "UTF-8");
                    p.getIn().setBody(body);
                });

            }
        });

        context.getRouteDefinition("post-news-summary").adviceWith(context, new AdviceWithRouteBuilder() {
            
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("wordpress:*").skipSendToOriginalEndpoint().process(p -> {
                    p.getIn().getBody(Post.class).setId(1);
                });
            }
        });
    }

    @Test
    public void testMatchSummary() {
        Object output = template.requestBodyAndHeader("direct:get-match-summary", null, "fixtureId", FIXTURE_ID);
        assertThat(output).isInstanceOf(StatisticsSummary.class);
        assertThat(((StatisticsSummary)output).getSummary()).contains(MATCH_SUMMARY);
    }

    // when sending to WP, we get the match summary as the content
    @Test
    public void testSendWordpress() {
        Object output = template.requestBodyAndHeader("direct:send-to-wordpress", null, "fixtureId", FIXTURE_ID);
        assertThat(output).isInstanceOf(Post.class);
        assertThat(((Post)output).getContent().getRendered()).contains(MATCH_SUMMARY);
    }
}
