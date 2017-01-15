package com.vitornovictor.mockwebserverdemo;

public class GitHubUser {
  private String login;
  private String id;

  public GitHubUser() {
  }

  public GitHubUser(String login, String id) {
    this.login = login;
    this.id = id;
  }

  @Override
  public String toString() {
    return this.login + ":" + this.id;
  }
}