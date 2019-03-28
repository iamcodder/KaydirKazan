package com.nosignalapp.kaydirkazan

import com.google.firebase.auth.FirebaseAuth

class LoginActivityModel{



    fun girisYap(email: String, password: String, mAuth: FirebaseAuth,loginCallBack:LoginContract.FirebaseLoginCallback){

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                if (it.isSuccessful)
                {
                    loginCallBack.onResult("Giriş Yapıldı")
                }
                else
                {
                    loginCallBack.onResult(it.exception?.localizedMessage.toString())
                }
            }
        }
    }




    fun kayitOl(email: String, password: String, mAuth: FirebaseAuth,loginCallBack:LoginContract.FirebaseLoginCallback){


        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        loginCallBack.onResult("Kayıt Yapıldı")
                    }
                     else
                    {
                        loginCallBack.onResult(it.exception?.localizedMessage.toString())
                    }
                }
        }
    }


}