package com.example.androidflashnotifications.Others;

import android.content.Context;
import android.database.Cursor;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Handler;
import android.provider.CallLog;
import android.widget.Toast;

public class FlashlightService {

    private Context context;
    private Camera camera;
    private Cursor cursor;

    private Runnable runnable;

    private Handler handler;

    private static FlashlightService instance;

    public static FlashlightService getInstance(Context context) {
        if (instance == null)
            instance = new FlashlightService(context);

        return instance;
    }

    private FlashlightService(Context context) {
        this.context = context;

        camera = Camera.open();
        handler = new Handler();
    }

    private void makeFlash(int flashesNumber) {

        for (int i = 0; i < flashesNumber; i++) {
            Camera.Parameters p = camera.getParameters();

            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();

            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
        }
    }

    public void unreadSmsNotification() {

        final Uri smsContent = Uri.parse("content://sms/inbox");
        final String where = "read = 0";

        instance.runnable = new Runnable() {
            @Override
            public void run() {
                cursor = context.getContentResolver().query(smsContent, null, where, null, null);
                cursor.moveToFirst();
                int unreaded = cursor.getCount();
                cursor.deactivate();

                if (unreaded > 0)
                    instance.makeFlash(1);

                instance.handler.postDelayed(runnable, 5000);
            }
        };
        instance.handler.post(runnable);
    }


    public void missedCallNotification() {

        final String[] projection2 = {CallLog.Calls.CACHED_NAME, CallLog.Calls.CACHED_NUMBER_LABEL, CallLog.Calls.TYPE};
        final String where = CallLog.Calls.TYPE + "=" + CallLog.Calls.MISSED_TYPE + " AND " + CallLog.Calls.NEW + "=1";

        runnable = new Runnable() {
            @Override
            public void run() {

                cursor = context.getContentResolver().query(Uri.parse("content://call_log/calls"), projection2, where, null, null);
                int missedCalls = cursor.getCount();

                if (missedCalls > 0)
                    instance.makeFlash(2);

                instance.handler.postDelayed(runnable, 5000);
            }
        };
        instance.handler.post(runnable);
    }

    public void multipleNotification() {

    }
}