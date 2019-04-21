package com.patronusstudio.kaydirkazan.Contract

import com.google.firebase.auth.FirebaseAuth

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

        fun beatRecord(dogruSayisi: Int, mAuth: FirebaseAuth)

        fun increaseRepliesAnswew(soruSayisi:Int,mAuth: FirebaseAuth)

        fun soruHatali(mAuth: FirebaseAuth,soru:String?)
    }

    interface firebase_Fetch{

        fun db_sonucu_yaz(message:String)

    }
}