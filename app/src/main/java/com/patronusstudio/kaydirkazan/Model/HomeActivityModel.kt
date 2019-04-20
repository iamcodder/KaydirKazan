package com.patronusstudio.kaydirkazan.Model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.patronusstudio.kaydirkazan.Contract.HomeContract

class HomeActivityModel(var mAuth: FirebaseAuth) {

    private var fDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("oyun").child("kullanicilar")

    private lateinit var user : userModel

    fun fetchData(fetchResult: HomeContract.FirebaseFetchCallBack) {

        var varMi: Boolean = false
        val liste: ArrayList<userModel> = ArrayList()


        val postTListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {
                    val newUser: userModel? = ds.getValue(userModel::class.java)

                    if (newUser != null) {
                        liste.add(newUser)

                        if (newUser.uuid == mAuth.uid) {
                            fetchResult.onFetchResult(newUser)
                            user=newUser
                            varMi = true
                        }
                    }
                }
                if (varMi == false) {
                    fetchResult.profileYok()
                }
                sirala(liste,user,fetchResult)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }

        }

        fDatabase.addValueEventListener(postTListener)
    }


    fun dbyeProfiliYaz(mAuth: FirebaseAuth, loginCallBack: HomeContract.FirebaseFetchCallBack) {

        if (mAuth.currentUser != null) {

            val fUser = mAuth.currentUser

            val uuid: String = fUser!!.uid
            val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()



            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("email")
                .setValue(mAuth.currentUser!!.email)
            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("yuksekPuan").setValue("0")
            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("cevaplananSoruSayisi")
                .setValue("0")
            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("uuid").setValue(uuid)

            loginCallBack.onWritedDb()
        }
    }


    fun sirala(liste: ArrayList<userModel>,user:userModel,fetchResult: HomeContract.FirebaseFetchCallBack) {

        var siralama:Int=0

        for(i in 0 until liste.size){
            if(liste[i].yuksekPuan.toInt()>= user.yuksekPuan.toInt()){
                siralama++
            }
        }
        fetchResult.siralamaCekildi(siralama,liste.size-1)
        Log.d("Sülo siralama",""+siralama)
        liste.clear()


    }
}