package com.patronusstudio.kaydirkazan.Model

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener

class Admob (context: Context): RewardedVideoAdListener {

    var mRewardedVideoAd: RewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context)

    init{
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()
    }

    private fun loadRewardedVideoAd() {

        if(!mRewardedVideoAd.isLoaded){
            mRewardedVideoAd.loadAd("ca-app-pub-1818679104699845/8861470565",
                AdRequest.Builder().build())
        }
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
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
    }

}