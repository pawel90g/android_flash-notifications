package com.example.androidflashnotifications.Others;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private static String PREFS_NAME = "android_flash_notifications.data";
    private static PreferencesHelper instance;
    private SharedPreferences prefs;

    public PreferencesHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (load("notifications_switch").equals(""))
            save("false", "notifications_switch");
        if (load("calls_switch").equals(""))
            save("false", "calls_switch");
        if (load("sms_switch").equals(""))
            save("false", "sms_switch");
        if (load("time_switch").equals(""))
            save("false", "time_switch");
        if (load("accelerometer_switch").equals(""))
            save("false", "accelerometer_switch");

        if (load("calls_blinks").equals(""))
            save("2", "calls_blinks");
        if (load("sms_blinks").equals(""))
            save("1", "sms_blinks");
    }

    public static PreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesHelper(context);
        }
        return instance;
    }

    public void save(String value, String prefsName) {
        prefs.edit().putString(prefsName, value).commit();
    }

    public String load(String prefsName) {
        return prefs.getString(prefsName, "");
    }
}
