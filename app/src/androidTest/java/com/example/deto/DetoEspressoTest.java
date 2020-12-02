package com.example.deto;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetoEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void detoEspressoTest() {

        ViewInteraction appCompatEditText3 = onView(                                                     //Brugernavn og password indtastes
                allOf(withId(R.id.editTextTextPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Deto"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextTextPassword2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("2020"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(                                                       //Der bliver trykket login og historik over notifikationer vises (isDisplayed)
                allOf(withId(R.id.loginBT), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction tabView = onView(                                                               //åbner Grafisk oversigt vælger en person og grafen vises
                allOf(withContentDescription("Grafisk Oversigt"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tablay_out),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner),                                                              //Her vælges borgeren i dropdown
                        childAtPosition(
                                withParent(withId(R.id.view_pager)),
                                0),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())                                   //Graf
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(6);
        appCompatCheckedTextView.perform(click());

        ViewInteraction tabView2 = onView(                                                              //Kalibreringstab åbnes
                allOf(withContentDescription("Kalibering"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tablay_out),
                                        0),
                                2),
                        isDisplayed()));
        tabView2.perform(click());

        ViewInteraction videoView = onView(                                                             //Videoen startes
                allOf(withId(R.id.video_view),
                        childAtPosition(
                                withParent(withId(R.id.view_pager)),
                                2),
                        isDisplayed()));
        videoView.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")), withContentDescription("Play"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
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
}
