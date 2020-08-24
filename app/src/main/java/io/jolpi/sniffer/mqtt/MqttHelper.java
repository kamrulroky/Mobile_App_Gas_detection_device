package io.jolpi.sniffer.mqtt;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by nasser on 1/5/18.
 */

public class MqttHelper {
    public MqttAndroidClient mqttAndroidClient;

    final String serverUri = 	"tcp://mqtt.snifferiot.xyz:1883";//"tcp://m11.cloudmqtt.com:18965"; //"tcp://m14.cloudmqtt.com:17352";  //

    final String clientId = "AndroidClient";
    final String subscriptionTopicGas = "/gas_reading_gprs"; //"/gas_reading_gprs"; //"/test";//
    final String subscriptionTopicTemp = "/temp_reading_gprs"; //"/test2";//
    final String subscriptionTopicHumidity = "/hum_reading_gprs";

    final String username = "SnifferIoT";//"MQ4-GPRS"; //"jlfgeznh";//
    final String password = "AllahIs1";//"JolPi"; //"xlwERlTlqcy8";//

    public MqttHelper(Context context){
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        connect();
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopicGas();
                    //subscribeToTopicTemp();
                    //subscribeToTopicHumidity();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }


    private void subscribeToTopicGas() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopicGas, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Gas Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Gas Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Gas Exceptionst subscribing");
            ex.printStackTrace();
        }
    }

    private void subscribeToTopicTemp() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopicTemp, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Temp Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Temp Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Temp Exceptionst subscribing");
            ex.printStackTrace();
        }
    }

    private void subscribeToTopicHumidity() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopicHumidity, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Hum Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Hum Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Hum Exceptionst subscribing");
            ex.printStackTrace();
        }
    }
}
