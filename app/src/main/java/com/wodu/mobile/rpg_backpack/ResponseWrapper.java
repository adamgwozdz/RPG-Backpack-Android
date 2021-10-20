package com.wodu.mobile.rpg_backpack;

import com.google.gson.JsonObject;

public class ResponseWrapper {

    private JsonObject body;
    private String errorMessage;

    public ResponseWrapper(JsonObject body, String errorMessage) {
        this.body = body;
        this.errorMessage = errorMessage;
    }

    public JsonObject getBody() {
        return body;
    }

    public void setBody(JsonObject body) {
        this.body = body;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
