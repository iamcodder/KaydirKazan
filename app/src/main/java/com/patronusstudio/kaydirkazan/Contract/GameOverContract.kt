package com.patronusstudio.kaydirkazan.Contract

interface GameOverContract {

    interface View{

        fun bindViews()

        fun clickControl()

        fun kontrolEt()

        fun toastYazdir(message: String)

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun rekorKirildi(dogruSayisi: Int)

        fun cevaplananSoruSayisiniArttir(soruSayisi:Int)

        fun soruHatali(soru:String)
    }

    interface FirebaseSonucu{

        fun gelistiriciye_haber(message:String)

    }
}