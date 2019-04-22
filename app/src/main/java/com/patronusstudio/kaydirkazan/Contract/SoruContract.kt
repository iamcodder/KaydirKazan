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

        fun finishTime()

        fun startTimer()

        fun resetTimer()

        fun stopTimer()

        fun startTrueAnim()

        fun startFalseAnim()

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun trueAnswer(dogruSayisi:Int)

        fun falseAnswer()

        fun overTime()

    }

    interface FirebaseFetch {

        fun listeyiGetir(soruListesi:ArrayList<soruModel> )
    }
}