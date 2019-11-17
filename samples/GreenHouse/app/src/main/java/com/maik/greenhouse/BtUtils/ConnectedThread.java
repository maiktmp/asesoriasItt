package com.maik.greenhouse.BtUtils;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.maik.greenhouse.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler mHandler;

    ConnectedThread(BluetoothSocket socket, Handler handler) {

        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        mHandler = handler;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs

        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                String readMessage = new String(buffer, 0, bytes);
                Log.e("ARDUINO", readMessage);
                Message message = new Message();
                message.what = MainActivity.TRASNFER_DATA;
                message.obj=readMessage;
                mHandler.sendMessage(message);
                // Send the obtained bytes to the UI activity
            } catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}