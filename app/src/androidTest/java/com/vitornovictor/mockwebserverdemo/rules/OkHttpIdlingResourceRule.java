package com.vitornovictor.mockwebserverdemo.rules;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.vitornovictor.mockwebserverdemo.api.OkHttpClientProvider;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class OkHttpIdlingResourceRule implements TestRule {
  private static final String IDLING_RESOURCE_NAME = "OkHttpIdlingResource";

  @Override
  public Statement apply(final Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        IdlingResource idlingResource =
            OkHttp3IdlingResource.create(IDLING_RESOURCE_NAME, OkHttpClientProvider.getInstance());

        Espresso.registerIdlingResources(idlingResource);

        base.evaluate();

        Espresso.unregisterIdlingResources(idlingResource);
      }
    };
  }
}