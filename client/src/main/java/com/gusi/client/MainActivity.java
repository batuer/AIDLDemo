package com.gusi.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.gusi.server.IGusiAidlInterface;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "Ylw_Client";
  private ServiceConnection mServiceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      Log.i(TAG, "onServiceConnected: ");
      gusiAidlInterface = IGusiAidlInterface.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
      Log.i(TAG, "onServiceDisconnected: ");
      gusiAidlInterface = null;
    }
  };
  private IGusiAidlInterface gusiAidlInterface;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    connect(null);
  }

  public void connect(View view) {
    if (gusiAidlInterface == null) {
      Intent intent = new Intent();
      intent.setAction("android.intent.action.Gusi");
      intent.setPackage("com.gusi.server");
      boolean b = bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
      Log.i(TAG, "connect: " + b);
    }
  }

  public void runtimeException(View view) {

    if (gusiAidlInterface != null) {
      try {
        gusiAidlInterface.testCrash(0);
      } catch (Exception e) {
        Log.e(TAG, "runtimeException: ", e);
      }
    } else {
      Log.e(TAG, "gusiAidlInterface is null!");
    }
  }

  public void exception(View view) {

    if (gusiAidlInterface != null) {
      try {
        gusiAidlInterface.testCrash(1);
      } catch (Exception e) {
        Log.e(TAG, "exception: ", e);
      }
    } else {
      Log.e(TAG, "gusiAidlInterface is null!");
    }
  }

  public void nativeCrash(View view) {
    if (gusiAidlInterface != null) {
      try {
        gusiAidlInterface.testCrash(2);
      } catch (Exception e) {
        Log.e(TAG, "nativeCrash: ", e);
      }
    } else {
      Log.e(TAG, "gusiAidlInterface is null!");
    }
  }
}