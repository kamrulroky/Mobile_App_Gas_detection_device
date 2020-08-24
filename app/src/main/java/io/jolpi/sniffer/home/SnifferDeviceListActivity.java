package io.jolpi.sniffer.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.jolpi.sniffer.R;

public class SnifferDeviceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sniffer_device_list);
        setTitle("Sniffer IoT Device List");
    }
}
