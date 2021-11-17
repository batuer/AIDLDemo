package com.gusi.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author Ylw
 * @since 2021/11/17 19:51
 */
public class GusiService extends Service {
  private static final String TAG = "Ylw_GusiService";

  static {
    System.loadLibrary("native-lib");
  }

  public native String stringFromJNI();

  public native void testFromJNI();

  public native String getStr();

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    IBinder iBinder = gusiAidlInterface.asBinder();
    Log.i(TAG, "onBind: " + getStr());
    return iBinder;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Log.i(TAG, "onCreate: ");
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
      Log.i(TAG, "testCrash: end");
    }
  };
}
