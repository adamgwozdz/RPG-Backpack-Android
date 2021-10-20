package com.wodu.mobile.rpg_backpack;

import androidx.annotation.Nullable;

public class Event<T> {

    private T mContent;

    private boolean hasBeenHandled = false;


    public Event( T content) {
        if (content == null) {
            throw new IllegalArgumentException("null values in Event are not allowed.");
        }
        mContent = content;
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
            return mContent;
        }
    }

    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }
}
