package com.vitornovictor.mockwebserverdemo.util;

public final class Parameters {
  private Parameters() {
  }

  static final int RESPONSE_CODE_USER_NOT_FOUND = 404;

  static final long DELAY_PERIOD = 2;
  static final long BYTES_PER_PERIOD = 1;

  public static final String VALID_USER = "validUser";
  public static final String VALID_USER_ID = "12345";

  public static final String NONEXISTENT_USER = "nonexistentUser";

  public static final String TIMEOUT_USER = "timeoutUser";
  static final String TIMEOUT_USER_ID = "54321";

  public static final String MALFORMED_USER = "malformedUser";
}
