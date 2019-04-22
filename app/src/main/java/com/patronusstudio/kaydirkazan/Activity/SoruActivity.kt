package com.patronusstudio.kaydirkazan.Activity

import android.animation.Animator
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.patronusstudio.kaydirkazan.Contract.SoruContract
import com.patronusstudio.kaydirkazan.Model.SoruActivityAdapter
import com.patronusstudio.kaydirkazan.Model.SoruActivityModel
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
    lateinit var mAuth:FirebaseAuth
    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    var gelenBundle:Bundle? = null
    var ekrandakiKartKonumu:Int = 0
    var dogruSayisi:Int=0

    var TOPLAM_SURE:Long=16000

    val zaman=zamanlayici(TOPLAM_SURE,1000)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soru)



        var isim:String=""
        val ran = Random()
        val x = ran.nextInt(5)

        when(x){
            0 -> isim="rekorlar"
            1 -> isim="neZaman"
            2 -> isim="genelKultur"
            3 -> isim="tahminEt"
            4 -> isim="tahminEt"
        }

        presenter= SoruActivityPresenter(SoruActivityModel(isim,firebaseDatabase))
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

        liste = ArrayList()

        mAuth= FirebaseAuth.getInstance()

        if(!activity_soru_meteor.isAnimating) activity_soru_meteor.playAnimation()


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

    }

    //oyun bittiğinde yapılacak olanlar
    override fun gameOver() {
        val intent=Intent(this,GameOverActivity::class.java)
        intent.putExtra("dogruSayisi",dogruSayisi.toInt())
        intent.putExtra("rekor",this.mKullanici.yuksekPuan.toInt())
        intent.putExtra("cevaplananSoruMiktari",this.mKullanici.cevaplananSoruSayisi.toInt())
        intent.putExtra("cevaplananSoru",""+liste[ekrandakiKartKonumu].soru)
        intent.putExtra("dogruCevap",this.liste[ekrandakiKartKonumu].dogruCevap.toString())
        startActivity(intent)
        CustomIntent.customType(this, "up-to-bottom")

    }

    override fun finishTime() {
        val intent=Intent(this,GameOverActivity::class.java)
        intent.putExtra("dogruSayisi",dogruSayisi.toInt())
        intent.putExtra("rekor",this.mKullanici.yuksekPuan.toInt())
        intent.putExtra("cevaplananSoruMiktari",this.mKullanici.cevaplananSoruSayisi.toInt())
        intent.putExtra("cevaplananSoru",""+liste[ekrandakiKartKonumu].soru)
        intent.putExtra("dogruCevap","Malesef zaman doldu")
        startActivity(intent)
        CustomIntent.customType(this, "up-to-bottom")
    }

    //geçilmiş olan kart
    override fun onCardDisappeared(view: View?, position: Int) {
    }

    //kartın kaydırılma oranı
    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
        if(direction!=null)
        {
            when(direction.name) {
                "Right" ->
                    if (liste[ekrandakiKartKonumu].sagCevap == liste[ekrandakiKartKonumu].dogruCevap) {
                        dogruSayisi++
                        presenter.trueAnswer(this.dogruSayisi)
                    } else {
                        presenter.falseAnswer()
                    }

                "Left" ->
                    if (liste[ekrandakiKartKonumu].solCevap == liste[ekrandakiKartKonumu].dogruCevap) {
                        dogruSayisi++
                        presenter.trueAnswer(this.dogruSayisi)
                    } else {
                        presenter.falseAnswer()
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
    }

    override fun onStop() {
        super.onStop()
        activity_soru_meteor.cancelAnimation()
        activity_soru_loading_infinity_bar.cancelAnimation()
        zaman.cancel()
        activity_soru_bomba.cancelAnimation()
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
            presenter.overTime()
        }

        override fun onTick(millisUntilFinished: Long) {
        }
    }

}
