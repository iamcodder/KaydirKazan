package com.patronusstudio.kaydirkazan.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.patronusstudio.kaydirkazan.Contract.LoginContractJ;
import com.patronusstudio.kaydirkazan.Model.LoginActivityModelJ;
import com.patronusstudio.kaydirkazan.Presenter.LoginActivityPresenterJ;
import com.patronusstudio.kaydirkazan.R;
import maes.tech.intentanim.CustomIntent;

public class LoginActivityJ extends AppCompatActivity implements LoginContractJ.View {

    private LoginActivityPresenterJ presenter;
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN=1;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleSignInAccount mAccount;
    private SignInButton btn_google;

    private Button btn_login,btn_register,btn_forgotpass;
    private EditText edx_email,edx_password;

    private ProgressBar login_progress;

    private LottieAnimationView login_animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter=new LoginActivityPresenterJ(new LoginActivityModelJ());
        presenter.setView(this);
        presenter.created();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(this,HomeActivityJ.class));
            finish();
        }
    }

    @Override
    public void bindViews() {

        btn_login=findViewById(R.id.login_activity_login_button);
        btn_register=findViewById(R.id.login_activity_register_button);
        btn_forgotpass=findViewById(R.id.login_activity_sifremi_unuttum);
        btn_google=(SignInButton) findViewById(R.id.sign_in_button);

        edx_email=findViewById(R.id.login_email);
        edx_password=findViewById(R.id.login_password);

        login_progress=findViewById(R.id.activity_login_progress);

        login_animation=findViewById(R.id.login_animation);

    }

    @Override
    public void clicked() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buttonLoginClicked(edx_email.getText().toString(),edx_password.getText().toString(),mAuth);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buttonRegisterClicked(edx_email.getText().toString(),edx_password.getText().toString(),mAuth);
            }
        });

        btn_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buttonForgetPasswordClicked(edx_email.getText().toString(),mAuth);
            }
        });

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buttonGoogleSignInClicked();
            }
        });
    }

    @Override
    public void configureGoogleSignIn() {

        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);


    }

    @Override
    public void signInWithGoogle() {
        login_progress.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    this.mAccount=account;
                    presenter.googleSignInDoing(account,mAuth);
                }
            } catch (Exception e) {
                Log.d("Sülo",e.getLocalizedMessage());
                Log.d("Sülo",e.getMessage().toString());
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void progressBarActive() {
        login_animation.setVisibility(View.VISIBLE);
        login_animation.playAnimation();
    }

    @Override
    public void progressBarPassive() {
        login_animation.setVisibility(View.GONE);
        login_animation.cancelAnimation();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigationHome() {

        startActivity(new Intent(this,HomeActivityJ.class));
        CustomIntent.customType(this, "left-to-right");
    }

    @Override
    public void navigationHomeWithGoogle() {
        login_progress.setVisibility(View.GONE);

        Intent intent=new Intent(this, HomeActivityJ.class);

        intent.putExtra("kullanici","baba");
        startActivity(intent);
        CustomIntent.customType(this, "left-to-right");
    }

    @Override
    public void loginError() {
        login_progress.setVisibility(View.GONE);
        login_animation.cancelAnimation();
        login_animation.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}
