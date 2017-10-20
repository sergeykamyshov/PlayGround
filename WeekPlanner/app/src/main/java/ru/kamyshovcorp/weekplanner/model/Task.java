package ru.kamyshovcorp.weekplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {

    private boolean mDoneFlag;
    private String mDescription;

    public Task(boolean done, String description) {
        mDoneFlag = done;
        mDescription = description;
    }

    protected Task(Parcel in) {
        mDoneFlag = in.readByte() != 0;
        mDescription = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public boolean isDone() {
        return mDoneFlag;
    }

    public void setDone(boolean done) {
        mDoneFlag = done;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mDoneFlag ? 1 : 0));
        dest.writeString(mDescription);
    }
}
