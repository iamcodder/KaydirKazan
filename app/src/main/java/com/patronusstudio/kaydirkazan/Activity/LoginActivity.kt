package com.patronusstudio.kaydirkazan.Activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.patronusstudio.kaydirkazan.Contract.LoginContract
import com.patronusstudio.kaydirkazan.Model.LoginActivityModel
import com.patronusstudio.kaydirkazan.Presenter.LoginActivityPresenter
import com.patronusstudio.kaydirkazan.R
import kotlinx.android.synthetic.main.activity_login.*
import maes.tech.intentanim.CustomIntent
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var loginActivityPresenter: LoginActivityPresenter
    lateinit var mAuth: FirebaseAuth

    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    lateinit var mAccount:GoogleSignInAccount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginActivityPresenter = LoginActivityPresenter(LoginActivityModel())
        loginActivityPresenter.setView(this)
        loginActivityPresenter.created()
    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser!=null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    override fun bindViews() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

    }

    override fun clicked() {

        login_activity_login_button.setOnClickListener {
            loginActivityPresenter.buttonLoginClicked(
                login_email.text.toString(),
                login_password.text.toString(),
                mAuth
            )
        }

        login_activity_register_button.setOnClickListener {
            loginActivityPresenter.buttonRegisterClicked(
                login_email.text.toString(),
                login_password.text.toString(),
                mAuth
            )
        }


        login_activity_sifremi_unuttum.setOnClickListener {
            loginActivityPresenter.buttonForgetPasswordClicked(login_email.text.toString(),mAuth)
        }

        sign_in_button.setOnClickListener{
            loginActivityPresenter.buttonGoogleSignInClicked()
        }
    }

    override fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    override fun signInWithGoogle() {
        activity_login_progress.visibility=View.VISIBLE
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    this.mAccount=account
                    loginActivityPresenter.googleSignInDoing(account,mAuth)
                }
            } catch (e: ApiException) {

                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun progressBarActive() {

        login_animation.visibility=View.VISIBLE
        login_animation.playAnimation()

    }

    override fun progressBarPassive() {
        login_animation.visibility=View.INVISIBLE
        login_animation.cancelAnimation()
    }

    override fun showToast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun navigationHome() {
        intent=Intent(this, HomeActivity::class.java)
        startActivity(intent)
        CustomIntent.customType(this, "left-to-right")
    }

    override fun navigationHomeWithGoogle() {
        activity_login_progress.visibility=View.GONE
        intent=Intent(this, HomeActivity::class.java)
        intent.putExtra("kullanici","baba")
        startActivity(intent)
        CustomIntent.customType(this, "left-to-right")
    }

    override fun onBackPressed() {
        finishAffinity()
        System.exit(0)
    }
}
