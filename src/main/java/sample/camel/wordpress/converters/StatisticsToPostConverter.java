package sample.camel.wordpress.converters;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.component.wordpress.api.model.Content;
import org.apache.camel.component.wordpress.api.model.Format;
import org.apache.camel.component.wordpress.api.model.Post;
import sample.camel.wordpress.model.StatisticsSummary;

@Converter
public final class StatisticsToPostConverter {

    @Converter
    public static Post toPost(StatisticsSummary statisticsSummary, Exchange exchange) {
        final Post post = new Post();
        post.setContent(new Content(statisticsSummary.getSummary()));
        post.setFormat(Format.standard);
        post.setTitle(new Content(String.format("%s X %s Results", statisticsSummary.getFixture().getHomeTeamName(), statisticsSummary.getFixture().getAwayTeamName())));

        return post;
    }

}
