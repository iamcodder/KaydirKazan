package com.patronusstudio.kaydirkazan.Activity

import android.animation.Animator
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.patronusstudio.kaydirkazan.Constant.OyunIslevi
import com.patronusstudio.kaydirkazan.Contract.SoruContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase
import com.patronusstudio.kaydirkazan.Model.SoruActivityAdapter
import com.patronusstudio.kaydirkazan.Model.soruModel
import com.patronusstudio.kaydirkazan.Model.userModel
import com.patronusstudio.kaydirkazan.Presenter.SoruActivityPresenter
import com.patronusstudio.kaydirkazan.R
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import kotlinx.android.synthetic.main.activity_soru.*
import maes.tech.intentanim.CustomIntent
import java.util.*



class SoruActivity : AppCompatActivity(), SoruContract.View,CardStackListener {

    lateinit var presenter:SoruActivityPresenter
    lateinit var mKullanici:userModel
    lateinit var cardStackManager:CardStackLayoutManager
    lateinit var adapter:SoruActivityAdapter
    lateinit var liste:ArrayList<soruModel>
    lateinit var swipeSettings:SwipeAnimationSetting
    lateinit var mediaPlayer: MediaPlayer
    var gelenBundle:Bundle? = null
    var ekrandakiKartKonumu:Int = 0
    var dogruSayisi:Int=0
    var bomba_sesi:Boolean=false
    var TOPLAM_SURE:Long=16000

