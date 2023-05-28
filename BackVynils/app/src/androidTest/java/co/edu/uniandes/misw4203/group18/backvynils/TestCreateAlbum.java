package co.edu.uniandes.misw4203.group18.backvynils;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import co.edu.uniandes.misw4203.group18.backvynils.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestCreateAlbum {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.createAlbumButton)).perform(click());
    }


    @Test
    public void testCreateAlbum_Success() {
        // Title
        onView(withId(R.id.titleEditText)).perform(typeText("Test Album"), closeSoftKeyboard());
        // Cover Image URL
        onView(withId(R.id.urlEditText)).perform(typeText("Cover Image URL"), closeSoftKeyboard());
        // Release Date
        onView(withId(R.id.releaseDateEditText)).perform(typeText("2022-05-08"), closeSoftKeyboard());
        // Description
        onView(withId(R.id.descriptionEditText)).perform(typeText("This is a test description"), closeSoftKeyboard());
        // Genre
        onView(withId(R.id.genreSpinner)).perform(click());
        onView(ViewMatchers.withText("Rock")).perform(click());
        // Record Label
        onView(withId(R.id.recordLabelSpinner)).perform(click());
        onView(ViewMatchers.withText("Sony Music")).perform(click());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(ViewMatchers.withText("Album added successfully"))
                .inRoot(ToastMatcher.isToast())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateAlbum_LongName() {
        // Title
        onView(withId(R.id.titleEditText)).perform(typeText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma"), closeSoftKeyboard());
        // Cover Image URL
        onView(withId(R.id.urlEditText)).perform(typeText("Cover Image URL"), closeSoftKeyboard());
        // Release Date
        onView(withId(R.id.releaseDateEditText)).perform(typeText("2022-05-08"), closeSoftKeyboard());
        // Description
        onView(withId(R.id.descriptionEditText)).perform(typeText("This is a test description"), closeSoftKeyboard());
        // Genre
        onView(withId(R.id.genreSpinner)).perform(click());
        onView(ViewMatchers.withText("Rock")).perform(click());
        // Record Label
        onView(withId(R.id.recordLabelSpinner)).perform(click());
        onView(ViewMatchers.withText("Sony Music")).perform(click());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(ViewMatchers.withText("Please enter a title with less than 100 characters"))
                .inRoot(ToastMatcher.isToast())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateAlbum_LongUrl() {
        // Title
        onView(withId(R.id.titleEditText)).perform(typeText("Test Album"), closeSoftKeyboard());
        // Cover Image URL
        onView(withId(R.id.urlEditText)).perform(typeText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec qua"), closeSoftKeyboard());
        // Release Date
        onView(withId(R.id.releaseDateEditText)).perform(typeText("2022-05-08"), closeSoftKeyboard());
        // Description
        onView(withId(R.id.descriptionEditText)).perform(typeText("This is a test description"), closeSoftKeyboard());
        // Genre
        onView(withId(R.id.genreSpinner)).perform(click());
        onView(ViewMatchers.withText("Rock")).perform(click());
        // Record Label
        onView(withId(R.id.recordLabelSpinner)).perform(click());
        onView(ViewMatchers.withText("Sony Music")).perform(click());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(ViewMatchers.withText("Please enter a cover image URL with less than 200 characters"))
                .inRoot(ToastMatcher.isToast())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateAlbum_BadDate() {
        // Title
        onView(withId(R.id.titleEditText)).perform(typeText("Test Album"), closeSoftKeyboard());
        // Cover Image URL
        onView(withId(R.id.urlEditText)).perform(typeText("Cover Image URL"), closeSoftKeyboard());
        // Release Date
        onView(withId(R.id.releaseDateEditText)).perform(typeText("Test Date"), closeSoftKeyboard());
        // Description
        onView(withId(R.id.descriptionEditText)).perform(typeText("This is a test description"), closeSoftKeyboard());
        // Genre
        onView(withId(R.id.genreSpinner)).perform(click());
        onView(ViewMatchers.withText("Rock")).perform(click());
        // Record Label
        onView(withId(R.id.recordLabelSpinner)).perform(click());
        onView(ViewMatchers.withText("Sony Music")).perform(click());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(ViewMatchers.withText("Please enter the release date in the format yyyy-mm-dd"))
                .inRoot(ToastMatcher.isToast())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateAlbum_LongDescription() {
        // Title
        onView(withId(R.id.titleEditText)).perform(typeText("Test Album"), closeSoftKeyboard());
        // Cover Image URL
        onView(withId(R.id.urlEditText)).perform(typeText("Cover Image URL"), closeSoftKeyboard());
        // Release Date
        onView(withId(R.id.releaseDateEditText)).perform(typeText("2022-05-08"), closeSoftKeyboard());
        // Description
        onView(withId(R.id.descriptionEditText)).perform(typeText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Na"), closeSoftKeyboard());
        // Genre
        onView(withId(R.id.genreSpinner)).perform(click());
        onView(ViewMatchers.withText("Rock")).perform(click());
        // Record Label
        onView(withId(R.id.recordLabelSpinner)).perform(click());
        onView(ViewMatchers.withText("Sony Music")).perform(click());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(ViewMatchers.withText("Please enter a description with less than 1000 characters"))
                .inRoot(ToastMatcher.isToast())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateAlbum_NoGenre() {
        // Title
        onView(withId(R.id.titleEditText)).perform(typeText("Test Album"), closeSoftKeyboard());
        // Cover Image URL
        onView(withId(R.id.urlEditText)).perform(typeText("Cover Image URL"), closeSoftKeyboard());
        // Release Date
        onView(withId(R.id.releaseDateEditText)).perform(typeText("2022-05-08"), closeSoftKeyboard());
        // Description
        onView(withId(R.id.descriptionEditText)).perform(typeText("This is a test description"), closeSoftKeyboard());
        // Record Label
        onView(withId(R.id.recordLabelSpinner)).perform(click());
        onView(ViewMatchers.withText("Sony Music")).perform(click());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(ViewMatchers.withText("Please choose a genre"))
                .inRoot(ToastMatcher.isToast())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateAlbum_NoRecordLabel() {
        // Title
        onView(withId(R.id.titleEditText)).perform(typeText("Test Album"), closeSoftKeyboard());
        // Cover Image URL
        onView(withId(R.id.urlEditText)).perform(typeText("Cover Image URL"), closeSoftKeyboard());
        // Release Date
        onView(withId(R.id.releaseDateEditText)).perform(typeText("2022-05-08"), closeSoftKeyboard());
        // Description
        onView(withId(R.id.descriptionEditText)).perform(typeText("This is a test description"), closeSoftKeyboard());
        // Genre
        onView(withId(R.id.genreSpinner)).perform(click());
        onView(ViewMatchers.withText("Rock")).perform(click());
        // Record Label (empty)
        onView(withId(R.id.recordLabelSpinner)).perform(click());
        onView(ViewMatchers.withText("Choose a record label")).perform(click());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(ViewMatchers.withText("Please choose a record label"))
                .inRoot(ToastMatcher.isToast())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateAlbum_Empty() {
        // Record Label (empty)
        onView(withId(R.id.recordLabelSpinner)).perform(click());
        onView(ViewMatchers.withText("Choose a record label")).perform(click());

        // Click create button
        onView(withId(R.id.createButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify toast message
        onView(ViewMatchers.withText("Please make sure all fields have been filled correctly"))
                .inRoot(ToastMatcher.isToast())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
