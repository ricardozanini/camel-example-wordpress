package sample.camel.wordpress.converters;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.component.wordpress.api.model.Content;
import org.apache.camel.component.wordpress.api.model.Format;
import org.apache.camel.component.wordpress.api.model.Post;
import org.apache.camel.component.wordpress.api.model.PublishableStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.camel.wordpress.model.StatisticsSummary;

@Converter
public final class StatisticsToPostConverter {

    // without the author, we can't create posts.
    private static final int DEFAULT_AUTHOR_ID = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsToPostConverter.class);

    @Converter
    public static Post toPost(StatisticsSummary statisticsSummary, Exchange exchange) {
        final Post post = new Post();
        final Content postContent = new Content(statisticsSummary.getSummary());
        postContent.setRaw(statisticsSummary.getSummary());
        final Content titleContent = new Content(String.format("%s X %s Results", statisticsSummary.getFixture().getHomeTeamName(), statisticsSummary.getFixture().getAwayTeamName()));
        titleContent.setRaw(titleContent.getRendered());
        post.setContent(postContent);
        post.setFormat(Format.standard);
        post.setStatus(PublishableStatus.publish);
        post.setTitle(titleContent);
        post.setAuthor(DEFAULT_AUTHOR_ID);
        LOGGER.debug("Converted StatisticsSummary {} to Post {}", statisticsSummary, post);
        return post;
    }

}
