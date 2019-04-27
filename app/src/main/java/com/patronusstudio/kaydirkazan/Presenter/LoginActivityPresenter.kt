package com.patronusstudio.kaydirkazan.Presenter

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Contract.LoginContract
import com.patronusstudio.kaydirkazan.Model.LoginActivityModel

class LoginActivityPresenter(var model: LoginActivityModel) : LoginContract.Presenter,
    LoginContract.FirebaseLoginCallback {

    lateinit var mView: LoginContract.View


    override fun setView(view: LoginContract.View) {
        this.mView = view
    }

    override fun created() {
        mView.bindViews()
        mView.clicked()

        mView.configureGoogleSignIn()
    }

    override fun buttonLoginClicked(email: String, password: String, auth: FirebaseAuth) {
        mView.progressBarActive()

        model.girisYap(email, password, auth,this)
    }

    override fun buttonForgetPasswordClicked(email: String, auth: FirebaseAuth) {
        mView.progressBarActive()
        model.sifremiUnuttum(email,auth,this)
    }

    override fun buttonGoogleSignInClicked() {
        mView.signInWithGoogle()
    }

    override fun googleSignInDoing(acct: GoogleSignInAccount, auth: FirebaseAuth) {
        model.googleGirişi(acct,auth,this)
    }


    override fun onPasswordResetResult(message: String) {
        mView.progressBarPassive()
        mView.showToast(message)
    }


    override fun buttonRegisterClicked(email: String, password: String, auth: FirebaseAuth) {
        mView.progressBarActive()

        model.kayitOl(email, password, auth,this)

    }

    override fun onLoginResult(message: String, isLogin: Boolean) {
        mView.progressBarPassive()

        mView.showToast(message)

        if(isLogin) mView.navigationHome()
    }

    override fun onRegisterResult(message: String,isRegister:Boolean) {

        mView.progressBarPassive()

        mView.showToast(message)

        if (isRegister) mView.navigationHome()
    }

    override fun onLoginResultWithGoogle(message: String, isLogin: Boolean) {
        mView.progressBarPassive()

        mView.showToast(message)

        if(isLogin) mView.navigationHomeWithGoogle()

    }

}