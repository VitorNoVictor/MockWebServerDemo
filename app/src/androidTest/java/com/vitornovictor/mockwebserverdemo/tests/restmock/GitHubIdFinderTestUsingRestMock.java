package com.vitornovictor.mockwebserverdemo.tests.restmock;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.vitornovictor.mockwebserverdemo.MainActivity;
import com.vitornovictor.mockwebserverdemo.R;
import com.vitornovictor.mockwebserverdemo.rules.OkHttpIdlingResourceRule;
import com.vitornovictor.mockwebserverdemo.util.MainActivityVerifications;
import com.vitornovictor.mockwebserverdemo.util.Parameters;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RequestsVerifier;
import java.io.IOException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.vitornovictor.mockwebserverdemo.util.MainActivityActions.searchUser;
import static com.vitornovictor.mockwebserverdemo.util.MainActivityVerifications.verifyResultLabel;
import static io.appflate.restmock.utils.RequestMatchers.pathEndsWith;

@Ignore
@LargeTest
@RunWith(AndroidJUnit4.class)
public class GitHubIdFinderTestUsingRestMock {
  @Rule
  public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<>(MainActivity.class, true, false);

  @Rule
  public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

  @Before
  public void reset() {
    RESTMockServer.reset();
  }

  @Test
  public void showsIdForExistentUser() throws IOException {
    RESTMockServer.whenGET(pathEndsWith(Parameters.VALID_USER_1))
                  .thenReturnFile("responses/valid_user.json");

    activityTestRule.launchActivity(null);

    searchUser(Parameters.VALID_USER_1);

    verifyResultLabel(Parameters.VALID_USER_1, Parameters.VALID_USER_1_ID);
  }

  @Test
  public void showsMessageFoNonexistentUser() {
    RESTMockServer.whenGET(pathEndsWith(Parameters.NONEXISTENT_USER))
                  .thenReturnEmpty(Parameters.RESPONSE_CODE_USER_NOT_FOUND);

    activityTestRule.launchActivity(null);

    searchUser(Parameters.NONEXISTENT_USER);

    MainActivityVerifications.verifyResultLabel(R.string.user_not_found);
  }

  @Test
  public void requestsRightUser() throws InterruptedException {
    RESTMockServer.whenGET(pathEndsWith(Parameters.VALID_USER_1))
                  .thenReturnFile("responses/valid_user.json");

    activityTestRule.launchActivity(null);

    searchUser(Parameters.VALID_USER_1);

    RequestsVerifier.verifyGET(pathEndsWith(Parameters.VALID_USER_1)).invoked();
  }
}
