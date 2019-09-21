package com.android.master.activity;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.master.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTestPhotoWatchVideoFromStep {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource2;

    @Before
    public void registerIdlingResource() {
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity((ActivityScenario.ActivityAction<MainActivity>) activity -> {
            mIdlingResource2 =  activity.getIdlingResource();
            IdlingRegistry.getInstance().register(mIdlingResource2);
        });
    }

    @Test
    public void mainActivityTestPhotoWatchVideo() {
        ViewInteraction cardView = onView(
                allOf(withId(R.id.cv_recipe),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_recipe),
                                        0),
                                0),
                        isDisplayed()));
        cardView.perform(click());

       ViewInteraction cardView2 = onView(
                allOf(withId(R.id.cv_step),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_step),
                                        0),
                                0),
                        isDisplayed()));
        cardView2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.exo_play), withContentDescription("Reproducir"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource2 != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource2);
        }
    }
}
