package com.vitornovictor.mockwebserverdemo.util;

public final class ServerResponse {
  private static final String VALID_RESPONSE_TEMPLATE = "{\n"
      + "  \"login\": \"%s\",\n"
      + "  \"id\": \"%s\",\n"
      + "  \"avatar_url\": null,\n"
      + "  \"gravatar_id\": \"\",\n"
      + "  \"url\": null,\n"
      + "  \"html_url\": null,\n"
      + "  \"followers_url\": null,\n"
      + "  \"following_url\": null,\n"
      + "  \"gists_url\": null,\n"
      + "  \"starred_url\": null,\n"
      + "  \"subscriptions_url\": null,\n"
      + "  \"organizations_url\": null,\n"
      + "  \"repos_url\": null,\n"
      + "  \"events_url\": null,\n"
      + "  \"received_events_url\": null,\n"
      + "  \"type\": \"User\",\n"
      + "  \"site_admin\": false,\n"
      + "  \"name\": null,\n"
      + "  \"company\": null,\n"
      + "  \"blog\": null,\n"
      + "  \"location\": null,\n"
      + "  \"email\": null,\n"
      + "  \"hireable\": true,\n"
      + "  \"bio\": null,\n"
      + "  \"public_repos\": 1,\n"
      + "  \"public_gists\": 0,\n"
      + "  \"followers\": 4,\n"
      + "  \"following\": 25,\n"
      + "  \"created_at\": \"2015-04-18T14:48:41Z\",\n"
      + "  \"updated_at\": \"2017-01-15T19:08:30Z\"\n"
      + "}";

  private static final String MALFORMED_RESPONSE = ""
      + "<!DOCTYPE html>\n"
      + "<html>\n"
      + "<body>\n"
      + "\n"
      + "<h1>Invalid Response</h1>\n"
      + "\n"
      + "</body>\n"
      + "</html>";

  private ServerResponse() {
  }

  public static String buildFor(String user, String id) {
    return String.format(VALID_RESPONSE_TEMPLATE, user, id);
  }

  static String getMalformedResponse() {
    return MALFORMED_RESPONSE;
  }
}
