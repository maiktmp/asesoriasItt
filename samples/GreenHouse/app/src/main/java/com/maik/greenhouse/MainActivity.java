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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.maik.greenhouse.BtUtils.AcceptThread;
import com.maik.greenhouse.BtUtils.Notifications;
import com.maik.greenhouse.FB.FBInteractors;
import com.maik.greenhouse.databinding.MainBinding;
import com.maik.greenhouse.models.GreenHouse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
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
    public Handler mHandlerTime;
    private boolean chekin = false;
    private StringBuilder dataReceived = new StringBuilder();
    private OutputStream outStream;
    private String watterCode = "100";
    private String motorCode = "101";
    public static Boolean canNotify = true;
    private GreenHouse greenHouse;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fbInteractors = FBInteractors.getInstance();
        setUpActionButton();

        /**
         * BT CONFIGURATIONS.
         */

//        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (!mBluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, ACTION_REQUEST_ENABLE);
//        } else {
//            connectBtw();
//        }
    }

    private void sendNotification() {
        if (!canNotify) return;
        Notifications.createNotificationChannel(this);
        Notifications.launchNotification(this);

        Handler mHandlerTimer = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                MainActivity.canNotify = true;
            }
        };
        canNotify = false;
        mHandlerTimer.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showGreenhouseData();
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
                if (Double.valueOf(value[0]) >= greenHouse.getLimit()) {
                    sendNotification();
                }
                dataReceived = new StringBuilder();
            }
        }
    }

    private void showGreenhouseData() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        fbInteractors.getGreenHouse(greenHouse -> {
            this.greenHouse = greenHouse;
            storageRef.child("imageService.jpg").getDownloadUrl().addOnSuccessListener(uri ->
                    Glide.with(this)
                            .load(uri)
                            .into(vBind.ivGreenhouse));

            vBind.tvName.setText(greenHouse.getName());
            vBind.tvDescription.setText(greenHouse.getDescription());
        });

    }

    private void onSuccessBt() {
//        sendData("500");
        setUpWatterBtn();
        setUpMotorsBtn();
    }


    /**
     * 100 encender bombas
     * 101 encender ventiladores
     * 102 apagar bombas
     * 103 apagar ventiladores
     */

    private void setUpWatterBtn() {
        vBind.btnWatter.setOnClickListener(v -> {
            if (watterCode.equals("102")) {
                vBind.btnWatter.setText("Apagar bomba de agua");
                watterCode = "100";
            } else {
                vBind.btnWatter.setText("Encender bomba de agua");
                watterCode = "102";
            }
            sendData(watterCode);
        });
    }

    private void setUpMotorsBtn() {
        vBind.btnMotor.setOnClickListener(v -> {
            if (motorCode.equals("103")) {
                vBind.btnMotor.setText("Apagar ventiladores");
                motorCode = "101";
            } else {
                vBind.btnMotor.setText("Encender ventiladores");
                motorCode = "103";
            }
            sendData(motorCode);
        });
    }

    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();
        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
        }
    }

    private void setUpActionButton() {
        vBind.btnUpdate.setOnClickListener(v -> {
            startActivity(new Intent(this, FormActivity.class));
        });
        vBind.tvAvg.setOnClickListener(v -> {
            startActivity(new Intent(this, AverageActivity.class));
        });
        vBind.btnSync.setOnClickListener(v -> {
            if (isOnline()) {

                Map<String, String> avg = greenHouse.getAverage();
                avg.put("20-11-2019", "25.6");

                fbInteractors.update(greenHouse, success -> {
                    if (success) {
                        new MaterialAlertDialogBuilder(this)
                                .setTitle("Éxito")
                                .setMessage("Temperatura registrada")
                                .setPositiveButton("Ok", null)
                                .show();
                    } else {
                        new MaterialAlertDialogBuilder(this)
                                .setTitle("Error")
                                .setMessage("Intente más tarde")
                                .setPositiveButton("Ok", null)
                                .show();
                    }
                });

            } else {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Error")
                        .setMessage("No se cuenta con acceso a internet, intente más tarde")
                        .setPositiveButton("Ok", null)
                        .show();
            }
        });

    }


    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
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