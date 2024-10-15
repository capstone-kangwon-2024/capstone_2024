package com.example.capstone;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import javax.net.ssl.SSLSocketFactory;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CCTVActivity extends AppCompatActivity {
    private ExoPlayer player;
    private static final String TAG = "MainActivity";
    private MqttClient client;

    private boolean isRecording = false;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Button homeButton;
    private Button modeButton;

    private Button btnUp;
    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;

    private Button btn1;
    private Button btn2;
    private Button btn3;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);


        btnUp = findViewById(R.id.button_up);
        btnDown = findViewById(R.id.button_down);
        btnLeft = findViewById(R.id.button_left);
        btnRight = findViewById(R.id.button_right);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);

        modeButton = findViewById(R.id.button_mode);

        WebView webView = findViewById(R.id.player_view);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.0.5:5000");

        // MQTT
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

        // HOME BUTTON
        homeButton = findViewById(R.id.button_home);
        homeButton.setOnClickListener(v -> {
            player.stop();
            player.release();
            finish();
        });

        //////////

        btnUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        publishMessage("Forward");
                        return true;
                    case MotionEvent.ACTION_UP:
                        publishMessage("Stop");
                        return true;

                    default:
                        return false;
                }
            }
        });
        btnDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        publishMessage("Backward");
                        return true;

                    case MotionEvent.ACTION_UP:
                        publishMessage("Stop");
                        return true;

                    default:
                        return false;
                }
            }
        });
        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        publishMessage("Left");
                        return true;

                    case MotionEvent.ACTION_UP:
                        publishMessage("Stop");
                        return true;

                    default:
                        return false;
                }
            }
        });
        btnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        publishMessage("Right");
                        return true;

                    case MotionEvent.ACTION_UP:
                        publishMessage("Stop");
                        return true;

                    default:
                        return false;
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMessage("Ball");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMessage("Feed");
            }
        });

        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMessage("mode");
            }
        });

        touchRecordButton(btn3);

        //////////////////////////////


    }

    private void touchRecordButton(Button btn3) {
        btn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Toggle the recording state
                        if (!isRecording) {
                            publishMessage("Start Recording");
                            v.setBackgroundResource(R.drawable.button_record_state_pressed);
                            Toast.makeText(CCTVActivity.this, "Recording started", Toast.LENGTH_SHORT).show();
                            isRecording = true;
                        } else {
                            publishMessage("Stop Recording");
                            v.setBackgroundResource(R.drawable.button_record_state);
                            Toast.makeText(CCTVActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
                            isRecording = false;
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        return true;
                }
                return false;
            }
        });
    }

    private void publishMessage(final String message) {
        executorService.execute(() -> {
            try {
                client.publish(
                        "control/",
                        message.getBytes(UTF_8),
                        2,
                        false);
                Log.d(TAG, "Message sent: " + message);
            } catch (MqttException e) {
                Log.e(TAG, "Error sending message", e);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        if (client != null && client.isConnected()) {
            try {
                client.disconnect();
            } catch (MqttException e) {
                Log.e(TAG, "Error disconnecting from MQTT broker", e);
            }
        }
        executorService.shutdown();
    }
}