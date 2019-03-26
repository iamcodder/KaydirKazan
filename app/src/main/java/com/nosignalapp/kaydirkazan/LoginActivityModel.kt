package com.nosignalapp.kaydirkazan

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class LoginActivityModel {

    var donus_degeri: Boolean = false

    fun giris_yap(email: String, password: String, mAuth: FirebaseAuth): Boolean {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                donus_degeri = if (it.isSuccessful) {
                    Log.d("Süleyman", "isSuccesfull")
                    true
                } else {
                    Log.d("Süleyman", "isError")
                    false
                }
            }
        return donus_degeri
    }

    fun kayit_ol(email: String, password: String, mAuth: FirebaseAuth): Boolean {


        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                donus_degeri = if (it.isSuccessful) {
                    Log.d("Süleyman", "mmauth isSuccessful")
                    true
                } else {
                    Log.d("Süleyman", "mmauth isFail")
                    false
                }
            }
        return donus_degeri
    }


}