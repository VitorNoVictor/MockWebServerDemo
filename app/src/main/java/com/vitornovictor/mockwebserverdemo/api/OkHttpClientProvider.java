package com.vitornovictor.mockwebserverdemo.api;

import okhttp3.OkHttpClient;

public abstract class OkHttpClientProvider {
  private static OkHttpClient instance = null;

  public static OkHttpClient getInstance() {
    if (instance == null) {
      instance = new OkHttpClient();
    }

    return instance;
  }
}