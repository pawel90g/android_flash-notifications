package com.example.androidflashnotifications.Others;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    private static Boolean ring = false;
    private static Boolean callReceived = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        PreferencesHelper prefs = PreferencesHelper.getInstance(context);
        if (prefs.load("notifications_switch").equals("true")) {

            Boolean isTime = true;
            if(prefs.load("time_switch").equals("true")) {
                isTime = TimeService.getInstance(context).isTime();
            }
            // check for incoming sms
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")
                    && prefs.load("sms_switch").equals("true") && isTime) {
                FlashlightService.getInstance(context).unreadSmsNotification();
            } else {

                if (prefs.load("calls_switch").equals("true") && isTime) {
                    // check for missed call
                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        ring = true;
                    }

                    if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                        callReceived = true;
                    }

                    if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        if (ring == true && callReceived == true) {
                            ring = false;
                            callReceived = false;

                            FlashlightService.getInstance(context).missedCallNotification();
                        }
                    }
                }
            }
        }
    }
}
