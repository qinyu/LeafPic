package org.horaapps.leafpic.activities


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.`is`
import org.horaapps.leafpic.R
import org.horaapps.leafpic.RecyclerViewMatcher.Companion.withRecyclerView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EspressoLoadTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @get:Rule
    var permissionRule = GrantPermissionRule.grant(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE")


    private lateinit var activity:MainActivity

    @Before
    @Throws(Exception::class)
    fun setUp() {
        activity = activityTestRule.activity
    }


    @Test
    fun should_sort_albums_20_times_by_different_mode() {
        assertThat(activity.title, `is`<CharSequence>("LeafPic (debug)"))
        repeat(20) {
            sortAlbumsBy("Date", activity)
            onView(withRecyclerView(R.id.albums).atPosition(0))
                    .check(matches(albumWithNameAndCount("Camera", "283")))

            sortAlbumsBy("Name", activity)
            onView(withRecyclerView(R.id.albums).atPosition(0))
                    .check(matches(albumWithNameAndCount("网易云音乐相册", "1")))
        }
    }
}
