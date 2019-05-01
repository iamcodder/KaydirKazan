package com.patronusstudio.kaydirkazan.Contract

import com.patronusstudio.kaydirkazan.Model.soruModel
import com.patronusstudio.kaydirkazan.Model.userModel

interface FirebaseContract {


    interface KullaniciIslemleri{
        fun kullanici_cekildi(kullanici: userModel)
        fun kullanici_cekilemedi(mesaj:String)
        fun kullanici_cekilemedi_dbde_yok()
        fun kullanici_verileri_dbye_yazildi(mesaj: String)

        interface siralamasi{
            fun siralamaCekildi(seninSiran:Int,toplamSira:Int)
        }
        interface bilgileri{
            fun kullaniciSonuclari(displayName:String?,kayitOlmaTarihi:Long)
        }
    }


    interface Sorun{
        fun gelistiriciye_haber(message:String)
    }

    interface SoruListesi{
        fun soruListesiniDondur(soruListesi: ArrayList<soruModel>)
    }

}