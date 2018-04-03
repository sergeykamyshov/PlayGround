package ru.sergeykamyshov.emotiondiary.model;

import java.util.Date;

public class Event {

    public Event(Date date, String situation, String thoghts, String emotion, String reation) {
        mDate = date;
        mSituation = situation;
        mThoghts = thoghts;
        mEmotion = emotion;
        mReation = reation;
    }

    private Date mDate;
    private String mSituation;
    private String mThoghts;
    private String mEmotion;
    private String mReation;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getSituation() {
        return mSituation;
    }

    public void setSituation(String situation) {
        mSituation = situation;
    }

    public String getThoghts() {
        return mThoghts;
    }

    public void setThoghts(String thoghts) {
        mThoghts = thoghts;
    }

    public String getEmotion() {
        return mEmotion;
    }

    public void setEmotion(String emotion) {
        mEmotion = emotion;
    }

    public String getReation() {
        return mReation;
    }

    public void setReation(String reation) {
        mReation = reation;
    }
}
