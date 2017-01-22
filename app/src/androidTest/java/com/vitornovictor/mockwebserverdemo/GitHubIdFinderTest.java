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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.vitornovictor.mockwebserverdemo.util.MainActivityActions.searchUser;
import static com.vitornovictor.mockwebserverdemo.util.MainActivityVerifications.verifyDefaultErrorMessage;
import static com.vitornovictor.mockwebserverdemo.util.MainActivityVerifications.verifyResultLabel;

@RunWith(AndroidJUnit4.class)
public class GitHubIdFinderTest {
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

    searchUser(Parameters.NONEXISTENT_USER);

    verifyResultLabel(R.string.user_not_found);
  }

  @Test
  public void showsErrorMessageOnTimeout() {
    mockWebServerRule.server.setDispatcher(new ResponseDispatcher());
    activityTestRule.launchActivity(null);

    searchUser(Parameters.TIMEOUT_USER);

    verifyDefaultErrorMessage();
  }

  @Test
  public void showsErrorMessageOnInvalidResponse() {
    mockWebServerRule.server.setDispatcher(new ResponseDispatcher());
    activityTestRule.launchActivity(null);

    searchUser(Parameters.MALFORMED_USER);

    verifyDefaultErrorMessage();
  }
}
