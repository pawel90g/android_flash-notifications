package com.example.androidflashnotifications.Others;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeService {

    private static TimeService instance;
    private PreferencesHelper prefs;

    private TimeService(Context context) {
        prefs = PreferencesHelper.getInstance(context);
    }

    public static TimeService getInstance(Context context) {
        if (instance == null)
            instance = new TimeService(context);

        return instance;
    }

    public Boolean isTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        String now = sdf.format(new Date());

        String startTime = prefs.load("start_time");
        String endTime = prefs.load("end_time");

        if(startTime.compareTo(endTime) > 0)
            if(now.compareTo(startTime) > 0 || now.compareTo(endTime) < 0)
                return true;

        if(startTime.compareTo(endTime) == 0)
            if(now.compareTo(startTime) == 0 && now.compareTo(endTime) == 0)
                return true;

        if(startTime.compareTo(endTime) < 0)
            if(now.compareTo(startTime) > 0 && now.compareTo(endTime) < 0)
                return true;

        return false;
    }
}
