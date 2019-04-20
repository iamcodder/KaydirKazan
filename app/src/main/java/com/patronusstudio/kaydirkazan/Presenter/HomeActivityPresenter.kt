package com.patronusstudio.kaydirkazan.Presenter

import com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Contract.HomeContract
import com.patronusstudio.kaydirkazan.Model.HomeActivityModel
import com.patronusstudio.kaydirkazan.Model.userModel

class HomeActivityPresenter (var model:HomeActivityModel): HomeContract.Presenter,HomeContract.FirebaseFetchCallBack {


    lateinit var mView:HomeContract.View

    override fun setView(view: HomeContract.View) {
        this.mView=view
    }

    override fun created() {
        mView.bindViews()
    }

    override fun fetchData() {
        model.fetchData(this)
        mView.showToast("Veriler çekiliyor...")
    }


    override fun onFetchResult(user: userModel) {
        mView.showPuan(user)
        mView.clickControl()
        mView.showToast("İyi oyunlar...")
    }

    override fun onWritedDb() {
        model.fetchData(this)
    }

    override fun dbyeYaz(mAuth: FirebaseAuth) {
        model.dbyeProfiliYaz(mAuth,this)
    }

    override fun startGameButton() {
        mView.startGame()
    }

    override fun logineGit() {
        mView.startLogin()
    }

    override fun profileYok() {
        mView.profileYok()
    }

    override fun siralamaCekildi(seninSiran: Int, toplamSira: Int) {
        mView.showSort(seninSiran,toplamSira)
    }
}