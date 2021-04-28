package com.example.ui123;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class activity_06 extends AppCompatActivity {
    
    private Button button;
    private Button button2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_06);

        button= findViewById(R.id.running_button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_06.this, activity_05.class);
                startActivity(intent);

        button2=findViewById(R.id.option_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(activity_06.this, activity_07.class);
                startActivity(intent1);

            }
        });

            }
        });
    }
}