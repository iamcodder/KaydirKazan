package com.patronusstudio.kaydirkazan.Activity

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.patronusstudio.kaydirkazan.Contract.HomeContract
import com.patronusstudio.kaydirkazan.Model.HomeActivityModel
import com.patronusstudio.kaydirkazan.Model.userModel
import com.patronusstudio.kaydirkazan.Presenter.HomeActivityPresenter
import com.patronusstudio.kaydirkazan.R
import kotlinx.android.synthetic.main.activity_home.*
import maes.tech.intentanim.CustomIntent


class HomeActivity : AppCompatActivity(), HomeContract.View {

    lateinit var presenter: HomeActivityPresenter
    lateinit var mUser: FirebaseUser
    lateinit var mAuth: FirebaseAuth
    lateinit var mKullanici: userModel
    lateinit var iintent: Intent
    var gelenBundle: Bundle? = null
    var cikis_sayisi: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mAuth = FirebaseAuth.getInstance()

        //google giriş yapıp yapmadığını kontrol ediyoruz
        if (intent.extras != null) {
            gelenBundle = intent.extras

            presenter = HomeActivityPresenter(HomeActivityModel(mAuth))
            presenter.setView(this)
            presenter.created()
            presenter.loggedGoogle()

        }

        else {
            presenter = HomeActivityPresenter(HomeActivityModel(mAuth))
            presenter.setView(this)
            presenter.created()
            presenter.fetchDataOnFirebaseWithMAIL()
        }


    }

    override fun bindViews() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            mUser = FirebaseAuth.getInstance().currentUser!!
            mKullanici = userModel()
            Log.d("Sülo", mUser.uid)
        }

        val animation_drawable: AnimationDrawable = activity_home_constraint.background as AnimationDrawable
        animation_drawable.setEnterFadeDuration(1000)
        animation_drawable.setExitFadeDuration(2000)
        animation_drawable.start()
    }

    override fun clickControl() {

        button_oyna.setOnClickListener {
            presenter.startGameButton()
        }

        button_uygulama_hakkinda.setOnClickListener {
            startActivity(Intent(this, UygulamaHakkinda::class.java))
        }

        button_cikis_yap.setOnClickListener {
            presenter.logineGit()
        }
    }

    override fun showPuan(user: userModel) {
        activity_home_puan.text = "Skor : ${user.yuksekPuan}"
        this.mKullanici = user
    }

    override fun startGame() {
        iintent = Intent(this, SoruActivity::class.java)
        iintent.putExtra("kullanıcı bilgisi", this.mKullanici)
        startActivity(iintent)
        CustomIntent.customType(this, "left-to-right")
    }

    override fun startLogin() {
        val intent3 = Intent(this, LoginActivity::class.java)
        intent3.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent3)
        mAuth.signOut()
        CustomIntent.customType(this, "right-to-left")

    }

    override fun onBackPressed() {

        if (cikis_sayisi == 1) {
            cikis_sayisi = 0
            finishAffinity()
            System.exit(0)
        } else {
            Toast.makeText(this, "Çıkmak için bir kere daha basın", Toast.LENGTH_SHORT).show()
            cikis_sayisi++
        }

    }

}
