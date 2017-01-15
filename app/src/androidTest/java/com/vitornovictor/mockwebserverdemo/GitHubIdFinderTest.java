package com.vitornovictor.mockwebserverdemo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class GitHubIdFinderTest {
  private static final String EXISTENT_USER = "existentUser";
  private static final String EXISTENT_USER_ID = "1234";
  private static final String RESULT_FORMAT = "%s:%s";

  private static final String NONEXISTENT_USER = "nonexistentUser";

  private static final String USER_FOR_TIMEOUT = "timeoutUser";
  private static final String USER_FOR_SERVER_ERROR = "serverErrorUser";
  private static final String USER_FOR_INVALID_RESPONSE = "invalidResponseUser";


  @Rule
  public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

  @Test
  public void showsIdForExistentUser() {
    searchUser(EXISTENT_USER);

    String expectedResultLabel = buildResultFor(EXISTENT_USER, EXISTENT_USER_ID);

    verifyResultLabel(expectedResultLabel);
  }

  @Test
  public void showsMessageFoNonexistentUser() {
    searchUser(NONEXISTENT_USER);

    verifyResultLabel(R.string.user_not_found);
  }

  @Test
  public void showsErrorMessageOnTimeout() {
    searchUser(USER_FOR_TIMEOUT);

    verifyDefaultErrorMessage();
  }

  @Test
  public void showsErrorMessageOnServerError() {
    searchUser(USER_FOR_SERVER_ERROR);
    verifyDefaultErrorMessage();
  }

  @Test
  public void showsErrorMessageOnInvalidResponse() {
    searchUser(USER_FOR_INVALID_RESPONSE);

    verifyDefaultErrorMessage();
  }

  private static void searchUser(String user) {
    onView(withId(R.id.username)).perform(typeText(user));
    onView(withId(R.id.search_user_button)).perform(click());
  }

  private void verifyResultLabel(int stringRes) {
    String expectedLabel = InstrumentationRegistry.getTargetContext().getString(stringRes);
    verifyResultLabel(expectedLabel);
  }

  private void verifyResultLabel(String resultLabel) {
    onView(withId(R.id.result)).check(matches(withText(resultLabel)));
  }

  private void verifyDefaultErrorMessage() {
    onView(withId(android.support.design.R.id.snackbar_text)).check(matches(isDisplayed()));
  }

  private static String buildResultFor(String user, String id) {
    return String.format(RESULT_FORMAT, user, id);
  }
}
