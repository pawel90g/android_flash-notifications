package com.example.androidflashnotifications.Acitivties;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import com.example.androidflashnotifications.Others.PreferencesHelper;
import com.example.androidflashnotifications.R;

import java.util.Calendar;

public class ConfigActivity extends Activity {

    private PreferencesHelper prefs;
    private Switch notificationsSwitch;
    private Switch callsSwitch;
    private Switch smsSwitch;
    private Switch timeSwitch;
    private Switch accelerometerSwitch;
    private EditText callsBlinksET;
    private EditText smsBlinksET;
    private EditText blinkIntervalET;
    private Button startTimeBtn;
    private Button endTimeBtn;
    private TextView startTimeTV;
    private View.OnClickListener startTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment newFragment = new TimePickerFragment(prefs, "start_time", startTimeTV);
            newFragment.show(getFragmentManager(), "timePicker");
        }
    };
    private TextView endTimeTV;
    private View.OnClickListener endTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment newFragment = new TimePickerFragment(prefs, "end_time", endTimeTV);
            newFragment.show(getFragmentManager(), "timePicker");
        }
    };
    private CompoundButton.OnCheckedChangeListener notificationsCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            prefs.save(String.valueOf(notificationsSwitch.isChecked()), "notifications_switch");

            dependsOnNotificationSwitch();
        }
    };
    private CompoundButton.OnCheckedChangeListener callsCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            prefs.save(String.valueOf(callsSwitch.isChecked()), "calls_switch");

            dependsOnCallsSwitch();
            blinkInterval();
        }
    };
    private CompoundButton.OnCheckedChangeListener smsCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            prefs.save(String.valueOf(smsSwitch.isChecked()), "sms_switch");

            dependsOnSmsSwitch();
            blinkInterval();
        }
    };
    private CompoundButton.OnCheckedChangeListener timeCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            prefs.save(String.valueOf(timeSwitch.isChecked()), "time_switch");

            dependsOnTimeSwitch();
        }
    };
    private CompoundButton.OnCheckedChangeListener accelerometerCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            prefs.save(String.valueOf(accelerometerSwitch.isChecked()), "accelerometer_switch");
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.config_layout);

        prefs = PreferencesHelper.getInstance(getApplicationContext());

        notificationsSwitch = (Switch) findViewById(R.id.notifications_switch);
        notificationsSwitch.setOnCheckedChangeListener(notificationsCheckedChangeListener);

        callsSwitch = (Switch) findViewById(R.id.calls_switch);
        callsSwitch.setOnCheckedChangeListener(callsCheckedChangeListener);

        smsSwitch = (Switch) findViewById(R.id.sms_switch);
        smsSwitch.setOnCheckedChangeListener(smsCheckedChangeListener);

        timeSwitch = (Switch) findViewById(R.id.time_switch);
        timeSwitch.setOnCheckedChangeListener(timeCheckedChangeListener);

        accelerometerSwitch = (Switch) findViewById(R.id.accelerometer_switch);
        accelerometerSwitch.setOnCheckedChangeListener(accelerometerCheckedChangeListener);


        callsBlinksET = (EditText) findViewById(R.id.calls_blinks_number);
        callsBlinksET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prefs.save(callsBlinksET.getText().toString(), "calls_blinks");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        smsBlinksET = (EditText) findViewById(R.id.sms_blinks_number);
        smsBlinksET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prefs.save(smsBlinksET.getText().toString(), "sms_blinks");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        blinkIntervalET = (EditText) findViewById(R.id.blink_interval);
        blinkIntervalET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prefs.save(blinkIntervalET.getText().toString(), "blink_interval");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        startTimeBtn = (Button) findViewById(R.id.start_time_btn);
        startTimeBtn.setOnClickListener(startTimeClickListener);

        endTimeBtn = (Button) findViewById(R.id.end_time_btn);
        endTimeBtn.setOnClickListener(endTimeClickListener);

        startTimeTV = (TextView) findViewById(R.id.start_time_tv);
        endTimeTV = (TextView) findViewById(R.id.end_time_tv);
    }

    @Override
    public void onResume() {
        super.onResume();

        loadConfig();
    }

    private void loadConfig() {
        if (prefs == null)
            prefs = PreferencesHelper.getInstance(getApplicationContext());

        notificationsSwitch.setChecked(Boolean.parseBoolean(prefs.load("notifications_switch")));
        callsSwitch.setChecked(Boolean.parseBoolean(prefs.load("calls_switch")));
        smsSwitch.setChecked(Boolean.parseBoolean(prefs.load("sms_switch")));
        timeSwitch.setChecked(Boolean.parseBoolean(prefs.load("time_switch")));
        accelerometerSwitch.setChecked(Boolean.parseBoolean(prefs.load("accelerometer_switch")));

        callsBlinksET.setText(prefs.load("calls_blinks"));
        smsBlinksET.setText(prefs.load("sms_blinks"));
        blinkIntervalET.setText(prefs.load("blink_interval"));

        String startTime = prefs.load("start_time");
        if (startTime.length() == 6) {
            startTime = startTime.substring(0, 4);
            startTime = startTime.substring(0, 2) + ":" + startTime.substring(2, 4);
        } else if (startTime.length() == 5) {
            startTime = startTime.substring(0, 3);
            startTime = startTime.substring(0, 1) + ":" + startTime.substring(1, 3);
        }
        startTimeTV.setText(startTime);

        String endTime = prefs.load("end_time");
        if (endTime.length() == 6) {
            endTime = endTime.substring(0, 4);
            endTime = endTime.substring(0, 2) + ":" + endTime.substring(2, 4);
        } else if (endTime.length() == 5) {
            endTime = endTime.substring(0, 3);
            endTime = endTime.substring(0, 1) + ":" + endTime.substring(1, 3);
        }
        endTimeTV.setText(endTime);

        dependsOnNotificationSwitch();
    }

    private void dependsOnNotificationSwitch() {
        if (notificationsSwitch.isChecked()) {
            callsSwitch.setEnabled(true);
            smsSwitch.setEnabled(true);
            timeSwitch.setEnabled(true);
            accelerometerSwitch.setEnabled(true);

            dependsOnCallsSwitch();
            dependsOnSmsSwitch();
            dependsOnTimeSwitch();
            blinkInterval();
        } else {
            callsSwitch.setEnabled(false);
            smsSwitch.setEnabled(false);
            timeSwitch.setEnabled(false);
            accelerometerSwitch.setEnabled(false);

            callsBlinksET.setEnabled(false);
            smsBlinksET.setEnabled(false);

            startTimeBtn.setEnabled(false);
            endTimeBtn.setEnabled(false);

            blinkIntervalET.setEnabled(false);
        }
    }

    private void dependsOnSmsSwitch() {
        if (smsSwitch.isChecked())
            smsBlinksET.setEnabled(true);
        else
            smsBlinksET.setEnabled(false);
    }

    private void dependsOnCallsSwitch() {
        if (callsSwitch.isChecked())
            callsBlinksET.setEnabled(true);
        else
            callsBlinksET.setEnabled(false);
    }

    private void dependsOnTimeSwitch() {
        if (timeSwitch.isChecked()) {
            startTimeBtn.setEnabled(true);
            endTimeBtn.setEnabled(true);
        } else {
            startTimeBtn.setEnabled(false);
            endTimeBtn.setEnabled(false);
        }
    }

    private void blinkInterval() {
        if (smsSwitch.isChecked() ||
                callsSwitch.isChecked())
            blinkIntervalET.setEnabled(true);
        else
            blinkIntervalET.setEnabled(false);
    }

    public class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private PreferencesHelper prefs;
        private String caller;
        private TextView textView;

        public TimePickerFragment(PreferencesHelper prefs, String caller, TextView tv) {
            this.prefs = prefs;
            this.caller = caller;
            this.textView = tv;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String minutes = String.valueOf(minute);
            if (minute < 10)
                minutes = "0" + String.valueOf(minute);

            prefs.save(String.valueOf(hourOfDay) + minutes + "00", caller);
            textView.setText(String.valueOf(hourOfDay) + ":" + minutes);
        }
    }
}