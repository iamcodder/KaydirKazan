package com.nosignalapp.kaydirkazan

import com.google.firebase.auth.FirebaseAuth

//direk class oluştururken constructor tanımlaması yapabiliyoruz.Misal burada olduğu gibi
class LoginActivityPresenter(var model: LoginActivityModel) : LoginContract.Presenter {

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

    override fun buton_login_clicked(email: String, password: String, auth: FirebaseAuth) {
        mView.progressBarActive()

        model.giris_yap(email, password, auth)

        mView.progressBarPassive()

    }

    override fun buton_register_clicked(email: String, password: String, auth: FirebaseAuth) {
        mView.progressBarActive()

        model.kayit_ol(email, password, auth)

        mView.progressBarPassive()

    }


}