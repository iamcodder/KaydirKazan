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

        fun mesajGoster(mesaj: String)

    }

    interface Presenter{

        fun setView(view:View)

        fun created()

        fun fetchData()

        fun startGameButton()

        fun logineGit()

    }


}