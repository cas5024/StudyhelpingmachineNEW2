package com.example.ui123;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.AlgoSdk.NskAlgoDataType;
import com.neurosky.AlgoSdk.NskAlgoSdk;
import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//MusicSelect에서 받은 음악데이터를 가지고 ui1액티비티에서 onPause메소드에서 뇌파에 따라
//음악이 나오는 기능 구현하고
//onCreate메소드에서 기본적인 streamReader이나 NskAlgoSdk객체 생성해줌
public class ui1 extends AppCompatActivity {
    final String TAG = "ui1_Activity";

    //attValue medValue 값 화면표시 , 굳이 필요없을 듯.
    private TextView attValue;
    private TextView medValue;

    // COMM SDK handles
    private TgStreamReader tgStreamReader;
    private BluetoothAdapter mBluetoothAdapter;
    private NskAlgoSdk nskAlgoSdk;
    private TgStreamHandler callback;
    private static NskAlgoSdk.OnAttAlgoIndexListener mOnAttAlgoIndexListener;

    //UI components
    private Button headsetButton;
    private Button cannedButton;
    private Button setAlgosButton;
    private Button setIntervalButton;
    private Button startButton;
    private Button stopButton;

    // canned data variables -> 뇌파데이터
    private short raw_data[] = {0};
    private int raw_data_index= 0;
    private float output_data[];
    private int output_data_count = 0;
    private int raw_data_sec_len = 85;

    // internal variables
    private boolean bInited = false;
    private boolean bRunning = false;


