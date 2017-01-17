package com.vitornovictor.mockwebserverdemo;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.vitornovictor.mockwebserverdemo.api.OkHttpClientProvider;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

class OkHttpIdlingResourceRule implements TestRule {
  @Override
  public Statement apply(final Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        IdlingResource idlingResource =
            OkHttp3IdlingResource.create("okhttp", OkHttpClientProvider.getInstance());
        Espresso.registerIdlingResources(idlingResource);

        base.evaluate();

        Espresso.unregisterIdlingResources(idlingResource);
      }
    };
  }
}