package com.vitornovictor.mockwebserverdemo.util;

import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class ResponseDispatcher extends Dispatcher {
  @Override
  public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
    String path = recordedRequest.getPath();
    String[] parts = path.split("/");
    String username = parts[parts.length - 1];

    if (username.equalsIgnoreCase(Parameters.VALID_USER_1)) {
      return buildResponseForBody(ServerResponse.buildFor(Parameters.VALID_USER_1,
                                                          Parameters.VALID_USER_1_ID));
    }

    if (username.equalsIgnoreCase(Parameters.NONEXISTENT_USER)) {
      return buildResponseForCode(Parameters.RESPONSE_CODE_USER_NOT_FOUND);
    }

    if (username.equalsIgnoreCase(Parameters.TIMEOUT_USER)) {
      String body = ServerResponse.buildFor(Parameters.TIMEOUT_USER, Parameters.TIMEOUT_USER_ID);

      return buildResponseForBodyWithDelay(body,
                                           Parameters.BYTES_PER_PERIOD,
                                           Parameters.DELAY_PERIOD,
                                           TimeUnit.SECONDS);
    }

    if (username.equalsIgnoreCase(Parameters.MALFORMED_USER)) {
      return buildResponseForBody(ServerResponse.getMalformedResponse());
    }

    return null;
  }

  private MockResponse buildResponseForBody(String body) {
    return new MockResponse().setBody(body);
  }

  private MockResponse buildResponseForBodyWithDelay(
      String body, long bytesPerPeriod, long period, TimeUnit unit) {

    return new MockResponse().setBody(body).throttleBody(bytesPerPeriod, period, unit);
  }

  private MockResponse buildResponseForCode(int responseCode) {
    return new MockResponse().setResponseCode(responseCode);
  }
}
