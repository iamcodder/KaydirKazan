package com.patronusstudio.kaydirkazan.Model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.patronusstudio.kaydirkazan.Contract.HomeContract

class HomeActivityModel(var mAuth: FirebaseAuth) {

    private var fDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("oyun").child("kullanicilar")



    fun fetchData(fetchResult: HomeContract.FirebaseFetchCallBack) {

        var varMi:Boolean=false

        val postTListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {
                    val newUser: userModel? = ds.getValue(userModel::class.java)

                    if (newUser!=null && newUser.uuid == mAuth.uid) {
                        fetchResult.onFetchResult(newUser)
                        varMi=true
                    }
                }
                if(varMi==false){
                    fetchResult.profileYok()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }

        }

        fDatabase.addValueEventListener(postTListener)
    }


    fun dbyeProfiliYaz(mAuth: FirebaseAuth,loginCallBack: HomeContract.FirebaseFetchCallBack){

        if(mAuth.currentUser!=null) {

            val fUser=mAuth.currentUser

            val uuid: String = fUser!!.uid
            val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()



            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("email").setValue(mAuth.currentUser!!.email)
            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("yuksekPuan").setValue("0")
            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("cevaplananSoruSayisi").setValue("0")
            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("uuid").setValue(uuid)

            loginCallBack.onWritedDb()
        }
    }

}