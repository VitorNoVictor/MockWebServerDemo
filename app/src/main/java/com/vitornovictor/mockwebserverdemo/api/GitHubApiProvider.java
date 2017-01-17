package com.vitornovictor.mockwebserverdemo.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubApiProvider {
  public static GitHubApi getInstance(String baseUrl) {
    return new Retrofit.Builder().baseUrl(baseUrl)
                                 .addConverterFactory(GsonConverterFactory.create())
                                 .client(OkHttpClientProvider.getInstance())
                                 .build()
                                 .create(GitHubApi.class);
  }
}
