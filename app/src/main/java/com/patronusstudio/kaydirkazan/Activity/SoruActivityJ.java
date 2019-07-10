package com.patronusstudio.kaydirkazan.Activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.patronusstudio.kaydirkazan.Constant.OyunIsleviJ;
import com.patronusstudio.kaydirkazan.Contract.SoruContractJ;
import com.patronusstudio.kaydirkazan.Model.IFirebaseDatabaseJ;
import com.patronusstudio.kaydirkazan.Model.SoruActivityAdapterJ;
import com.patronusstudio.kaydirkazan.Model.soruModelJ;
import com.patronusstudio.kaydirkazan.Model.userModelJ;
import com.patronusstudio.kaydirkazan.Presenter.SoruActivityPresenterJ;
import com.patronusstudio.kaydirkazan.R;
import com.yuyakaido.android.cardstackview.*;
import maes.tech.intentanim.CustomIntent;

import java.util.ArrayList;

public class SoruActivityJ extends AppCompatActivity implements SoruContractJ.View, CardStackListener {

    private SoruActivityPresenterJ presenter;
    private userModelJ mKullanici;
    private CardStackLayoutManager cardStackLayoutManager;
    private SoruActivityAdapterJ adapter;
    private ArrayList<soruModelJ> liste;
    private SwipeAnimationSetting swipeAnimationSetting;
    private MediaPlayer mediaPlayer;
    private Bundle gelen_bundle;
    private int ekrandaki_kart_konumu=0, dogru_sayisi = 0;
    private boolean bomba_sesi = false;
    private int TOPLAM_SURE = 16000;

    private Intent intent;

    private AnimationDrawable animation_drawable;

    private TextView txt_rekor, txt_puan;

    private ConstraintLayout constraintLayout;
    private CardStackView cardStackView;

    private CountDownTimer timer;

