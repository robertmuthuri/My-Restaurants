package com.example.myrestaurants;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;


import com.example.myrestaurants.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

@LargeTest

public class MainActivityInstrumentationTest {

    @Rule // tells device which activity to launch before each test
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateEditText() {
        onView(withId(R.id.locationEditText)).perform(typeText("Portland"))
                .check(matches(withText("Portland")));
    }

    @Test
    public void validatedEditText() {
        onView                                                  // specifies that we want to interact with a view
                (withId                                         // a ViewMatcher method that allows us to find specific views by ID
                        (R.id.locationEditText))
                .perform
                        (typeText                               // a ViewAction method that allows us to type the specified text into our EditText
                                ("Nairobi"))
                .check
                        (matches                                // a ViewAssertion method that validates the specific properties of the given view
                                (withText
                                        ("Nairobi")));
    }

    @Test
    public void locationIsSentToRestaurantActivity() {
        String location = "Nairobi";
        onView(withId(R.id.locationEditText)).perform(typeText(location)).perform(closeSoftKeyboard());
        try {   // the sleep method requires to be checked and handled so we use try block
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            System.out.println("got interrupted!");
        }
//        onView(withId(R.id.locationEditText)).perform((typeText(location)));
        onView(withId(R.id.findRestaurantsButton)).perform(click());
        onView(withId(R.id.locationTextView)).check(matches
                (withText("Here are all the restaurants near: " + location)));
    }

}
