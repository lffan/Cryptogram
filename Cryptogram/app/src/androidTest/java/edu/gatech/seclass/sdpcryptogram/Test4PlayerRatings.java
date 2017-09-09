package edu.gatech.seclass.sdpcryptogram;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static edu.gatech.seclass.sdpcryptogram.TestUtil.*;

/**
 * Test for player ratings
 * @author - Longfei
 * Generated using Barista - http://moquality.com/barista
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test4PlayerRatings {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);

    // check that the player rating list can be displayed correctly
    // check the ranking order is correct
    @Test
    public void test1_PlayerRatingRanking() throws InterruptedException {

        // create a new user with a random username
        String username = addPlayerRandy("random");

        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.username)).perform(click());
        onView(withId(R.id.username)).perform(clearText(), typeText(username));
        onView(withId(R.id.login_button)).perform(click());
        onView(withContentDescription("Open navigation drawer")).perform(click());
        onView(withText("Player Ratings")).perform(click());
        onView(withId(R.id.player_ratings_recycler_view)).check(matches(isDisplayed()));

        onView(withId(R.id.player_ratings_recycler_view))
                .check(matches(atPosition(0, hasDescendant(withText("George Burdell")))));

        onView(withId(R.id.player_ratings_recycler_view))
                .check(matches(atPosition(1, hasDescendant(withText("Nancy Drew")))));

        onView(withId(R.id.player_ratings_recycler_view))
                .check(matches(atPosition(2, hasDescendant(withText("John Doe")))));

    }
}