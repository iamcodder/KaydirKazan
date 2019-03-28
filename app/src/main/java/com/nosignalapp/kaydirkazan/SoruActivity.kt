package com.nosignalapp.kaydirkazan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nosignalapp.kaydirkazan.Contract.SoruContract
import com.nosignalapp.kaydirkazan.Model.SoruActivityModel
import com.nosignalapp.kaydirkazan.Model.userModel
import com.nosignalapp.kaydirkazan.Presenter.SoruActivityPresenter

class SoruActivity : AppCompatActivity(), SoruContract.View {

    lateinit var presenter:SoruActivityPresenter
    lateinit var mKullanici:userModel
    var gelenBundle:Bundle? = null

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



    }

    override fun clickControl() {
    }
}
