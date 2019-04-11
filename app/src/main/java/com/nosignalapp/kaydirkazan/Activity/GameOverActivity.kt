package com.nosignalapp.kaydirkazan.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.nosignalapp.kaydirkazan.Contract.GameOverContract
import com.nosignalapp.kaydirkazan.Model.GameOverModel
import com.nosignalapp.kaydirkazan.Presenter.GameOverPresenter
import com.nosignalapp.kaydirkazan.R
import kotlinx.android.synthetic.main.activity_game_over.*


class GameOverActivity : AppCompatActivity(),GameOverContract.View {

    var gelenBundle:Bundle? = null
    var dogruSayisi:Int=0
    var rekor:Int=0
    lateinit var intent_home:Intent
    lateinit var intent_soru:Intent
    lateinit var presenter:GameOverPresenter
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        presenter= GameOverPresenter(GameOverModel())
        presenter.setView(this)
        presenter.created()
    }


    override fun bindViews() {
        gelenBundle=intent.extras
        dogruSayisi = gelenBundle!!.getInt("dogruSayisi")
        rekor = gelenBundle!!.getInt("rekor")
        mAuth= FirebaseAuth.getInstance()
        intent_home=Intent(this,HomeActivity::class.java)
    }

    override fun clickControl() {

        game_over_menuye_don.setOnClickListener {
            intent_home.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent_home)
        }

        game_over_devam_et.setOnClickListener {
            finish()
        }

    }


    override fun kontrolEt() {
        if(this.dogruSayisi>this.rekor){
            activity_gameOver_lottie.setAnimation(R.raw.award)
            presenter.beatRecord(dogruSayisi,mAuth)

        }

        else{
            activity_gameOver_lottie.setAnimation(R.raw.error)
        }

    }

    override fun onBackPressed() {

    }
}
