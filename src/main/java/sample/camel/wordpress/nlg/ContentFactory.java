package sample.camel.wordpress.nlg;

import sample.camel.wordpress.model.Statistics;

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
    
    public String generate(final Statistics statistics) {
        return context.getRealiser().realiseSentence(introTemplate.createParagraph(statistics));
    }
    
}
