package com.example.capstone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;

import javax.net.ssl.SSLSocketFactory;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private static final String TAG = "MainActivity";
    private MqttClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button_send);

        try {
            client = new MqttClient(
                    "ssl://1c15066522914e618d37acbb80809524.s1.eu.hivemq.cloud:8883",
                    MqttClient.generateClientId(),
                    new MemoryPersistence());

            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName("tester");
            mqttConnectOptions.setPassword("Test1234".toCharArray());
            mqttConnectOptions.setSocketFactory(SSLSocketFactory.getDefault());
            client.connect(mqttConnectOptions);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.e(TAG, "Connection lost", cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    Log.d(TAG, topic + ": " + Arrays.toString(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery complete");
                }
            });

            client.subscribe("#", 1);

        } catch (MqttException e) {
            Log.e(TAG, "Error connecting to MQTT broker", e);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    client.publish(
                            "testtopic/",
                            "hello".getBytes(UTF_8),
                            2,
                            false);
                    Log.d(TAG, "Message sent: hello");
                } catch (MqttException e) {
                    Log.e(TAG, "Error sending message", e);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null && client.isConnected()) {
            try {
                client.disconnect();
            } catch (MqttException e) {
                Log.e(TAG, "Error disconnecting from MQTT broker", e);
            }
        }
    }
}