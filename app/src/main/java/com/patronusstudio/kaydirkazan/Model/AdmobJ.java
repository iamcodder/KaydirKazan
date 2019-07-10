package com.patronusstudio.kaydirkazan.Model;

import android.content.Context;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.patronusstudio.kaydirkazan.Contract.AdmobContractJ;
import com.patronusstudio.kaydirkazan.Presenter.GameOverPresenterJ;

public class AdmobJ implements RewardedVideoAdListener {

    private Context mContext;
    private AdmobContractJ admob_sonuc;
    private RewardedVideoAd mRewardedVideoAd;

    public AdmobJ(Context mContext){
        this.mContext=mContext;
    }

    public void initialize(GameOverPresenterJ admob_sonuc){
        this.admob_sonuc=admob_sonuc;
        mRewardedVideoAd=MobileAds.getRewardedVideoAdInstance(mContext);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        if(!mRewardedVideoAd.isLoaded()){
            mRewardedVideoAd.loadAd("ca-app-pub-1818679104699845/8480542597",
                    new AdRequest.Builder().addTestDevice("F17BD0906D7EAE0C87C14E637D73D52C").build());

        }
    }

    public void odullu_reklami_yukle(){
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(
                    "ca-app-pub-1818679104699845/8480542597",
                    new AdRequest.Builder().addTestDevice("F17BD0906D7EAE0C87C14E637D73D52C").build()
            );
            reklamiGoster();
        }
        else {
            reklamiGoster();
        }
    }

    private void reklamiGoster() {
        admob_sonuc.videolu_reklam_yuklendi(mRewardedVideoAd);
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        admob_sonuc.videolu_reklam_izlendi();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        admob_sonuc.videolu_reklam_yuklenemedi("Error : "+i);
    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}
