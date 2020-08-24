package io.jolpi.sniffer.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.jolpi.sniffer.R;
import io.jolpi.sniffer.home.view.HomeActivity;

public class JolpiDeviceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jolpi_device_list);
        setTitle("Jolpi Device List");
    }

    public void onSnifferIoTButtonClicked(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
