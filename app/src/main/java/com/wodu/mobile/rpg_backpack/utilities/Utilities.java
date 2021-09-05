package com.wodu.mobile.rpg_backpack.utilities;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class Utilities {

    @Nullable
    public static Timestamp convertToTimestamp(String dateString) {
        if (dateString != "null") {
            Date parsedDate = null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                parsedDate = dateFormat.parse(dateString.replace('T', ' '));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new java.sql.Timestamp(parsedDate.getTime());
        } else
            return null;
    }


    public static String jsonResponseStringToString(String jsonResponseString) {
        return jsonResponseString.replaceAll("\"", "");
    }
}
