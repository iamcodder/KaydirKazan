package com.patronusstudio.kaydirkazan.Presenter

import android.view.View
import com.patronusstudio.kaydirkazan.Contract.UygulamHakkindaContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase

class UygulamaHakkindaPresenter(var firebaseDatabase: IFirebaseDatabase) : UygulamHakkindaContract.Presenter,UygulamHakkindaContract.FirebaseSonuc{

    lateinit var mView:UygulamHakkindaContract.View

    override fun setView(view: UygulamHakkindaContract.View) {
        this.mView=view
    }

    override fun onCreated() {
        mView.bindView()
        firebaseDatabase.kullaniciBilgileri(this)
    }

    override fun kullaniciSonuclari(displayName: String?, kayitOlmaTarihi: Long) {
        mView.yazilariGoster(displayName,kayitOlmaTarihi)
    }
}