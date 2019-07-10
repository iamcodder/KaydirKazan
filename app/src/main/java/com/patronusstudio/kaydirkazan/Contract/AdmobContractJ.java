package com.patronusstudio.kaydirkazan.Contract;

import com.google.android.gms.ads.reward.RewardedVideoAd;

public interface AdmobContractJ {

    void videolu_reklam_yuklendi(RewardedVideoAd mRewardedVideoAd);

    void videolu_reklam_izlendi();

    void videolu_reklam_yuklenemedi(String message);

}
