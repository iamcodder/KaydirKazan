package com.nosignalapp.kaydirkazan.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.nosignalapp.kaydirkazan.Contract.LoginContract

class LoginActivityModel{


    fun girisYap(email: String, password: String, mAuth: FirebaseAuth,loginCallBack: LoginContract.FirebaseLoginCallback){

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                if (it.isSuccessful)
                {
                    loginCallBack.onLoginResult("Giriş Yapıldı",true)
                }
                else
                {
                    loginCallBack.onLoginResult(it.exception?.localizedMessage.toString(),false)
                }
            }
        }
        else{
            loginCallBack.onLoginResult("Hatalı alanları düzeltin",false)
        }
    }

    fun kayitOl(email: String, password: String, mAuth: FirebaseAuth,loginCallBack: LoginContract.FirebaseLoginCallback){

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        dbyeProfiliYaz(email,mAuth,loginCallBack)
                    }
                     else
                    {
                        loginCallBack.onRegisterResult(it.exception?.localizedMessage.toString(),false)
                    }
                }
            }
        else{
            loginCallBack.onRegisterResult("Hatalı alanları düzeltin",false)
        }
    }

    fun dbyeProfiliYaz(email: String,mAuth: FirebaseAuth,loginCallBack: LoginContract.FirebaseLoginCallback){

        if(mAuth.currentUser!=null) {

            val fUser=mAuth.currentUser

            val uuid: String = fUser!!.uid
            val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("email").setValue(email)
            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("yuksekPuan").setValue("0")

            loginCallBack.onRegisterResult("Kayıt Yapıldı",true)
        }
    }
}