package co.edu.uniandes.misw4203.group18.backvynils.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import co.edu.uniandes.misw4203.group18.backvynils.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
    fun navigateToAlbumListFroOtherFragments() {
        val collectorBtn = onView(allOf(withId(R.id.collectorListFragment)))
        Thread.sleep(3000)
        collectorBtn.perform(click())
        Thread.sleep(3000)

        val albumBtn = onView(allOf(withId(R.id.albumListFragment)))
        Thread.sleep(3000)
        albumBtn.perform(click())
        Thread.sleep(3000)

        onView(allOf(
            withId(R.id.albumListTitle),
            withText(R.string.title_albums_list_fragment),
            isDisplayed()
        ))
        onView(
            allOf(
                withId(R.id.createAlbumButton),
                withText(R.string.add_album),
                isDisplayed(),
                isClickable()
            )
        )
        onView(allOf(withId(R.id.albumsRecyclerView), isDisplayed()))

        val artistBtn = onView(allOf(withId(R.id.artistListFragment)))
        Thread.sleep(3000)
        artistBtn.perform(click())
        Thread.sleep(3000)

        Thread.sleep(3000)
        albumBtn.perform(click())
        Thread.sleep(3000)

        onView(allOf(
            withId(R.id.albumListTitle),
            withText(R.string.title_albums_list_fragment),
            isDisplayed()
        ))
        onView(
            allOf(
                withId(R.id.createAlbumButton),
                withText(R.string.add_album),
                isDisplayed(),
                isClickable()
            )
        )
        onView(allOf(withId(R.id.albumsRecyclerView), isDisplayed()))
    }

    @Test
    fun navigateToAlbumListFromCreateAlbumFragment() {
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

        val albumBtn = onView(allOf(withId(R.id.albumListFragment)))
        Thread.sleep(3000)
        albumBtn.perform(click())
        Thread.sleep(3000)

        onView(allOf(
            withId(R.id.albumListTitle),
            withText(R.string.title_albums_list_fragment),
            isDisplayed()
        ))
        onView(
            allOf(
                withId(R.id.createAlbumButton),
                withText(R.string.add_album),
                isDisplayed(),
                isClickable()
            )
        )
    }

    @Test
    fun createAlbumFromListComponentsTest() {
        onView(
            allOf(
                withId(R.id.albumListFragment),
                withText(R.string.title_albums_list_fragment),
                isDisplayed(),
                isClickable()
            )
        )
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

        onView(
            allOf(
                withId(R.id.createAlbumButton),
                withText(R.string.create_new_album),
                isDisplayed(),
            )
        )
    }

}
