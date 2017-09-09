package edu.gatech.seclass.sdpcryptogram;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by wc on 05/07/2017.
 */
@IgnoreExtraProperties
public class Cryptogram {
    public String cryptoId= "";
    public String encodedPhrase= "";
    public String solutionPhrase= "";

    public Cryptogram() {
    }

    public Cryptogram(List<String> arr) {
        this.cryptoId = arr.get(0);
        this.encodedPhrase = arr.get(1);
        this.solutionPhrase = arr.get(2);
    }

    public Cryptogram(String encodedPhrase, String solutionPhrase, String cryptoId) {
        this.encodedPhrase = encodedPhrase;
        this.solutionPhrase = solutionPhrase;
        this.cryptoId = cryptoId;
    }
}
