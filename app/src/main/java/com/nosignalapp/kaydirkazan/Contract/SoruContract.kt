package com.nosignalapp.kaydirkazan.Contract

interface SoruContract {

    interface View{

        fun bindViews()

        fun clickControl()

    }

    interface Presenter{

        fun setView(view:View)

        fun created()
    }
}