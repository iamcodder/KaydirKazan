package com.patronusstudio.kaydirkazan.Contract;

import com.google.android.gms.ads.reward.RewardedVideoAd;

public interface GameOverContractJ {

    interface View{

        void bindViews();

        void clickControl();

        void kontrolEt();

        void toastYazdir(String message);

        void videoluReklamiGoster(RewardedVideoAd mRewardedVideoAd);

        void videoluReklamIzlendi();

        void videoluReklamYuklenemedi(String message);

        void sesiOynat(int ses);


    }

    interface Presenter{

        void setView(View view);

        void created();

        void rekorKirildi(int dogruSayisi);

        void cevaplananSoruSayisiniArttir(int soruSayisi);

        void soruHatali(String soru);

        void reklam_yukle();

        void sesiOynat(int ses);

    }


}
