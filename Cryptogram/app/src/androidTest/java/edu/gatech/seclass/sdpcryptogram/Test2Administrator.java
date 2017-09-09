package edu.gatech.seclass.sdpcryptogram;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
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
import static edu.gatech.seclass.sdpcryptogram.TestUtil.randomString;


/**
 * Test functions of administrator
 * @author - Longfei and Basker
 * Generated using Barista - http://moquality.com/barista
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test2Administrator {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);

    @Test
    public void test1_AdminMenu() {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).check(matches(isDisplayed()));
        onView(withId(R.id.add_cryptogram)).check(matches(isDisplayed()));
    }

    // check function of adding invalid and valid players
    @Test
    public void test2_AddNewPlayer() throws InterruptedException {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).perform(click());

        // add a player with duplicated username
        onView(withId(R.id.add_username)).perform(clearText(), typeText("example555"));
        onView(withId(R.id.add_first_name)).perform(clearText(), typeText("Kenny"));
        onView(withId(R.id.add_last_name)).perform(clearText(), typeText("McCormick"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_player)).perform(click());
        Thread.sleep(2000);

        // add a player with missing last/first names
        onView(withId(R.id.add_username)).perform(clearText(), typeText("example666"));
        onView(withId(R.id.add_first_name)).perform(clearText());
        onView(withId(R.id.add_last_name)).perform(clearText(), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_player)).perform(click());
        Thread.sleep(2000);

        // add a valid player
        onView(withId(R.id.add_username)).perform(clearText(), typeText("randy"));
        onView(withId(R.id.add_first_name)).perform(clearText(), typeText("Randy"));
        onView(withId(R.id.add_last_name)).perform(clearText(), typeText("Marsh"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_player)).perform(click());
        Thread.sleep(2000);

        onView(withId(R.id.cancel_add_player)).perform(click());
        Espresso.pressBack();
    }

    // check function of adding a valid new player with random username
    // then try to add it again as an duplicated player
    // then login as the newly created player
    @Test
    public void test3_AddDuplicatedPlayer() throws InterruptedException {
        // generate a random username
        String username = randomString(8);
        String lastName = randomString(5);

        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).perform(click());

        // add a new user with a random username
        onView(withId(R.id.add_username)).perform(click());
        onView(withId(R.id.add_username)).perform(clearText(), typeText(username));
        onView(withId(R.id.add_first_name)).perform(clearText(), typeText(lastName));
        onView(withId(R.id.add_last_name)).perform(clearText(), typeText("Marsh"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_player)).perform(click());
        Thread.sleep(1500);

        // check function of adding a duplicated player with an existing username
        onView(withId(R.id.add_username)).perform(clearText(), typeText(username));
        onView(withId(R.id.add_first_name)).perform(clearText(), typeText(lastName));
        onView(withId(R.id.add_last_name)).perform(clearText(), typeText("Marsh"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_player)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.cancel_add_player)).perform(click());

        // check if the newly created player can login
        Espresso.pressBack();
        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.username)).perform(click());
        onView(withId(R.id.username)).perform(clearText(), typeText(username));
        onView(withId(R.id.login_button)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.available_cryptograms_recycler_view)).check(matches(isDisplayed()));

        Espresso.pressBack();
    }

    // check function of adding valid and invalid new cryptograms
    @Test
    public void test4_AddNewCryptogram() throws InterruptedException {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_cryptogram)).perform(click());

        // add a new cryptogram (not new if test has been run once)
        onView(withId(R.id.solution_phrase)).perform(clearText(), typeText("This is a cat."));
        onView(withId(R.id.encoded_phrase)).perform(clearText(), typeText("Yjod od s vsy."), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        Thread.sleep(1500);

        // check function of adding a valid new cryptogram containing numbers and special characters
        onView(withId(R.id.solution_phrase)).perform(clearText(), typeText("42 is the @nswer!"));
        onView(withId(R.id.encoded_phrase)).perform(clearText(), typeText("42 od yjr @mdert!"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        Thread.sleep(1500);

        // check function of adding an invalid cryptogram
        onView(withId(R.id.solution_phrase)).perform(clearText(), typeText("This is solution."));
        onView(withId(R.id.encoded_phrase)).perform(clearText(), typeText("Wrong encoded."), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        Thread.sleep(1500);

        onView(withId(R.id.cancel_button)).perform(click());
        Espresso.pressBack();
    }
}
