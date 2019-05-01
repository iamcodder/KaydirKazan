package com.patronusstudio.kaydirkazan.Model

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.patronusstudio.kaydirkazan.Contract.AdmobContract

class Admob(var context: Context) : RewardedVideoAdListener {

    lateinit var admob_sonuc: AdmobContract
    lateinit var mRewardedVideoAd: RewardedVideoAd

    fun initiliaze(admob_sonuc:AdmobContract) {
        this.admob_sonuc = admob_sonuc
        mRewardedVideoAd=MobileAds.getRewardedVideoAdInstance(context)
        mRewardedVideoAd.rewardedVideoAdListener = this
        baslangicta_yukle()
    }

    fun baslangicta_yukle(){
        if (!mRewardedVideoAd.isLoaded) {
            mRewardedVideoAd.loadAd(
                "ca-app-pub-1818679104699845/8861470565",
                AdRequest.Builder().addTestDevice("D239974A1C94D237A5745EC53CF138BE").build()
            )
        }
    }

    fun odullu_reklami_yukle() {
        if (!mRewardedVideoAd.isLoaded) {
            mRewardedVideoAd.loadAd(
                "ca-app-pub-1818679104699845/8861470565",
                AdRequest.Builder().addTestDevice("D239974A1C94D237A5745EC53CF138BE").build()
            )
            reklamiGoster()
        }
        else {
            reklamiGoster()
        }
    }

    fun reklamiGoster() {
        admob_sonuc.videolu_reklam_yuklendi(mRewardedVideoAd)
    }

    override fun onRewardedVideoAdClosed() {
    }

    override fun onRewardedVideoAdLeftApplication() {
    }

    override fun onRewardedVideoAdLoaded() {
    }

    override fun onRewardedVideoAdOpened() {
    }

    override fun onRewardedVideoCompleted() {
    }

    override fun onRewarded(p0: RewardItem?) {
        admob_sonuc.videolu_reklam_izlendi()
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        admob_sonuc.videolu_reklam_yuklenemedi("Error : $p0")
    }

}