package sample.camel.wordpress.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Head2Head implements Serializable {

    private static final long serialVersionUID = 3513430085068411798L;
    private Fixture lastWinAwayTeam;
    private Fixture lastWinHomeTeam;

    public Fixture getLastWinAwayTeam() {
        return lastWinAwayTeam;
    }

    public void setLastWinAwayTeam(Fixture lastWinAwayTeam) {
        this.lastWinAwayTeam = lastWinAwayTeam;
    }

    public Fixture getLastWinHomeTeam() {
        return lastWinHomeTeam;
    }

    public void setLastWinHomeTeam(Fixture lastWinHomeTeam) {
        this.lastWinHomeTeam = lastWinHomeTeam;
    }

}
