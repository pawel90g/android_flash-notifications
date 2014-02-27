package com.example.androidflashnotifications.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.example.androidflashnotifications.Others.BroadcastReceiver;

public class FlashNotificationService extends Service {

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver();
    IntentFilter intentFilter;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(broadcastReceiver, intentFilter);
        return START_STICKY;
    }
}