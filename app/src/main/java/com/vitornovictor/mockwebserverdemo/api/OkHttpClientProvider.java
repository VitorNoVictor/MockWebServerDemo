package com.vitornovictor.mockwebserverdemo.api;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

public abstract class OkHttpClientProvider {
  private static final int TIMEOUT_SECONDS = 1;
  private static OkHttpClient instance = null;

  public static OkHttpClient getInstance() {
    if (instance == null) {
      instance = new OkHttpClient.Builder().readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                                           .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                                           .build();
    }

    return instance;
  }
}