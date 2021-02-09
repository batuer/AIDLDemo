package com.jj.tt.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by chenmingying on 2018/3/1.
 */

public class Book implements Parcelable {
  private String name;
  private String uuid;

  public Book() {

  }

  public Book(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(uuid);
  }

  public void readFromParcel(Parcel dest) {
    name = dest.readString();
    uuid = dest.readString();
  }

  protected Book(Parcel in) {
    this.name = in.readString();
    this.uuid = in.readString();
    Log.w("Book_client1_" + uuid, Log.getStackTraceString(new Throwable("")));
  }

  public static final Creator<Book> CREATOR = new Creator<Book>() {
    @Override
    public Book createFromParcel(Parcel source) {
      return new Book(source);
    }

    @Override
    public Book[] newArray(int size) {
      return new Book[size];
    }
  };
}
