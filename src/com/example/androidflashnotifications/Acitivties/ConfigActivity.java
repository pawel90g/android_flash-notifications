package com.example.androidflashnotifications.Acitivties;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.androidflashnotifications.Others.AccelerometerService;
import com.example.androidflashnotifications.R;
import org.w3c.dom.Text;

public class ConfigActivity extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.config_layout);

        finish();
    }
}