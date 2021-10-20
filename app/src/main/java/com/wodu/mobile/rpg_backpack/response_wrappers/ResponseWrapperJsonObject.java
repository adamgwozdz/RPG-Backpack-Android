package com.wodu.mobile.rpg_backpack.response_wrappers;

import com.google.gson.JsonObject;

public class ResponseWrapperJsonObject extends ResponseWrapper {

    private JsonObject body;

    public ResponseWrapperJsonObject(JsonObject body, String errorMessage) {
        super(errorMessage);
        this.body = body;
    }

    public JsonObject getBody() {
        return body;
    }

    public void setBody(JsonObject body) {
        this.body = body;
    }
}
