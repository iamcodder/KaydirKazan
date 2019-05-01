package com.patronusstudio.kaydirkazan.Contract

import com.patronusstudio.kaydirkazan.Model.soruModel

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

        fun sesiOynat(ses:Int)

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun dogruCevap(dogruSayisi:Int)

        fun yanlisCevap()

        fun zamanTukendi()

        fun sesiOynat(ses:Int)

    }

}