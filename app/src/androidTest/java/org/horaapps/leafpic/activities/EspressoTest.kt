package org.horaapps.leafpic.activities


import android.app.Activity
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.view.View

import org.hamcrest.Matcher
import org.horaapps.leafpic.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.isClickable
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.horaapps.leafpic.RecyclerViewMatcher.Companion.withRecyclerView

@LargeTest
@RunWith(AndroidJUnit4::class)
class EspressoTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @get:Rule
    var permissionRule = GrantPermissionRule.grant(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE")

    @Test
    fun should_sort_albums_by_different_mode() {
        assertThat(activityTestRule.activity.title, `is`<CharSequence>("LeafPic (debug)"))

        sortAlbumsBy("Date", activityTestRule.activity)
        onView(withRecyclerView(R.id.albums).atPosition(0))
                .check(matches(albumWithNameAndCount("Camera", "283")))


        sortAlbumsBy("Name", activityTestRule.activity)
        onView(withRecyclerView(R.id.albums).atPosition(0))
                .check(matches(albumWithNameAndCount("网易云音乐相册", "1")))

        //        onView(allOf(withContentDescription("Open navigation drawer"), withParent(withId(R.id.toolbar))))
        //                .perform(click());
        //
        //        onView(withId(R.id.navigation_item_settings))
        //                .check(matches(isEnabled()))
        //                .perform(click());
    }

}

fun sortAlbumsBy(date: String, activity: Activity) {
    onView(allOf(withId(R.id.sort_action), withContentDescription("Sort")))
            .check(matches(isClickable()))
            .perform(click())

    onView(withText(date)).inRoot(withDecorView(not(`is`(activity.window.decorView))))
            .perform(click())
}


fun albumWithNameAndCount(camera: String, s: String): Matcher<View> {
    return allOf(hasDescendant(withText(camera)), hasDescendant(withText(s)))
}
