package com.patronusstudio.kaydirkazan.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

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


}