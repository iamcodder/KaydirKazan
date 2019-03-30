package com.nosignalapp.kaydirkazan.Contract

import com.nosignalapp.kaydirkazan.Model.userModel

interface HomeContract {

    interface View{

        fun bindViews()

        fun clickControl()

        fun showPuan(user: userModel)

        fun startGame()

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun fetchDataOnFirebase()

        fun startGameButton()

    }

    interface FirebaseFetchCallBack{

        fun onFetchResult(user:userModel)

    }
}