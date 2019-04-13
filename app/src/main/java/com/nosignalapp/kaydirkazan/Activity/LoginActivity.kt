package com.nosignalapp.kaydirkazan.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nosignalapp.kaydirkazan.Contract.LoginContract
import com.nosignalapp.kaydirkazan.Model.LoginActivityModel
import com.nosignalapp.kaydirkazan.Presenter.LoginActivityPresenter
import com.nosignalapp.kaydirkazan.R
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

//        login_email.setText("me.iamcodder@gmail.com")
//        login_password.setText("1234321")
    }

    override fun clicked() {
        //View tıklama işlemini dinlemeye koyuluyor.
        //Örneğin burada login butonuna basılırsa ;

        login_button.setOnClickListener {
            //Abi bendeki view'da login butonuna tıklandı.Sen sadece bana hangi butona tıklanıldığını
            //söylememi istedin.Kalan işleri sen kendin hallet diyor.
            loginActivityPresenter.buttonLoginClicked(
                login_email.text.toString(),
                login_password.text.toString(),
                mAuth
            )
        }

        register_button.setOnClickListener {
            loginActivityPresenter.buttonRegisterClicked(
                login_email.text.toString(),
                login_password.text.toString(),
                mAuth
            )
        }

        guest_button.setOnClickListener {
            intent=Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun progressBarActive() {

        login_animation.visibility=View.VISIBLE
        login_animation.playAnimation()

    }

    override fun progressBarPassive() {
        login_animation.visibility=View.GONE
        login_animation.cancelAnimation()
    }

    override fun showToast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun navigationHome() {
        intent=Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    override fun onBackPressed() {
        finishAffinity()
        System.exit(0)
    }
}
