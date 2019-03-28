package com.nosignalapp.kaydirkazan.Model

import com.google.firebase.auth.FirebaseAuth
import com.nosignalapp.kaydirkazan.Contract.LoginContract

class LoginActivityModel{



    fun girisYap(email: String, password: String, mAuth: FirebaseAuth,loginCallBack: LoginContract.FirebaseLoginCallback){

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                if (it.isSuccessful)
                {
                    loginCallBack.onResult("Giriş Yapıldı",true)
                }
                else
                {
                    loginCallBack.onResult(it.exception?.localizedMessage.toString(),false)
                }
            }
        }
    }




    fun kayitOl(email: String, password: String, mAuth: FirebaseAuth,loginCallBack: LoginContract.FirebaseLoginCallback){


        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        loginCallBack.onResult("Kayıt Yapıldı",true)
                    }
                     else
                    {
                        loginCallBack.onResult(it.exception?.localizedMessage.toString(),false)
                    }
                }
        }
    }


}