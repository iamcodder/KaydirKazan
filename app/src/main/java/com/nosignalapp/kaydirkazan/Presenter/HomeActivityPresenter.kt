package com.nosignalapp.kaydirkazan.Presenter

import com.nosignalapp.kaydirkazan.Contract.HomeContract
import com.nosignalapp.kaydirkazan.Model.HomeActivityModel
import com.nosignalapp.kaydirkazan.Model.userModel

class HomeActivityPresenter (var model:HomeActivityModel): HomeContract.Presenter,HomeContract.FirebaseFetchCallBack {

    lateinit var mView:HomeContract.View

    override fun setView(view: HomeContract.View) {
        this.mView=view
    }

    override fun created() {
        mView.bindViews()
        mView.clickControl()
    }

    override fun fetchDataOnFirebase() {
        model.fetchData(this)
    }

    override fun onFetchResult(user: userModel) {
        mView.showPuan(user)
    }

    override fun startGameButton() {
        mView.startGame()
    }

    override fun logineGit() {
        mView.startLogin()
    }

}