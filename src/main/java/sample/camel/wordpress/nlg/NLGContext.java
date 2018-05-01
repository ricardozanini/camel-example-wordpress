package sample.camel.wordpress.nlg;

import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.XMLLexicon;
import simplenlg.realiser.english.Realiser;

final class NLGContext {

    private final Realiser realiser;
    private final NLGFactory phraseFactory;
    private final Lexicon lexicon;

    public NLGContext() {
        lexicon = new XMLLexicon();
        phraseFactory = new NLGFactory(lexicon);
        realiser = new Realiser(lexicon);
    }

    public Lexicon getLexicon() {
        return lexicon;
    }
    
    public NLGFactory getPhraseFactory() {
        return phraseFactory;
    }
    
    public Realiser getRealiser() {
        return realiser;
    }
}
