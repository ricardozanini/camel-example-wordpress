package sample.camel.wordpress.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsSummary extends Statistics {

    private static final long serialVersionUID = 1006604923159727556L;
    private final Statistics statistics;
    private String summary;

    public StatisticsSummary(final Statistics statistics) {
        this.statistics = statistics;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public Fixture getFixture() {
        return statistics.getFixture();
    }

    @Override
    public Head2Head getHead2head() {
        return statistics.getHead2head();
    }

    @Override
    public void setFixture(Fixture fixture) {
        statistics.setFixture(fixture);
    }

    @Override
    public void setHead2head(Head2Head head2head) {
        statistics.setHead2head(head2head);
    }

}
