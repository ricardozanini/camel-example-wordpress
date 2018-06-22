package sample.camel.wordpress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExampleCamelWordpressRouteConfig {

    @Value("${football.api.fixture.path}")
    private String footballApiFixturePath;

    @Value("${football.api.host}")
    private String footballApiHost;

    @Value("${football.api.token}")
    private String footballApiToken;

    @Value("${wordpress.url}")
    private String wordpressUrl;

    @Value("${wordpress.user}")
    private String wordpressUser;

    @Value("${wordpress.password}")
    private String wordpressPassword;

    public String getFootballApiFixturePath() {
        return footballApiFixturePath;
    }

    public void setFootballApiFixturePath(String footballApiFixturePath) {
        this.footballApiFixturePath = footballApiFixturePath;
    }

    public String getFootballApiHost() {
        return footballApiHost;
    }

    public void setFootballApiHost(String footballApiHost) {
        this.footballApiHost = footballApiHost;
    }

    public String getFootballApiToken() {
        return footballApiToken;
    }

    public void setFootballApiToken(String footballApiToken) {
        this.footballApiToken = footballApiToken;
    }

    public String getWordpressUrl() {
        return wordpressUrl;
    }

    public void setWordpressUrl(String wordpressUrl) {
        this.wordpressUrl = wordpressUrl;
    }

    public String getWordpressUser() {
        return wordpressUser;
    }

    public void setWordpressUser(String wordpressUser) {
        this.wordpressUser = wordpressUser;
    }

    public String getWordpressPassword() {
        return wordpressPassword;
    }

    public void setWordpressPassword(String wordpressPassword) {
        this.wordpressPassword = wordpressPassword;
    }

}
