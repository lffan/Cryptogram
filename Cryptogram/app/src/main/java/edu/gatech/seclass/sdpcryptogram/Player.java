package edu.gatech.seclass.sdpcryptogram;

/**
 * Created by wc on 05/07/2017.
 */
import com.google.firebase.database.IgnoreExtraProperties;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * Created by wc on 05/07/2017.
 */
@IgnoreExtraProperties
public class Player {
    private String username= "";
    private String firstname= "";
    private String lastname= "";
    private Integer solvedCount = 0;
    private Integer ranking = 0;
    private Integer started = 0;
    private Integer totalIncorrect = 0;

    public Player() {
    }
    public Player(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Player(String username, ExternalWebService.PlayerRating rating) {
        this.username = username;
        this.firstname = rating.getFirstname();
        this.lastname = rating.getLastname();
        this.started = rating.getStarted();
        this.solvedCount = rating.getSolved();
        this.totalIncorrect = rating.getIncorrect();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getSolvedCount() {
        return solvedCount;
    }

    public void addSolvedCount() {
        this.solvedCount++;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getStarted() {
        return started;
    }

    public void addStarted() {
        this.started++;
    }

    public Integer getTotalIncorrect() {
        return totalIncorrect;
    }

    public void addTotalIncorrect() {
        this.totalIncorrect++;
    }
}
