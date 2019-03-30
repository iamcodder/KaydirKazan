package com.nosignalapp.kaydirkazan.Model

import android.util.Log
import com.google.firebase.database.*
import com.nosignalapp.kaydirkazan.Contract.SoruContract
import com.google.firebase.database.DataSnapshot



class SoruActivityModel(soruTuru:String,firebaseDatabase: FirebaseDatabase) {

    var databaseReference: DatabaseReference = firebaseDatabase.reference.child("oyun").child("sorular")
        .child(soruTuru)


    fun soruCek(listeyiDondur:SoruContract.FirebaseFetch){

        var soruListesi:ArrayList<soruModel> = ArrayList()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {

                    val soruNesnesi = ds.getValue(soruModel::class.java)!!

                    soruListesi.add(soruNesnesi)

                }
                listeyiDondur.listeyiGetir(soruListesi)

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        databaseReference.addValueEventListener(postListener)

    }

}