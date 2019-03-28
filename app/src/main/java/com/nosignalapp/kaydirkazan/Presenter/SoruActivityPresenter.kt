package com.nosignalapp.kaydirkazan.Presenter

import com.nosignalapp.kaydirkazan.Contract.SoruContract
import com.nosignalapp.kaydirkazan.Model.SoruActivityModel

class SoruActivityPresenter (var model:SoruActivityModel) : SoruContract.Presenter{

    lateinit var mView:SoruContract.View

    override fun setView(view: SoruContract.View) {
        mView=view
    }

    override fun created() {
        mView.bindViews()
        mView.clickControl()
    }
}