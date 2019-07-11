package com.patronusstudio.kaydirkazan.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class soruModelJ implements Parcelable {

    private String solCevap;
    private String sagCevap;
    private String soru;
    private String dogruCevap;

    public soruModelJ(){

    }

    public soruModelJ(String solCevap, String sagCevap, String soru, String dogruCevap) {
        this.solCevap = solCevap;
        this.sagCevap = sagCevap;
        this.soru = soru;
        this.dogruCevap = dogruCevap;
    }

    protected soruModelJ(Parcel in) {
        solCevap = in.readString();
        sagCevap = in.readString();
        soru = in.readString();
        dogruCevap = in.readString();
    }

    public static final Creator<soruModelJ> CREATOR = new Creator<soruModelJ>() {
        @Override
        public soruModelJ createFromParcel(Parcel in) {
            return new soruModelJ(in);
        }

        @Override
        public soruModelJ[] newArray(int size) {
            return new soruModelJ[size];
        }
    };

    public String getSolCevap() {
        return solCevap;
    }

    public void setSolCevap(String solCevap) {
        this.solCevap = solCevap;
    }

    public String getSagCevap() {
        return sagCevap;
    }

    public void setSagCevap(String sagCevap) {
        this.sagCevap = sagCevap;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getDogruCevap() {
        return dogruCevap;
    }

    public void setDogruCevap(String dogruCevap) {
        this.dogruCevap = dogruCevap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(solCevap);
        parcel.writeString(sagCevap);
        parcel.writeString(soru);
        parcel.writeString(dogruCevap);
    }
}
