package com.vitornovictor.mockwebserverdemo.environment;

import android.app.Application;
import android.content.Context;
import io.appflate.restmock.android.RESTMockTestRunner;

public class TestRunnerUsingRestMock extends RESTMockTestRunner {
  @Override
  public Application newApplication(
      ClassLoader cl, String className, Context context)
  throws InstantiationException, IllegalAccessException, ClassNotFoundException {

    return super.newApplication(cl, TestApplicationUsingRestMock.class.getName(), context);
  }
}
