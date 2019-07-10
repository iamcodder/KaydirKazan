package com.patronusstudio.kaydirkazan.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.patronusstudio.kaydirkazan.Constant.OyunIsleviJ;
import com.patronusstudio.kaydirkazan.Contract.GameOverContractJ;
import com.patronusstudio.kaydirkazan.Model.AdmobJ;
import com.patronusstudio.kaydirkazan.Model.IFirebaseDatabaseJ;
import com.patronusstudio.kaydirkazan.Model.soruModelJ;
import com.patronusstudio.kaydirkazan.Model.userModelJ;
import com.patronusstudio.kaydirkazan.Presenter.GameOverPresenterJ;
import com.patronusstudio.kaydirkazan.R;
import maes.tech.intentanim.CustomIntent;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class GameOverActivityJ extends AppCompatActivity implements GameOverContractJ.View {

    private Bundle gelen_bundle;
    private int dogru_sayisi,rekor,cevaplanan_soru_miktarı;
    private String dogru_cevap="";
    private String soru="";
    private Intent intent_home;
    private GameOverPresenterJ presenter;
    private userModelJ mKullanici;
    private soruModelJ mSoru;
    private MediaPlayer mediaPlayer;

    private Button btn_menuye_don,btn_devam_et,btn_hatali;

    private LottieAnimationView lottie_game_over;

    private TextView txt_dogru_sonuc;

    private CardView card_dogru_sonuc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        presenter=new GameOverPresenterJ(new IFirebaseDatabaseJ(),new AdmobJ(this));
        presenter.setView(this);
        presenter.created();
    }

    @Override
    public void bindViews() {

        gelen_bundle=getIntent().getExtras();
        if(gelen_bundle!=null){
            mKullanici= (userModelJ) gelen_bundle.getSerializable("kullanici");
            mSoru=(soruModelJ) gelen_bundle.getSerializable("soru");
            rekor=Integer.parseInt(mKullanici.getYuksekPuan());
            cevaplanan_soru_miktarı=Integer.parseInt(mKullanici.getCevaplananSoruSayisi());
            soru=mSoru.getSoru();
            dogru_cevap=mSoru.getDogruCevap();

            dogru_sayisi=gelen_bundle.getInt("dogruSayisi");

        }

        intent_home=new Intent(this,HomeActivityJ.class);
        mediaPlayer=MediaPlayer.create(this,R.raw.lose);

        btn_devam_et=findViewById(R.id.game_over_devam_et);
        btn_menuye_don=findViewById(R.id.game_over_menuye_don);
        btn_hatali=findViewById(R.id.game_over_cevap_hatali);

        lottie_game_over=findViewById(R.id.activity_gameOver_lottie);
        txt_dogru_sonuc=findViewById(R.id.activity_game_over_dogru_sonuc);
        card_dogru_sonuc=findViewById(R.id.activity_game_over_dogru_sonuc_cardView);

    }

    @Override
    public void clickControl() {

        btn_menuye_don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_home.setFlags(FLAG_ACTIVITY_NEW_TASK);
                CustomIntent.customType(getApplicationContext(), "right-to-left");
                startActivity(intent_home);
            }
        });

        btn_devam_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(OyunIsleviJ.IZLENMIS_REKLAM_SAYISI>3){
                    OyunIsleviJ.IZLENMIS_REKLAM_SAYISI=0;
                    Toast.makeText(getApplicationContext(), "Bu oyunda izlenecek reklam kalmadı :)", Toast.LENGTH_SHORT).show();
                    intent_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CustomIntent.customType(getApplicationContext(), "left-to-right");
                    startActivity(intent_home);
                }

                else {
                    Toast.makeText(getApplicationContext(), "Reklam yükleniyor...", Toast.LENGTH_LONG).show();
                    presenter.reklam_yukle();
                }

            }
        });

        btn_hatali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.soruHatali(soru);
            }
        });

    }

    @Override
    public void toastYazdir(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void videoluReklamiGoster(RewardedVideoAd mRewardedVideoAd) {

        mRewardedVideoAd.show();
        OyunIsleviJ.IZLENMIS_REKLAM_SAYISI++;

    }

    @Override
    public void videoluReklamIzlendi() {

        OyunIsleviJ.KAYDIRMA_YAPILABILIR=true;
        Toast.makeText(this, "Devam edebilirsiniz.", Toast.LENGTH_SHORT).show();
        CustomIntent.customType(this, "up-to-bottom");
        finish();
    }

    @Override
    public void videoluReklamYuklenemedi(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void sesiOynat(int ses) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), ses);
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), ses);
            mediaPlayer.start();
        }
    }

    @Override
    public void kontrolEt() {
        cevaplanan_soru_miktarı = cevaplanan_soru_miktarı + dogru_sayisi;
        cevaplanan_soru_miktarı++;
        presenter.cevaplananSoruSayisiniArttir(cevaplanan_soru_miktarı);

        if(dogru_cevap.equals("Malesef zaman doldu")){

            lottie_game_over.setAnimation(R.raw.error);
            txt_dogru_sonuc.setText(dogru_cevap);
            card_dogru_sonuc.setCardBackgroundColor(ContextCompat.getColor(this,R.color.purple_light));

        }


        else if(this.dogru_sayisi>this.rekor){

            lottie_game_over.setAnimation(R.raw.award);
            txt_dogru_sonuc.setText("\nYanlış cevap vermiş olsanda kendi rekorunu kırdın.\nDoğru cevap ${dogruCevap} olacaktı.\nYinede tebrikler\n");
            card_dogru_sonuc.setCardBackgroundColor(ContextCompat.getColor(this,R.color.turuncu));

            presenter.rekorKirildi(dogru_sayisi);
            presenter.sesiOynat(R.raw.win);

        }

        else{
            lottie_game_over.setAnimation(R.raw.error);
            txt_dogru_sonuc.setText("\nYanlış cevap verdin.\nDoğru cevap ${dogruCevap} olacaktı.\n");
            card_dogru_sonuc.setBackgroundColor(ContextCompat.getColor(this,R.color.kirmizi));
            presenter.sesiOynat(R.raw.lose);
        }

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
}
