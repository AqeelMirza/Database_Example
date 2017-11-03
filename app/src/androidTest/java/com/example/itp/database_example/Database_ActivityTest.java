package com.example.itp.database_example;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Database_ActivityTest {

    public static final String STRING_TO_BE_TYPED = "abcdefg";

    @Rule
    public ActivityTestRule<Database_Activity> mActivityTestRule = new ActivityTestRule<>(Database_Activity.class);

    @Test
    public void database_ActivityTest() {


        //onView(withId(R.id.button)).perform(click());

       onView(withId(R.id.et_updatevalue))
                .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());

        onView(withId(R.id.button_pay)).perform(click());


        // Check that the text was changed.
     //   onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)));




    }
}
