package com.patronusstudio.kaydirkazan.Activity

import android.animation.Animator
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import com.airbnb.lottie.LottieResult
import com.google.android.gms.ads.MobileAds
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

            presenter = HomeActivityPresenter(HomeActivityModel(mAuth))
            presenter.setView(this)
            presenter.created()
            presenter.fetchData()


    }


    override fun bindViews() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            mUser = FirebaseAuth.getInstance().currentUser!!
            mKullanici = userModel()
        }

        val animation_drawable: AnimationDrawable = activity_home_constraint.background as AnimationDrawable
        animation_drawable.setEnterFadeDuration(1000)
        animation_drawable.setExitFadeDuration(2000)
        animation_drawable.start()

        lottieAnimationView2.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                Log.d("Sülo","onAnimationRepeat")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.d("Sülo","onAnimationEnd")
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.d("Sülo","onAnimationCancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.d("Sülo","onAnimationStart")
            }

        })
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

    override fun showSort(seninSiran: Int, toplamSira: Int) {
        activity_home_siralama.text="Sıralaman : $seninSiran"
    }

    override fun loadingShow() {
        activity_home_loading.visibility= View.VISIBLE
        activity_home_loading.playAnimation()


    }

    override fun hideLoadingShow() {
        activity_home_loading.visibility= View.GONE
        activity_home_loading.cancelAnimation()
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

    override fun profileYok() {
        presenter.dbyeYaz(mAuth)
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

    override fun onPause() {
        super.onPause()
        lottieAnimationView2.pauseAnimation()
    }

    override fun onResume() {
        super.onResume()
        lottieAnimationView2.playAnimation()
    }

    override fun onStop() {
        super.onStop()
        lottieAnimationView2.pauseAnimation()
    }

    override fun onRestart() {
        super.onRestart()
        lottieAnimationView2.playAnimation()
    }

}
