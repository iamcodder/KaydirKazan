package com.patronusstudio.kaydirkazan.Contract

import com.google.android.gms.ads.reward.RewardedVideoAd

interface AdmobContract {

    fun videolu_reklam_yuklendi(mRewardedVideoAd: RewardedVideoAd)

    fun videolu_reklam_izlendi()

    fun videolu_reklam_yuklenemedi(message: String)
}