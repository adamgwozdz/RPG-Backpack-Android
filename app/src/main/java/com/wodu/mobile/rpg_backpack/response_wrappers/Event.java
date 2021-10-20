package com.wodu.mobile.rpg_backpack.response_wrappers;

import androidx.annotation.Nullable;

public class Event<T> {

    private T content;

    private boolean hasBeenHandled = false;


    public Event( T content) {
        if (content == null) {
            throw new IllegalArgumentException("null values in Event are not allowed.");
        }
        this.content = content;
    }

    /**
     * Can be called only once!!!
     * @return Content wrapped in Event class
     */
    @Nullable
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }

    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }
}
