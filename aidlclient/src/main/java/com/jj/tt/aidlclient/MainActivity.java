package com.jj.tt.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jj.tt.aidlserver.BookCallBack;
import com.jj.tt.aidlserver.IBookService;


public class MainActivity extends AppCompatActivity {

    private boolean isConnectService = false;
    private IBookService iBookService = null;
    private EditText mEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEt = findViewById(R.id.et);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isConnectService)
            unbindService(serviceConnection);
        if (iBookService != null) {
            try {
                iBookService.unregisterCallback(bookCallBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w("Fire",
                    (Looper.myLooper() == Looper.getMainLooper()) + " : connected:" + Thread.currentThread() + " : " + Looper.myLooper());
            iBookService = IBookService.Stub.asInterface(service);

            isConnectService = true;
        }


        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w("Fire",
                    (Looper.myLooper() == Looper.getMainLooper()) + " : Disconnected: " + Thread.currentThread());
            isConnectService = false;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.w("Fire",
                    "MainActivity:68行:" + (Looper.myLooper() == Looper.getMainLooper()) + " : onBindingDied : " + Thread.currentThread() + " : " + name);
        }
    };


    //实现回调接口获取相关数据
    private final BookCallBack.Stub bookCallBack = new BookCallBack.Stub() {
        @Override
        public void getCount(final int count) throws RemoteException {
            Log.w("Fire",
                    "MainActivity:68行:" + Thread.currentThread() + " : " + (Looper.myLooper() == Looper.getMainLooper()));
        }

        @Override
        public void get4BookName(String bookName) throws RemoteException {
            Log.w("Fire",
                    "MainActivity:74行:" + Thread.currentThread() + " : " + (Looper.myLooper() == Looper.getMainLooper()));

        }

        @Override
        public void get3BookName(String bookName) throws RemoteException {
            Log.w("Fire",
                    "MainActivity:82行:" + Thread.currentThread() + " : " + (Looper.myLooper() == Looper.getMainLooper()));

        }

        @Override
        public void get5BookName(String bookName) throws RemoteException {
            Log.w("Fire",
                    "MainActivity:89行:" + Thread.currentThread() + " : " + (Looper.myLooper() == Looper.getMainLooper()));

        }
    };


    public void connect(View view) {
        Intent intent = new Intent();
        intent.setPackage("com.jj.tt.aidldemo");
        intent.setAction("android.intent.action.BOOK");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void disconnect(View view) {
        unbindService(serviceConnection);
    }

    public void get(View view) {
        String trim = mEt.getText().toString().trim();
        int time = 10;
        if (!TextUtils.isEmpty(trim)) {
            time = Integer.parseInt(trim);
        }
        try {
            iBookService.get1(time);
        } catch (RemoteException e) {
            Log.e("Fire", "MainActivity:111行:" + e.toString());
        }
    }

    public void getString(final View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.w("Fire", "MainActivity:134行:---------" );
                mEt.setText("31000");
            }
        }, 1000);
        String trim = mEt.getText().toString().trim();
        int time = 10;
        if (!TextUtils.isEmpty(trim)) {
            time = Integer.parseInt(trim);
        }
        try {
            Log.w("Fire", "MainActivity:137行:-");
            String block = iBookService.getBlock(time);
            Log.w("Fire", "MainActivity:132行:" + block);
        } catch (RemoteException e) {
            Log.e("Fire", "MainActivity:119行:" + e.toString());
        }
//        for (int i = 0; i < 32; i++) {
//            final int finalI = i;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String trim = mEt.getText().toString().trim();
//                    int time = 10;
//                    if (!TextUtils.isEmpty(trim)) {
//                        time = Integer.parseInt(trim);
//                    }
//                    try {
//                        String block = iBookService.getBlock(time);
//                        Log.w("Fire", finalI + "MainActivity:132行:" + block);
//                    } catch (RemoteException e) {
//                        Log.e("Fire", "MainActivity:119行:" + e.toString());
//                    }
//                }
//            }).start();
//        }
    }
}
