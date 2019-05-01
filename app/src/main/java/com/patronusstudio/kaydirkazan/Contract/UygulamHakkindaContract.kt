package com.patronusstudio.kaydirkazan.Contract

interface UygulamHakkindaContract {

    interface View{

        fun bindView()

        fun yazilariGoster(displayName:String?,kayitOlmaTarihi:Long)

        fun siralamaGoster(seninSiran:Int,toplamSira:Int)

    }

    interface Presenter{

        fun onCreated()

        fun setView(view: View)

    }

}