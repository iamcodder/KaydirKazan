package com.nosignalapp.kaydirkazan.Presenter

import com.nosignalapp.kaydirkazan.Contract.HomeContract
import com.nosignalapp.kaydirkazan.Model.HomeActivityModel

class HomeActivityPresenter (var model:HomeActivityModel): HomeContract.Presenter {

    lateinit var mView:HomeContract.View

    override fun setView(view: HomeContract.View) {
        this.mView=view
    }

    override fun created() {
        mView.bindViews()
        mView.clickControl()
    }


}