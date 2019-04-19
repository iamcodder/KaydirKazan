package com.patronusstudio.kaydirkazan.Contract

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth

interface LoginContract {

    interface View {

        fun bindViews()

        fun clicked()

        fun configureGoogleSignIn()

        fun signInWithGoogle()

        fun progressBarActive()

        fun progressBarPassive()

        fun showToast(message: String)

        fun navigationHome()

        fun navigationHomeWithGoogle()

    }

    interface Presenter {

        fun setView(view: View)

        fun created()

        fun buttonLoginClicked(email: String, password: String, auth: FirebaseAuth)

        fun buttonRegisterClicked(email: String, password: String, auth: FirebaseAuth)

        fun buttonForgetPasswordClicked(email:String,auth:FirebaseAuth)

        fun buttonGoogleSignInClicked()

        fun googleSignInDoing(acct: GoogleSignInAccount,auth: FirebaseAuth)

    }

    interface FirebaseLoginCallback{

        fun onLoginResult(message:String,isLogin:Boolean)

        fun onLoginResultWithGoogle(message:String,isLogin:Boolean)

        fun onRegisterResult(message:String,isRegister:Boolean)

        fun onPasswordResetResult(message: String)
    }


}