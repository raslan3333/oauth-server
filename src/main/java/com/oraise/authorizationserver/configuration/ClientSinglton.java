package com.oraise.authorizationserver.configuration;

public class ClientSinglton {
  public String url;
  private static ClientSinglton ourInstance = new ClientSinglton();

  public static ClientSinglton getInstance() {
    return ourInstance;
  }

  private ClientSinglton() {
  }
}
