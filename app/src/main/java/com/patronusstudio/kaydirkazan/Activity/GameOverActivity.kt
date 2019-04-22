package com.patronusstudio.kaydirkazan.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import maes.tech.intentanim.CustomIntent


class GameOverActivity : AppCompatActivity(),GameOverContract.View, RewardedVideoAdListener {
    override fun onRewardedVideoAdClosed() {
        loadRewardedVideoAd()
    }

    override fun onRewardedVideoAdLeftApplication() {
    }

    override fun onRewardedVideoAdLoaded() {
        Toast.makeText(this,"Reklam yüklendi.Tekrar basarak izlemeye geçin.",Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdOpened() {
    }

    override fun onRewardedVideoCompleted() {
    }

    override fun onRewarded(p0: RewardItem?) {
        Toast.makeText(this,"Devam edebilirsiniz.",Toast.LENGTH_SHORT).show()
        finish()
        CustomIntent.customType(this, "up-to-bottom")
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        Log.d("Süleyman",p0.toString())
    }

    var gelenBundle:Bundle? = null
    var dogruSayisi:Int=0
    var rekor:Int=0
    var cevaplananSoruMiktari:Int=0
    var dogruCevap:String?=""
    var soru:String?=""
    lateinit var intent_home:Intent
    lateinit var presenter:GameOverPresenter
    lateinit var mAuth: FirebaseAuth
    private lateinit var mRewardedVideoAd: RewardedVideoAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        MobileAds.initialize(this, "ca-app-pub-1818679104699845~3155151657")

        presenter= GameOverPresenter(GameOverModel())
        presenter.setView(this)
        presenter.created()


        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()
    }
    private fun loadRewardedVideoAd() {

        if(!mRewardedVideoAd.isLoaded){
            mRewardedVideoAd.loadAd("ca-app-pub-1818679104699845/8861470565",
                AdRequest.Builder().build())
        }
    }



    override fun bindViews() {
        gelenBundle=intent.extras
        dogruSayisi = gelenBundle!!.getInt("dogruSayisi")
        rekor = gelenBundle!!.getInt("rekor")
        cevaplananSoruMiktari = gelenBundle!!.getInt("cevaplananSoruMiktari")
        soru= gelenBundle!!.getString("cevaplananSoru")
        dogruCevap= gelenBundle!!.getString("dogruCevap")
        mAuth= FirebaseAuth.getInstance()
        intent_home=Intent(this,HomeActivity::class.java)
    }

    override fun clickControl() {

        game_over_menuye_don.setOnClickListener {
            intent_home.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent_home)
            CustomIntent.customType(this, "right-to-left")
        }

        game_over_devam_et.setOnClickListener {
            if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            }
        }

        game_over_cevap_hatali.setOnClickListener {
            presenter.soruHatali(mAuth,soru)
        }
    }

    override fun toastYazdir(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun kontrolEt() {
        cevaplananSoruMiktari += dogruSayisi
        cevaplananSoruMiktari++
        presenter.increaseRepliesAnswew(cevaplananSoruMiktari,this.mAuth)

        if(dogruCevap.equals("Malesef zaman doldu")){
            activity_gameOver_lottie.setAnimation(R.raw.error)
            activity_game_over_dogru_sonuc.text="\n${dogruCevap.toString()}\n"
            activity_game_over_dogru_sonuc_cardView.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_light))
        }

        else if(this.dogruSayisi>this.rekor){
            activity_gameOver_lottie.setAnimation(R.raw.award)
            activity_game_over_dogru_sonuc.text="\nYanlış cevap vermiş olsanda kendi rekorunu kırdın.\nDoğru cevap ${dogruCevap} olacaktı.\nYinede tebrikler\n"

            activity_game_over_dogru_sonuc_cardView.setBackgroundColor(ContextCompat.getColor(this, R.color.turuncu))
            presenter.beatRecord(dogruSayisi,mAuth)
        }
        else{
            activity_gameOver_lottie.setAnimation(R.raw.error)
            activity_game_over_dogru_sonuc.text="\nYanlış cevap verdin.\nDoğru cevap ${dogruCevap} olacaktı.\n"
            activity_game_over_dogru_sonuc_cardView.setBackgroundColor(ContextCompat.getColor(this, R.color.kirmizi))
        }

    }

    override fun onBackPressed() {

    }

    override fun onPause() {
        super.onPause()
        mRewardedVideoAd.pause(this)
    }

    override fun onResume() {
        super.onResume()
        mRewardedVideoAd.resume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRewardedVideoAd.destroy(this)
    }
}
