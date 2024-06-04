package com.jochemtb.gezinsgericht.API.Results;

public class ResultsRequest {

    private String sessionId;

    public ResultsRequest(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
