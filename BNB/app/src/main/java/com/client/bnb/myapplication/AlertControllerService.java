package com.client.bnb.myapplication;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.client.bnb.myapplication.models.BluetoothSensor;
import com.client.bnb.myapplication.models.Sensor;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmytro on 22.01.2017.
 */

public class AlertControllerService extends Service {
    private BluetoothAdapter mBluetoothAdapter;
    TextToSpeech textToSpeech;
    Message message;
    static boolean isServiceActive;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        isServiceActive = false;
        unregisterReceiver(mReceiver);
        Runtime.getRuntime().gc();
        textToSpeech.shutdown();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        Log.i("BNB SERVICE--->"," Started");
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
       // mBluetoothAdapter.enable();
       // mBluetoothAdapter.startDiscovery();

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(new Locale("uk_UA"));
                }
            }
        });

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        if(!thread.isAlive()) {
            thread.start();
        }
        Log.i("BNB SERVICE--->"," Working");
        isServiceActive = true;

        return super.onStartCommand(intent, flags, startId);
    }


    public void sendNotification() {
//       // controller.setLanguage(settings.getUserLanguage(),getResources());
//        // Use NotificationCompat.Builder to set up our notification.
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//
//        //icon appears in device notification bar and right hand corner of notification
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//
//        // This intent is fired when notification is clicked
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        // Set the intent that will fire when the user taps the notification.
//        builder.setContentIntent(pendingIntent);
//
//        // Large icon appears on the left of the notification
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.fire_icon));
//
//        // Content title, which appears in large type at the top of the notification
//        builder.setContentTitle("Home Informer: "+getApplicationContext().getString(R.string.ServiceAlert));
//
//        // Content text, which appears in smaller text below the title
//        builder.setContentText(getApplicationContext().getString(R.string.ServiceContentText)+settings.getCriticalTemp()+" ℃");
//
//        // The subtext, which appears under the text on newer devices.
//        // This will show-up in the devices with Android 4.2 and above only
//        builder.setSubText(getApplicationContext().getString(R.string.ServiceSubText));
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Uri alarmSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm);
//        builder.setSound(alarmSound);
//        //Vibration
//        builder.setVibrate(new long[] { 1000, 1000 });
//        //LED
//        builder.setLights(Color.BLUE, 3000, 3000);
//        // Will display the notification in the notification bar
//        notificationManager.notify(1, builder.build());

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                boolean bnbDevice = deviceName != null ? (deviceName.contains("MyESP32") ? true : false) : false;
               if(bnbDevice) {
                   String deviceHardwareAddress = device.getAddress(); // MAC address
                   int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);

                   BluetoothSensor findedDevice = new BluetoothSensor();
                   findedDevice.setBluetoothDevice(device);
                   findedDevice.setRSSI(rssi);
                   devices.add(findedDevice);

                   Log.i("BLE--->", "Name: " + deviceName + " MAC: " + deviceHardwareAddress + " RSSI: " + rssi);
                   //txtView.append("\n Name: " + deviceName + " MAC: " + deviceHardwareAddress + " RSSI: " + rssi);
               }
                Log.i("BLE--->", "NOT BNB DEVICE: Name: " + deviceName);
            }
        }
    };

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            TTS.SpeakText(textToSpeech, getApplicationContext(),(String)message.obj);
        }
    };


    Set<BluetoothSensor> devices= new HashSet<BluetoothSensor>();

    Thread thread = new Thread() {
        @Override
        public void run() {

            Log.i("BLE--->", "IS Discovering " + mBluetoothAdapter.isDiscovering());
            while(true) {
                try {
                    if (mBluetoothAdapter.isDiscovering() && devices.size() != 0) {
                        mBluetoothAdapter.cancelDiscovery();
                        Log.i("BLE--->", "NEAREST Start searching nearest");
                        Log.i("BLE--->", "NEAREST list");
                        for (BluetoothSensor sensor : devices) {
                            Log.i("DEVICE LIST--->", "name " + sensor.getBluetoothDevice().getName() + " mac " + sensor.getBluetoothDevice().getAddress() + " rssi " + sensor.getRSSI());
                        }

                        BluetoothSensor nearestDevice = getNearestDevice(devices);
                        Log.i("BNB SEARCH--->", "Finished");
                        Log.i("BNB SEARCH--->", " NEAREST Name: " + nearestDevice.getBluetoothDevice().getName() + " RSSI: " + nearestDevice.getRSSI());

                        devices.clear();
                        Thread.sleep(5000);
                        Log.i("BLE--->", "NEAREST cleared and start searching again");
                        message = mHandler.obtainMessage(0, "Знайдено пристрій неподалік. Завантаження...");
                        message.sendToTarget();
                        message = mHandler.obtainMessage(0, "");
                        message.sendToTarget();
                        if (APIController.isInternetAvailable(getApplicationContext())) {
                            APIController.getTextFromAPIBySensorKey(textToSpeech, getApplicationContext(), nearestDevice.getBluetoothDevice().getAddress(), mBluetoothAdapter);
                        } else {
                            message = mHandler.obtainMessage(0, "Відсутнє з'єднання з інтернетом. Неможливо завантажити дані.");
                            message.sendToTarget();
                        }

                    }
                } catch (Exception e){
                    Log.i("BLE--->", "NEAREST error " + e);
                }
                }
        }
    };



    public BluetoothSensor getNearestDevice(Set<BluetoothSensor> devices){
        BluetoothSensor nearestDevice = new BluetoothSensor();
        for (BluetoothSensor sensor: devices) {
            if(sensor.getRSSI() >= nearestDevice.getRSSI()){
                nearestDevice = sensor;
            }
        }
        return nearestDevice;
    }

}
