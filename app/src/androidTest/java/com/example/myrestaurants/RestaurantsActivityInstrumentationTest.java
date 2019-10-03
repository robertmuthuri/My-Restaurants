package com.example.myrestaurants;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsNot.not;


public class RestaurantsActivityInstrumentationTest {

    @Rule // Creates a rule to begin the RestaurantsActivity before each test
    public ActivityTestRule<RestaurantsActivity> activityTestRule = new ActivityTestRule<>(RestaurantsActivity.class);

    @Test
    public void listItemClickDisplaysToastWithCorrectRestaurant() {
        View activityDecorView = activityTestRule.getActivity().getWindow().getDecorView();

        String restaurantName = "Mi Mero Mole";

        // check that on clicking on the first item in list, the toast displays that first item
        onData(anything())  // use the onData method to interact with data in an adapter
                .inAdapterView(withId(R.id.listView))
                .atPosition(0)
                .perform(click());
                onView(withText(restaurantName)).inRoot(withDecorView(not(activityDecorView))) // use the onview method on a particular data point
                        .check(matches(withText(restaurantName)));
    }
}
