package com.example.ui123;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.ui123.R;
import com.example.ui123.ui1;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener(){ //OnTouchListener 클래스 내용
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, ui1.class);
                startActivity(intent1);
                }
        });


    }
}