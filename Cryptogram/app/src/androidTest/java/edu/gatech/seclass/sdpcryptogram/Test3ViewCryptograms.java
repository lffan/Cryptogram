package edu.gatech.seclass.sdpcryptogram;

import android.support.test.espresso.contrib.RecyclerViewActions;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static edu.gatech.seclass.sdpcryptogram.TestUtil.addPlayerRandy;


/**
 * Test for SDPCryptogram
 * @author - Longfei
 * Generated using Barista - http://moquality.com/barista
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test3ViewCryptograms {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);


    // Test that cryptogram list and each cryptogram can be viewed correctly
    // Test that the number of started cryptograms is correct after viewing some cryptograms
    @Test
    public void test1_ViewCryptograms() throws InterruptedException {
        // create a new user with a random username
        String username = addPlayerRandy("random");

        // log in as the new user
        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.username)).perform(click());
        onView(withId(R.id.username)).perform(clearText(), typeText(username));
        onView(withId(R.id.login_button)).perform(click());

        // check the cryptogram list view is OK
        onView(withId(R.id.available_cryptograms_recycler_view)).check(matches(isDisplayed()));
        Thread.sleep(1000);

        // view "FOUR" cryptograms
        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(1000);
        onView(withId(R.id.back_cryptogram_button)).perform(click());
        onView(withId(R.id.started_num)).check(matches(withText("1")));

        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Thread.sleep(1000);
        onView(withId(R.id.back_cryptogram_button)).perform(click());
        onView(withId(R.id.started_num)).check(matches(withText("2")));


        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Thread.sleep(1000);
        onView(withId(R.id.back_cryptogram_button)).perform(click());
        onView(withId(R.id.started_num)).check(matches(withText("3")));


        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        Thread.sleep(1000);
        onView(withId(R.id.back_cryptogram_button)).perform(click());

        // view the same cryptogram again
        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Thread.sleep(1000);
        onView(withId(R.id.back_cryptogram_button)).perform(click());

        // check the in progress number is set to be "4" now
        onView(withId(R.id.started_num)).check(matches(withText("4")));
    }
}
