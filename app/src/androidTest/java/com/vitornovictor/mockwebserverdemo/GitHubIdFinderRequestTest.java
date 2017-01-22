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
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.vitornovictor.mockwebserverdemo.util.MainActivityActions.searchUser;
import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GitHubIdFinderRequestTest {
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
  public void requestsRightUser() throws InterruptedException {
    searchUser(Parameters.VALID_USER_1);

    RecordedRequest recordedRequest = mockWebServerRule.server.takeRequest();
    String path = recordedRequest.getPath();
    String[] pathComponents = path.split("/");
    String username = pathComponents[pathComponents.length - 1];

    assertEquals(Parameters.VALID_USER_1, username);
  }
}
