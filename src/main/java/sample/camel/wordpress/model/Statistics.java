package sample.camel.wordpress.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistics implements Serializable {

    private static final long serialVersionUID = -3343172209562939031L;

    private Fixture fixture;
    private Head2Head head2head;

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public Head2Head getHead2head() {
        return head2head;
    }

    public void setHead2head(Head2Head head2head) {
        this.head2head = head2head;
    }

}
