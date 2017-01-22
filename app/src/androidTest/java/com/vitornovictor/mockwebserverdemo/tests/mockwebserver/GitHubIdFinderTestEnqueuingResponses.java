package com.vitornovictor.mockwebserverdemo.tests.mockwebserver;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.vitornovictor.mockwebserverdemo.MainActivity;
import com.vitornovictor.mockwebserverdemo.environment.TestApplication;
import com.vitornovictor.mockwebserverdemo.rules.MockWebServerRule;
import com.vitornovictor.mockwebserverdemo.rules.OkHttpIdlingResourceRule;
import com.vitornovictor.mockwebserverdemo.util.Parameters;
import com.vitornovictor.mockwebserverdemo.util.ServerResponse;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.vitornovictor.mockwebserverdemo.util.MainActivityActions.goBackToSearchForm;
import static com.vitornovictor.mockwebserverdemo.util.MainActivityActions.searchUser;
import static com.vitornovictor.mockwebserverdemo.util.MainActivityVerifications.verifyResultLabel;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GitHubIdFinderTestEnqueuingResponses {
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
        new MockResponse().setBody(ServerResponse.buildFor(Parameters.VALID_USER_1,
                                                           Parameters.VALID_USER_1_ID));
    mockWebServerRule.server.enqueue(mockResponse);

    activityTestRule.launchActivity(null);

    searchUser(Parameters.VALID_USER_1);

    verifyResultLabel(Parameters.VALID_USER_1, Parameters.VALID_USER_1_ID);
  }

  @Test
  public void performsTwoSearchesAndShowsCorrectIdsBothTimes() throws IOException {
    MockResponse firstUserResponse =
        new MockResponse().setBody(ServerResponse.buildFor(Parameters.VALID_USER_1,
                                                           Parameters.VALID_USER_1_ID));
    mockWebServerRule.server.enqueue(firstUserResponse);

    MockResponse secondUserResponse =
        new MockResponse().setBody(ServerResponse.buildFor(Parameters.VALID_USER_2,
                                                           Parameters.VALID_USER_2_ID));
    mockWebServerRule.server.enqueue(secondUserResponse);

    activityTestRule.launchActivity(null);

    searchUser(Parameters.VALID_USER_1);
    verifyResultLabel(Parameters.VALID_USER_1, Parameters.VALID_USER_1_ID);

    goBackToSearchForm();

    searchUser(Parameters.VALID_USER_2);
    verifyResultLabel(Parameters.VALID_USER_2, Parameters.VALID_USER_2_ID);
  }
}
