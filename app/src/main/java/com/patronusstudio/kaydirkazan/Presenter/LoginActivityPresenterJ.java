package com.patronusstudio.kaydirkazan.Presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.patronusstudio.kaydirkazan.Contract.LoginContractJ;
import com.patronusstudio.kaydirkazan.Model.LoginActivityModelJ;

public class LoginActivityPresenterJ implements LoginContractJ.Presenter,
        LoginContractJ.FirebaseLoginCallback {

    private LoginActivityModelJ model;
    private LoginContractJ.View mView;

    public LoginActivityPresenterJ(LoginActivityModelJ model) {
        this.model = model;
    }

    @Override
    public void setView(@NotNull LoginContractJ.View view) {
        this.mView = view;
    }

    @Override
    public void created() {
        mView.bindViews();
        mView.clicked();

        mView.configureGoogleSignIn();
    }

    @Override
    public void buttonLoginClicked(@NotNull String email, @NotNull String password, @NotNull FirebaseAuth auth) {
        mView.progressBarActive();

        model.girisYap(email, password, auth,this);
    }

    @Override
    public void buttonForgetPasswordClicked(@NotNull String email, @NotNull FirebaseAuth auth) {
        mView.progressBarActive();
        model.sifremiUnuttum(email,auth,this);
    }

    @Override
    public void buttonGoogleSignInClicked() {
        mView.signInWithGoogle();
    }

    @Override
    public void googleSignInDoing(@NotNull GoogleSignInAccount acct, @NotNull FirebaseAuth auth) {
        model.googleGirişi(acct,auth,this);
    }

    @Override
    public void onPasswordResetResult(@NotNull String message) {
        mView.progressBarPassive();
        mView.showToast(message);
    }

    @Override
    public void buttonRegisterClicked(@NotNull String email, @NotNull String password, @NotNull FirebaseAuth auth) {
        mView.progressBarActive();
        model.kayitOl(email, password, auth,this);
    }

    @Override
    public void onLoginResult(String message, Boolean isLogin) {
        mView.progressBarPassive();
        mView.showToast(message);
        if(isLogin) mView.navigationHome();
    }

    @Override
    public void onLoginResultWithGoogle(String message, Boolean isLogin) {
        mView.progressBarPassive();

        mView.showToast(message);

        if(isLogin) mView.navigationHomeWithGoogle();
    }

    @Override
    public void onRegisterResult(String message, Boolean isRegister) {
        mView.progressBarPassive();

        mView.showToast(message);

        if (isRegister) mView.navigationHome();
    }

}
