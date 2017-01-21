package com.vitornovictor.mockwebserverdemo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.vitornovictor.mockwebserverdemo.environment.TestApplication;
import com.vitornovictor.mockwebserverdemo.rules.MockWebServerRule;
import com.vitornovictor.mockwebserverdemo.rules.OkHttpIdlingResourceRule;
import com.vitornovictor.mockwebserverdemo.util.ResponseDispatcher;
import com.vitornovictor.mockwebserverdemo.util.ServerResponse;
import com.vitornovictor.mockwebserverdemo.util.Parameters;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.vitornovictor.mockwebserverdemo.util.MainActivityActions.searchUser;
import static com.vitornovictor.mockwebserverdemo.util.MainActivityVerifications.verifyDefaultErrorMessage;
import static com.vitornovictor.mockwebserverdemo.util.MainActivityVerifications.verifyResultLabel;

@RunWith(AndroidJUnit4.class)
public class GitHubIdFinderTest {
  private static final String NONEXISTENT_USER = "nonexistentUser";

  private static final String USER_FOR_TIMEOUT = "timeoutUser";
  private static final String USER_FOR_SERVER_ERROR = "serverErrorUser";
  private static final String USER_FOR_INVALID_RESPONSE = "invalidResponseUser";

  @Rule
  public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<>(MainActivity.class, true, false);

  @Rule
  public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Before
  public void setBaseUrl() {
    TestApplication app =
        (TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();

    app.setBaseUrl(mockWebServerRule.server.url("/").toString());
  }

  @Test
  public void showsIdForExistentUser() throws IOException {
    MockResponse mockResponse =
        new MockResponse().setBody(ServerResponse.buildFor(Parameters.VALID_USER,
                                                           Parameters.VALID_USER_ID));
    mockWebServerRule.server.enqueue(mockResponse);

    activityTestRule.launchActivity(null);

    searchUser(Parameters.VALID_USER);

    verifyResultLabel(Parameters.VALID_USER, Parameters.VALID_USER_ID);
  }

  @Test
  public void showsMessageFoNonexistentUser() {
    mockWebServerRule.server.setDispatcher(new ResponseDispatcher());
    activityTestRule.launchActivity(null);

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
}
