package com.maik.greenhouse;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.maik.greenhouse.BtUtils.AcceptThread;
import com.maik.greenhouse.FB.FBInteractors;
import com.maik.greenhouse.databinding.MainBinding;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private MainBinding vBind;
    private DatabaseReference mDatabase;
    private FBInteractors fbInteractors;
    private String TAG = "MAIN_ACT";


    public static final String BT_DEVICE_HC05 = "98:D3:31:F5:94:1D";
    public static final int TRASNFER_DATA = 101;
    public static final int ACTION_REQUEST_ENABLE = 101;
    public Handler mHandler;
    private boolean chekin = false;
    private StringBuilder dataReceived = new StringBuilder();
    private OutputStream outStream;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fbInteractors = FBInteractors.getInstance();
        showGreenhouseImage();

        /**
         * BT CONFIGURATIONS.
         */

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ACTION_REQUEST_ENABLE);
        } else {
            connectBtw();
        }
    }


    @SuppressLint({"CheckResult", "HandlerLeak"})
    private void connectBtw() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            setHupHandler();
            AcceptThread acceptThread = null;
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BT_DEVICE_HC05);
            acceptThread = new AcceptThread(device, mBluetoothAdapter, mHandler);

            try {
                outStream = acceptThread.getMmSocket().getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Espere mientras se configura ")
                    .setTitle("Espere");
            AlertDialog dialog = builder.create();
            dialog.show();

            AcceptThread finalAcceptThread = acceptThread;

            Single
                    .fromCallable(() -> finalAcceptThread.run(this::getBTData))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success -> {
                        if (success) {
                            dialog.hide();
                            onSuccessBt();
                        }
                    });
        }
    }


    @SuppressLint("HandlerLeak")
    private void setHupHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == TRASNFER_DATA) {
                    System.out.println(msg.obj);
                    getBTData((String) msg.obj);
                }
            }
        };
    }


    private void getBTData(String data) {
        if (data != null) {
            dataReceived.append(data);
            if (dataReceived.length() > 2) {
                String[] value = dataReceived.toString().split("°");
                vBind.tvTemperature.setText(value[0] + "°");
                dataReceived = new StringBuilder();
            }
        }
    }

    private void showGreenhouseImage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        fbInteractors.getGreenHouse(greenHouse -> {
            storageRef.child("imageService.jpg").getDownloadUrl().addOnSuccessListener(uri ->
                    Glide.with(this)
                            .load(uri)
                            .into(vBind.ivGreenhouse));
        });

    }

    private void onSuccessBt() {
        sendData("500");
    }

    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
        }
    }

}


/**
 * FIREBASE TOKEN
 */
/*
  FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();

                    System.out.println("TOKEN_GENERATE: " + token);
                    // Log and toast
                    String msg = "TOKEN_SUCCESS";
                    Log.d("TOKEN", msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
* */