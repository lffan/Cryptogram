package edu.gatech.seclass.sdpcryptogram;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by wc on 05/07/2017.
 */
@IgnoreExtraProperties
public class PlayCryptogram {
    private String username = "";
    private String cryptogramId = "";
    private String progress = "Not started";
    private String priorSolution = "";
    private Integer incorrectSubmit = 0;

    public PlayCryptogram() {
    }

    public PlayCryptogram(String username, String cryptogramId) {
        this.username = username;
        this.cryptogramId = cryptogramId;
    }

    public String getCryptogramId() {
        return this.cryptogramId;
    }
    public void setPriorSolution(String solution) {
        this.priorSolution = solution;
    }

    public String getPriorSolution() {
        return this.priorSolution;
    }

    public void setInProgress() {
        this.progress = "In progress";
    }

    public void setProgressComplete() {
        this.progress = "Solved";
    }

    public String getProgress() {
        return this.progress;
    }

    public void addIncorrectSubmit() {
        this.incorrectSubmit += 1;
    }

    public int getIncorrectSubmit() {
        return this.incorrectSubmit;
    }

    public String getUsername() {
        return username;
    }
}
