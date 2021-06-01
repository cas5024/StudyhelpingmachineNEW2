package com.example.ui123;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

import java.util.ArrayList;
import java.util.List;


public class Musicselect extends AppCompatActivity {

    TgStreamReader tgStreamReader;
    private BluetoothAdapter mbluetoothAdapter;     //BluetoothAdapter 변수선언 in MusicSelect class

    ImageButton play;
    ImageButton musicback;
    ImageButton musicnext;
    ImageButton pause;
    SeekBar seekbar;

    MediaPlayer mediaPlayer;
<<<<<<< HEAD

=======
    //이 배열에 raw폴더의 음악들 넣는다.
>>>>>>> re/basictestbranch3
    int[] array = {R.raw.burkinelectric, R.raw.unavailable};
    int index = 0;
    //전달할 musicselect배열번호
    static int musicselected;
    ListView listview1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicselcet);
        //리스트뷰 (뷰그룹)구현 부분 -> MainActivity에서 MusicSelectActivity로 전환되지않는다. ->ArrayAdapter객체에서 인자를 simple~로 고치니 해결!
        List<String> music_strings =new ArrayList<>();


        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, music_strings) ;
        listview1 =(ListView) findViewById(R.id.listViewMp3);
        listview1.setAdapter(adapter);
        music_strings.add("music1");
        music_strings.add("music2");












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