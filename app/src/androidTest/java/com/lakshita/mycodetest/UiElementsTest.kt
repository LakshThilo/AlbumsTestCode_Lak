@file:Suppress("DEPRECATION")

package com.lakshita.mycodetest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UiElementsTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun uiElementsTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.album_recycler_view),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))
    }

    @Test
    fun scrollToViews() {
        onView(withId(R.id.album_recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(50))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(99))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

}


