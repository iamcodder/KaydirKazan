package com.patronusstudio.kaydirkazan.Model

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.patronusstudio.kaydirkazan.Activity.HomeActivity
import com.patronusstudio.kaydirkazan.Contract.LoginContract

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
            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("cevaplananSoruSayisi").setValue("0")
            mDatabase.reference.child("oyun").child("kullanicilar").child(uuid).child("uuid").setValue(uuid)

            loginCallBack.onRegisterResult("Kayıt Yapıldı",true)
        }
    }

    fun sifremiUnuttum(email: String,mAuth: FirebaseAuth,loginCallBack: LoginContract.FirebaseLoginCallback){

        if(email!=""){
            mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    loginCallBack.onPasswordResetResult("Emailinizi kontrol edin")
            }
                .addOnFailureListener {
                    loginCallBack.onPasswordResetResult(it.localizedMessage.toString())
                }

        }
        else{
            loginCallBack.onPasswordResetResult("Email girin")
        }
    }

    fun googleGirişi(acct: GoogleSignInAccount, auth: FirebaseAuth,loginCallBack: LoginContract.FirebaseLoginCallback){

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                loginCallBack.onLoginResultWithGoogle("Tebrikler",true)
            }
            .addOnFailureListener {
                loginCallBack.onLoginResult(it.localizedMessage,false)
            }
    }
}