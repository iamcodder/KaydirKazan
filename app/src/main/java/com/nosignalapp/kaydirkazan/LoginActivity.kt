package com.nosignalapp.kaydirkazan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var loginActivityPresenter: LoginActivityPresenter
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginActivityPresenter = LoginActivityPresenter(LoginActivityModel())
        //görüntü oluştu diyoruz ve bu görünümü gönderiyoruz
        loginActivityPresenter.setView(this)
        //loginActivityPresenter'i activity oluşturuldu diyerek uyarıyoruz
        loginActivityPresenter.created()
    }

    override fun bindViews() {
        mAuth = FirebaseAuth.getInstance()
        login_email.setText("me.iamcodder@gmail.com")
        login_password.setText("1234321")
    }

    override fun clicked() {
        //View tıklama işlemini dinlemeye koyuluyor.
        //Örneğin burada login butonuna basılırsa ;

        login_button.setOnClickListener {
            //Abi bendeki view'da login butonuna tıklandı.Sen sadece bana hangi butona tıklanıldığını
            //söylememi istedin.Kalan işleri sen kendin hallet diyor.
            loginActivityPresenter.buton_login_clicked(
                login_email.text.toString(),
                login_password.text.toString(),
                mAuth
            )
        }

        register_button.setOnClickListener {
            loginActivityPresenter.buton_register_clicked(
                login_email.text.toString(),
                login_password.text.toString(),
                mAuth
            )
        }
    }

    override fun progressBarActive() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun progressBarPassive() {
        progress_bar.visibility = View.INVISIBLE
    }
}
