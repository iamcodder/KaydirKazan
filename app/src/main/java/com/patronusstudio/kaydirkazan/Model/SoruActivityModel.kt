package com.patronusstudio.kaydirkazan.Model

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.patronusstudio.kaydirkazan.Contract.SoruContract
import com.google.firebase.database.DataSnapshot
import java.util.*


class SoruActivityModel(soruTuru:String,firebaseDatabase: FirebaseDatabase) {

    var databaseReference: DatabaseReference = firebaseDatabase.reference.child("oyun").child("sorular")
        .child(soruTuru)


    fun butunSorulariCek(listeyiDondur:SoruContract.FirebaseFetch){

        try {
            var soruListesi:ArrayList<soruModel> = ArrayList()

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (ds in dataSnapshot.children) {

                        val soruNesnesi = ds.getValue(soruModel::class.java)

                        if(soruNesnesi!=null)
                            soruListesi.add(soruNesnesi)

                    }
                    soruListesiRandom(soruListesi,listeyiDondur)

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }
            databaseReference.addValueEventListener(postListener)
        }
        catch (e:Exception){
            Log.d("Sülo",e.localizedMessage)
        }

    }

    fun soruListesiRandom(soruListesi:ArrayList<soruModel>,listeyiDondur:SoruContract.FirebaseFetch){

        var dondurulecekListe:ArrayList<soruModel> = ArrayList<soruModel>()
        var soruListesi_child_sayisi:Int=soruListesi.size

        for(i in 0 until soruListesi.size){

            var randomSayi = (0..soruListesi_child_sayisi).random()

            dondurulecekListe.add(soruListesi[randomSayi])
            soruListesi.removeAt(randomSayi)

            soruListesi_child_sayisi=soruListesi.size-1
            if(soruListesi_child_sayisi==-1){
                soruListesi_child_sayisi=0
            }
        }
        listeyiDondur.listeyiGetir(dondurulecekListe)

    }

}