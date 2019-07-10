package com.patronusstudio.kaydirkazan.Activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.patronusstudio.kaydirkazan.Constant.OyunIsleviJ;
import com.patronusstudio.kaydirkazan.Contract.HomeContractJ;
import com.patronusstudio.kaydirkazan.Model.IFirebaseDatabaseJ;
import com.patronusstudio.kaydirkazan.Model.userModelJ;
import com.patronusstudio.kaydirkazan.Presenter.HomeActivityPresenterJ;
import com.patronusstudio.kaydirkazan.R;
import maes.tech.intentanim.CustomIntent;

public class HomeActivityJ extends AppCompatActivity implements HomeContractJ.View {

    private HomeActivityPresenterJ presenter;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private userModelJ mKullanici;
    private Intent intent;
    private Bundle gelenBundle;
    private int cikis_sayisi=0;
    private AnimationDrawable animation_drawable;
    private ConstraintLayout constraintLayout;
    private TextView txt_puan,txt_siralama;

    private Button btn_oyna,btn_uygulama_hakkinda,btn_cikis_yap;

    private LottieAnimationView home_loading,lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        OyunIsleviJ.KAYDIRMA_YAPILABILIR=true;
        mAuth=FirebaseAuth.getInstance();

        MobileAds.initialize(this, "ca-app-pub-1818679104699845~3155151657");

        presenter=new HomeActivityPresenterJ(new IFirebaseDatabaseJ());
        presenter.setView(this);
        presenter.created();
        presenter.fetchData();
    }


    @Override
    public void bindViews() {

        if(mAuth.getCurrentUser()!=null){
            mUser=mAuth.getCurrentUser();
            mKullanici=new userModelJ();
        }

        btn_oyna=findViewById(R.id.button_oyna);
        btn_uygulama_hakkinda=findViewById(R.id.button_uygulama_hakkinda);
        btn_cikis_yap=findViewById(R.id.button_cikis_yap);

        txt_puan=findViewById(R.id.activity_home_puan);
        txt_siralama=findViewById(R.id.activity_home_siralama);

        home_loading=findViewById(R.id.activity_home_loading);
        lottieAnimationView=findViewById(R.id.lottieAnimationView2);

        constraintLayout=findViewById(R.id.activity_home_constraint);
        animation_drawable= (AnimationDrawable) constraintLayout.getBackground();
        animation_drawable.setEnterFadeDuration(1000);
        animation_drawable.setExitFadeDuration(2000);
        animation_drawable.start();
    }

    @Override
    public void clickControl() {

        btn_oyna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startGameButton();
            }
        });

        btn_uygulama_hakkinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),UygulamaHakkindaJ.class);
                intent.putExtra("kullanıcı bilgisi", mKullanici);
                startActivity(intent);
                CustomIntent.customType(getApplicationContext(), "left-to-right");
            }
        });

        btn_cikis_yap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.logineGit();
            }
        });

    }

    @Override
    public void showPuan(userModelJ user) {

        txt_puan.setText("Skor : "+user.getYuksekPuan());
        this.mKullanici=user;

    }

    @Override
    public void showSort(int seninSiran, int toplamSira) {

        txt_siralama.setText("Sıralaman : "+seninSiran);

    }

    @Override
    public void loadingShow() {

        home_loading.setVisibility(View.VISIBLE);
        home_loading.playAnimation();

    }

    @Override
    public void hideLoadingShow() {

        home_loading.setVisibility(View.INVISIBLE);
        home_loading.cancelAnimation();

    }

    @Override
    public void startGame() {

        intent=new Intent(this,SoruActivityJ.class);
        intent.putExtra("kullanıcı bilgisi", this.mKullanici);
        startActivity(intent);
        CustomIntent.customType(this, "left-to-right");
    }

    @Override
    public void startLogin() {

        intent=new Intent(this,LoginActivityJ.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        mAuth.signOut();
        CustomIntent.customType(this, "right-to-left");

    }

    @Override
    public void profileYok() {
        presenter.kullanici_cekilemedi_dbde_yok();
    }

    @Override
    public void mesajGoster(String mesaj) {
        Toast.makeText(this,mesaj, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (cikis_sayisi == 1) {
            cikis_sayisi = 0;
            finishAffinity();
            System.exit(0);
        } else {
            Toast.makeText(this, "Çıkmak için bir kere daha basın", Toast.LENGTH_SHORT).show();
            cikis_sayisi++;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        lottieAnimationView.pauseAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lottieAnimationView.playAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lottieAnimationView.pauseAnimation();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lottieAnimationView.playAnimation();
    }
}
