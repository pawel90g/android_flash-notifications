package com.example.androidflashnotifications.Others;

import android.content.Context;
import android.widget.Toast;

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

        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        int now = Integer.parseInt(sdf.format(new Date()));

        int startTime = Integer.parseInt(prefs.load("start_time"));
        int endTime = Integer.parseInt(prefs.load("end_time"));

        if (startTime < endTime) {
            if (startTime < now && now < endTime) {
                return true;
            } else {
                return false;
            }
        }

        if (startTime == endTime) {
            if (startTime == now) {
                return true;
            } else {
                return false;
            }
        }

        if (startTime > endTime) {
            if (startTime > now || now > endTime) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
