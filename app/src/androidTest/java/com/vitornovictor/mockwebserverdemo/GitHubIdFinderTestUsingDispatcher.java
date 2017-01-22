package com.vitornovictor.mockwebserverdemo;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.vitornovictor.mockwebserverdemo.environment.TestApplication;
import com.vitornovictor.mockwebserverdemo.rules.MockWebServerRule;
import com.vitornovictor.mockwebserverdemo.rules.OkHttpIdlingResourceRule;
import com.vitornovictor.mockwebserverdemo.util.Parameters;
import com.vitornovictor.mockwebserverdemo.util.ResponseDispatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.vitornovictor.mockwebserverdemo.util.MainActivityActions.searchUser;
import static com.vitornovictor.mockwebserverdemo.util.MainActivityVerifications.verifyDefaultErrorMessage;
import static com.vitornovictor.mockwebserverdemo.util.MainActivityVerifications.verifyResultLabel;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GitHubIdFinderTestUsingDispatcher {
  @Rule
  public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<>(MainActivity.class, true, false);

  @Rule
  public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Before
  public void setup() {
    TestApplication app =
        (TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();

    app.setBaseUrl(mockWebServerRule.server.url("/").toString());

    mockWebServerRule.server.setDispatcher(new ResponseDispatcher());
    activityTestRule.launchActivity(null);
  }

  @Test
  public void showsMessageFoNonexistentUser() {
    searchUser(Parameters.NONEXISTENT_USER);

    verifyResultLabel(R.string.user_not_found);
  }

  @Test
  public void showsErrorMessageOnTimeout() {
    searchUser(Parameters.TIMEOUT_USER);

    verifyDefaultErrorMessage();
  }

  @Test
  public void showsErrorMessageOnInvalidResponse() {
    searchUser(Parameters.MALFORMED_USER);

    verifyDefaultErrorMessage();
  }
}
