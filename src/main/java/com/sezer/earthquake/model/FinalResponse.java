package com.sezer.earthquake.model;

import java.util.List;

public class FinalResponse {

    String status;

    String message;

    List<Data> responses;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getResponses() {
        return responses;
    }

    public void setResponses(List<Data> responses) {
        this.responses = responses;
    }
}
