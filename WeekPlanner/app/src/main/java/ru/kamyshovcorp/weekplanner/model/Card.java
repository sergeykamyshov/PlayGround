package ru.kamyshovcorp.weekplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Card implements Parcelable {

    private String mTitle;
    private List<Task> mTasks;

    public Card(String title, List<Task> tasks) {
        mTitle = title;
        mTasks = tasks;
    }

    protected Card(Parcel in) {
        mTitle = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeTypedList(mTasks);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }
}
