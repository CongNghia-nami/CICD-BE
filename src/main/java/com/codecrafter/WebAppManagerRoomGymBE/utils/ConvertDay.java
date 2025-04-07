package com.codecrafter.WebAppManagerRoomGymBE.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConvertDay {

    public static long ConvertCreateDateNotifyToTimestamp(String date) {
        // 12-07-2021 13:19:47
        SimpleDateFormat formatTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        Date timestamp = null;
        try {
            if (date.contains("-")) {
                timestamp = formatTime.parse(date);
            } else {
                return Long.valueOf(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestamp.getTime();
    }
}
