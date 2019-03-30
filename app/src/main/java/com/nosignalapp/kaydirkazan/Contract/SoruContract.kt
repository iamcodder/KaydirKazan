package com.nosignalapp.kaydirkazan.Contract

import com.nosignalapp.kaydirkazan.Model.soruModel

interface SoruContract {

    interface View{

        fun bindViews()

        fun cardTasarimi()

        fun recyclerSetle(soruListesi: ArrayList<soruModel>)

        fun gameOver()

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun falseAnswer()
    }

    interface FirebaseFetch {

        fun listeyiGetir(soruListesi:ArrayList<soruModel> )
    }
}