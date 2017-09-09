package edu.gatech.seclass.sdpcryptogram;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by rigel on 7/14/17.
 */

public class CryptogramTest {

    /**
     * Test creating Cryptogram from a list of strings
     */
    @Test
    public void testCreateCryptogram1() {
        ArrayList cl = new ArrayList();

        cl.add("001");
        cl.add("qwerty");
        cl.add("asdfgh");
        Cryptogram c = new Cryptogram(cl);
        assertEquals(c.cryptoId, "001");
        assertEquals(c.encodedPhrase, "qwerty");
        assertEquals(c.solutionPhrase, "asdfgh");
    }

    /**
     * Test creating cryptogram with parameters specified by three string
     */
    @Test
    public void testCreateCryptogram2() {
        Cryptogram c = new Cryptogram("asdfgh", "qwerty", "001");
        assertEquals(c.cryptoId, "001");
        assertEquals(c.solutionPhrase, "qwerty");
        assertEquals(c.encodedPhrase, "asdfgh");
    }

    /**
     * Test creating PlayCryptogram
     */
    @Test
    public void testCreatePlayCryptogram() {
        PlayCryptogram pc = new PlayCryptogram("eric", "001");
        assertEquals(pc.getUsername(), "eric");
        assertEquals(pc.getCryptogramId(), "001");
        assertEquals(pc.getIncorrectSubmit(), 0);
        assertEquals(pc.getPriorSolution(), "");
        assertEquals(pc.getProgress(), "Not started");
    }
}