    //onCreate메소드 시작****
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_1);

        //기본적인 필수적인 객체 생성-> NskAlgoSdk 객체생성, BluetoothAdapter객체 생성.
        nskAlgoSdk = new NskAlgoSdk();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //TgStreamHandler 객체 생성
        TgStreamHandler callback = new TgStreamHandler() {

            @Override
            public void onDataReceived(int i, int i1, Object o) {

            }

            @Override
            public void onStatesChanged(int connectionStates) {
                // TODO Auto-generated method stub
                Log.d(TAG, "connectionStates change to: " + connectionStates);
                switch (connectionStates) {
                    case ConnectionStates.STATE_CONNECTING:
                        // Do something when connecting
                        break;
                    case ConnectionStates.STATE_CONNECTED:
                        // Do something when connected
                        tgStreamReader.start();
                        showToast("Connected", Toast.LENGTH_SHORT);
                        break;
                    case ConnectionStates.STATE_WORKING:
                        // Do something when working

                        //(9) demo of recording raw data , stop() will call stopRecordRawData,
                        //or you can add a button to control it.
                        //You can change the save path by calling setRecordStreamFilePath(String filePath) before startRecordRawData
                        //tgStreamReader.startRecordRawData();

                        ui1.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Button startButton = (Button) findViewById(R.id.bt_next_button);
                                startButton.setEnabled(true);
                            }

                        });

                        break;
                    case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                        // Do something when getting data timeout

                        //(9) demo of recording raw data, exception handling
                        //tgStreamReader.stopRecordRawData();

                        showToast("Get data time out!", Toast.LENGTH_SHORT);

                        if (tgStreamReader != null && tgStreamReader.isBTConnected()) {
                            tgStreamReader.stop();
                            tgStreamReader.close();
                        }

                        break;
                    case ConnectionStates.STATE_STOPPED:
                        // Do something when stopped
                        // We have to call tgStreamReader.stop() and tgStreamReader.close() much more than
                        // tgStreamReader.connectAndstart(), because we have to prepare for that.

                        break;
                    case ConnectionStates.STATE_DISCONNECTED:
                        // Do something when disconnected
                        break;
                    case ConnectionStates.STATE_ERROR:
                        // Do something when you get error message
                        break;
                    case ConnectionStates.STATE_FAILED:
                        // Do something when you get failed message
                        // It always happens when open the BluetoothSocket error or timeout
                        // Maybe the device is not working normal.
                        // Maybe you have to try again
                        break;
                }
            }

            @Override
            public void onChecksumFail(byte[] bytes, int i, int i1) {

            }

            @Override
            public void onRecordFail(int i) {

            }
        };

        //버튼 연결
        Button cannedButton = findViewById(R.id.bt_button);
        Button next = findViewById(R.id.bt_next_button);
        Button headsetButton = findViewById(R.id.headset_button);
        Button startButton =findViewById(R.id.bt_next_button);
        //버튼에 클릭리스너 set
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ui1.this, activity_05.class); // next버튼 누르면 ui2activity 시작됨.
                startActivity(intent);
            }

        });

        //버튼에 클릭 리스너 set
        headsetButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    output_data_count = 0;
                    output_data = null;

                    raw_data = new short[512];
                    raw_data_index = 0;

                    cannedButton.setEnabled(false);
                    headsetButton.setEnabled(false);

                    startButton.setEnabled(false);

                    // Example of constructor public TgStreamReader(BluetoothAdapter ba, TgStreamHandler tgStreamHandler)
                    tgStreamReader = new TgStreamReader(mBluetoothAdapter,callback);

                    //tgStreamReader여기 맞나?
                    tgStreamReader.setGetDataTimeOutTime(6);

                    if(tgStreamReader != null && tgStreamReader.isBTConnected()){

                        // Prepare for connecting
                        tgStreamReader.stop();
                        tgStreamReader.close();
                    }

                    // (4) Demo of  using connect() and start() to replace connectAndStart(),
                    // please call start() when the state is changed to STATE_CONNECTED
                    tgStreamReader.connect();
                }
            }
        );

        //cannedButton에 클릭리스너 setting
        cannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output_data_count = 0;
                output_data = null;

                System.gc();

                headsetButton.setEnabled(false);
                cannedButton.setEnabled(false);

                AssetManager assetManager = getAssets();
                InputStream inputStream = null;

                Log.d(TAG, "Reading output data");
                try {
                    int j;
                    // check the output count first
                    inputStream = assetManager.open("output_data.bin");
                    output_data_count = 0;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    try {
                        String line = reader.readLine();
                        while (!(line == null || line.isEmpty())) {
                            output_data_count++;
                            line = reader.readLine();
                        }
                    } catch (IOException e) {

                    }
                    inputStream.close();

                    if (output_data_count > 0) {
                        inputStream = assetManager.open("output_data.bin");
                        output_data = new float[output_data_count];
                        //ap = new float[output_data_count];
                        j = 0;
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        try {
                            String line = reader.readLine();
                            while (j < output_data_count) {
                                output_data[j++] = Float.parseFloat(line);
                                line = reader.readLine();
                            }
                        } catch (IOException e) {

                        }
                        inputStream.close();
                    }
                } catch (IOException e) {
                }

                Log.d(TAG, "Reading raw data");
                try {
                    inputStream = assetManager.open("raw_data_em.bin");
                    raw_data = readData(inputStream, 512*raw_data_sec_len);
                    raw_data_index = 512*raw_data_sec_len;
                    inputStream.close();
                    nskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_BULK_EEG.value, raw_data, 512 * raw_data_sec_len);
                } catch (IOException e) {

                }
                Log.d(TAG, "Finished reading data");
            }
        });

        //startButton에 클릭리스너 setting
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bRunning == false) {
                    nskAlgoSdk.NskAlgoStart(false);
                } else {
                    nskAlgoSdk.NskAlgoPause();
                }
            }
        });


    }

    //onCreate메소드 끝*****






    //여기에 다른 액티비티로 넘어갔을 때 동작 코드
    @Override
    protected void onPause() {
        super.onPause();
        // 이거 데이터 받아서 setText보여주는 거 textview에
        nskAlgoSdk.setOnAttAlgoIndexListener(new NskAlgoSdk.OnAttAlgoIndexListener() {
            @Override
            public void onAttAlgoIndex(int value) {
                Log.d(TAG, "NskAlgoAttAlgoIndexListener: Attention:" + value);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // change UI elements here
                        //여기에 value값(집중도)에 따라 음악나오는 메소드 구현
                        AttStartMusic(value);

                    }
                });
            }
        }
        );


    }



    public void showToast(final String msg, final int timeStyle) {
        ui1.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), msg, timeStyle).show();
            }

        });
    }

    //(활용할 메소드1)
    private short [] readData(InputStream is, int size) {
        short data[] = new short[size];
        int lineCount = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while (lineCount < size) {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) {
                    Log.d(TAG, "lineCount=" + lineCount);
                    break;
                }
                data[lineCount] = Short.parseShort(line);
                lineCount++;
            }
            Log.d(TAG, "lineCount=" + lineCount);
        } catch (IOException e) {

        }
        return data;
    }
    //Att값에 따라 음악 재생하는 메소드
    public void AttStartMusic(int value) {
        if(value<=50)
            {

        }
    }



}



