package com.patronusstudio.kaydirkazan.Presenter

import com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Contract.SoruContract
import com.patronusstudio.kaydirkazan.Model.SoruActivityModel
import com.patronusstudio.kaydirkazan.Model.soruModel
import com.patronusstudio.kaydirkazan.Model.userModel

class SoruActivityPresenter (var model:SoruActivityModel) : SoruContract.Presenter,SoruContract.FirebaseFetch{

    lateinit var mView:SoruContract.View

    override fun setView(view: SoruContract.View) {
        mView=view
    }

    override fun created() {
        mView.bindViews()
        model.butunSorulariCek(this)
        mView.progressShow()
    }

    override fun listeyiGetir(soruListesi: ArrayList<soruModel>) {
        mView.recyclerSetle(soruListesi)
        mView.cardTasarimi()
        mView.progressHide()
    }

    override fun trueAnswer(dogruSayisi:Int) {

        mView.trueAnswerNumber(dogruSayisi)

    }

    override fun falseAnswer() {
        mView.gameOver()
    }

    override fun beatRecord(dogruSayisi: Int, user: userModel, mAuth: FirebaseAuth) {
//        model.rekorKirildi(dogruSayisi,user,mAuth)
    }
}