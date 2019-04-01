package com.nosignalapp.kaydirkazan.Presenter

import com.google.firebase.auth.FirebaseAuth
import com.nosignalapp.kaydirkazan.Contract.GameOverContract
import com.nosignalapp.kaydirkazan.Model.GameOverModel

class GameOverPresenter(var model:GameOverModel) : GameOverContract.Presenter {

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
}