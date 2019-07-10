package com.patronusstudio.kaydirkazan.Presenter;

import com.google.firebase.database.annotations.NotNull;
import com.patronusstudio.kaydirkazan.Contract.FirebaseContractJ;
import com.patronusstudio.kaydirkazan.Contract.HomeContractJ;
import com.patronusstudio.kaydirkazan.Model.IFirebaseDatabaseJ;
import com.patronusstudio.kaydirkazan.Model.userModelJ;

public class HomeActivityPresenterJ implements HomeContractJ.Presenter,FirebaseContractJ.KullaniciIslemleri, FirebaseContractJ.KullaniciIslemleri.siralamasi {

    private IFirebaseDatabaseJ firebaseDatabase;

    private HomeContractJ.View mView;

    public HomeActivityPresenterJ(IFirebaseDatabaseJ firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    public void setView(HomeContractJ.View view) {
        this.mView=view;
    }

    @Override
    public void created() {
        mView.bindViews();
    }

    @Override
    public void fetchData() {
        mView.loadingShow();
        firebaseDatabase.kullaniciVerileriniCek(this,this);
    }

    @Override
    public void kullanici_cekildi(@NotNull userModelJ kullanici) {
        mView.hideLoadingShow();
        mView.showPuan(kullanici);
        mView.clickControl();
    }

    @Override
    public void kullanici_cekilemedi(@NotNull String mesaj) {
        mView.mesajGoster(mesaj);
    }

    @Override
    public void kullanici_cekilemedi_dbde_yok() {
        firebaseDatabase.dbyeProfiliYaz(this);
    }

    @Override
    public void kullanici_verileri_dbye_yazildi(@NotNull String mesaj) {
        firebaseDatabase.kullaniciVerileriniCek(this,this);
    }

    @Override
    public void siralamaCekildi(int seninSiran, int toplamSira) {
        mView.showSort(seninSiran,toplamSira);
    }


    @Override
    public void startGameButton() {
        mView.startGame();
    }

    @Override
    public void logineGit() {
        mView.startLogin();
    }
}
