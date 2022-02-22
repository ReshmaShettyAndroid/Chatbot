package com.example.chatbot.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CommonUtils {
    private String format = "dd-MM-yyyy  h:m a";


    public String getDate() {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.format(Calendar.getInstance().getTime());
    }

}
