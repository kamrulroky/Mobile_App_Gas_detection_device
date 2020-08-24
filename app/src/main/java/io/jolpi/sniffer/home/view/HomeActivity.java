package io.jolpi.sniffer.home.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.jolpi.sniffer.BadgeDrawable;
import io.jolpi.sniffer.R;
import io.jolpi.sniffer.custom_view.GaugeView;
import io.jolpi.sniffer.custom_view.Thermometer;
import io.jolpi.sniffer.home.SnifferDeviceListActivity;
import io.jolpi.sniffer.mqtt.MqttHelper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GaugeView mGaugeView1;
   // private GaugeView mGaugeView2;
    private Thermometer thermometer;
    private TextView mTvDataTransferRate;
    private TextView mTvHumidity;
    private TextView mTvTemperature;
    private TextView mTvGasData;

    private RelativeLayout mLayoutHumiditySingle;
    private RelativeLayout mLayoutHumidityDouble;
    private RelativeLayout mLayoutHumidityTriple;

    private float temperature;
    private int gas;
    private int humidity;
    private int dataTransferRate;
    private Timer timer;
    private final Random RAND = new Random();

    MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupViews();
        mGaugeView1.setTargetValue(0);

        startMqtt();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        MenuItem itemCart = menu.findItem(R.id.action_notification);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, "9");

        //return true;

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_device_list) {
            startActivity(new Intent(this, SnifferDeviceListActivity.class));
        }
// else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //simulateDashboardValues();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // unregisterAll();
    }

    //--------------------------
    // functions
    //--------------------------

    private void setupViews() {
        mGaugeView1 = (GaugeView) findViewById(R.id.gauge_view1);
        //mGaugeView2 = (GaugeView) findViewById(R.id.gauge_view2);
        thermometer = (Thermometer) findViewById(R.id.thermometer);
        mTvDataTransferRate = (TextView) findViewById(R.id.tv_data_transfer);
        mTvHumidity = (TextView) findViewById(R.id.tv_humidity);
        mTvTemperature = (TextView) findViewById(R.id.tv_temp_value);
        mTvGasData = (TextView) findViewById(R.id.tv_gas_data);
        mLayoutHumiditySingle = (RelativeLayout) findViewById(R.id.layout_humidity_single);
        mLayoutHumidityDouble = (RelativeLayout) findViewById(R.id.layout_humidity_double);
        mLayoutHumidityTriple = (RelativeLayout) findViewById(R.id.layout_humidity_triple);

        //mTimer.start();

    }


//
//    private final CountDownTimer mTimer = new CountDownTimer(60000, 3000) {
//
//        @Override
//        public void onTick(final long millisUntilFinished) {
//            mGaugeView1.setTargetValue(RAND.nextInt(101));
//            mGaugeView2.setTargetValue(RAND.nextInt(101));
//        }
//
//        @Override
//        public void onFinish() {}
//    };

    private void simulateDashboardValues() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                //temperature =  13; //Utils.randInt(-10, 35);

                int minTemp = 0;
                int maxTemp = 60;

                int minGas = 0;
                int maxGas = 120;

                int minHumidity = 0;
                int maxHumidity = 100;

                int minTransfer = 0;
                int maxTransfer = 512;

                Random r = new Random();
                temperature = r.nextInt(maxTemp - minTemp + 1) + minTemp;
                gas = r.nextInt(maxGas - minGas + 1) + minGas;
                humidity = r.nextInt(maxHumidity - minHumidity + 1) + minHumidity;
                dataTransferRate = r.nextInt(maxTransfer - minTransfer + 1) + minTransfer;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        thermometer.setCurrentTemp(temperature);
                        mTvTemperature.setText(String.valueOf(Math.round(temperature)) + "°"+"c");
                        mGaugeView1.setTargetValue(gas);
                        mTvDataTransferRate.setText(String.valueOf(dataTransferRate) + " kb/s");
                        mTvHumidity.setText(String.valueOf(humidity)+"%");
                        if (humidity < 34) {
                            mLayoutHumiditySingle.setVisibility(View.VISIBLE);
                            mLayoutHumidityDouble.setVisibility(View.GONE);
                            mLayoutHumidityTriple.setVisibility(View.GONE);
                        } else if (humidity >= 34 && humidity < 66) {
                            mLayoutHumiditySingle.setVisibility(View.GONE);
                            mLayoutHumidityDouble.setVisibility(View.VISIBLE);
                            mLayoutHumidityTriple.setVisibility(View.GONE);
                        } else  {
                            mLayoutHumiditySingle.setVisibility(View.GONE);
                            mLayoutHumidityDouble.setVisibility(View.GONE);
                            mLayoutHumidityTriple.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }, 0, 3500);
    }

    private void unregisterAll() {
        timer.cancel();
    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.e("Mqtt_Debug",topic + ": "+mqttMessage.toString());
                processingDeviceData(mqttMessage.toString());
                //String gasReading = mqttMessage.toString();
                //Integer gasReadingInt = Integer.parseInt(gasReading);
               // mGaugeView1.setTargetValue(gasReadingInt);

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    private void processingDeviceData(String deviceDataStr) {
        try {
            JSONObject jsonObj = new JSONObject(deviceDataStr);
            String deviceID = jsonObj.getString("DeviceID");
            String gas = jsonObj.getString("GAS");
            String humidity = jsonObj.getString("Humidity");
            String temperature = jsonObj.getString("Temperature");

            Log.e("DeviceId", deviceID);
            Log.e("gas", gas);
            Log.e("humidity", humidity);
            Log.e("temperature", temperature);

           final Float temp = Float.parseFloat(temperature);
            final Integer gasInt = Integer.parseInt(gas);
            final Float hum = Float.parseFloat(humidity);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    thermometer.setCurrentTemp(temp);
                    mTvTemperature.setText(String.valueOf(Math.round(temp)) + "°"+"c");
                    mGaugeView1.setTargetValue(gasInt);
                    mTvGasData.setText(String.valueOf(gasInt) + "ppm");
                    mTvDataTransferRate.setText(String.valueOf(64) + " kb/s");
                    mTvHumidity.setText(String.valueOf(hum)+"%");
                    if (hum < 34) {
                        mLayoutHumiditySingle.setVisibility(View.VISIBLE);
                        mLayoutHumidityDouble.setVisibility(View.GONE);
                        mLayoutHumidityTriple.setVisibility(View.GONE);
                    } else if (hum >= 34 && hum < 66) {
                        mLayoutHumiditySingle.setVisibility(View.GONE);
                        mLayoutHumidityDouble.setVisibility(View.VISIBLE);
                        mLayoutHumidityTriple.setVisibility(View.GONE);
                    } else  {
                        mLayoutHumiditySingle.setVisibility(View.GONE);
                        mLayoutHumidityDouble.setVisibility(View.GONE);
                        mLayoutHumidityTriple.setVisibility(View.VISIBLE);
                    }
                }
            });

        } catch (final JSONException e) {
        }
    }


}
