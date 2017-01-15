package com.vitornovictor.mockwebserverdemo;

import android.app.Application;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubIdFinderApplication extends Application {
  private static final String BASE_URL = "https://api.github.com";
  private GitHubApi gitHubApi;

  public GitHubApi getGitHubApi() {
    if (gitHubApi == null) {
      gitHubApi = new Retrofit.Builder().baseUrl(BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build()
                                        .create(GitHubApi.class);
    }

    return gitHubApi;
  }
}
