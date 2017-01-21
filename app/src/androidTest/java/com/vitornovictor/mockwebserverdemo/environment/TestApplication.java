package com.vitornovictor.mockwebserverdemo.environment;

import com.vitornovictor.mockwebserverdemo.GitHubIdFinderApplication;
import com.vitornovictor.mockwebserverdemo.api.GitHubApi;
import com.vitornovictor.mockwebserverdemo.api.GitHubApiProvider;

public class TestApplication extends GitHubIdFinderApplication {
  private String baseUrl;

  @Override
  public GitHubApi getGitHubApi() {
    return GitHubApiProvider.getInstance(baseUrl);
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }
}
