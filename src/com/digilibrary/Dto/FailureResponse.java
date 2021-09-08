package com.digilibrary.Dto;

public class FailureResponse {
    public String failureMessage;

    public FailureResponse(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
}
