package sample.camel.wordpress;

import static org.assertj.core.api.Assertions.assertThat;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = ExampleCamelWordpressApplication.class)
public class ExampleCamelWordpressRouteTest {

    private static final String MATCH_SUMMARY = "drew";
    private static final int FIXTURE_ID = 123;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleCamelWordpressRouteTest.class);

    @Autowired
    private CamelContext context;
    @Autowired
    private ProducerTemplate template;

    @Before
    public void setup() throws Exception {
        context.getRouteDefinition("get-fixture-details").adviceWith(context, new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                LOGGER.info("************************** Intercepting rest endpoints *************************");
                interceptSendToEndpoint("rest://*").skipSendToOriginalEndpoint().process(p -> {
                    final String body = IOUtils.toString(this.getClass().getResourceAsStream("/mock-data/fixture-158193.json"), "UTF-8");
                    p.getIn().setBody(body);
                });
            }
        });
    }

    @Test
    public void test() {
        Object output = template.requestBodyAndHeader("direct:get-match-summary", null, "fixtureId", FIXTURE_ID);
        assertThat(output).isInstanceOf(String.class);
        assertThat((String)output).contains(MATCH_SUMMARY);
    }

}
