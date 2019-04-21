package com.patronusstudio.kaydirkazan.Presenter

import  com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Contract.GameOverContract
import com.patronusstudio.kaydirkazan.Model.GameOverModel

class GameOverPresenter(var model:GameOverModel) : GameOverContract.Presenter,GameOverContract.firebase_Fetch {

    lateinit var mView:GameOverContract.View

    override fun setView(view: GameOverContract.View) {
        this.mView=view
    }

    override fun created() {
        mView.bindViews()
        mView.clickControl()
        mView.kontrolEt()
    }

    override fun beatRecord(dogruSayisi: Int, mAuth: FirebaseAuth) {
        model.rekoruYaz(dogruSayisi,mAuth)
    }

    override fun increaseRepliesAnswew(soruSayisi: Int, mAuth: FirebaseAuth) {
        model.cevaplananSoruSayisiniArttir(soruSayisi,mAuth)
    }

    override fun soruHatali(mAuth: FirebaseAuth, soru: String?) {
        model.soruHataliysa(mAuth,soru,this)
    }

    override fun db_sonucu_yaz(message: String) {
        mView.toastYazdir(message)
    }

}