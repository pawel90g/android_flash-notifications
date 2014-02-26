package com.example.androidflashnotifications.Others;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    private static Boolean ring = false;
    private static Boolean callReceived = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        // check for incoming sms
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            FlashlightService.getInstance(context).unreadSmsNotification();
        } else {

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
