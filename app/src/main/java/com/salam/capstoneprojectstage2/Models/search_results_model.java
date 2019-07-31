package com.salam.capstoneprojectstage2.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class search_results_model implements Parcelable {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    public search_results_model(String title, String jurisdiction, String date, String caseid) {
        this.title = title;
        this.jurisdiction = jurisdiction;
        this.date = date;
        this.caseid = caseid;

    }

    String title;
    String jurisdiction;
    String date;
    String caseid;


    public search_results_model() {
    }

    protected search_results_model(Parcel in) {
        title = in.readString();
        jurisdiction = in.readString();
        date = in.readString();
    }

    public static final Creator<search_results_model> CREATOR = new Creator<search_results_model>() {
        @Override
        public search_results_model createFromParcel(Parcel in) {
            return new search_results_model(in);
        }

        @Override
        public search_results_model[] newArray(int size) {
            return new search_results_model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(jurisdiction);
        dest.writeString(date);
    }
}
