package com.patronusstudio.kaydirkazan.Contract

import com.google.android.gms.ads.reward.RewardedVideoAd
import com.patronusstudio.kaydirkazan.Model.Admob

interface GameOverContract {

    interface View{

        fun bindViews()

        fun clickControl()

        fun kontrolEt()

        fun toastYazdir(message: String)

        fun videoluReklamiGoster(mRewardedVideoAd: RewardedVideoAd)

        fun videoluReklamIzlendi()

        fun videoluReklamYuklenemedi(message: String)

        fun sesiOynat(ses:Int)


    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun rekorKirildi(dogruSayisi: Int)

        fun cevaplananSoruSayisiniArttir(soruSayisi:Int)

        fun soruHatali(soru:String)

        fun reklam_yukle()

        fun sesiOynat(ses:Int)

    }

}