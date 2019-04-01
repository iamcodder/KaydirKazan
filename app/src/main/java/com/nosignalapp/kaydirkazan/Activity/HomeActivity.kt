package com.nosignalapp.kaydirkazan.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    }

    override fun clickControl() {

        button_oyna.setOnClickListener(View.OnClickListener {
            presenter.startGameButton()
        })
    }

    override fun showPuan(user: userModel) {
        activity_home_puan.text = user.yuksekPuan
        this.mKullanici=user
    }

    override fun startGame() {
        iintent.putExtra("kullanıcı bilgisi",this.mKullanici)
        startActivity(iintent)
    }

    override fun onBackPressed() {

    }

}
