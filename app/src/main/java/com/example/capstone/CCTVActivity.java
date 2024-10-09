package com.example.capstone;

import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import android.util.Log;
import android.view.View;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import javax.net.ssl.SSLSocketFactory;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Arrays;


public class CCTVActivity extends AppCompatActivity {
    private ExoPlayer player;
    private static final String TAG = "MainActivity";
    private MqttClient client;

    private Button homeButton;
    private Button modeButton;

    private Button btnUp;
    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;

    private Button btn1;
    private Button btn2;

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

        modeButton = findViewById(R.id.button_mode);

        StyledPlayerView playerView = findViewById(R.id.player_view);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        String streamUrl = getIntent().getStringExtra("STREAM_URL");
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(streamUrl));

        RtspMediaSource.Factory mediaSourceFactory = new RtspMediaSource.Factory();
        RtspMediaSource mediaSource = mediaSourceFactory.createMediaSource(mediaItem);

        player.setMediaSource(mediaSource);
        player.prepare();
        player.setPlayWhenReady(true);

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
    }

    private void publishMessage(String message) {
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
    }
}