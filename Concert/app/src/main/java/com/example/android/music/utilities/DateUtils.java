package com.example.android.music.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {

    private DateUtils(){
        // Default empty constructor
    }

    public static String makeDate(String concertDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(concertDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            return new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(date);
        } else {
            return "";
        }
    }

    public static String makeDay(String concertTime) {
        Date day = null;
        try {
            day = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(concertTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (day != null) {
            return new SimpleDateFormat("EEEE", Locale.US).format(day);
        } else {
            return "";
        }
    }
}
