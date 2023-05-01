package co.edu.uniandes.misw4203.group18.backvynils;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import co.edu.uniandes.misw4203.group18.backvynils.R;
import co.edu.uniandes.misw4203.group18.backvynils.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestCollectorsList {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() throws InterruptedException {
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.collectorListFragment)));
        Thread.sleep(3000);
        collectorBtn.perform(click());
        Thread.sleep(3000);

        ViewInteraction collectorItem = onView(allOf(withId(R.id.collectorName),withText("Manolo Bellon")));
        collectorItem.perform(click());
        Thread.sleep(3000);

        onView(allOf(withId(R.id.textView2))).check(matches(withText("Hello blank fragment")));
    }
    @Test
    public void cambioVistas() throws  InterruptedException {
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.collectorListFragment)));
        Thread.sleep(3000);
        collectorBtn.perform(click());
        Thread.sleep(3000);

        ViewInteraction albumBtn = onView(allOf(withId(R.id.albumListFragment)));
        Thread.sleep(3000);
        albumBtn.perform(click());
        Thread.sleep(3000);

        collectorBtn.perform(click());
        Thread.sleep(3000);

        ViewInteraction artistBtn = onView(allOf(withId(R.id.artistListFragment)));
        Thread.sleep(3000);
        artistBtn.perform(click());
        Thread.sleep(3000);

        collectorBtn.perform(click());
        Thread.sleep(3000);

        ViewInteraction collectorItem = onView(allOf(withId(R.id.collectorName),withText("Manolo Bellon")));
        collectorItem.perform(click());
        Thread.sleep(3000);

        artistBtn.perform(click());
        Thread.sleep(3000);
    }
}
