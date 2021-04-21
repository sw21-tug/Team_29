package com.example.mulatschaktracker

import android.content.Context
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Assert;
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        assertEquals("com.example.mulatschaktracker", appContext.packageName)
    }
    @Test
    fun DataBaseIsPresent_isCorrect() {
        //->ShouldWork
        val appContext: Context = ApplicationProvider.getApplicationContext();
        val repo = UserRepository(appContext);
        repo.resetDatabase();

        assert(true);
    }
    @Test
    fun CreateUserTest()
    {
        val test = UserObject("test");
        val appContext: Context = ApplicationProvider.getApplicationContext();
        val repo = UserRepository(appContext);
        repo.resetDatabase()
        val newUserId = repo.createUser(test);

        assert(newUserId > 0);
    }

    @Test(expected = Exception::class)
    fun CreateUserTestExistingUser()
    {

        val test = UserObject("test");
        val appContext: Context = ApplicationProvider.getApplicationContext();
        val repo = UserRepository(appContext);
        repo.resetDatabase()
        val newUserId2 = repo.createUser(test);
        val newUserId1 = repo.createUser(test);

        assert(false);
    }

    @Test(expected = Exception::class)
    fun CreateInvalidUserUserTest()
    {
        val emptyUser = UserObject("");
        val appContext: Context = ApplicationProvider.getApplicationContext();
        val repo = UserRepository(appContext);
        val expectedUserId = repo.createUser(emptyUser);
        repo.resetDatabase()

        assert(false);
    }
    @Test
    fun  GetExitngUserTest()
    {
        val newUser = UserObject("NewUser");
        val appContext: Context = ApplicationProvider.getApplicationContext();
        val repo = UserRepository(appContext);
        repo.resetDatabase();
        val newUserId = repo.createUser(newUser);
        assert(newUserId > 0);
        val newUserObject = repo.getUser(newUser.name);

        assertEquals(newUserId,newUserObject.id);

    }

    @Test(expected = Exception::class)
    fun  GetNonExitngUserTest()
    {
        val newUser = UserObject("NewUser");
        val newUserNotExisting = UserObject("NewUserNotExisting");
        val appContext: Context = ApplicationProvider.getApplicationContext();
        val repo = UserRepository(appContext);
        repo.resetDatabase();
        val newUserId = repo.createUser(newUser);
        val newUserObject = repo.getUser(newUserNotExisting.name);

        assert(newUserObject.id > 0);
        assert(false);

    }


    @Test(expected = Exception::class)
    fun  GetEmptyUser()
    {
        val newEmptyUser = UserObject("");
        val appContext: Context = ApplicationProvider.getApplicationContext();
        val repo = UserRepository(appContext);
        repo.resetDatabase();
        val newUserObject = repo.getUser(newEmptyUser.name);

        assert(false );

    }
}
