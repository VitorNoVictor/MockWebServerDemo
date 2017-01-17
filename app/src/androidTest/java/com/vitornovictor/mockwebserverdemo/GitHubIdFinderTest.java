package com.vitornovictor.mockwebserverdemo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Ignore;
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
  private static final String RESULT_FORMAT = "%s:%s";

  private static final String NONEXISTENT_USER = "nonexistentUser";

  private static final String USER_FOR_TIMEOUT = "timeoutUser";
  private static final String USER_FOR_SERVER_ERROR = "serverErrorUser";
  private static final String USER_FOR_INVALID_RESPONSE = "invalidResponseUser";

  @Rule
  public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<>(MainActivity.class, true, false);

  @Rule
  public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

  @Test
  public void showsIdForExistentUser() throws IOException {
    MockWebServer server = new MockWebServer();
    server.start();

    TestApplication app = (TestApplication)
        InstrumentationRegistry.getTargetContext().getApplicationContext();
    app.setBaseUrl(server.url("/").toString());

    server.enqueue(new MockResponse().setBody(TestResponse.ValidUser.RESPONSE));

    activityTestRule.launchActivity(null);

    searchUser(TestResponse.ValidUser.USERNAME);

    String expectedResultLabel =
        buildResultFor(TestResponse.ValidUser.USERNAME, TestResponse.ValidUser.ID);

    verifyResultLabel(expectedResultLabel);
    server.shutdown();
  }

  @Ignore
  @Test
  public void showsMessageFoNonexistentUser() {
    searchUser(NONEXISTENT_USER);

    verifyResultLabel(R.string.user_not_found);
  }

  @Ignore
  @Test
  public void showsErrorMessageOnTimeout() {
    searchUser(USER_FOR_TIMEOUT);

    verifyDefaultErrorMessage();
  }

  @Ignore
  @Test
  public void showsErrorMessageOnServerError() {
    searchUser(USER_FOR_SERVER_ERROR);
    verifyDefaultErrorMessage();
  }

  @Ignore
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
