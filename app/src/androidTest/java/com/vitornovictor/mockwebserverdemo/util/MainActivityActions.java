package com.vitornovictor.mockwebserverdemo.util;

import com.vitornovictor.mockwebserverdemo.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityActions {
  public static void searchUser(String user) {
    onView(withId(R.id.username)).perform(typeText(user));
    onView(withId(R.id.search_user_button)).perform(click());
  }

  public static void goBackToSearchForm() {
    onView(withId(R.id.clear_result_button)).perform(click());
  }
}
