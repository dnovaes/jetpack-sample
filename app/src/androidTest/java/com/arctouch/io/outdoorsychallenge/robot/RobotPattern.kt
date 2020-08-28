package com.arctouch.io.outdoorsychallenge.robot

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.arctouch.io.outdoorsychallenge.assertions.withItemCount
import org.hamcrest.Matchers.allOf

fun fillTextField(@IdRes resId: Int, text: String): ViewInteraction =
    onView(withId(resId)).perform(ViewActions.replaceText(text))

fun clickOnActionButton(@IdRes viewId: Int): ViewInteraction =
    onView(withId(viewId)).perform(pressImeActionButton())

fun matchWithItemCount(@IdRes viewId: Int, amount: Int): ViewInteraction =
    onView(allOf(withId(viewId), isDisplayed())).check(matches(withItemCount(amount)))

fun matchVisible(@IdRes viewId: Int): ViewInteraction = matchVisibility(viewId, VISIBLE)

fun matchGone(@IdRes viewId: Int): ViewInteraction = matchVisibility(viewId, GONE)

private fun matchVisibility(
    @IdRes viewId: Int,
    visibility: ViewMatchers.Visibility
): ViewInteraction = onView(withId(viewId)).check(matches(withEffectiveVisibility(visibility)))
