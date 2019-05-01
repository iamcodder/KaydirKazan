package com.patronusstudio.kaydirkazan.Presenter

import com.patronusstudio.kaydirkazan.Contract.FirebaseContract
import com.patronusstudio.kaydirkazan.Contract.UygulamHakkindaContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase

class UygulamaHakkindaPresenter(var firebaseDatabase: IFirebaseDatabase) : UygulamHakkindaContract.Presenter,FirebaseContract.KullaniciIslemleri.siralamasi,FirebaseContract.KullaniciIslemleri.bilgileri{

    lateinit var mView:UygulamHakkindaContract.View

    override fun setView(view: UygulamHakkindaContract.View) {
        this.mView=view
    }

    override fun onCreated() {
        mView.bindView()
        firebaseDatabase.kullaniciBilgileri(this)

        firebaseDatabase.kullanicilariSirala(this)
    }

    override fun kullaniciSonuclari(displayName: String?, kayitOlmaTarihi: Long) {
        mView.yazilariGoster(displayName,kayitOlmaTarihi)
    }

    override fun siralamaCekildi(seninSiran: Int, toplamSira: Int) {
        mView.siralamaGoster(seninSiran,toplamSira)
    }
}