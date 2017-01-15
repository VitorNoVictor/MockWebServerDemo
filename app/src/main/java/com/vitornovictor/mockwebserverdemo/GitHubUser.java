package com.vitornovictor.mockwebserverdemo;

class GitHubUser {
  private String login;
  private String id;

  GitHubUser(String login, String id) {
    this.login = login;
    this.id = id;
  }

  @Override
  public String toString() {
    return this.login + ":" + this.id;
  }
}