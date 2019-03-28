package com.nosignalapp.kaydirkazan

import com.google.firebase.auth.FirebaseAuth

interface LoginContract {

    interface View {

        fun bindViews()

        fun clicked()

        fun progressBarActive()

        fun progressBarPassive()

        fun showToast(message: String)

        fun navigationHome()

    }

    interface Presenter {

        fun setView(view: View)

        fun created()

        fun buttonLoginClicked(email: String, password: String, auth: FirebaseAuth)

        fun buttonRegisterClicked(email: String, password: String, auth: FirebaseAuth)

    }

    interface FirebaseLoginCallback{

        fun onResult(message:String)
    }


}