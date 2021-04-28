package com.example.ui123;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ui2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_2);

        Button next2 = findViewById(R.id.next_button2);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ui2.this, Musicselect.class); // next버튼 누르면 ui2activity 시작됨.
                startActivity(intent);
            }
        });
    }}