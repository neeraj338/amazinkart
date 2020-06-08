package com.amazinkart.exception;

public class DownstreamTimeoutException extends RuntimeException {

  public DownstreamTimeoutException(String message) {
    super(message);
  }
}
