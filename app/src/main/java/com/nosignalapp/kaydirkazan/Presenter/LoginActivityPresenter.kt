package com.nosignalapp.kaydirkazan.Presenter

import com.google.firebase.auth.FirebaseAuth
import com.nosignalapp.kaydirkazan.Contract.LoginContract
import com.nosignalapp.kaydirkazan.Model.LoginActivityModel

//direk class oluştururken constructor tanımlaması yapabiliyoruz.Misal burada olduğu gibi
class LoginActivityPresenter(var model: LoginActivityModel) : LoginContract.Presenter,
    LoginContract.FirebaseLoginCallback {

    //mView ile daha sonra işlemler yapacağız.Örneğin ekranda progress bar gösterme gizleme gibi
    //lateinit olarak tanımlama sebebimizde bunu constructor ile tanımlama yapacağız diyoruz
    //yani değerini sonra değiştirceğiz
    lateinit var mView: LoginContract.View


    override fun setView(view: LoginContract.View) {
        this.mView = view
    }

    override fun created() {
        mView.bindViews()
        //click işlemlerinin yapıldığı kısmı da çalıştırıyoruz
        mView.clicked()
    }

    override fun buttonLoginClicked(email: String, password: String, auth: FirebaseAuth) {
        mView.progressBarActive()

        model.girisYap(email, password, auth,this)

    }



    override fun buttonRegisterClicked(email: String, password: String, auth: FirebaseAuth) {
        mView.progressBarActive()

        model.kayitOl(email, password, auth,this)

    }

    override fun onResult(message: String,isLogin:Boolean) {
        mView.progressBarPassive()

        mView.showToast(message)

        if(isLogin) mView.navigationHome()
    }

}