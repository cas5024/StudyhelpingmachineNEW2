package com.example.ui123;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;


public class Musicselect extends AppCompatActivity {

    TgStreamReader tgStreamReader;
    private BluetoothAdapter mbluetoothAdapter;     //BluetoothAdapter 변수선언 in MusicSelect class

    ImageButton play;
    ImageButton musicback;
    ImageButton musicnext;
    ImageButton pause;
    SeekBar seekbar;

    MediaPlayer mediaPlayer;
    //이 배열에 raw폴더의 음악들 넣는다.
    int[] array = {R.raw.burkinelectric, R.raw.unavailable};
    int index = 0;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicselcet);

        //전달할 musicselect배열번호
        int musicselected=0;

        mediaPlayer = MediaPlayer.create(this,R.raw.burkinelectric);

        play = findViewById(R.id.btn_play);
        musicback =  findViewById(R.id.btn_musicback);
        musicnext =  findViewById(R.id.btn_musicnext);
        pause = findViewById(R.id.btn_pause);
        seekbar = findViewById(R.id.seekBar1);

        seekbar.setMax(mediaPlayer.getDuration());

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                if(fromUser)
                    mediaPlayer.seekTo(progress);

            }

        });
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mediaPlayer.start();
                if(mediaPlayer.isPlaying()){
                    Toast.makeText(getApplicationContext(), "재생중입니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),array[index]);
                    mediaPlayer.start();
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();

                }
            }
        });
        musicnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    index+=1;
                    if(index>=array.length){
                        index=0;
                    }
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), array[index]);
                    mediaPlayer.start();
                }else{
                    Toast.makeText(getApplicationContext(), "재생중이 아닙니다",Toast.LENGTH_LONG).show();
                }

            }
        });

        musicback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    index-=1;
                    if(index<0){
                        index=array.length-1;
                    }
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), array[index]);
                    mediaPlayer.start();
                }else{
                    Toast.makeText(getApplicationContext(), "재생중이 아닙니다",Toast.LENGTH_LONG).show();
                }
            }
        }
        );
        Button next2 = findViewById(R.id.nextmusic);

        //다음 ui1액티비티로 넘어감.
        next2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Musicselect.this, ui1.class); // next버튼 누르면 ui2activity 시작됨.
                //int형 musicselected intent ui1.class로 전달 key값: "musicselected"
                intent.putExtra("musicselected1",  musicselected);

                startActivity(intent);


            }
        }

        );
    }//onCreate end


}