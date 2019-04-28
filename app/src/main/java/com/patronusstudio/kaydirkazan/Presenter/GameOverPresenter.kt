package com.patronusstudio.kaydirkazan.Presenter

import com.patronusstudio.kaydirkazan.Contract.GameOverContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase

class GameOverPresenter(var firebaseDatabase: IFirebaseDatabase) : GameOverContract.Presenter,GameOverContract.FirebaseSonucu {

    lateinit var mView:GameOverContract.View

    override fun setView(view: GameOverContract.View) {
        this.mView=view
    }

    override fun created() {
        mView.bindViews()
        mView.clickControl()
        mView.kontrolEt()
    }

    override fun rekorKirildi(dogruSayisi: Int) {
        firebaseDatabase.rekoruYaz(dogruSayisi)
    }

    override fun cevaplananSoruSayisiniArttir(soruSayisi: Int) {
        firebaseDatabase.cevaplananSoruSayisiniArttir(soruSayisi)
    }

    override fun soruHatali(soru: String) {
        firebaseDatabase.soruHataliysa(soru,this)
    }

    override fun gelistiriciye_haber(message: String) {
        mView.toastYazdir(message)
    }


}