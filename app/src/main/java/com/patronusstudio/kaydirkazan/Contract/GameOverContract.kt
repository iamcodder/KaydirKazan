package com.patronusstudio.kaydirkazan.Contract

import com.google.firebase.auth.FirebaseAuth

interface GameOverContract {

    interface View{

        fun bindViews()

        fun clickControl()

        fun kontrolEt()

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun beatRecord(dogruSayisi: Int, mAuth: FirebaseAuth)
    }
}