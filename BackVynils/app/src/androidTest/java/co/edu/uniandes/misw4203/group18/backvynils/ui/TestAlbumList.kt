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
class TestAlbumList {

    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun albumsListFragmentMainComponentsTest() {
        onView(
            allOf(
                withId(R.id.albumListFragment),
                withText(R.string.title_albums_list_fragment),
                isDisplayed()
            )
        )
        onView(
            allOf(
                withId(R.id.createAlbumButton),
                withText(R.string.add_album),
                isDisplayed(),
                isClickable()
            )
        )
        onView(allOf(withId(R.id.albumListProgressBar), isDisplayed()))
        onView(allOf(withId(R.id.albumsRecyclerView), isDisplayed()))
    }

    @Test
    fun createAlbumFromListComponentsTest() {
        val albumListMenuBtn = onView(
            allOf(
                withId(R.id.albumListFragment),
                withText(R.string.title_albums_list_fragment),
                isDisplayed(),
                isClickable()
            )
        )
        Thread.sleep(3000)
        albumListMenuBtn.perform(click())
        Thread.sleep(3000)

        val albumListCreateBtn = onView(
            allOf(
                withId(R.id.createAlbumButton),
                withText(R.string.add_album),
                isDisplayed(),
                isClickable()
            )
        )
        Thread.sleep(3000)
        albumListCreateBtn.perform(click())
        Thread.sleep(3000)
    }
}
