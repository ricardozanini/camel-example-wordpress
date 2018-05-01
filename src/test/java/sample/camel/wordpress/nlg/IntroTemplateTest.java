package sample.camel.wordpress.nlg;

import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.camel.wordpress.model.Statistics;
import simplenlg.framework.DocumentElement;

public class IntroTemplateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntroTemplateTest.class);
    private ObjectMapper objectMapper;
    private Statistics leipzigXBayern; //awayWin
    private Statistics fluminenseXSantos; //homeWin
    private Statistics flamengoXAtleticoMG; //draw with goals
    private Statistics avaiXVitoria; //draw without goals
    private NLGContext context;

    @Before
    public void setUp() throws Exception {
        context = new NLGContext();
        objectMapper = new ObjectMapper();
        leipzigXBayern = objectMapper.readValue(this.getClass().getResourceAsStream("/mock-data/fixture-162285.json"), Statistics.class);
        fluminenseXSantos = objectMapper.readValue(this.getClass().getResourceAsStream("/mock-data/fixture-158190.json"), Statistics.class);
        flamengoXAtleticoMG = objectMapper.readValue(this.getClass().getResourceAsStream("/mock-data/fixture-158193.json"), Statistics.class);
        avaiXVitoria = objectMapper.readValue(this.getClass().getResourceAsStream("/mock-data/fixture-158186.json"), Statistics.class);
    }

    @Test
    public void testAwayWin() {
        final IntroTemplate introTemplate = new IntroTemplate(context);
        final DocumentElement paragraph = introTemplate.createParagraph(leipzigXBayern);
        final String paragraphString = context.getRealiser().realiseSentence(paragraph);
        
        LOGGER.info("The paragraph: {}", paragraphString);
        assertThat(paragraphString).isNotEmpty();
        assertThat(paragraphString).contains("brought home a victory");
    }
    
    @Test
    public void testHomeWin() {
        final IntroTemplate introTemplate = new IntroTemplate(context);
        final DocumentElement paragraph = introTemplate.createParagraph(fluminenseXSantos);
        final String paragraphString = context.getRealiser().realiseSentence(paragraph);
        
        LOGGER.info("The paragraph: {}", paragraphString);
        assertThat(paragraphString).isNotEmpty();
        assertThat(paragraphString).contains("welcomed and won");
    }
    
    
    @Test
    public void testDrawWithGoals() {
        final IntroTemplate introTemplate = new IntroTemplate(context);
        final DocumentElement paragraph = introTemplate.createParagraph(flamengoXAtleticoMG);
        final String paragraphString = context.getRealiser().realiseSentence(paragraph);
        
        LOGGER.info("The paragraph: {}", paragraphString);
        assertThat(paragraphString).isNotEmpty();
        assertThat(paragraphString).contains("drew");
    }
    
    @Test
    public void testDrawWithoutGoals() {
        final IntroTemplate introTemplate = new IntroTemplate(context);
        final DocumentElement paragraph = introTemplate.createParagraph(avaiXVitoria);
        final String paragraphString = context.getRealiser().realiseSentence(paragraph);
        
        LOGGER.info("The paragraph: {}", paragraphString);
        assertThat(paragraphString).isNotEmpty();
        assertThat(paragraphString).contains("did not score");
    }

}
