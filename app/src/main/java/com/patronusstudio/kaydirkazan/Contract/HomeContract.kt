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

    interface FirebaseFetchCallBack{

        fun kullanici_cekildi(kullanici:userModel)
        fun kullanici_cekilemedi(mesaj:String)
        fun kullanici_cekilemedi_dbde_yok()

        fun kullanici_verileri_dbye_yazildi(mesaj: String)

        fun siralamaCekildi(seninSiran:Int,toplamSira:Int)
    }

}