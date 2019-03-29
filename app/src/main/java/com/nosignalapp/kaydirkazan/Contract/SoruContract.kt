package com.nosignalapp.kaydirkazan.Contract

interface SoruContract {

    interface View{

        fun bindViews()

        fun cardTasarimi()

        fun recyclerSetle()

        fun gameOver()

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun falseAnswer()
    }
}