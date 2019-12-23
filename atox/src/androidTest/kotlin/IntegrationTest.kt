package ltd.evilcorp.atox

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.core.AllOf.allOf
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntegrationTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun deleteEverything(): Unit = with(InstrumentationRegistry.getInstrumentation()) {
            targetContext.deleteDatabase("core_db")
            targetContext.filesDir.listFiles()?.forEach {
                it.delete()
            }
        }
    }

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun profileCreationWorks() {
        // ProfileFragment
        onView(withId(R.id.username)).perform(typeText("mr robotto"), closeSoftKeyboard())
        onView(withId(R.id.btnCreate)).perform(click())

        // ContactListFragment
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.profileName)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.profileName), withText("mr robotto")))
            .check(matches(isDisplayed()))
    }
}
