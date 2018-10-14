package com.test.weather.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Constants {

    public static final String API_KEY = "6e103aab3598145a543c52c5709162f6";
    public static final String URL = "http://api.openweathermap.org/data/2.5/weather?";

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static String convertTimeToDay(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSSS", Locale.getDefault());
        String days = "";
        try {
            Date date = format.parse(time + 1000);
            System.out.println("Our time " + date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.HOUR) +":"+calendar.get(Calendar.MINUTE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
}
