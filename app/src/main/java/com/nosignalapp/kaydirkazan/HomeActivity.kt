package com.nosignalapp.kaydirkazan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nosignalapp.kaydirkazan.Contract.HomeContract
import com.nosignalapp.kaydirkazan.Model.HomeActivityModel
import com.nosignalapp.kaydirkazan.Model.userModel
import com.nosignalapp.kaydirkazan.Presenter.HomeActivityPresenter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() ,HomeContract.View {

    lateinit var presenter:HomeActivityPresenter
    lateinit var mUser: FirebaseUser
    lateinit var mKullanici:userModel
    lateinit var iintent:Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        presenter=HomeActivityPresenter(HomeActivityModel())
        presenter.setView(this)
        presenter.created()

    }

    override fun bindViews() {
        mUser= FirebaseAuth.getInstance().currentUser!!
        mKullanici=userModel(mUser.email.toString(),mUser.uid)
        iintent= Intent(this,SoruActivity::class.java)
    }

    override fun clickControl() {

        button_oyna.setOnClickListener(View.OnClickListener {
            iintent.putExtra("kullanıcı bilgisi",mKullanici)
            startActivity(iintent)
        })
    }

}
