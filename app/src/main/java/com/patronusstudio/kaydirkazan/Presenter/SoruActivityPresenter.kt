package com.patronusstudio.kaydirkazan.Presenter

import com.patronusstudio.kaydirkazan.Contract.SoruContract
import com.patronusstudio.kaydirkazan.Mode.IFirebaseDatabase
import com.patronusstudio.kaydirkazan.Model.soruModel

class SoruActivityPresenter(var firebaseDatabase: IFirebaseDatabase) : SoruContract.Presenter, SoruContract.FirebaseSonuc {

    lateinit var mView: SoruContract.View

    override fun setView(view: SoruContract.View) {
        mView = view }

    override fun created() {
        mView.bindViews()
        firebaseDatabase.sorulari_cek(this)
        mView.progressShow()
        mView.stopTimer() }

    override fun soruListesiniDondur(soruListesi: ArrayList<soruModel>) {
        mView.recyclerSetle(soruListesi)
        mView.cardTasarimi()
        mView.progressHide()
        mView.startTimer() }

    override fun dogruCevap(dogruSayisi: Int) {
        mView.startTrueAnim()
        mView.trueAnswerNumber(dogruSayisi)
        mView.resetTimer() }

    override fun yanlisCevap() {
        mView.startFalseAnim()
        mView.gameOver()
        mView.stopTimer() }

    override fun zamanTukendi() {
        mView.finishTime() }

}