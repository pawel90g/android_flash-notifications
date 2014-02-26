package com.example.androidflashnotifications.Others;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeService {
    private String startTime = "080000";
    private String stopTime = "230000";

    public TimeService() { }

    public Boolean isTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        String now = sdf.format(new Date());

        if(now.compareTo(startTime) > 0 && now.compareTo(stopTime) < 0)
            return true;

        return false;
    }
}
