package com.patronusstudio.kaydirkazan.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class firebaseUserModel implements Parcelable {

    private String email;
    private String resim_url;
    private String uid;
    private String telefon_numarasi;
    private String isim;

    public firebaseUserModel(){ }

    public firebaseUserModel(String email, String resim_url, String uid, String telefon_numarasi, String isim) {
        this.email = email;
        this.resim_url = resim_url;
        this.uid = uid;
        this.telefon_numarasi = telefon_numarasi;
        this.isim = isim;
    }

    protected firebaseUserModel(Parcel in) {
        email = in.readString();
        resim_url = in.readString();
        uid = in.readString();
        telefon_numarasi = in.readString();
        isim = in.readString();
    }

    public static final Creator<firebaseUserModel> CREATOR = new Creator<firebaseUserModel>() {
        @Override
        public firebaseUserModel createFromParcel(Parcel in) {
            return new firebaseUserModel(in);
        }

        @Override
        public firebaseUserModel[] newArray(int size) {
            return new firebaseUserModel[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResim_url() {
        return resim_url;
    }

    public void setResim_url(String resim_url) {
        this.resim_url = resim_url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTelefon_numarasi() {
        return telefon_numarasi;
    }

    public void setTelefon_numarasi(String telefon_numarasi) {
        this.telefon_numarasi = telefon_numarasi;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(resim_url);
        parcel.writeString(uid);
        parcel.writeString(telefon_numarasi);
        parcel.writeString(isim);
    }
}
