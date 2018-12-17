package com.client.bnb.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    TextView bluetoothStatusTV;
    Button serviceStatusBTN;

    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        bluetoothStatusTV = (TextView) findViewById(R.id.textView);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(new Locale("uk_UA"));
                }
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //mBluetoothAdapter.enable();
        //mBluetoothAdapter.startDiscovery();

        if (mBluetoothAdapter.isEnabled()) {
            Log.i("BNB BLE--->","Bluetoth suported and Enabled= true");
            SpeakText("Bluetooth увімкнений.");
            //startService(new Intent(this,  AlertControllerService.class));
            bluetoothStatusTV.setText("BLUETOOTH Увімкнено");
            Log.i("BNB BLE--->","Bluetooth ready to receive devices");
        } else {
            Log.i("BNB BLE--->","Bluetooth are disabled= " + mBluetoothAdapter.enable() +" state= " +mBluetoothAdapter.getState());
            SpeakText("Bluetooth вимкнений.");
            bluetoothStatusTV.setText("BLUETOOTH Вимкнено");
        }

        serviceStatusBTN=(Button)findViewById(R.id.button);
        if(AlertControllerService.isServiceActive) {
            serviceStatusBTN.setText("Стоп");
        } else {
            serviceStatusBTN.setText("Старт");
        }

        serviceStatusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AlertControllerService.isServiceActive) {
                    stopService(new Intent(getApplicationContext(),  AlertControllerService.class));
                    serviceStatusBTN.setText("Старт");
                    Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_LONG).show();
                    SpeakText("Сервіс зупинено.");
                } else {
                    startService(new Intent(getApplicationContext(),  AlertControllerService.class));
                    serviceStatusBTN.setText("Стоп");
                    Toast.makeText(getApplicationContext(), "Service Stoped", Toast.LENGTH_LONG).show();
                    SpeakText("Сервіс запущено.");

                }
            }
        });

    }

    @Override
    protected  void  onResume(){
        super.onResume();
      //  mBluetoothAdapter.startDiscovery();
        Log.d("BNB BLE--->", "onResume called");
    //    stopService(new Intent(this,  AlertControllerService.class));
        Log.d("BNB BLE--->", "onResume service stoped");

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("BNB BLE--->", "onDestroy ");
        Runtime.getRuntime().gc();
        startService(new Intent(this, AlertControllerService.class));
        Log.d("BNB BLE--->", "onDestroy service run");

    }

    public void onPause(){
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public  void SpeakText(String text){
        String toSpeak = text;
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
}
