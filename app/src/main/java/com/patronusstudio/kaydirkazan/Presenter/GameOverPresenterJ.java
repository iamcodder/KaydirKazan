package com.patronusstudio.kaydirkazan.Presenter;

import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.firebase.database.annotations.NotNull;
import com.patronusstudio.kaydirkazan.Contract.AdmobContractJ;
import com.patronusstudio.kaydirkazan.Contract.FirebaseContractJ;
import com.patronusstudio.kaydirkazan.Contract.GameOverContractJ;
import com.patronusstudio.kaydirkazan.Model.AdmobJ;
import com.patronusstudio.kaydirkazan.Model.IFirebaseDatabaseJ;

public class GameOverPresenterJ implements GameOverContractJ.Presenter, FirebaseContractJ.Sorun , AdmobContractJ {

    IFirebaseDatabaseJ firebaseDatabase;
    AdmobJ admobb;

    GameOverContractJ.View mView;

    public GameOverPresenterJ(IFirebaseDatabaseJ firebaseDatabase, AdmobJ admobb) {
        this.firebaseDatabase = firebaseDatabase;
        this.admobb = admobb;
    }
    @Override
    public void setView(GameOverContractJ.View view) {
        this.mView=view;
    }

    @Override
    public void created() {
        mView.bindViews();
        mView.clickControl();
        mView.kontrolEt();
        admobb.initialize(this);
    }

    @Override
    public void rekorKirildi(int dogruSayisi) {
        firebaseDatabase.rekoruYaz(dogruSayisi);
    }

    @Override
    public void cevaplananSoruSayisiniArttir(int soruSayisi) {
        firebaseDatabase.cevaplananSoruSayisiniArttir(soruSayisi);
    }

    @Override
    public void soruHatali(@NotNull String soru) {
        firebaseDatabase.soruHataliysa(soru,this);
    }

    @Override
    public void sesiOynat(int ses) {
        mView.sesiOynat(ses);
    }

    @Override
    public void gelistiriciye_haber(@NotNull String message) {
        mView.toastYazdir(message);
    }

    @Override
    public void reklam_yukle() {
        admobb.odullu_reklami_yukle();
    }

    @Override
    public void videolu_reklam_yuklendi(@NotNull RewardedVideoAd mRewardedVideoAd) {
        mView.videoluReklamiGoster(mRewardedVideoAd);
    }

    @Override
    public void videolu_reklam_izlendi() {
        mView.videoluReklamIzlendi();
    }

    @Override
    public void videolu_reklam_yuklenemedi(@NotNull String message) {
        mView.videoluReklamYuklenemedi(message);
    }



}
