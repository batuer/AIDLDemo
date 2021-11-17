package com.gusi.server;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "Ylw_" + MainActivity.class.getSimpleName();

  // Used to load the 'native-lib' library on application startup.
//  static {
//    System.loadLibrary("native-lib");
//  }

  private TextView mTv;
  private ListView mListView;
  private BaseAdapter mAdapter;
  private TextView mHeadView;
  private TextView mFootView;

  private List<String> mItemList = new ArrayList<>();
  private List<TextView> mHeadList = new ArrayList<>();
  private List<TextView> mFooterList = new ArrayList<>();
  private CheckBox mCb;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.i(TAG, "onCreate: ");
    Intent intent = new Intent(this, GusiService.class);
    intent.setAction("android.intent.action.Gusi");
    intent.setPackage("com.gusi.server");
    startService(intent);
    // Example of a call to a native method
    mTv = findViewById(R.id.sample_text);
//    mTv.setText(stringFromJNI());

    mListView = findViewById(R.id.list);
//        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItemList);
    mAdapter = new BaseAdapter() {
      @Override
      public int getCount() {
        return mItemList.size();
      }

      @Override
      public Object getItem(int position) {
        return null;
      }

      @Override
      public long getItemId(int position) {
        return position;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
          convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(mItemList.get(position));
        return convertView;
      }
    };
    mListView.setAdapter(mAdapter);
  }


  /**
   * A native method that is implemented by the 'native-lib' native library, which is packaged with
   * this application.
   */
//  public native String stringFromJNI();
//
//  public native void testFromJNI();
//
//  public native String getStr();
  public void test(View view) {
//    String str = getStr();
//    Log.i(TAG, "test: " + str);
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

  public void addItem(View view) {
//    Log.e(TAG, "run: " + Thread.currentThread().getName());
//    String str = null;
//    try {
//      str = getStr();
//    } catch (Throwable e) {
//      Log.w(TAG, "addItem: " + e.toString());
//    }
//    Log.w(TAG, "addItem: " + str);
  }

  public void removeItem(View view) {
    Log.w("Fire", "MainActivity:67行:" + get());
    if (!mItemList.isEmpty()) {
      mItemList.remove(0);
      mAdapter.notifyDataSetChanged();
    }
    Log.e("Fire", "MainActivity:72行:" + get());
  }

  public void addHead(View view) {
    Log.w("Fire", "MainActivity:76行:" + get());
    TextView textView = (TextView) View.inflate(this, android.R.layout.simple_list_item_1, null);
    textView.setText("Head: " + System.currentTimeMillis());
    mHeadList.add(textView);
    mListView.addHeaderView(textView);
    Log.e("Fire", "MainActivity:81行:" + get());
  }

  public void removeHead(View view) {
    Log.w("Fire", "MainActivity:85行:" + get());
    if (!mHeadList.isEmpty()) {
      TextView textView = mHeadList.remove(0);
      mListView.removeHeaderView(textView);
    }
    Log.e("Fire", "MainActivity:90行:" + get());
  }

  public void addFoot(View view) {
    Log.w("Fire", "MainActivity:94行:" + get());
    TextView textView = (TextView) View.inflate(this, android.R.layout.simple_list_item_1, null);
    textView.setText("Foot: " + System.currentTimeMillis());
    mFooterList.add(textView);
    mListView.addFooterView(textView);
    Log.e("Fire", "MainActivity:99行:" + get());
  }

  public void removeFoot(View view) {
    Log.w("Fire", "MainActivity:103行:" + get());
    if (!mFooterList.isEmpty()) {
      TextView textView = mFooterList.remove(0);
      mListView.removeFooterView(textView);
    }
    Log.e("Fire", "MainActivity:108行:" + get());
  }

  private String get() {
    return "ListViewCount: " + mListView.getCount() + " ,AdapterCount: " + mAdapter.getCount() + " ,HeadCount: " + mListView.getHeaderViewsCount() +
        " ,FootCount: " + mListView.getFooterViewsCount();
  }

  private IGusiAidlInterface gusiAidlInterface;

  public void connect(View view) {
    if (gusiAidlInterface == null) {
      Intent intent = new Intent();
      intent.setAction("android.intent.action.Gusi");
      intent.setPackage("com.gusi.server");
      boolean b = bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
      Log.i(TAG, "connect: " + b);
    } else {
      Log.i(TAG, "connected!");
    }
  }

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
}
