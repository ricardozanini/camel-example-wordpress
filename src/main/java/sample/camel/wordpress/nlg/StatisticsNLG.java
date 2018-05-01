package sample.camel.wordpress.nlg;

import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import sample.camel.wordpress.model.Fixture;
import sample.camel.wordpress.model.Statistics;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.NPPhraseSpec;

class StatisticsNLG {

    private final NLGFactory phraseFactory;
    private NPPhraseSpec awayTeam;
    private NPPhraseSpec homeTeam;
    private NPPhraseSpec matchDate;
    private NPPhraseSpec score;

    public StatisticsNLG(final Statistics statistics, final NLGFactory phraseFactory) {
        this.phraseFactory = phraseFactory;
        this.convert(statistics);
    }

    private void convert(final Statistics statistics) {
        awayTeam = phraseFactory.createNounPhrase(statistics.getFixture().getAwayTeamName());
        homeTeam = phraseFactory.createNounPhrase(statistics.getFixture().getHomeTeamName());
        score = phraseFactory.createNounPhrase(String.format("of %s", statistics.getFixture().getResult().formattedFinalResults()));
        matchDate = this.formatMatchDate(statistics.getFixture());
    }

    private NPPhraseSpec formatMatchDate(final Fixture fixture) {
        final DateTime matchDate = new DateTime(fixture.getDate());

        if (matchDate.toLocalDate().equals(new LocalDate())) {
            return phraseFactory.createNounPhrase("today");
        }

        if (new LocalDate().minusDays(1).equals(matchDate.toLocalDate())) {
            return phraseFactory.createNounPhrase("yesterday");
        }

        if (new LocalDate().minusDays(6).isBefore(matchDate.toLocalDate())) {
            return phraseFactory.createNounPhrase(String.format("on last %s", matchDate.toString("EEE", Locale.US)));
        }

        return phraseFactory.createNounPhrase(String.format("in %s", matchDate.toString("MMM, d", Locale.US)));
    }

    public NPPhraseSpec getAwayTeam() {
        return awayTeam;
    }

    public NPPhraseSpec getHomeTeam() {
        return homeTeam;
    }

    public NPPhraseSpec getMatchDate() {
        return matchDate;
    }

    public NPPhraseSpec getScore() {
        return score;
    }
}
