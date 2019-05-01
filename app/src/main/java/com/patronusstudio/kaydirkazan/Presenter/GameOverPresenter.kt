package com.patronusstudio.kaydirkazan.Presenter

import com.google.android.gms.ads.reward.RewardedVideoAd
import com.patronusstudio.kaydirkazan.Contract.AdmobContract
import com.patronusstudio.kaydirkazan.Contract.FirebaseContract
import com.patronusstudio.kaydirkazan.Contract.GameOverContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase
import com.patronusstudio.kaydirkazan.Model.Admob

class GameOverPresenter(var firebaseDatabase: IFirebaseDatabase,var admobb:Admob) : GameOverContract.Presenter,FirebaseContract.Sorun ,AdmobContract{

    lateinit var mView:GameOverContract.View

    override fun setView(view: GameOverContract.View) {
        this.mView=view
    }

    override fun created() {
        mView.bindViews()
        mView.clickControl()
        mView.kontrolEt()
        admobb.initiliaze(this)
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

    override fun sesiOynat(ses: Int) {
        mView.sesiOynat(ses)
    }

    override fun gelistiriciye_haber(message: String) {
        mView.toastYazdir(message)
    }

    override fun reklam_yukle() {
        admobb.odullu_reklami_yukle()
    }

    override fun videolu_reklam_yuklendi(mRewardedVideoAd: RewardedVideoAd) {
        mView.videoluReklamiGoster(mRewardedVideoAd)
    }

    override fun videolu_reklam_izlendi() {
        mView.videoluReklamIzlendi()
    }

    override fun videolu_reklam_yuklenemedi(message: String) {
        mView.videoluReklamYuklenemedi(message)
    }


}