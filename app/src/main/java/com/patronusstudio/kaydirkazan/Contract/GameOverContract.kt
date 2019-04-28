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

    }

    interface Presenter{

        fun setView(view:View)

        fun setAdmob(admobb: Admob)

        fun created()

        fun rekorKirildi(dogruSayisi: Int)

        fun cevaplananSoruSayisiniArttir(soruSayisi:Int)

        fun soruHatali(soru:String)

        fun reklam_yukle()
    }

    interface FirebaseSonucu{

        fun gelistiriciye_haber(message:String)
    }

    interface AdmobIslemleri{

        fun videolu_reklam_yuklendi(mRewardedVideoAd: RewardedVideoAd)

        fun videolu_reklam_izlendi()

        fun videolu_reklam_yuklenemedi(message: String)

    }
}