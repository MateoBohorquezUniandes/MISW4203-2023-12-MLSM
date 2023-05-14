package co.edu.uniandes.misw4203.group18.backvynils.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import co.edu.uniandes.misw4203.group18.backvynils.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestArtistList {

    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun artistListFragmentMainComponentsTest() {
        onView(
            allOf(
                withId(R.id.artistListFragment),
                withText(R.string.title_artists_list_fragment),
                isDisplayed()
            )
        )

        onView(allOf(withId(R.id.artistListProgressBar), isDisplayed()))
        onView(allOf(withId(R.id.artistsRecyclerView), isDisplayed()))
    }
}
