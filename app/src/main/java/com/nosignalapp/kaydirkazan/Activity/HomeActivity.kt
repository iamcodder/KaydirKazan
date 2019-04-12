package com.nosignalapp.kaydirkazan.Activity

import android.app.ActivityManager
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nosignalapp.kaydirkazan.Contract.HomeContract
import com.nosignalapp.kaydirkazan.Model.HomeActivityModel
import com.nosignalapp.kaydirkazan.Model.userModel
import com.nosignalapp.kaydirkazan.Presenter.HomeActivityPresenter
import com.nosignalapp.kaydirkazan.R
import kotlinx.android.synthetic.main.activity_home.*



class HomeActivity : AppCompatActivity() ,HomeContract.View {

    lateinit var presenter:HomeActivityPresenter
    lateinit var mUser: FirebaseUser
    lateinit var mAuth: FirebaseAuth
    lateinit var mKullanici:userModel
    lateinit var iintent:Intent
    var cikis_sayisi:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mAuth=FirebaseAuth.getInstance()

        presenter=HomeActivityPresenter(HomeActivityModel(mAuth))
        presenter.setView(this)
        presenter.created()
        presenter.fetchDataOnFirebase()

    }

    override fun bindViews() {
        mUser= FirebaseAuth.getInstance().currentUser!!
        mKullanici=userModel()
        iintent= Intent(this, SoruActivity::class.java)
        var animation_drawable:AnimationDrawable= activity_home_constraint.background as AnimationDrawable
        animation_drawable.setEnterFadeDuration(1000)
        animation_drawable.setExitFadeDuration(2000)
        animation_drawable.start()
    }

    override fun clickControl() {

        button_oyna.setOnClickListener {
            presenter.startGameButton()
        }
        button_cikis_yap.setOnClickListener {
            presenter.logineGit()
        }
    }

    override fun showPuan(user: userModel) {
        activity_home_puan.text = "Skor : ${user.yuksekPuan}"
        this.mKullanici=user
    }

    override fun startGame() {
        iintent.putExtra("kullanıcı bilgisi",this.mKullanici)
        startActivity(iintent)
    }

    override fun startLogin() {
        val intent3 = Intent(this,LoginActivity::class.java)
        intent3.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent3)
    }

    override fun onBackPressed() {

//        val intent3 = Intent(Intent.ACTION_MAIN)
//        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        intent3.addCategory(Intent.CATEGORY_HOME)
//        startActivity(intent3)

        if(cikis_sayisi==1){
            cikis_sayisi=0
            finishAffinity()
            System.exit(0)
        }
        else{
            Toast.makeText(this,"Çıkmak için bir kere daha basın",Toast.LENGTH_SHORT).show()
            cikis_sayisi++
        }

    }

}
