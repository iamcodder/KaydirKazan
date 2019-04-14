package com.patronusstudio.kaydirkazan.Contract

import com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Model.soruModel
import com.patronusstudio.kaydirkazan.Model.userModel

interface SoruContract {

    interface View{

        fun bindViews()

        fun cardTasarimi()

        fun recyclerSetle(soruListesi: ArrayList<soruModel>)

        fun trueAnswerNumber(dogruSayisi: Int)

        fun progressShow()

        fun progressHide()

        fun gameOver()

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun trueAnswer(dogruSayisi:Int)

        fun falseAnswer()

        fun beatRecord(dogruSayisi: Int,user: userModel,mAuth:FirebaseAuth)
    }

    interface FirebaseFetch {

        fun listeyiGetir(soruListesi:ArrayList<soruModel> )
    }
}