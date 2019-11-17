package com.maik.greenhouse.BtUtils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import com.maik.greenhouse.callbacks.CBGeneric;

import java.io.IOException;
import java.util.UUID;

public class AcceptThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final BluetoothAdapter mBluetoothAdapter;
    private final Handler mHandler;


    public AcceptThread(BluetoothDevice device, BluetoothAdapter mBluetoothAdapter, Handler mHandler) {
        this.mHandler = mHandler;
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        this.mBluetoothAdapter = mBluetoothAdapter;
        mmDevice = device;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            tmp = device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        } catch (IOException e) {
        }
        mmSocket = tmp;
    }

    public boolean run(CBGeneric<String> cb2) {
        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();
        try {
            mmSocket.connect();
        } catch (Exception e) {
            try {
                mmSocket.close();
                return false;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        manageConnectedSocket();
        return true;
    }
    private void manageConnectedSocket() {
        ConnectedThread connectedThread = new ConnectedThread(mmSocket,mHandler);
        connectedThread.start();
    }
    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }

    public BluetoothSocket getMmSocket() {
        return mmSocket;
    }

    public BluetoothDevice getMmDevice() {
        return mmDevice;
    }

    public BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }

}