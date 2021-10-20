package com.wodu.mobile.rpg_backpack.response_wrappers;

import com.google.gson.JsonObject;

public class ResponseWrapper {

    private String errorMessage;

    public ResponseWrapper(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
