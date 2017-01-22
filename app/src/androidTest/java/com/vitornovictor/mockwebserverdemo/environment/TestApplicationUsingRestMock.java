package com.vitornovictor.mockwebserverdemo.environment;

import com.vitornovictor.mockwebserverdemo.GitHubIdFinderApplication;
import com.vitornovictor.mockwebserverdemo.api.GitHubApi;
import com.vitornovictor.mockwebserverdemo.api.GitHubApiProvider;
import io.appflate.restmock.RESTMockServer;

public class TestApplicationUsingRestMock extends GitHubIdFinderApplication{
  @Override
  public GitHubApi getGitHubApi() {
    return GitHubApiProvider.getInstance(RESTMockServer.getUrl());
  }
}
