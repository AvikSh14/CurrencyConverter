package com.example.currencyconverter

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.currencyconverter.activities.MainActivity
import org.hamcrest.CoreMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.currencyconverter", appContext.packageName)
//    }

    @Test
    fun testCurrencyConverter() {
        onView(withId(R.id.etAmount)).perform(typeText("10"))
        onView(withId(R.id.spinnerCurrencyList)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("USD")))
            .perform(click())
    }
}