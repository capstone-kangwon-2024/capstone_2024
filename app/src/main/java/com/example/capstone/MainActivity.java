package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener(v -> openCCTVActivity("rtsp://210.99.70.120:1935/live/cctv001.stream"));
    }

    private void openCCTVActivity(String url) {
        Intent intent = new Intent(MainActivity.this, CCTVActivity.class);
        intent.putExtra("STREAM_URL", url);
        startActivity(intent);
    }
}