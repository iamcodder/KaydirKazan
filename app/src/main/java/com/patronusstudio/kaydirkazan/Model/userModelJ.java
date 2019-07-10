package com.patronusstudio.kaydirkazan.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class userModelJ implements Parcelable {

    String email="";
    String uuid="";
    String yuksekPuan="0";
    String cevaplananSoruSayisi="";

    public userModelJ(){
    }

    public userModelJ(String email, String uuid, String yuksekPuan, String cevaplananSoruSayisi) {
        this.email = email;
        this.uuid = uuid;
        this.yuksekPuan = yuksekPuan;
        this.cevaplananSoruSayisi = cevaplananSoruSayisi;
    }

    protected userModelJ(Parcel in) {
        email = in.readString();
        uuid = in.readString();
        yuksekPuan = in.readString();
        cevaplananSoruSayisi = in.readString();
    }

    public static final Creator<userModelJ> CREATOR = new Creator<userModelJ>() {
        @Override
        public userModelJ createFromParcel(Parcel in) {
            return new userModelJ(in);
        }

        @Override
        public userModelJ[] newArray(int size) {
            return new userModelJ[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getYuksekPuan() {
        return yuksekPuan;
    }

    public void setYuksekPuan(String yuksekPuan) {
        this.yuksekPuan = yuksekPuan;
    }

    public String getCevaplananSoruSayisi() {
        return cevaplananSoruSayisi;
    }

    public void setCevaplananSoruSayisi(String cevaplananSoruSayisi) {
        this.cevaplananSoruSayisi = cevaplananSoruSayisi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(uuid);
        parcel.writeString(yuksekPuan);
        parcel.writeString(cevaplananSoruSayisi);
    }
}
