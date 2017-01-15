package com.vitornovictor.mockwebserverdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface GitHubApi {
  @GET("users/{username}")
  Call<GitHubUser> getUser(@Path("username") String username);
}
