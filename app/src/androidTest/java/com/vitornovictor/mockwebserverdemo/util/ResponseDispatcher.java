package com.vitornovictor.mockwebserverdemo.util;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class ResponseDispatcher extends Dispatcher {
  @Override
  public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
    String path = recordedRequest.getPath();
    String[] parts = path.split("/");
    String username = parts[parts.length - 1];

    if (username.equalsIgnoreCase(Parameters.VALID_USER)) {
      return buildForBody(ServerResponse.buildFor(Parameters.VALID_USER, Parameters.VALID_USER_ID));
    }

    if (username.equalsIgnoreCase(Parameters.NONEXISTENT_USER)) {
      return buildForResponseCode(Parameters.RESPONSE_CODE_USER_NOT_FOUND);
    }

    return null;
  }

  private MockResponse buildForBody(String body) {
    return new MockResponse().setBody(body);
  }

  private MockResponse buildForResponseCode(int responseCode) {
    return new MockResponse().setResponseCode(responseCode);
  }
}
