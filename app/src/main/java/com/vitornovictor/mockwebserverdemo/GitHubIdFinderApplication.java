package com.vitornovictor.mockwebserverdemo;

import android.app.Application;
import com.vitornovictor.mockwebserverdemo.api.GitHubApi;
import com.vitornovictor.mockwebserverdemo.api.GitHubApiProvider;

public class GitHubIdFinderApplication extends Application {
  private static final String BASE_URL = "https://api.github.com";
  private GitHubApi gitHubApi;

  public GitHubApi getGitHubApi() {
    if (gitHubApi == null) {
      gitHubApi = GitHubApiProvider.getInstance(BASE_URL);
    }

    return gitHubApi;
  }
}
