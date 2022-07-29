package com.client.bnb.myapplication.models;

import android.bluetooth.BluetoothDevice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmytro on 21.10.2018.
 */

public class BluetoothSensor {

    @SerializedName("BluetoothDevice")
    @Expose
    private BluetoothDevice bluetoothDevice;

    @SerializedName("RSSI")
    @Expose
    private int RSSI = -100;

    public BluetoothDevice getBluetoothDevice() { return bluetoothDevice; }
    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) { this.bluetoothDevice = bluetoothDevice; }

    public int getRSSI() { return RSSI; }
    public void setRSSI(int RSSI) { this.RSSI = RSSI; }

}
