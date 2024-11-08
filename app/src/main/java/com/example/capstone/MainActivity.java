package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    public static String ip_txt = "http://192.168.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}