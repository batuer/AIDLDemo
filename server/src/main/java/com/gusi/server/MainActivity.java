package com.gusi.server;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Intent intent = new Intent(this, GusiService.class);
    intent.setAction("android.intent.action.Gusi");
    intent.setPackage("com.gusi.server");
    startService(intent);
  }
}