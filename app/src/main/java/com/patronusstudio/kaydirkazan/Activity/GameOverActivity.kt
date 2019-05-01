package com.patronusstudio.kaydirkazan.Activity

import android.content.Intent
import android.media.MediaPlayer
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
import com.patronusstudio.kaydirkazan.Constant.FirebaseKey
import com.patronusstudio.kaydirkazan.Constant.OyunIslevi
import com.patronusstudio.kaydirkazan.Contract.GameOverContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase
import com.patronusstudio.kaydirkazan.Model.Admob
import com.patronusstudio.kaydirkazan.Model.soruModel
import com.patronusstudio.kaydirkazan.Model.userModel
import com.patronusstudio.kaydirkazan.Presenter.GameOverPresenter
import com.patronusstudio.kaydirkazan.R
import kotlinx.android.synthetic.main.activity_game_over.*
import maes.tech.intentanim.CustomIntent


class GameOverActivity : AppCompatActivity(), GameOverContract.View {


    var gelenBundle:Bundle? = null
    var dogruSayisi: Int = 0
    var rekor: Int = 0
    var cevaplananSoruMiktari: Int = 0
    var dogruCevap: String = ""
    var soru: String = ""
    lateinit var intent_home: Intent
    lateinit var presenter: GameOverPresenter
    lateinit var mKullanici:userModel
    lateinit var mSoru:soruModel
    lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        presenter = GameOverPresenter(IFirebaseDatabase(), Admob(this))
        presenter.setView(this)
        presenter.created()
    }


    override fun bindViews() {

        gelenBundle = intent.extras
        if(gelenBundle!=null){
            mKullanici= gelenBundle!!.getSerializable("kullanici") as userModel
            mSoru= gelenBundle!!.getSerializable("soru") as soruModel

            rekor=mKullanici.yuksekPuan.toInt()
            cevaplananSoruMiktari=mKullanici.cevaplananSoruSayisi.toInt()
            soru=mSoru.soru
            dogruCevap=mSoru.dogruCevap

            dogruSayisi = gelenBundle!!.getInt("dogruSayisi")
        }
        intent_home = Intent(this, HomeActivity::class.java)
        mediaPlayer= MediaPlayer.create(this,R.raw.lose)
    }

    override fun clickControl() {

        game_over_menuye_don.setOnClickListener {
            intent_home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
            CustomIntent.customType(this, "right-to-left")
            startActivity(intent_home)
        }

        game_over_devam_et.setOnClickListener {
            if(FirebaseKey.IZLENMIS_REKLAM_SAYISI>3){
                FirebaseKey.IZLENMIS_REKLAM_SAYISI=0
                Toast.makeText(this,"Bu oyunda izlenecek reklam kalmadı :)",Toast.LENGTH_SHORT).show()
                intent_home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
                CustomIntent.customType(this, "left-to-right")
                startActivity(intent_home)
            }
            else{
                Toast.makeText(this,"Reklam yükleniyor...",Toast.LENGTH_LONG).show()
                presenter.reklam_yukle()
            }
        }

        game_over_cevap_hatali.setOnClickListener {
            presenter.soruHatali(soru)
        }
    }

    override fun videoluReklamiGoster(mRewardedVideoAd: RewardedVideoAd) {
        mRewardedVideoAd.show()
        FirebaseKey.IZLENMIS_REKLAM_SAYISI++
    }

    override fun videoluReklamIzlendi() {
        OyunIslevi.KAYDIRMA_YAPILABILIR=true
        Toast.makeText(this, "Devam edebilirsiniz.", Toast.LENGTH_SHORT).show()
        CustomIntent.customType(this, "up-to-bottom")
        finish()
    }

    override fun videoluReklamYuklenemedi(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun toastYazdir(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun sesiOynat(ses:Int){

        if(mediaPlayer.isPlaying){
            mediaPlayer.stop()
            mediaPlayer= MediaPlayer.create(this,ses)
            mediaPlayer.start()
        }
        else{
            mediaPlayer= MediaPlayer.create(this,ses)
            mediaPlayer.start()
        }

    }

    override fun kontrolEt() {
        cevaplananSoruMiktari =cevaplananSoruMiktari+ dogruSayisi
        cevaplananSoruMiktari++
        presenter.cevaplananSoruSayisiniArttir(cevaplananSoruMiktari)

        when {
            dogruCevap.equals("Malesef zaman doldu") -> {
                activity_gameOver_lottie.setAnimation(R.raw.error)
                activity_game_over_dogru_sonuc.text = "\n${dogruCevap.toString()}\n"
                activity_game_over_dogru_sonuc_cardView.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.purple_light
                    )
                )
            }
            this.dogruSayisi > this.rekor -> {
                activity_gameOver_lottie.setAnimation(R.raw.award)
                activity_game_over_dogru_sonuc.text =
                    "\nYanlış cevap vermiş olsanda kendi rekorunu kırdın.\nDoğru cevap ${dogruCevap} olacaktı.\nYinede tebrikler\n"

                activity_game_over_dogru_sonuc_cardView.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.turuncu
                    )
                )
                presenter.rekorKirildi(dogruSayisi)
                presenter.sesiOynat(R.raw.win)
            }
            else -> {
                activity_gameOver_lottie.setAnimation(R.raw.error)
                activity_game_over_dogru_sonuc.text = "\nYanlış cevap verdin.\nDoğru cevap ${dogruCevap} olacaktı.\n"
                activity_game_over_dogru_sonuc_cardView.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.kirmizi
                    )
                )
                presenter.sesiOynat(R.raw.lose)
            }
        }

    }

    override fun onBackPressed() {

    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.stop()
    }

}
