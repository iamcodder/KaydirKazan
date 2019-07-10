package com.patronusstudio.kaydirkazan.Model;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.FirebaseDatabase;
import com.patronusstudio.kaydirkazan.Contract.LoginContractJ;

import java.util.Objects;

public class LoginActivityModelJ {


    public void girisYap(String email, String password, FirebaseAuth mAuth, final LoginContractJ.FirebaseLoginCallback loginCallBack){

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                loginCallBack.onLoginResult("Giriş Yapıldı", true);
                            } else {
                                loginCallBack.onLoginResult(Objects.requireNonNull(task.getException()).getLocalizedMessage(), false);
                            }

                        }
                    });
        }
        else{
                loginCallBack.onLoginResult("Hatalı alanları düzeltin",false);
            }

    }

    public void kayitOl(final String email, String password, final FirebaseAuth mAuth, final LoginContractJ.FirebaseLoginCallback loginCallBack){

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                dbyeProfiliYaz(email,mAuth,loginCallBack);
                            }
                            else
                            {
                                loginCallBack.onRegisterResult(Objects.requireNonNull(task.getException()).getLocalizedMessage(),false);
                            }
                        }
                    });
        }
        else{
            loginCallBack.onRegisterResult("Hatalı alanları düzeltin",false);
        }
    }

    public void dbyeProfiliYaz(String email,FirebaseAuth mAuth,LoginContractJ.FirebaseLoginCallback loginCallBack){

        if(mAuth.getCurrentUser()!=null) {

            FirebaseUser fUser=mAuth.getCurrentUser();

            String uuid = fUser.getUid();
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

            mDatabase.getReference().child("oyun").child("kullanicilar").child(uuid).child("email").setValue(email);
            mDatabase.getReference().child("oyun").child("kullanicilar").child(uuid).child("yuksekPuan").setValue("0");
            mDatabase.getReference().child("oyun").child("kullanicilar").child(uuid).child("cevaplananSoruSayisi").setValue("0");
            mDatabase.getReference().child("oyun").child("kullanicilar").child(uuid).child("uuid").setValue(uuid);

            loginCallBack.onRegisterResult("Kayıt Yapıldı",true);
        }
    }

    public void sifremiUnuttum(String email, FirebaseAuth mAuth, final LoginContractJ.FirebaseLoginCallback loginCallBack ){

        if(!email.equals("")){
            mAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            loginCallBack.onPasswordResetResult("Emailinizi kontrol edin");
                        }
                    })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginCallBack.onPasswordResetResult(e.getLocalizedMessage());
                    }
                });

        }
        else{
            loginCallBack.onPasswordResetResult("Email girin");
        }
    }

    public void  googleGirişi(GoogleSignInAccount acct, FirebaseAuth auth, final LoginContractJ.FirebaseLoginCallback loginCallBack){

        GoogleAuthCredential credential = (GoogleAuthCredential) GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        loginCallBack.onLoginResultWithGoogle("Tebrikler",true);
                    }
                })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Sülo ",e.getLocalizedMessage());
                    loginCallBack.onLoginResult(e.getLocalizedMessage(),false);
                }
            }) ;
    }

}
