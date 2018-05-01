package sample.camel.wordpress.nlg;

import sample.camel.wordpress.model.Result;
import sample.camel.wordpress.model.Statistics;
import simplenlg.features.Feature;
import simplenlg.features.Tense;    
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;

class IntroTemplate {

    private final NLGFactory phraseFactory;

    public IntroTemplate(NLGContext context) {
        this.phraseFactory = context.getPhraseFactory();
    }

    public DocumentElement createParagraph(final Statistics statistics) {
        final ResultPossibility possibility = discoverResult(statistics.getFixture().getResult());

        // refactoring needed
        switch (possibility) {
        case AWAY_WIN:
            return createParagraphAwayWin(statistics);
        case DRAW_WITH_GOALS:
            return createParagraphDrawWithGoals(statistics);
        case DRAW_WITHOUT_GOALS:
            return createParagraphDrawWithoutGoal(statistics);
        case HOME_WIN:
            return createParagraphHomeWin(statistics);
        default:
            throw new UnsupportedOperationException();
        }
    }

    private DocumentElement createParagraphAwayWin(final Statistics statistics) {
        final StatisticsNLG statisticsNLG = new StatisticsNLG(statistics, phraseFactory);
        SPhraseSpec p1 = phraseFactory.createClause(statisticsNLG.getAwayTeam(), "visit", statisticsNLG.getHomeTeam());
        p1.setFeature(Feature.TENSE, Tense.PAST);
        p1.setFeature(Feature.CUE_PHRASE, statisticsNLG.getMatchDate());

        SPhraseSpec p2 = phraseFactory.createClause("", "bring", "home");
        p2.setFeature(Feature.COMPLEMENTISER, "and");
        p2.setFeature(Feature.TENSE, Tense.PAST);

        NPPhraseSpec p3 = phraseFactory.createNounPhrase("victory by the score");
        p3.setDeterminer("a");
        p3.addComplement(statisticsNLG.getScore());

        p2.addComplement(p3);
        p1.addComplement(p2);

        DocumentElement par = phraseFactory.createParagraph();
        par.addComponent(p1);

        return par;
    }

    private DocumentElement createParagraphHomeWin(final Statistics statistics) {
        final StatisticsNLG statisticsNLG = new StatisticsNLG(statistics, phraseFactory);
        SPhraseSpec p1 = phraseFactory.createClause(statisticsNLG.getHomeTeam(), "welcome");
        p1.setFeature(Feature.TENSE, Tense.PAST);
        p1.setFeature(Feature.CUE_PHRASE, statisticsNLG.getMatchDate());
        
        SPhraseSpec p2 = phraseFactory.createClause("", "win", statisticsNLG.getAwayTeam());
        p2.setFeature(Feature.COMPLEMENTISER, "and");
        p2.setFeature(Feature.TENSE, Tense.PAST);
        
        NPPhraseSpec p3 = phraseFactory.createNounPhrase("by the score");
        p3.addComplement(statisticsNLG.getScore());

        p2.addComplement(p3);
        p1.addComplement(p2);

        DocumentElement par = phraseFactory.createParagraph();
        par.addComponent(p1);

        return par;
    }

    private DocumentElement createParagraphDrawWithGoals(final Statistics statistics) {
        final StatisticsNLG statisticsNLG = new StatisticsNLG(statistics, phraseFactory);
        final String goal = statistics.getFixture().getResult().getGoalsAwayTeam() > 1 ? "goals" : "goal";
        
        SPhraseSpec p1 = phraseFactory.createClause(statisticsNLG.getHomeTeam(), null);
        p1.setFeature(Feature.CUE_PHRASE, statisticsNLG.getMatchDate());
        
        SPhraseSpec p2 = phraseFactory.createClause(statisticsNLG.getAwayTeam(), "draw");
        p2.setFeature(Feature.COMPLEMENTISER, "and");
        p2.setFeature(Feature.TENSE, Tense.PAST);
        
        NPPhraseSpec p3 = phraseFactory.createNounPhrase(String.format("with %s %s for each side", statistics.getFixture().getResult().getGoalsAwayTeam(), goal));
        
        p2.addComplement(p3);
        p1.addComplement(p2);
        
        DocumentElement par = phraseFactory.createParagraph();
        par.addComponent(p1);

        return par;
    }

    private DocumentElement createParagraphDrawWithoutGoal(final Statistics statistics) {
        final StatisticsNLG statisticsNLG = new StatisticsNLG(statistics, phraseFactory);
        
        SPhraseSpec p1 = phraseFactory.createClause(statisticsNLG.getHomeTeam(), null);
        p1.setFeature(Feature.CUE_PHRASE, statisticsNLG.getMatchDate());
        
        SPhraseSpec p2 = phraseFactory.createClause(statisticsNLG.getAwayTeam(), "draw");
        p2.setFeature(Feature.COMPLEMENTISER, "and");
        p2.setFeature(Feature.TENSE, Tense.PAST);

        p1.addComplement(p2);
        
        SPhraseSpec p3 = phraseFactory.createClause("The teams", "score", "a goal during the game");
        p3.setFeature(Feature.TENSE, Tense.PAST);
        p3.setFeature(Feature.NEGATED, true);
        
        DocumentElement par = phraseFactory.createParagraph();
        par.addComponent(p1);
        par.addComponent(p3);

        return par;
    }

    private ResultPossibility discoverResult(final Result result) {
        final int goalsAwayTeam = result.getGoalsAwayTeam();
        final int goalsHomeTeam = result.getGoalsHomeTeam();

        if (goalsAwayTeam == goalsHomeTeam) {
            if (goalsAwayTeam == 0 && goalsHomeTeam == 0) {
                return ResultPossibility.DRAW_WITHOUT_GOALS;
            }
            return ResultPossibility.DRAW_WITH_GOALS;
        }

        return goalsHomeTeam > goalsAwayTeam ? ResultPossibility.HOME_WIN : ResultPossibility.AWAY_WIN;
    }

    static enum ResultPossibility {
        AWAY_WIN, HOME_WIN, DRAW_WITH_GOALS, DRAW_WITHOUT_GOALS;
    }
}
