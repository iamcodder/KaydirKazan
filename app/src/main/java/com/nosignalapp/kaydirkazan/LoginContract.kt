package com.nosignalapp.kaydirkazan

import com.google.firebase.auth.FirebaseAuth

interface LoginContract {

    interface View{

        fun bindViews()

        fun clicked()

        fun progressBarActive()

        fun progressBarPassive()

    }

    interface Presenter{

        fun setView(view: View)

        fun created()

        fun buton_login_clicked(email:String, password:String, auth: FirebaseAuth)

        fun buton_register_clicked(email: String, password: String,auth: FirebaseAuth)
    }
}