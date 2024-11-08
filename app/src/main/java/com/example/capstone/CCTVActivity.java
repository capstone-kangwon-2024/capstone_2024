package com.example.capstone;

import android.annotation.SuppressLint;
<<<<<<< HEAD
import android.graphics.Color;
=======
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
import android.os.Bundle;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.example.capstone.CCTVActivity;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
=======
import com.google.android.exoplayer2.ExoPlayer;

>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
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
<<<<<<< HEAD
import java.util.Set;
=======
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 이 클래스는 화면 라이브 송출을 보여주고, 실시간 조종을 하는 화면입니다.
 */
public class CCTVActivity extends AppCompatActivity {
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

<<<<<<< HEAD
    private Button btn1;
    private Button btn2;
    private Button btn3;
=======
    private Button btn_ball;
    private Button btn_feed;
    private Button btn_record;
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);


        // 라이브 조종에 필요한 버튼 정의
        btnUp = findViewById(R.id.button_up);
        btnDown = findViewById(R.id.button_down);
        btnLeft = findViewById(R.id.button_left);
        btnRight = findViewById(R.id.button_right);
<<<<<<< HEAD
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
=======
        btn_ball = findViewById(R.id.button1);
        btn_feed = findViewById(R.id.button2);
        btn_record = findViewById(R.id.button3);
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303

        modeButton = findViewById(R.id.button_mode);

        WebView webView = findViewById(R.id.player_view);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());
<<<<<<< HEAD
        webView.loadUrl(MainActivity.ip_txt);

        // MQTT
=======
        webView.loadUrl("http://192.168.0.5:5000");

        ////////////////////////////////////////////////////////////////////////////////////////////
        // MQTT를 사용해 로봇과 실시간 통신을 통해 로봇을 제어합니다. 이를 위해, 여러 세팅을 사전에 해놓은 코드입니다.
        // 아래 버튼 터치시 발생하는 이벤트 리스너 부분에 실제 버튼 클릭시 로봇에 메시지를 보내는 부분이 있습니다.
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
        try {
            // MQTT 클라이언트 객체를 생성합니다.
            // 1. 브로커의 주소: SSL을 통해 연결 (보안)
            // 2. 클라이언트 ID: 고유한 식별자를 생성
            // 3. MemoryPersistence: 메시지 상태를 메모리에 저장
            client = new MqttClient(
                    "ssl://1c15066522914e618d37acbb80809524.s1.eu.hivemq.cloud:8883", // MQTT 브로커의 주소
                    MqttClient.generateClientId(), // 클라이언트 ID 자동 생성
                    new MemoryPersistence()); // 메모리 기반의 메시지 저장소

            // 연결 옵션 설정
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName("tester"); // MQTT 브로커에 로그인할 사용자 이름
            mqttConnectOptions.setPassword("Test1234".toCharArray()); // 사용자 비밀번호 설정 (char 배열로 변환)
            mqttConnectOptions.setSocketFactory(SSLSocketFactory.getDefault()); // SSL 연결을 위한 소켓 팩토리 설정

            // MQTT 브로커에 연결
            client.connect(mqttConnectOptions);

            // MQTT 메시지 콜백 설정: 메시지를 수신하거나 연결 상태를 처리하는 메서드
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    // 연결이 끊어졌을 때 호출되는 메서드
                    Log.e(TAG, "Connection lost", cause); // 연결 손실 로그 출력
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    // 메시지가 도착했을 때 호출되는 메서드
                    Log.d(TAG, topic + ": " + Arrays.toString(message.getPayload())); // 수신한 메시지의 주제 및 내용 로그 출력
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // 메시지 전송이 완료되었을 때 호출되는 메서드
                    Log.d(TAG, "Delivery complete"); // 전송 완료 로그 출력
                }
            });

<<<<<<< HEAD
=======
            // 모든 주제에 대해 메시지를 구독합니다. ("#"는 모든 주제를 의미)
            // QoS 1: 메시지 전송이 최소 한 번 이루어지도록 보장합니다.
            client.subscribe("#", 1);

