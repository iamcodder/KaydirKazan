package com.nosignalapp.kaydirkazan.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nosignalapp.kaydirkazan.Contract.HomeContract

class HomeActivityModel(var mAuth: FirebaseAuth) {

    private var fDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("oyun").child("kullanicilar")


    fun fetchData(fetchResult: HomeContract.FirebaseFetchCallBack) {

        val postTListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {

                    val newUser: userModel? = ds.getValue(userModel::class.java)


                    if (newUser!=null && newUser.uuid == mAuth.uid) {
                        fetchResult.onFetchResult(newUser)
                    }

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        fDatabase.addValueEventListener(postTListener)
    }

}