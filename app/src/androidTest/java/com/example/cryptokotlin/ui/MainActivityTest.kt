package com.example.cryptokotlin.ui

import android.content.Context
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.cryptokotlin.R
import com.example.cryptokotlin.adapter.CryptoAdapter
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private lateinit var context: Context
    private val NAME = "bitcoin"

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        context = getInstrumentation().targetContext
    }

    @Test
    fun listVisibleOnApplaunch() {
        onView(withId(R.id.rvCrypto)).check(matches(isDisplayed()))
    }

    @Test
    fun itemViewClick() {
        /*---from your emulator setting-->developer option--> switch off animation---*/
        onView(withId(R.id.rvCrypto)).perform(scrollToPosition<CryptoAdapter.CryptoViewHolder>(4))
        onView(withId(R.id.rvCrypto)).perform(
            actionOnItemAtPosition<CryptoAdapter.CryptoViewHolder>(
                4,
                click()
            )
        )
    }

    @Test
    fun testItemNotFound(){
        // Click on the search icon
        onView(withId(R.id.etSearch)).perform(click())
        // write Query
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("No such item"),
            pressImeActionButton()
        )
       // as of now i did not create a empty view so checking on this only
        // can update in future accordingly
        onView(withId(R.id.rvCrypto)).check(matches(isDisplayed()))
    }

     @Test
    fun testItemFound(){
        // Click on the search icon
        onView(withId(R.id.etSearch)).perform(click())
        // write Query
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText(NAME),
            pressImeActionButton()
        )
         onView(withText(containsString(NAME))).check(matches(isDisplayed()))
    }
}
