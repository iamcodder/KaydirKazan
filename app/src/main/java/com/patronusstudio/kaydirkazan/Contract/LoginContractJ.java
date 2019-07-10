package com.patronusstudio.kaydirkazan.Contract;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public interface LoginContractJ {

    interface View {

        void bindViews();

        void clicked();

        void configureGoogleSignIn();

        void signInWithGoogle();

        void progressBarActive();

        void progressBarPassive();

        void showToast(String message);

        void navigationHome();

        void navigationHomeWithGoogle();

        void loginError();

    }

    interface Presenter {

        void setView(View view);

        void created();

        void buttonLoginClicked(String email,String password, FirebaseAuth auth);

        void buttonRegisterClicked(String email,String password,FirebaseAuth auth);

        void buttonForgetPasswordClicked(String email,FirebaseAuth auth);

        void buttonGoogleSignInClicked();

        void googleSignInDoing(GoogleSignInAccount acct,FirebaseAuth auth);

    }

    interface FirebaseLoginCallback{

        void onLoginResult(String message,Boolean isLogin);

        void onLoginResultWithGoogle(String message,Boolean isLogin);

        void onRegisterResult(String message,Boolean isRegister);

        void onPasswordResetResult(String message);
    }

}
