package com.patronusstudio.kaydirkazan.Contract

import com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Model.userModel

interface HomeContract {

    interface View{

        fun bindViews()

        fun clickControl()

        fun showPuan(user: userModel)

        fun startGame()

        fun startLogin()

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun loggedGoogle()

        fun fetchDataOnFirebaseWithMAIL()

        fun startGameButton()

        fun logineGit()

    }

    interface FirebaseFetchCallBack{

        fun onFetchResult(user:userModel)

        fun onWritedDb()
    }
}