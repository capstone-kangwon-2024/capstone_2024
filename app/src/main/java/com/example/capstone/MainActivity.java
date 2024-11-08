package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
=======
import android.widget.Button;
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * MainActivity 클래스는 애플리케이션의 메인 화면을 나타내는 클래스입니다.
 * 사용자 인터페이스(UI) 구성 및 다른 활동으로의 전환을 처리합니다.
 */
public class MainActivity extends AppCompatActivity {
    public static String ip_txt = "http://192.168.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 부모 클래스의 onCreate 메서드 호출
        setContentView(R.layout.activity_main); // activity_main 레이아웃을 사용하여 UI 설정

<<<<<<< HEAD
        Button button1 = findViewById(R.id.button1);
        EditText IP_Text = findViewById(R.id.IP_Text);
        Button IP_Text_Enter = findViewById(R.id.IP_Text_Enter);

        IP_Text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode){
                    case KeyEvent.KEYCODE_ENTER:
                        IP_Text_Enter.callOnClick();
                }
                return false;
            }
        });
        IP_Text_Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = IP_Text.getText().toString();
                ip_txt = "http://192.168.";

                if(input.charAt(input.length()-1) == '.') {
                    ip_txt = input.substring(0, input.length()-1);
                    Toast.makeText(MainActivity.this, "변경 : " + ip_txt, Toast.LENGTH_SHORT).show();
                } else {
                    ip_txt += input;
                    ip_txt += ":5000";
                }

            }
        });


        button1.setOnClickListener(v -> openCCTVActivity());
    }

    private void openCCTVActivity() {
        Intent intent = new Intent(MainActivity.this, CCTVActivity.class);

        if(ip_txt.equals("http://192.168.")) ip_txt += "0.24:5000";

        startActivity(intent);
=======
        // BUTTON 초기화
        Button button1 = findViewById(R.id.button1); // XML 레이아웃에서 button1을 찾습니다.

        // 버튼에 클릭 리스너 설정
        // 사용자가 버튼을 클릭했을 때 CCTVActivity(실시간 조종 화면)를 열도록 합니다.
        button1.setOnClickListener(v -> openCCTVActivity());
    }

    /**
     * openCCTVActivity 메서드는 CCTVActivity를 열기 위해 호출됩니다.
     * Intent 객체를 사용하여 현재 activity(MainActivity)에서 CCTVActivity로 전환합니다.
     */
    private void openCCTVActivity() {
        // 새로운 Intent 생성: 현재의 MainActivity에서 CCTVActivity로 전환하기 위한 Intent
        Intent intent = new Intent(MainActivity.this, CCTVActivity.class);
        startActivity(intent); // 새 액티비티(CCTVActivity)를 시작합니다.
>>>>>>> 889eefcc773f153806ef8d4a0eb31f72b629b303
    }
}