    val zaman=zamanlayici(TOPLAM_SURE,1000)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soru)
        presenter= SoruActivityPresenter(IFirebaseDatabase())
        presenter.setView(this)
        presenter.created()


    }

    override fun bindViews() {
        gelenBundle=intent.extras
        if(gelenBundle!=null && gelenBundle!!.getSerializable("kullanıcı bilgisi")!=null)  {
            mKullanici= gelenBundle!!.getSerializable("kullanıcı bilgisi") as userModel
        }
        activity_soru_rekor.text="Rekor : ${mKullanici.yuksekPuan}"

        val animation_drawable: AnimationDrawable = activity_soru_constraint.background as AnimationDrawable
        animation_drawable.setEnterFadeDuration(1000)
        animation_drawable.setExitFadeDuration(2000)
        animation_drawable.start()

        if(!activity_soru_meteor.isAnimating) activity_soru_meteor.playAnimation()
        liste = ArrayList()
        mediaPlayer=MediaPlayer.create(this,R.raw.lose)
    }


    override fun recyclerSetle(soruListesi: ArrayList<soruModel>) {

        this.liste=soruListesi

        cardStackManager= CardStackLayoutManager(this,this)
        adapter= SoruActivityAdapter(this.liste)
        activity_soru_cardStackView.layoutManager=cardStackManager
        activity_soru_cardStackView.adapter=adapter
    }

    override fun cardTasarimi() {
        //kaydırmadaki bekleme süresi..sayı ne kadar azsa o kadar hızlı oluyor
        swipeSettings=SwipeAnimationSetting.Builder()
            .setDuration(1500)
            .setInterpolator(AccelerateInterpolator())
            .build()
        cardStackManager.setSwipeAnimationSetting(swipeSettings)

        //arkada gözükmesini istediğimiz eleman sayısı
        cardStackManager.setVisibleCount(1)

        //kaydırırken kaç derecelik dönme olacağını yazıyoruz
        cardStackManager.setMaxDegree(90f)

        //kaydırırken ne kadarlık kaydırmanın aktif olacağını yazıyrouz
        cardStackManager.setSwipeThreshold(0.2f)

        //dikeyde kaydırılmayacağını , yatayda kaydırma olacağını belirtiyoruz
        cardStackManager.setCanScrollVertical(false)
        cardStackManager.setCanScrollHorizontal(true)
    }

    override fun startTimer() {
        zaman.start()
        activity_soru_bomba.playAnimation()
    }

    override fun resetTimer() {
        zaman.cancel()
        zaman.start()
        activity_soru_bomba.cancelAnimation()
        activity_soru_bomba.playAnimation()
    }

    override fun stopTimer() {
        zaman.cancel()
        activity_soru_bomba.cancelAnimation()
    }

    override fun startTrueAnim() {
        activity_soru_animasyon_sonucu.setAnimation(R.raw.trueanim)
        activity_soru_animasyon_sonucu.visibility=View.VISIBLE
        activity_soru_animasyon_sonucu.playAnimation()
        activity_soru_animasyon_sonucu.addAnimatorListener(object:Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                activity_soru_animasyon_sonucu.visibility=View.GONE
                activity_soru_animasyon_sonucu.pauseAnimation()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

    }

    override fun startFalseAnim() {
        activity_soru_animasyon_sonucu.setAnimation(R.raw.falseanim)
        activity_soru_animasyon_sonucu.visibility=View.VISIBLE
        activity_soru_animasyon_sonucu.playAnimation()
        activity_soru_animasyon_sonucu.addAnimatorListener(object:Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                activity_soru_animasyon_sonucu.visibility=View.GONE
                activity_soru_animasyon_sonucu.pauseAnimation()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

    }

    override fun progressShow() {
        activity_soru_loading_infinity_bar.visibility=View.VISIBLE
        activity_soru_loading_infinity_bar.playAnimation()
        activity_soru_meteor.cancelAnimation()
        activity_soru_meteor.visibility=View.GONE
    }

    override fun progressHide() {
        activity_soru_loading_infinity_bar.visibility=View.GONE
        activity_soru_loading_infinity_bar.cancelAnimation()
        activity_soru_meteor.playAnimation()
        activity_soru_meteor.visibility=View.VISIBLE
    }

    override fun trueAnswerNumber(dogruSayisi: Int) {
        activity_soru_puan_PUAN.text="Skor : $dogruSayisi"
        mediaPlayer.stop()
        bomba_sesi=false
    }

    override fun sesiOynat(ses: Int) {
        if(mediaPlayer.isPlaying){
            mediaPlayer.stop()
            mediaPlayer=MediaPlayer.create(this,ses)
            mediaPlayer.start()
            bomba_sesi=true
        }
        else{
            mediaPlayer=MediaPlayer.create(this,ses)
            mediaPlayer.start()
            bomba_sesi=true
        }
    }

    //oyun bittiğinde yapılacak olanlar
    override fun gameOver() {
        mediaPlayer.stop()
        bomba_sesi=false
        OyunIslevi.KAYDIRMA_YAPILABILIR=false
        val intent=Intent(this,GameOverActivity::class.java)
        intent.putExtra("dogruSayisi",dogruSayisi.toInt())
        intent.putExtra("kullanici",mKullanici)
        intent.putExtra("soru",liste[ekrandakiKartKonumu])
        CustomIntent.customType(this, "up-to-bottom")
        startActivity(intent)
    }

    override fun finishTime() {
        mediaPlayer.stop()
        bomba_sesi=false
        OyunIslevi.KAYDIRMA_YAPILABILIR=false
        val intent=Intent(this,GameOverActivity::class.java)
        intent.putExtra("dogruSayisi",dogruSayisi.toInt())
        intent.putExtra("kullanici",mKullanici)
        intent.putExtra("soru",liste[ekrandakiKartKonumu])
        CustomIntent.customType(this, "up-to-bottom")
        startActivity(intent)
    }



    //geçilmiş olan kart
    override fun onCardDisappeared(view: View?, position: Int) {
    }

    //kartın kaydırılma oranı
    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
        if(direction!=null && OyunIslevi.KAYDIRMA_YAPILABILIR)
        {
            when(direction.name) {
                "Right" ->
                    if (liste[ekrandakiKartKonumu].sagCevap == liste[ekrandakiKartKonumu].dogruCevap) {
                        dogruSayisi++
                        presenter.dogruCevap(this.dogruSayisi)
                    } else {
                        presenter.yanlisCevap()
                    }

                "Left" ->
                    if (liste[ekrandakiKartKonumu].solCevap == liste[ekrandakiKartKonumu].dogruCevap) {
                        dogruSayisi++
                        presenter.dogruCevap(this.dogruSayisi)
                    } else {
                        presenter.yanlisCevap()
                    }
            }
        }
    }

    override fun onCardCanceled() {
    }

    //o an ekranda görünen kart
    override fun onCardAppeared(view: View?, position: Int) {
        this.ekrandakiKartKonumu=position
    }

    override fun onCardRewound() {
    }


    override fun onPause() {
        super.onPause()
        activity_soru_meteor.cancelAnimation()
        activity_soru_loading_infinity_bar.cancelAnimation()
        zaman.cancel()
        activity_soru_bomba.cancelAnimation()
        mediaPlayer.stop()
    }

    override fun onStop() {
        super.onStop()
        activity_soru_meteor.cancelAnimation()
        activity_soru_loading_infinity_bar.cancelAnimation()
        zaman.cancel()
        activity_soru_bomba.cancelAnimation()
        mediaPlayer.stop()
    }

    override fun onResume() {
        super.onResume()
        activity_soru_meteor.playAnimation()
        zaman.start()
        activity_soru_bomba.playAnimation()
    }

    override fun onRestart() {
        super.onRestart()
        activity_soru_meteor.cancelAnimation()
        zaman.start()
        activity_soru_bomba.playAnimation()
    }

    inner class zamanlayici(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            presenter.zamanTukendi()
        }

        override fun onTick(millisUntilFinished: Long) {
           if(millisUntilFinished/1000<=5 && !bomba_sesi){
               presenter.sesiOynat(R.raw.timer)
           }
        }
    }

}
