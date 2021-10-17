package com.wodu.mobile.rpg_backpack.utilities;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

    //TODO Get timezone from user's device
    public static String userFriendlyTimestamp(Timestamp timestamp) {
        long milliseconds = timestamp.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(milliseconds));
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        return simpleDateFormat.format(calendar.getTime());
    }


    public static String jsonResponseStringToString(String jsonResponseString) {
        return jsonResponseString.replaceAll("\"", "");
    }
}
