package co.edu.uniandes.misw4203.group18.backvynils;

import android.os.Bundle;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import co.edu.uniandes.misw4203.group18.backvynils.ui.AddTrackFragment;
import co.edu.uniandes.misw4203.group18.backvynils.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestAddTrack {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Launch the AddTrackFragment
        Bundle bundle = new Bundle();
        bundle.putString(AddTrackFragment.ALBUM_ID, "1");
        mActivityTestRule.getScenario().onActivity(activity -> {
            AddTrackFragment fragment = new AddTrackFragment();
            fragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        });
    }

    @Test
    public void testAddTrack_Success() {
        // Type track name and duration
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText)).perform(typeText("Test Track"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.durationEditText)).perform(typeText("03:30"), closeSoftKeyboard());

        // Click create button
        Espresso.onView(ViewMatchers.withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message is displayed
        Espresso.onView(withText("Track added successfully"))
                .check(matches(isDisplayed()));

        // Verify return to AlbumDetail
        Espresso.onView(ViewMatchers.withId(R.id.albumDetailFragment))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testAddTrack_LongName() {
        // Type track name and duration
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText)).perform(typeText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.durationEditText)).perform(typeText("03:30"), closeSoftKeyboard());

        // Click create button
        Espresso.onView(ViewMatchers.withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message is displayed
        Espresso.onView(withText("Please enter a track title with less than 100 characters"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testAddTrack_BadDurationFormat() {
        // Type track name and duration
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText)).perform(typeText("Test Track"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.durationEditText)).perform(typeText("Duration"), closeSoftKeyboard());

        // Click create button
        Espresso.onView(ViewMatchers.withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message is displayed
        Espresso.onView(withText("Please enter the duration in the format MM:SS"))
                .check(matches(isDisplayed()));
    }

    public void testAddTrack_NoName() {
        // Type duration
        Espresso.onView(ViewMatchers.withId(R.id.durationEditText)).perform(typeText("03:30"), closeSoftKeyboard());

        // Click create button
        Espresso.onView(ViewMatchers.withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message is displayed
        Espresso.onView(withText("Please make sure all fields have been filled correctly"))
                .check(matches(isDisplayed()));
    }

    public void testAddTrack_NoDuration() {
        // Type track name
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText)).perform(typeText("Test Track"), closeSoftKeyboard());

        // Click create button
        Espresso.onView(ViewMatchers.withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message is displayed
        Espresso.onView(withText("Please make sure all fields have been filled correctly"))
                .check(matches(isDisplayed()));
    }

}
