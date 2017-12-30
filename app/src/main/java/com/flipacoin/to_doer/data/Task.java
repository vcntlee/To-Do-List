package com.flipacoin.to_doer.data;

public class Task {

    int mId;
    String mName;

    //TODO need to check how date is implemented in picker
    String mDate;

    String mNotes;
    int mLevel;
    int mStatus;

    public Task(){
        mName = "";
        mNotes = "";
    }

    public Task(int id, String name, String date, String notes, int level, int status){
        mId = id;
        mName = name;
        mDate = date;
        mNotes = notes;
        mLevel = level;
        mStatus = status;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getLevelString() {
        if (mLevel == 0) {
            return "Low";
        }
        else if (mLevel == 1) {
            return "Mid";
        }
        else {
            return "High";
        }
    }
    public String getStatusString() {
        if (mStatus == 1) {
            return "done!";
        } else {
            return "not done";
        }
    }
}
