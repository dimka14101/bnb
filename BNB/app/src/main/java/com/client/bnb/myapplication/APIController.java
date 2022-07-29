package com.client.bnb.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.client.bnb.myapplication.models.Sensor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmytro on 21.10.2018.
 */

public class APIController {


    public static void getTextFromAPIBySensorKey(final TextToSpeech textToSpeech, final Context context, String deviceMAC, final BluetoothAdapter bluetoothAdapter){
        Log.i("RETROFIT--->","SendingRequest");
        Call<Sensor> callSensorByKey = ApiModule.getClient().getSensorByKey(deviceMAC);
        callSensorByKey.enqueue(new Callback<Sensor>() {
            @Override
            public void onResponse(Call<Sensor> call, Response<Sensor> response) {
                try {
                    Log.i("RETROFIT--->","Received "+ response);
                    if(response.code()==200) {
                        Log.i("RETROFIT--->","Received Code 200 "+ response.body());
                        Sensor sensor = response.body();
                        if(sensor.getSText().length() != 0) {
                               TTS.SpeakText(textToSpeech, context, "Інформація з давача. "+sensor.getSText());
                                 TTS.SpeakText(textToSpeech, context, "");
                               bluetoothAdapter.startDiscovery();
                        }
                    } else
                    {
                        Log.i("RETROFIT--->","Received Code not 200 "+ response);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.i("RETROFIT--->","Received error "+ ex);
                    TTS.SpeakText(textToSpeech, context, "Помилка під час завантаження або інформація відсутня");
                    TTS.SpeakText(textToSpeech, context, "");
                    bluetoothAdapter.startDiscovery();
                }
            }

            @Override
            public void onFailure(Call<Sensor> call, Throwable t) {
                //controller.showToastMessage(getApplicationContext(),"Something went wrong");
                Log.i("RETROFIT--->","Failure  "+ t);
                bluetoothAdapter.startDiscovery();
            }

        });
    }


    public static boolean checkWifiConnection(Context context){
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if(wifi.isWifiEnabled() && isInternetAvailable(context))
            return true;
        else return false;
    }

    public static boolean isInternetAvailable(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}
