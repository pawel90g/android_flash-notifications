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

    private Button startTimeBtn;
    private Button endTimeBtn;

    private View.OnClickListener endTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment newFragment = new TimePickerFragment(prefs, "end_time");
            newFragment.show(getFragmentManager(), "timePicker");
        }
    };
    private View.OnClickListener startTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment newFragment = new TimePickerFragment(prefs, "start_time");
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
        }
    };
    private CompoundButton.OnCheckedChangeListener smsCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            prefs.save(String.valueOf(smsSwitch.isChecked()), "sms_switch");

            dependsOnSmsSwitch();
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


        startTimeBtn = (Button) findViewById(R.id.start_time_btn);
        startTimeBtn.setOnClickListener(startTimeClickListener);

        endTimeBtn = (Button) findViewById(R.id.end_time_btn);
        endTimeBtn.setOnClickListener(endTimeClickListener);
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
        } else {
            callsSwitch.setEnabled(false);
            smsSwitch.setEnabled(false);
            timeSwitch.setEnabled(false);
            accelerometerSwitch.setEnabled(false);

            callsBlinksET.setEnabled(false);
            smsBlinksET.setEnabled(false);

            startTimeBtn.setEnabled(false);
            endTimeBtn.setEnabled(false);
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

    public class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private PreferencesHelper prefs;
        private String caller;

        public TimePickerFragment(PreferencesHelper prefs, String caller) {
            this.prefs = prefs;
            this.caller = caller;
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
            prefs.save(hourOfDay+minute+"00", caller);
        }
    }
}