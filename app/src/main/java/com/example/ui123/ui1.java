package com.example.ui123;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ui1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_1);

        Button next = findViewById(R.id.bt_next_button);
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ui1.this, ui2.class); // next버튼 누르면 ui2activity 시작됨.
                startActivity(intent);
            }




        });
    }
}