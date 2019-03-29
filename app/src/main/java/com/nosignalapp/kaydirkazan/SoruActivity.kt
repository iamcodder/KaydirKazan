package com.nosignalapp.kaydirkazan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import com.nosignalapp.kaydirkazan.Contract.SoruContract
import com.nosignalapp.kaydirkazan.Model.SoruActivityAdapter
import com.nosignalapp.kaydirkazan.Model.SoruActivityModel
import com.nosignalapp.kaydirkazan.Model.soruModel
import com.nosignalapp.kaydirkazan.Model.userModel
import com.nosignalapp.kaydirkazan.Presenter.SoruActivityPresenter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_soru.*

class SoruActivity : AppCompatActivity(), SoruContract.View,CardStackListener {

    lateinit var presenter:SoruActivityPresenter
    lateinit var mKullanici:userModel
    lateinit var cardStackManager:CardStackLayoutManager
    lateinit var adapter:SoruActivityAdapter
    lateinit var liste:ArrayList<soruModel>
    lateinit var swipeSettings:SwipeAnimationSetting
    var gelenBundle:Bundle? = null
    var ekrandakiKartKonumu:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soru)

        presenter= SoruActivityPresenter(SoruActivityModel())
        presenter.setView(this)
        presenter.created()

    }

    override fun bindViews() {
        gelenBundle=intent.extras
        if(gelenBundle!=null)  mKullanici= gelenBundle!!.getSerializable("kullanıcı bilgisi") as userModel

        liste = ArrayList()
    }


    override fun recyclerSetle() {
        liste.clear()

        liste.add(soruModel("Süleyman sezer kimdir?","Babadır","Produr","Produr"))
        liste.add(soruModel("Kotlin kaç yılında Google desteği almıştır?","2017","2016","2017"))
        liste.add(soruModel("Sahibinden.com ne zaman piyasaya çıkmıştır?","2000","2007","2000"))

        cardStackManager= CardStackLayoutManager(this,this)
        adapter= SoruActivityAdapter(liste)
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
        cardStackManager.setCanScrollVertical(false);
        cardStackManager.setCanScrollHorizontal(true);
    }

    //oyun bittiğinde yapılacak olanlar
    override fun gameOver() {
        activity_soru_cardStackView.removeAllViewsInLayout()
        Toast.makeText(this,"Game Over",Toast.LENGTH_SHORT).show()
    }


    //geçilmiş olan kart
    override fun onCardDisappeared(view: View?, position: Int) {
        Log.d("Süleyman","onCardDisappeared : $position")
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
                        Toast.makeText(this, "Doğru Cevap", Toast.LENGTH_SHORT).show()
                    } else {
                        presenter.falseAnswer()
                        Toast.makeText(this, "Yanlış Cevap", Toast.LENGTH_SHORT).show()
                    }

                "Left" ->
                    if (liste[ekrandakiKartKonumu].solCevap == liste[ekrandakiKartKonumu].dogruCevap) {
                        Toast.makeText(this, "Doğru Cevap", Toast.LENGTH_SHORT).show()
                    } else {
                        presenter.falseAnswer()
                        Toast.makeText(this, "Yanlış Cevap", Toast.LENGTH_SHORT).show()
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


}
