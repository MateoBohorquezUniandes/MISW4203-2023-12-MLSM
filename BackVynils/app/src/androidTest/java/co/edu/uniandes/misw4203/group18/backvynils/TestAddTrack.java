package co.edu.uniandes.misw4203.group18.backvynils;

import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import co.edu.uniandes.misw4203.group18.backvynils.ui.AddTrackFragment;
import co.edu.uniandes.misw4203.group18.backvynils.ui.AlbumListFragment;
import co.edu.uniandes.misw4203.group18.backvynils.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestAddTrack {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);

        onView(withText("Buscando América")).perform(click());

        onView(withId(R.id.addTrackButton)).perform(click());
    }


    @Test
    public void testAddTrack_LongName() {
        // Type track name and duration
        onView(withId(R.id.nameEditText)).perform(typeText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma"), closeSoftKeyboard());
        onView(withId(R.id.durationEditText)).perform(typeText("03:30"), closeSoftKeyboard());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(withText("Please enter a track title with less than 100 characters"))
                .inRoot(ToastMatcher.isToast())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testAddTrack_BadDurationFormat() {
        // Type track name and duration
        onView(withId(R.id.nameEditText)).perform(typeText("Test Track"), closeSoftKeyboard());
        onView(withId(R.id.durationEditText)).perform(typeText("Duration"), closeSoftKeyboard());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(withText("Please enter the duration in the format MM:SS"))
                .inRoot(ToastMatcher.isToast())
                .check(matches(isDisplayed()));

    }

    @Test
    public void testAddTrack_NoName() {
        // Type duration
        onView(withId(R.id.durationEditText)).perform(typeText("03:30"), closeSoftKeyboard());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(withText("Please make sure all fields have been filled correctly"))
                .inRoot(ToastMatcher.isToast())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testAddTrack_NoDuration() {
        // Type track name
        onView(withId(R.id.nameEditText)).perform(typeText("Test Track"), closeSoftKeyboard());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(withText("Please make sure all fields have been filled correctly"))
                .inRoot(ToastMatcher.isToast())
                .check(matches(isDisplayed()));

    }

}
