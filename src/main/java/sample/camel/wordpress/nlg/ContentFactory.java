package sample.camel.wordpress.nlg;

import sample.camel.wordpress.model.Statistics;
import sample.camel.wordpress.model.StatisticsSummary;

/**
 * Factory for providing content according to {@link Statistics} data.
 */
public final class ContentFactory {
    
    private final IntroTemplate introTemplate;
    private final NLGContext context;

    private ContentFactory() {
        context = new NLGContext();
        introTemplate = new IntroTemplate(context);
    }
    
    public StatisticsSummary generate(final Statistics statistics) {
        final StatisticsSummary statisticsSummary = new StatisticsSummary(statistics);
        statisticsSummary.setSummary(context.getRealiser().realiseSentence(introTemplate.createParagraph(statistics)));
        return statisticsSummary;
    }
    
}
