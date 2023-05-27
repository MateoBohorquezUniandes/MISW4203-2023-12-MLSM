package co.edu.uniandes.misw4203.group18.backvynils;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import co.edu.uniandes.misw4203.group18.backvynils.ui.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestDetailCollector {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void DetailViewTest() throws InterruptedException {
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.collectorListFragment)));
        Thread.sleep(3000);
        collectorBtn.perform(click());
        Thread.sleep(3000);

        ViewInteraction collectorItem = onView(allOf(withId(R.id.collectorName),withText("Manolo Bellon")));
        collectorItem.perform(click());
        Thread.sleep(3000);

        onView(allOf(withId(R.id.collectorName))).check(matches(withText("Manolo Bellon")));
        onView(allOf(withId(R.id.collectorTel))).check(matches(withText("3502457896")));
        onView(allOf(withId(R.id.collectorMail))).check(matches(withText("manollo@caracol.com.co")));
    }
    @Test
    public void ReturnListCollectors() throws  InterruptedException {
        ViewInteraction collectorBtn = onView(allOf(withId(R.id.collectorListFragment)));
        Thread.sleep(3000);
        collectorBtn.perform(click());
        Thread.sleep(3000);

        ViewInteraction collectorItem = onView(allOf(withId(R.id.collectorName),withText("Manolo Bellon")));
        collectorItem.perform(click());
        Thread.sleep(3000);

        ViewInteraction collectorDetail = onView(allOf(withId(R.id.collectorTel), withText("3502457896")));
        collectorDetail.perform(pressBack());
        Thread.sleep(3000);

        collectorItem = onView(allOf(withId(R.id.collectorName),withText("Jaime Monsalve")));
        collectorItem.perform(click());
    }
}
