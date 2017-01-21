package com.vitornovictor.mockwebserverdemo.util;

import android.support.test.InstrumentationRegistry;
import com.vitornovictor.mockwebserverdemo.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityVerifications {
  private static final String RESULT_FORMAT = "%s:%s";

  public static void verifyResultLabel(int stringRes) {
    String expectedLabel = InstrumentationRegistry.getTargetContext().getString(stringRes);
    verifyResultLabel(expectedLabel);
  }

  public static void verifyResultLabel(String message) {
    onView(withId(R.id.result)).check(matches(withText(message)));
  }

  public static void verifyResultLabel(String user, String id) {
    onView(withId(R.id.result)).check(matches(withText(buildResultFor(user, id))));
  }

  public static void verifyDefaultErrorMessage() {
    onView(withId(android.support.design.R.id.snackbar_text)).check(matches(isDisplayed()));
  }

  private static String buildResultFor(String user, String id) {
    return String.format(RESULT_FORMAT, user, id);
  }
}