    private LottieAnimationView lottie_bomba, lottie_cevap, lottie_infinity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru);

        presenter = new SoruActivityPresenterJ(new IFirebaseDatabaseJ());
        presenter.setView(this);
        presenter.created();

    }

    @Override
    public void bindViews() {

        gelen_bundle = getIntent().getExtras();

        if (gelen_bundle != null) {
            mKullanici = (userModelJ) gelen_bundle.getSerializable("kullanıcı bilgisi");
        }

        txt_rekor = findViewById(R.id.activity_soru_rekor);
        txt_puan = findViewById(R.id.activity_soru_puan_PUAN);
        constraintLayout = findViewById(R.id.activity_soru_constraint);

        txt_rekor.setText("Rekor : " + mKullanici.getYuksekPuan());

        animation_drawable = (AnimationDrawable) constraintLayout.getBackground();
        animation_drawable.setEnterFadeDuration(1000);
        animation_drawable.setExitFadeDuration(2000);
        animation_drawable.start();

        liste = new ArrayList<>();
        mediaPlayer = MediaPlayer.create(this, R.raw.lose);

        cardStackView = findViewById(R.id.activity_soru_cardStackView);

        lottie_bomba = findViewById(R.id.activity_soru_bomba);
        lottie_cevap = findViewById(R.id.activity_soru_animasyon_sonucu);
        lottie_infinity = findViewById(R.id.activity_soru_loading_infinity_bar);

        timer = new CountDownTimer(TOPLAM_SURE, 1000) {
            @Override
            public void onTick(long l) {
                if (TOPLAM_SURE / 1000 <= 5 && !bomba_sesi) {
                    presenter.sesiOynat(R.raw.timer);
                }
            }

            @Override
            public void onFinish() {
                presenter.zamanTukendi();
            }
        };
    }

    @Override
    public void cardTasarimi() {
        //kaydırmadaki bekleme süresi..sayı ne kadar azsa o kadar hızlı oluyor
        swipeAnimationSetting = new SwipeAnimationSetting.Builder()
                .setDuration(1500)
                .setInterpolator(new AccelerateInterpolator())
                .build();

        cardStackLayoutManager.setSwipeAnimationSetting(swipeAnimationSetting);

        //arkada gözükmesini istediğimiz eleman sayısı
        cardStackLayoutManager.setVisibleCount(1);

        //kaydırırken kaç derecelik dönme olacağını yazıyoruz
        cardStackLayoutManager.setMaxDegree(90f);

        //kaydırırken ne kadarlık kaydırmanın aktif olacağını yazıyrouz
        cardStackLayoutManager.setSwipeThreshold(0.2f);

        //dikeyde kaydırılmayacağını , yatayda kaydırma olacağını belirtiyoruz
        cardStackLayoutManager.setCanScrollVertical(false);
        cardStackLayoutManager.setCanScrollHorizontal(true);

    }

    @Override
    public void recyclerSetle(ArrayList<soruModelJ> soruListesi) {

        this.liste = soruListesi;

        cardStackLayoutManager = new CardStackLayoutManager(this, this);
        adapter = new SoruActivityAdapterJ(liste, this);
        cardStackView.setLayoutManager(cardStackLayoutManager);
        cardStackView.setAdapter(adapter);

    }

    @Override
    public void trueAnswerNumber(int dogruSayisi) {
        txt_puan.setText("Skor : " + dogruSayisi);
        mediaPlayer.stop();
        bomba_sesi = false;
    }

    @Override
    public void progressShow() {
        lottie_infinity.setVisibility(View.VISIBLE);
        lottie_infinity.playAnimation();
    }

    @Override
    public void progressHide() {
        lottie_infinity.setVisibility(View.GONE);
        lottie_infinity.cancelAnimation();
    }

    @Override
    public void gameOver() {
        mediaPlayer.stop();
        bomba_sesi = false;
        OyunIsleviJ.KAYDIRMA_YAPILABILIR = false;
        intent = new Intent(this, GameOverActivityJ.class);
        intent.putExtra("dogruSayisi", dogru_sayisi);
        intent.putExtra("kullanici", mKullanici);
        intent.putExtra("soru", liste.get(ekrandaki_kart_konumu));
        CustomIntent.customType(this, "up-to-bottom");
        startActivity(intent);
    }

    @Override
    public void finishTime() {
        mediaPlayer.stop();
        bomba_sesi = false;
        OyunIsleviJ.KAYDIRMA_YAPILABILIR = false;
        intent = new Intent(this, GameOverActivityJ.class);
        intent.putExtra("dogruSayisi", dogru_sayisi);
        intent.putExtra("kullanici", mKullanici);
        intent.putExtra("soru", liste.get(ekrandaki_kart_konumu));
        CustomIntent.customType(this, "up-to-bottom");
        startActivity(intent);
    }

    @Override
    public void startTimer() {
        timer.start();
        lottie_bomba.playAnimation();

    }

    @Override
    public void resetTimer() {
        timer.cancel();
        timer.start();
        lottie_bomba.cancelAnimation();
        lottie_bomba.playAnimation();
    }

    @Override
    public void stopTimer() {
        timer.cancel();
        lottie_bomba.cancelAnimation();
    }

    @Override
    public void startTrueAnim() {

        lottie_cevap.setAnimation(R.raw.trueanim);
        lottie_cevap.setVisibility(View.VISIBLE);
        lottie_cevap.playAnimation();
        lottie_cevap.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                lottie_cevap.setVisibility(View.GONE);
                lottie_cevap.pauseAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    @Override
    public void startFalseAnim() {
        lottie_cevap.setAnimation(R.raw.falseanim);
        lottie_cevap.setVisibility(View.VISIBLE);
        lottie_cevap.playAnimation();
        lottie_cevap.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                lottie_cevap.setVisibility(View.GONE);
                lottie_cevap.pauseAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void sesiOynat(int ses) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(this, ses);
            mediaPlayer.start();
            bomba_sesi = true;
        } else {
            mediaPlayer = MediaPlayer.create(this, ses);
            mediaPlayer.start();
            bomba_sesi = true;
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    //kartın kaydırılma oranı
    @Override
    public void onCardSwiped(Direction direction) {

        if (direction != null && OyunIsleviJ.KAYDIRMA_YAPILABILIR) {

            switch (direction.name()) {

                case "Right":
                    if (liste.get(ekrandaki_kart_konumu).getSagCevap().equals(liste.get(ekrandaki_kart_konumu).getDogruCevap())) {
                        dogru_sayisi++;
                        presenter.dogruCevap(this.dogru_sayisi);
                    } else {
                        presenter.yanlisCevap();
                    }
                    break;
                case "Left":
                    if (liste.get(ekrandaki_kart_konumu).getSolCevap().equals(liste.get(ekrandaki_kart_konumu).getDogruCevap())) {
                        dogru_sayisi++;
                        presenter.dogruCevap(dogru_sayisi);
                    } else {
                        presenter.yanlisCevap();
                    }
                    break;
            }

        }

    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    //o an ekranda görünen kart
    @Override
    public void onCardAppeared(View view, int position) {
        ekrandaki_kart_konumu=position;
    }

    //geçilmiş olan kart
    @Override
    public void onCardDisappeared(View view, int position) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        lottie_infinity.cancelAnimation();
        timer.cancel();
        lottie_bomba.cancelAnimation();
        mediaPlayer.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lottie_infinity.cancelAnimation();
        timer.cancel();
        lottie_bomba.cancelAnimation();
        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.start();
        lottie_bomba.playAnimation();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        timer.start();
        lottie_bomba.playAnimation();
    }
}
