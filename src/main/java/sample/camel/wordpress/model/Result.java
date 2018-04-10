package sample.camel.wordpress.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements Serializable {

    private static final long serialVersionUID = 774714782310950585L;
    private Integer goalsHomeTeam;
    private Integer goalsAwayTeam;
    private Scoreboard halfTime;
    
    public Result() {
        this.halfTime = new Scoreboard();
    }

    public Integer getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(Integer goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public Integer getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(Integer goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }

    public Scoreboard getHalfTime() {
        return halfTime;
    }

    public void setHalfTime(Scoreboard halfTime) {
        this.halfTime = halfTime;
    }
    
}
