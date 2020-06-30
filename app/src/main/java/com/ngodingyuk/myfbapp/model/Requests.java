package com.ngodingyuk.myfbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Requests implements Parcelable {
    private String name;
    private String email;
    private String desc;

    private String id;

    public Requests() {
    }

    public Requests(String name, String email, String description) {
        this.name = name;
        this.email = email;
        this.desc = description;
    }

    protected Requests(Parcel in) {
        name = in.readString();
        email = in.readString();
        desc = in.readString();
        id = in.readString();
    }

    public static final Creator<Requests> CREATOR = new Creator<Requests>() {
        @Override
        public Requests createFromParcel(Parcel in) {
            return new Requests(in);
        }

        @Override
        public Requests[] newArray(int size) {
            return new Requests[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(desc);
        parcel.writeString(id);
    }

    public static Creator<Requests> getCREATOR() {
        return CREATOR;
    }
}
