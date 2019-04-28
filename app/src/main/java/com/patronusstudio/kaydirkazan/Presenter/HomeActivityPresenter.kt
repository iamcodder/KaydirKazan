package com.patronusstudio.kaydirkazan.Presenter

import com.patronusstudio.kaydirkazan.Contract.HomeContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase
import com.patronusstudio.kaydirkazan.Model.userModel

class HomeActivityPresenter (var firebaseDatabase: IFirebaseDatabase): HomeContract.Presenter,HomeContract.FirebaseSonucu {


    lateinit var mView:HomeContract.View

    override fun setView(view: HomeContract.View) {
        this.mView=view
    }

    override fun created() {
        mView.bindViews()
    }


    override fun fetchData() {
        mView.loadingShow()
        firebaseDatabase.kullaniciVerileriniCek(this)
    }

    override fun kullanici_cekildi(kullanici: userModel) {
        mView.hideLoadingShow()
        mView.showPuan(kullanici)
        mView.clickControl()
    }

    override fun kullanici_cekilemedi_dbde_yok() {
        firebaseDatabase.dbyeProfiliYaz(this)
    }

    override fun kullanici_cekilemedi(mesaj: String) {
        mView.mesajGoster(mesaj)
    }

    override fun kullanici_verileri_dbye_yazildi(mesaj: String) {
        firebaseDatabase.kullaniciVerileriniCek(this)
    }

    override fun siralamaCekildi(seninSiran: Int, toplamSira: Int) {
        mView.showSort(seninSiran,toplamSira)
    }


    override fun startGameButton() {
        mView.startGame()
    }

    override fun logineGit() {
        mView.startLogin()
    }

}