>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
        } catch (MqttException e) {
            // MQTT 브로커와 연결하는 과정에서 오류가 발생하면 아래 코드가 실행됩니다.
            Log.e(TAG, "Error connecting to MQTT broker", e); // 오류 발생 시 로그 출력
        }

        ////////////////////////////////////////////////////////////////////////////////////////////

        // HOME BUTTON
        // 이 버튼을 누르면 메인 화면으로 돌아갑니다.
        homeButton = findViewById(R.id.button_home);
        homeButton.setOnClickListener(v -> {
            finish();
        });


        /////////////////////////////////////////////////////////////////////////////////////////////
        // 아래 7개의 버튼 클릭시 발생하는 이벤트 리스너.
        // 버튼 클릭시 적절한 메시지를 로봇에게 보내 기능을 제어하기 위함
        // up, down, left, right, ball, feed, mode 총 6개의 버튼이 있음


        // 'Up' 버튼에 대한 터치시 메시지 보내기
        btnUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 버튼이 눌렸을 때 "Forward" 메시지를 전송하여 전방으로 이동 시작
                        publishMessage("Forward");
                        return true;
                    case MotionEvent.ACTION_UP:
                        // 버튼이 떼어졌을 때 "Stop" 메시지를 전송하여 이동 중단
                        publishMessage("Stop");
                        return true;
                    default:
                        return false; // 다른 이벤트는 무시
                }
            }
        });

        // 'Down' 버튼에 대한 터치시 메시지 보내기
        btnDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 버튼이 눌렸을 때 "Backward" 메시지를 전송하여 후방으로 이동 시작
                        publishMessage("Backward");
                        return true;
                    case MotionEvent.ACTION_UP:
                        // 버튼이 떼어졌을 때 "Stop" 메시지를 전송하여 이동 중단
                        publishMessage("Stop");
                        return true;
                    default:
                        return false; // 다른 이벤트는 무시
                }
            }
        });

        // 'Left' 버튼에 대한 터치시 메시지 보내기
        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 버튼이 눌렸을 때 "Left" 메시지를 전송하여 좌측으로 이동 시작
                        publishMessage("Left");
                        return true;
                    case MotionEvent.ACTION_UP:
                        // 버튼이 떼어졌을 때 "Stop" 메시지를 전송하여 이동 중단
                        publishMessage("Stop");
                        return true;
                    default:
                        return false; // 다른 이벤트는 무시
                }
            }
        });

        // 'Right' 버튼에 대한 터치시 메시지 보내기
        btnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 버튼이 눌렸을 때 "Right" 메시지를 전송하여 우측으로 이동 시작
                        publishMessage("Right");
                        return true;
                    case MotionEvent.ACTION_UP:
                        // 버튼이 떼어졌을 때 "Stop" 메시지를 전송하여 이동 중단
                        publishMessage("Stop");
                        return true;
                    default:
                        return false; // 다른 이벤트는 무시
                }
            }
        });

        // 'Ball' 버튼에 대한 클릭시 메시지 보내기
        btn_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                publishMessage("Fire"); Toast.makeText(CCTVActivity.this, "공을 발사합니다!", Toast.LENGTH_SHORT).show();
=======
                // 버튼 클릭 시 "Ball" 메시지를 전송하여 특정 행동을 수행하도록 요청
                publishMessage("Ball");
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
            }
        });

        // 'Feed' 버튼에 대한 클릭시 메시지 보내기
        btn_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                publishMessage("Feed"); Toast.makeText(CCTVActivity.this, "간식을 배급합니다!", Toast.LENGTH_SHORT).show();
