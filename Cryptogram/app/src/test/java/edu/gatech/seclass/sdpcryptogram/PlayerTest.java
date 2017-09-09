package edu.gatech.seclass.sdpcryptogram;

import org.junit.Test;

import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

import static org.junit.Assert.*;

/**
 * Created by rigel on 7/14/17.
 */

public class PlayerTest {
    /**
     * Test creating player with username, last name and first name
     */
    @Test
    public void testCreatePlayer1() {
        Player p = new Player("eric", "Eric", "Cartman");
        assertEquals("eric", p.getUsername());
        assertEquals("Eric", p.getFirstname());
        assertEquals("Cartman", p.getLastname());
    }

    /**
     * Test creating player from the info pulled from the external webservice
     */
    @Test
    public void testCreatePlayer2() {
        List<ExternalWebService.PlayerRating> playerRatings = ExternalWebService.getInstance().syncRatingService();
        ExternalWebService.PlayerRating rating = playerRatings.get(0);
        Player p = new Player("dummy", rating);
        assertEquals(p.getFirstname(), rating.getFirstname());
        assertEquals(p.getLastname(), rating.getLastname());
        assertEquals((int) p.getStarted(), rating.getStarted());
        assertEquals((int) p.getSolvedCount(), rating.getSolved());
        assertEquals((int) p.getTotalIncorrect(), rating.getIncorrect());
    }
}
