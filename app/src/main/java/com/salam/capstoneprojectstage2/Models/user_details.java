package com.salam.capstoneprojectstage2.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class user_details implements Parcelable {

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String First_name) {
        this.First_name = First_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String Last_name) {
        this.Last_name = Last_name;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getCompany_name() {
        return Company_name;
    }

    public void setCompany_name(String Company_name) {
        this.Company_name = Company_name;
    }

    public String getimageURL() {
        return imageURL;
    }

    public void setimageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAbout_you() {
        return About_you;
    }

    public void setAbout_you(String About_you) {
        this.About_you = About_you;
    }

    public user_details(String First_name, String Last_name, String Adress, String Company_name, String imageURL, String About_you) {
        this.First_name = First_name;
        this.Last_name = Last_name;
        this.Adress = Adress;
        this.Company_name = Company_name;
        this.imageURL = imageURL;
        this.About_you = About_you;
    }

    private String First_name;
    private String Last_name;
    private String Adress;
    private String Company_name;
    private String imageURL;
    private String About_you;

    public user_details() {
    }

    protected user_details(Parcel in) {
        First_name = in.readString();
        Last_name = in.readString();
        Adress = in.readString();
        Company_name = in.readString();
        imageURL = in.readString();
        About_you = in.readString();
    }

    public static final Creator<user_details> CREATOR = new Creator<user_details>() {
        @Override
        public user_details createFromParcel(Parcel in) {
            return new user_details(in);
        }

        @Override
        public user_details[] newArray(int size) {
            return new user_details[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(First_name);
        dest.writeString(Last_name);
        dest.writeString(Adress);
        dest.writeString(Company_name);
        dest.writeString(imageURL);
        dest.writeString(About_you);
    }
}
