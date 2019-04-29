package com.patronusstudio.kaydirkazan.Activity

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Contract.UygulamHakkindaContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase
import com.patronusstudio.kaydirkazan.Model.userModel
import com.patronusstudio.kaydirkazan.Presenter.UygulamaHakkindaPresenter
import com.patronusstudio.kaydirkazan.R
import kotlinx.android.synthetic.main.activity_uygulama_hakkinda.*
import java.text.SimpleDateFormat
import java.util.*

class UygulamaHakkinda : AppCompatActivity(),UygulamHakkindaContract.View {

    lateinit var presenter: UygulamaHakkindaPresenter
    lateinit var bundle: Bundle
    lateinit var kullanici:userModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uygulama_hakkinda)

        presenter=UygulamaHakkindaPresenter(IFirebaseDatabase())
        presenter.setView(this)
        presenter.onCreated()

    }

    override fun bindView() {

        bundle=intent.extras

        kullanici= bundle.getSerializable("kullanıcı bilgisi") as userModel

        val animation_drawable: AnimationDrawable = scroolview.background as AnimationDrawable
        animation_drawable.setEnterFadeDuration(1000)
        animation_drawable.setExitFadeDuration(2000)
        animation_drawable.start()
    }


    override fun yazilariGoster(displayName: String?, kayitOlmaTarihi: Long) {

        val dateString = SimpleDateFormat("dd/MM/yyyy").format(Date(kayitOlmaTarihi))

        activity_uygulama_hakkinda_hosgeldin_txt.text=getString(R.string.hosgeldiniz)+" $displayName"
        activity_uygulama_hakkinda_tarih_txt.text="Kayıt olma tarihi : $dateString"
        activity_uygulama_hakkinda_cevaplanan_soru_sayisi_txt.text="Cevaplanan soru sayısı : ${kullanici.cevaplananSoruSayisi}"


    }


}
