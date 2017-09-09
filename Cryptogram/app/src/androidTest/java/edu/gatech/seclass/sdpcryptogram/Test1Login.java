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
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author - Basker and Longfei
 * Generated using Barista - http://moquality.com/barista
 * This will verify that the administator and valid players can login
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test1Login {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);

    // check admin login
    @Test
    public void test1_AdminLogin() throws InterruptedException {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).check(matches(isDisplayed()));
        Thread.sleep(1000);
    }


    @Test
    public void test2_InvalidPlayerLogin() throws InterruptedException {
        onView(withId(R.id.player_radio)).perform(click());

        // check empty username login
        onView(withId(R.id.username)).perform(clearText());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username)).check(matches(hasErrorText("please enter a valid username")));
        Thread.sleep(1000);

        // check non-existing username login
        onView(withId(R.id.username)).perform(clearText(), typeText("invalid"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username)).check(matches(hasErrorText("please enter a valid username")));
        Thread.sleep(1000);

        // check that valid players created on other phones cannot login in this phone
        onView(withId(R.id.username)).perform(clearText(), typeText("example555"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username)).check(matches(hasErrorText("please enter a valid username")));
        Thread.sleep(1000);
    }

    // check that a valid player can log in
    @Test
    public void test3_ValidPlayerLogin() {
        // This test cannot be carried out before the administrator create a valid player,
        // so this test is fulfilled in `Test2Administrator`.
    }

}
