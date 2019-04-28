package com.patronusstudio.kaydirkazan.Presenter

import com.google.android.gms.ads.reward.RewardedVideoAd
import com.patronusstudio.kaydirkazan.Contract.GameOverContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase
import com.patronusstudio.kaydirkazan.Model.Admob

class GameOverPresenter(var firebaseDatabase: IFirebaseDatabase) : GameOverContract.Presenter,GameOverContract.FirebaseSonucu ,GameOverContract.AdmobIslemleri{

    lateinit var mView:GameOverContract.View
    lateinit var admobb:Admob

    override fun setView(view: GameOverContract.View) {
        this.mView=view
    }

    override fun setAdmob(admobb: Admob) {
        this.admobb=admobb
        admobb.initiliaze(this)
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