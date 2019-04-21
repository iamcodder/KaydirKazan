package com.patronusstudio.kaydirkazan.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.patronusstudio.kaydirkazan.Contract.GameOverContract
import java.util.*

class GameOverModel {


    fun rekoruYaz(dogruSayisi:Int,mAuth:FirebaseAuth){
        val uuid: String = mAuth.currentUser!!.uid

        val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("yuksekPuan").setValue("$dogruSayisi")

    }

    fun cevaplananSoruSayisiniArttir(soruSayisi:Int,mAuth: FirebaseAuth){
        val uuid: String = mAuth.currentUser!!.uid

        val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("cevaplananSoruSayisi").setValue("$soruSayisi")

    }


    fun soruHataliysa(mAuth:FirebaseAuth,soru:String?,firebaseCallBack:GameOverContract.firebase_Fetch){

        if(soru!=null){
            val uuid: String = mAuth.currentUser!!.uid
            val random:Random= Random()
            val randomSayi:Int=random.nextInt(1000)

            val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
            mDatabase.reference.child("oyun").child("hataliSorular").child(uuid).child("$randomSayi").setValue(soru)

            firebaseCallBack.db_sonucu_yaz("Geliştiriciye haber verildi.Teşekkürler")

        }
        else{
            firebaseCallBack.db_sonucu_yaz("Geliştiriciye haber verildi.Teşekkürler")
        }
    }
}