package com.mock.domain;

public class SocketResponse {

  public String responseMessage;

  public SocketResponse(String responseMessage) {
    this.responseMessage = responseMessage;
  }

  public String getResponseMessage() {
    return responseMessage;
  }

  public void setResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
  }
}
