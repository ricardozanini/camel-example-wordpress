package sample.camel.wordpress.converters;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.component.wordpress.api.model.Content;
import org.apache.camel.component.wordpress.api.model.Format;
import org.apache.camel.component.wordpress.api.model.Post;
import sample.camel.wordpress.model.StatisticsSummary;

@Converter
public final class StatisticsToPostConverter {

    // without the author, we can't create posts.
    private static final int DEFAULT_AUTHOR_ID = 1;

    @Converter
    public static Post toPost(StatisticsSummary statisticsSummary, Exchange exchange) {
        final Post post = new Post();
        post.setContent(new Content(statisticsSummary.getSummary()));
        post.setFormat(Format.standard);
        post.setTitle(new Content(String.format("%s X %s Results", statisticsSummary.getFixture().getHomeTeamName(), statisticsSummary.getFixture().getAwayTeamName())));
        post.setAuthor(DEFAULT_AUTHOR_ID);
        return post;
    }

}