=======
                // 버튼 클릭 시 "Feed" 메시지를 전송하여 먹이를 주도록 요청
                publishMessage("Feed");
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
            }
        });

        // 'Mode' 버튼에 대한 클릭시 메시지 보내기
        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                publishMessage("ChangeMode");
            }
        });

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "Connection lost", cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                String receivedMessage = new String(message.getPayload(), UTF_8);
                Log.d(TAG, topic + ": " + receivedMessage);

                if ("WARN".equals(receivedMessage)) {
                    Toast.makeText(CCTVActivity.this, "이상행동 탐지", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Detect abnormal behavior");
                }

                else {
                    // 다른 메시지를 받을 때 처리할 동작 추가
                    Toast.makeText(CCTVActivity.this, "공을 던질 수 없습니다. 사유 : " + receivedMessage, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Received CANT message from server");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.d(TAG, "Delivery complete");
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
                            btn3.setTextColor(Color.parseColor("#ff0000"));
                            isRecording = true;
                        } else {
                            publishMessage("Stop Recording");
                            v.setBackgroundResource(R.drawable.button_record_state);
                            Toast.makeText(CCTVActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
                            btn3.setTextColor(Color.parseColor("#ffffff"));
                            isRecording = false;
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        return true;
                }
                return false;
=======
                // 버튼 클릭 시 "mode" 메시지를 전송하여 모드 변경을 요청
                publishMessage("mode");
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
            }
        });

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "Connection lost", cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                String receivedMessage = new String(message.getPayload(), UTF_8);
                Log.d(TAG, topic + ": " + receivedMessage);

                // 메시지가 "CANT"일 경우 추가 동작
                if ("CANT".equals(receivedMessage)) {
                    // CANT 메시지를 받을 때 처리할 동작 추가
                    Toast.makeText(CCTVActivity.this, "현재 공을 던질 수 없습니다", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Received CANT message from server");
                    // 추가 작업을 여기에 넣으세요.
                }

                if ("WARN".equals(receivedMessage)) {
                    Toast.makeText(CCTVActivity.this, "이상행동 탐지", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Detect abnormal behavior");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.d(TAG, "Delivery complete");
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////

        ////

        // 이 함수 호출은 버튼 클릭시 영상 녹화 기능을 시작하기 위함
        touchRecordButton(btn_record);
    }

    /**
     * 주어진 버튼(btn3)에 터치 리스너를 설정하여 녹화 기능을 처리
     * 사용자가 버튼을 눌러 녹화 상태를 전환하고, 시각적 피드백을 제공
     *
     * @param btn3 터치 리스너가 적용될 버튼 (녹화 시작/정지를 위한 버튼)
     *
     * 주요 기능:
     * - 버튼을 눌렀을 때:
     *   - 녹화 시작 시:
     *     - MQTT 메시지: "Start Recording"
     *     - 배경 색상: **누를 때** 노란색
     *     - Toast 메시지: "Recording started"
     *     - 녹화 상태: **true**
     *   - 녹화 중지 시:
     *     - MQTT 메시지: "Stop Recording"
     *     - 배경 색상: **기본 상태** **#FFFFFF** (흰색)
     *     - Toast 메시지: "Recording stopped"
     *     - 녹화 상태: **false**
     *
     * 버튼 터치 이벤트 처리:
     * 1. ACTION_DOWN: 눌렸을 때 녹화 상태 전환 및 색상 변경
     * 2. ACTION_UP: 손을 떼었을 때 추가 처리 없음
     */
    private void touchRecordButton(Button btn3) {
        btn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Toggle the recording state
                        if (!isRecording) {
                            publishMessage("Start Recording"); // 로봇에게 메시지 보내는 부분
                            v.setBackgroundResource(R.drawable.button_record_state_pressed);
                            Toast.makeText(CCTVActivity.this, "Recording started", Toast.LENGTH_SHORT).show(); // 화면에 녹화 시작 메시지를 띄웁니다
                            isRecording = true;
                        } else {
                            publishMessage("Stop Recording"); // 로봇에세 메시지 보내는 부분
                            v.setBackgroundResource(R.drawable.button_record_state);
                            Toast.makeText(CCTVActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show(); // 화면에 녹화 종료 메시지를 띄웁니다.
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

<<<<<<< HEAD
    private void publishMessage(final String message) {
        executorService.execute(() -> {
            try {
                client.publish(
                        "control",
                        message.getBytes(UTF_8),
                        0,
                        false);
=======
    /**
     * 메시지를 로봇에게 전달하는 메시지 입니다. 넣어주시면 좋은 부분이 아래 표시되어 있습니다.
     * 위 버튼 클릭시 호출합니다.
     * 이 메서드는 전달받은 메시지를 MQTT 프로토콜을 통해 주제 "control/"에 전송합니다.
     *
     * @param message 전송할 메시지 문자열
     *
     * 특징
     * - 이 메서드는 executorService를 사용하여 비동기적으로 작동합니다.
     *   이는 메인 스레드를 차단하지 않고, 메시징 작업을 백그라운드에서 처리하기 위해 작성되었습니다.
     *   비동기 처리 기술을 활용하여 UI의 반응 속도를 유지하고 사용자 경험을 개선합니다.
     *   비동기 처리를 사용하지 않았을 때, 버튼 클릭 시 MQTT로 메시지를 전송하면
     *   메인 스레드가 블로킹되어 영상 재생이 멈추는 현상이 발생했습니다.
     *
     * *******이 부분은 비동기 처리를 통해 MQTT통신과 실시간 화면 송출간의 버벅거림 문제를 해결한 부분이므로 넣어주셨으면 좋겠습니다.******
     * 이유<< 비동기 처리를 활용한 에러 처리 >>
     * - MQTT 메시지를 전송하는 동안, 메인 스레드가 해당 작업을 처리하는데
     *   소요되는 시간 동안 UI가 업데이트되지 않기 때문입니다.
     * - 이로 인해 영상 프레임이 업데이트되지 않고, 사용자에게는 일시적인 '멈춤' 현상으로 보이게 됩니다.
     * - 비동기 처리를 사용하면 UI 스레드가 차단되지 않고 다른 작업을 동시에 처리할 수 있어
     *   사용자 경험을 개선하고, 매끄러운 인터페이스를 유지할 수 있게 됩니다.
     * - 메시지를 UTF-8 형식으로 인코딩하여 바이트 배열로 변환
     *
     *   ******************************************************************************************************************
     *
     * - publish() 메서드의 파라미터:
     *   1. "control/": MQTT 브로커에 메시지를 게시할 주제를 설정합니다.
     *      주제는 메시지를 그룹화하는 중요한 요소로, 수신자는 이 주제를 구독해야 해당 메시지를 받을 수 있습니다.
     *   2. message.getBytes(UTF_8): 전송할 메시지를 UTF-8 인코딩된 바이트 배열로 변환합니다. 이는
     *      MQTT 프로토콜이 바이트 형태로 데이터를 전송하기 때문에 필수적입니다.
     *   3. 2: Quality of Service (QoS) 레벨을 설정합니다. 여기서는 QoS 2를 사용하며, 이는 메시지가 '정확히 한 번' 전달되도록 보장합니다.
     *      높은 값의 QoS는 메커니즘이 추가되므로, 메시지 전송의 신뢰성을 높여주지만, 네트워크 대역폭과 지연이 증가할 수 있습니다.
     *   4. false: 메시지가 지속되어야 할지 여부를 지정합니다. false는 메시지가 브로커에 의해 저장되지 않음을 의미합니다.
     *      일반적으로 즉시 전송된 메시지이므로, 필요 시 true로 변경하여 브로커에 저장할 수 있습니다.
     *
     * - Log.d(TAG, "Message sent: " + message): 메시지를 성공적으로 전송한 후 로그에 기록
     *   이는 개발 과정에서 디버깅 및 메시지 전송 상태를 추적하는 데 유용합니다.
     *
     */
    private void publishMessage(final String message) {
        executorService.execute(() -> {
            try {
                // 메시지를 로봇에게 보냅니다.
                client.publish(
                        "control/",                         // 1. "control/": MQTT 브로커에 메시지를 게시할 주제를 설정합니다
                        message.getBytes(UTF_8),                  // 2. 전송할 메시지를 UTF-8 인코딩된 바이트 배열로 변환합니다.
                        2,                                        // 3. QoS 2를 사용
                        false);                                   // 4. false는 메시지는 중간 서버인 브로커에 저장하지 않음
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
                Log.d(TAG, "Message sent: " + message);
            } catch (MqttException e) {
                Log.e(TAG, "Error sending message", e);
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
        executorService.shutdown();
    }
}