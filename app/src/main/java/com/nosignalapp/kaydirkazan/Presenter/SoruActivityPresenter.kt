package com.nosignalapp.kaydirkazan.Presenter

import com.nosignalapp.kaydirkazan.Contract.SoruContract
import com.nosignalapp.kaydirkazan.Model.SoruActivityModel
import com.nosignalapp.kaydirkazan.Model.soruModel

class SoruActivityPresenter (var model:SoruActivityModel) : SoruContract.Presenter,SoruContract.FirebaseFetch{
    lateinit var mView:SoruContract.View

    override fun setView(view: SoruContract.View) {
        mView=view
    }

    override fun created() {
        mView.bindViews()
        model.soruCek(this)
    }

    override fun listeyiGetir(soruListesi: ArrayList<soruModel>) {
        mView.recyclerSetle(soruListesi)
        mView.cardTasarimi()
    }


    override fun falseAnswer() {
        mView.gameOver()
    }

}