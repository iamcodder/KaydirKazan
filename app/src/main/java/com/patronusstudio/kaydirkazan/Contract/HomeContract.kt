package com.patronusstudio.kaydirkazan.Contract

import com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Model.userModel

interface HomeContract {

    interface View{

        fun bindViews()

        fun clickControl()

        fun showPuan(user: userModel)

        fun showSort(seninSiran:Int,toplamSira:Int)

        fun loadingShow()

        fun hideLoadingShow()

        fun startGame()

        fun startLogin()

        fun profileYok()

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun fetchData()

        fun startGameButton()

        fun logineGit()

    }

    interface FirebaseFetchCallBack{

        fun onFetchResult(user:userModel)

        fun onWritedDb()

        fun profileYok()

        fun dbyeYaz(mAuth: FirebaseAuth)

        fun siralamaCekildi(seninSiran:Int,toplamSira:Int)
    }
}