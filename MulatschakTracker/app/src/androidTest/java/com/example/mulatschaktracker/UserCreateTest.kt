package com.example.mulatschaktracker

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import junit.framework.TestCase
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.*

@RunWith(AndroidJUnit4::class)
class UserCreateTest : TestCase() {

    lateinit var repo : UserRepository;

    @Before
    public override fun setUp() {
        super.setUp()


        //val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        //Assert.assertEquals("com.example.mulatschaktracker", appContext.packageName)
        val  appContext: Context = ApplicationProvider.getApplicationContext();
        repo =  UserRepository(appContext);


        //repo.resetDatabase();
    }

    val validUserName = "User1";
    val invalidUserName = "";

    @After
    public override fun tearDown() {}
    @get:Rule
    val activityRule = ActivityScenarioRule(CreateUserActivity::class.java)

    @Test
    fun ValidUserNameTest(){

        onView(withId(R.id.UserName_Input)).perform(typeText(validUserName));
        onView(withId(R.id.SubmitUserName)).perform(click());

        val  user = repo.getUser(validUserName);

        assert(user.name.equals(validUserName));
        assert(user.id >= 0);

    }

    @Test
    fun InvalidUserNameTest(){

        onView(withId(R.id.UserName_Input)).perform(typeText(invalidUserName));
        onView(withId(R.id.SubmitUserName)).perform(click());

        //ToDO: CheckAlert
        onView(withText("InvalidUserMessage")).check(matches(isDisplayed()));
    }

    @Test
    fun UserNameInUseTest(){

        onView(withId(R.id.UserName_Input)).perform(typeText(validUserName));
        onView(withId(R.id.SubmitUserName)).perform(click());
        onView(withId(R.id.UserName_Input)).perform(typeText(validUserName));
        onView(withId(R.id.SubmitUserName)).perform(click());

        //ToDO: CheckAlert
        onView(withText("UserAlreadyinDBMessage")).check(matches(isDisplayed()));

    }

}