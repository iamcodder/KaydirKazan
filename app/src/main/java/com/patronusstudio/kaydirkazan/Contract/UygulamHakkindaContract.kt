package com.patronusstudio.kaydirkazan.Contract

interface UygulamHakkindaContract {

    interface View{

        fun bindView()

        fun yazilariGoster(displayName:String?,kayitOlmaTarihi:Long)

    }

    interface Presenter{

        fun onCreated()

        fun setView(view: View)

    }

    interface FirebaseSonuc{

        fun kullaniciSonuclari(displayName:String?,kayitOlmaTarihi:Long)

    }

}