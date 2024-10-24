package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity 클래스는 애플리케이션의 메인 화면을 나타내는 클래스입니다.
 * 사용자 인터페이스(UI) 구성 및 다른 활동으로의 전환을 처리합니다.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 부모 클래스의 onCreate 메서드 호출
        setContentView(R.layout.activity_main); // activity_main 레이아웃을 사용하여 UI 설정

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
    }
}