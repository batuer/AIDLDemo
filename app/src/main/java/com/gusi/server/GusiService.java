package com.gusi.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class GusiService extends Service {
  private static final String TAG = "Ylw_GusiService";

  public GusiService() {
  }

  static {
    System.loadLibrary("native-lib");
  }

  public native String stringFromJNI();

  public native void testFromJNI();

  public native String getStr();

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    return gusiAidlInterface.asBinder();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Log.i(TAG, "onCreate: " + stringFromJNI());
  }

  private IGusiAidlInterface gusiAidlInterface = new IGusiAidlInterface.Stub() {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }


    @Override
    public void testCrash(int which) throws RemoteException {
      Log.i(TAG, "testCrash: " + which + " : " + Binder.getCallingUid() + " : " + Binder.getCallingPid());
      if (which == 0) {
        String s = null;
        s.length();
      }
      if (which == 1) {
        throw new Error("" + which);
      }
      if (which == 2) {
        String s = stringFromJNI();
        Log.i(TAG, "testCrash: " + s);
        String str = getStr();
        Log.i(TAG, "testCrash: " + str);
      }
    }
  };
}