package com.patronusstudio.kaydirkazan.Presenter

import com.patronusstudio.kaydirkazan.Contract.SoruContract
import com.patronusstudio.kaydirkazan.Model.SoruActivityModel
import com.patronusstudio.kaydirkazan.Model.soruModel

class SoruActivityPresenter(var model: SoruActivityModel) : SoruContract.Presenter, SoruContract.FirebaseFetch {

    lateinit var mView: SoruContract.View

    override fun setView(view: SoruContract.View) {
        mView = view
    }

    override fun created() {
        mView.bindViews()
        model.butunSorulariCek(this)
        mView.progressShow()
        mView.stopTimer()
    }

    override fun listeyiGetir(soruListesi: ArrayList<soruModel>) {
        mView.recyclerSetle(soruListesi)
        mView.cardTasarimi()
        mView.progressHide()
        mView.startTimer()
    }

    override fun trueAnswer(dogruSayisi: Int) {

        mView.trueAnswerNumber(dogruSayisi)
        mView.resetTimer()

    }

    override fun falseAnswer() {
        mView.gameOver()
        mView.stopTimer()
    }

    override fun overTime() {
        mView.finishTime()
    }

}