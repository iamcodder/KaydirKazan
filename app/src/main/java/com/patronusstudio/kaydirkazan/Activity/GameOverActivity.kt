package com.patronusstudio.kaydirkazan.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Contract.GameOverContract
import com.patronusstudio.kaydirkazan.Model.GameOverModel
import com.patronusstudio.kaydirkazan.Presenter.GameOverPresenter
import com.patronusstudio.kaydirkazan.R
import kotlinx.android.synthetic.main.activity_game_over.*


class GameOverActivity : AppCompatActivity(),GameOverContract.View, RewardedVideoAdListener {
    override fun onRewardedVideoAdClosed() {
        loadRewardedVideoAd()
    }
    override fun onRewardedVideoAdLeftApplication() {}
    override fun onRewardedVideoAdLoaded() {}
    override fun onRewardedVideoAdOpened() {}
    override fun onRewardedVideoCompleted() {}
    override fun onRewarded(p0: RewardItem?) {
        Toast.makeText(this,"Devam edebiliriz :)",Toast.LENGTH_SHORT).show()
        finish()
    }
    override fun onRewardedVideoStarted() {}
    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        Log.d("Sülo",p0.toString())
    }

    var gelenBundle:Bundle? = null
    var dogruSayisi:Int=0
    var rekor:Int=0
    lateinit var intent_home:Intent
    lateinit var presenter:GameOverPresenter
    lateinit var mAuth: FirebaseAuth
    lateinit var rewardedAd: RewardedVideoAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        MobileAds.initialize(this, "ca-app-pub-1818679104699845~3155151657")
        rewardedAd=MobileAds.getRewardedVideoAdInstance(this)
        rewardedAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()

        presenter= GameOverPresenter(GameOverModel())
        presenter.setView(this)
        presenter.created()
    }

    private fun loadRewardedVideoAd() {
        rewardedAd.loadAd("ca-app-pub-1818679104699845/2281053687",
            AdRequest.Builder().addTestDevice("D239974A1C94D237A5745EC53CF138BE").build())
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

            if (rewardedAd.isLoaded) {
                rewardedAd.show()
            }
            else{
                loadRewardedVideoAd()
            }

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

    override fun onPause() {
        super.onPause()
        rewardedAd.pause(this)
    }

    override fun onResume() {
        super.onResume()
        rewardedAd.resume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        rewardedAd.destroy(this)
    }

    override fun onBackPressed() {

    }
}
