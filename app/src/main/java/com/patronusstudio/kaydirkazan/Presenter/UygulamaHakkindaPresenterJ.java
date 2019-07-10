package com.patronusstudio.kaydirkazan.Presenter;

import com.google.firebase.database.annotations.NotNull;
import com.patronusstudio.kaydirkazan.Contract.FirebaseContractJ;
import com.patronusstudio.kaydirkazan.Contract.UygulamHakkindaContractJ;
import com.patronusstudio.kaydirkazan.Model.IFirebaseDatabaseJ;

public class UygulamaHakkindaPresenterJ implements  UygulamHakkindaContractJ.Presenter,FirebaseContractJ.KullaniciIslemleri.siralamasi, FirebaseContractJ.KullaniciIslemleri.bilgileri {

    private IFirebaseDatabaseJ firebaseDatabase;
    private UygulamHakkindaContractJ.View mView;

    public UygulamaHakkindaPresenterJ(IFirebaseDatabaseJ firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    public void setView(@NotNull UygulamHakkindaContractJ.View view) {
        this.mView=view;
    }

    @Override
    public void onCreated() {
        mView.bindView();
        firebaseDatabase.kullaniciBilgileri(this);
        firebaseDatabase.kullanicilariSirala(this);
    }

    @Override
    public void siralamaCekildi(int seninSiran, int toplamSira) {
        mView.siralamaGoster(seninSiran,toplamSira);
    }

    @Override
    public void kullaniciSonuclari(String displayName, Long kayitOlmaTarihi) {
        mView.yazilariGoster(displayName,kayitOlmaTarihi);
    }
}
